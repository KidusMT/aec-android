package com.aait.aec.ui.result;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.correct.CorrectRequest;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.detail.ExamDetailDialog;
import com.aait.aec.utils.CommonUtils;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ResultActivity extends BaseActivity implements ResultMvpView, ResultAdapter.Callback {

    public static final String TAG = "ResultActivity";
    private static Exam mExam;
    private final int PICK_IMAGE_REQUEST = 10;
    @Inject
    ResultMvpPresenter<ResultMvpView> mPresenter;

    @Inject
    ResultAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.result_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.import_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_students_result)
    TextView tvNoStudents;

    @BindView(R.id.detail_result_date)
    TextView result_date;

    @BindView(R.id.detail_result_instructor_value)
    TextView resultInstructor;

    @BindView(R.id.detail_result_type_value)
    TextView resultType;

    @BindView(R.id.detail_result_weight_value)
    TextView resultWeight;
    Uri photoURI;
    ImageView mChartReport;
    List<MultipartBody.Part> parts = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    private Uri filePath;

    public static Intent getStartIntent(Context context, Exam exam) {
        mExam = exam;
        return new Intent(context, ResultActivity.class);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ResultActivity.this);

        mAdapter.setCallback(this);

        CommonUtils.hideKeyboard(this);

        setUp();

    }

    @Override
    public void showStudents(List<Student> students) {
        this.students = students;
        if (students.size() > 0) {
            if (tvNoStudents != null && tvNoStudents.getVisibility() == View.VISIBLE)
                tvNoStudents.setVisibility(View.GONE);
            if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addItems(students);
        } else {
            if (tvNoStudents != null && tvNoStudents.getVisibility() == View.GONE) {
                tvNoStudents.setVisibility(View.VISIBLE);
                tvNoStudents.setText(getText(R.string.no_student_items));
            }
            if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                mRecyclerView.setVisibility(View.GONE);
        }

        hideLoading();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

        if (mExam.getExamName() != null)
            mToolbar.setTitle(mExam.getExamName());
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (mExam.getCreatedDate() != null) {
            result_date.setText(String.valueOf(DateTimeUtils.formatWithStyle(mExam.getCreatedDate(), DateTimeStyle.MEDIUM)));
        }

        if (mExam.getExamWeight() != null) {
            resultWeight.setText(String.valueOf(mExam.getExamWeight()));
        }

        if (mExam.getExamType() != null) {
            resultType.setText(String.valueOf(mExam.getExamType()));
        }

        // todo find a way for adding the inst name later - or from db
        if (mExam.getUserId() != null) {
            resultInstructor.setText(String.valueOf(mExam.getUserId()));
        }

        setUpRecyclerView();

        //need to check the permissions
        checkFilePermissions();
    }

    @OnClick(R.id.iv_chart_report)
    void onReportClicked() {
        Log.e(TAG, "working working working");
//        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
        if (this.students != null)
            ExamDetailDialog.newInstance(this.students).show(getSupportFragmentManager(), "");
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_import, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.action_import) {
            chooseImage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int permissionCheck = 0;
                permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
                permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                if (permissionCheck != 0) {
                    this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
                }
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void chooseImage() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Log.e("chooseGallery Exception", String.valueOf(e.getMessage()));
        }
    }

    private void chooseImages() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle("Import Photo");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            } else if (options[item].equals("Choose from Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Uri> fileUris = new ArrayList<>();
        String containerName;
        if (mExam != null && !TextUtils.isEmpty(mExam.getExamName())) {
            containerName = mExam.getExamName().trim()//remove space at both ends
                    .toLowerCase()//changes the case to lower case
                    .replace(" ", "_");//replace the space with _ (under score)
        } else
            containerName = "images";// shouldn't be happening but for error handling todo find a better way

        CorrectRequest request = new CorrectRequest(containerName, mExam.getId());

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {// when uploading images
            filePath = data.getData();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                if (clipData.getItemCount() > 1) { // for multiple image
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        photoURI = uri;
                        fileUris.add(uri);
                        String filename = getFileName(uri);
                        Log.e("Multiple ", filename + " " + fileUris.size());

                        parts.add(prepareFilePart(getPath(this, uri)));
                    }
                    mExam.setContainer_name(containerName);
                    mPresenter.onUploadClicked(containerName, parts, request);
                }
            } else { // for single image
                Uri singleUri = data.getData();
                photoURI = singleUri;
                fileUris.add(photoURI);
                String filename = getFileName(photoURI);
                Log.e("Single ", filename + " " + fileUris.size());

                parts.add(prepareFilePart(getPath(this, singleUri)));
                mExam.setContainer_name(containerName);
                mPresenter.onUploadClicked(containerName, parts, request);
            }
        } else if (requestCode == 1) { // -----> when taking picture
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);
//                viewImage.setImageBitmap(bitmap);
                String path = android.os.Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String fileUri) {

        File file = new File(fileUri);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme() != null)
            if (uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
            }
        if (result == null) {
            result = uri.getPath();
            if (result != null) {
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;
    }

    @Override
    public void onItemClicked(Student student) {
        CommonUtils.toast(student.getName());
    }

}
