package com.aait.aec.ui.result;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.detail_result_date)
    TextView result_date;

    @BindView(R.id.detail_result_instructor_value)
    TextView resultInstructor;

    @BindView(R.id.detail_result_type_value)
    TextView resultType;

    @BindView(R.id.detail_result_weight)
    TextView resultWeight;
    Uri photoURI;

    ArrayList<Student> students = new ArrayList<>();
    List<MultipartBody.Part> parts = new ArrayList<>();
    private Uri filePath;

    public static Intent getStartIntent(Context context, Exam exam) {
        mExam = exam;
        Intent intent = new Intent(context, ResultActivity.class);
        return intent;
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
            result_date.setText(mExam.getCreatedDate());
        }

        if (mExam.getExamWeight() != null) {
            resultWeight.setText(mExam.getExamWeight());
        }

        if (mExam.getExamType() != null) {
            resultType.setText(mExam.getExamType());
        }

        // todo find a way for adding the inst name later - or from db
        if (mExam.getUserId() != null) {
            resultInstructor.setText(mExam.getUserId());
        }

        setUpRecyclerView();
        prepareStudents();

        //need to check the permissions
        checkFilePermissions();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Uri> fileUris = new ArrayList<>();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null) {
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

                    mPresenter.onUploadClicked("images", parts);
                }
            } else { // for single image
                Uri singleUri = data.getData();
                photoURI = singleUri;
                fileUris.add(photoURI);
                String filename = getFileName(photoURI);
                Log.e("Single ", filename + " " + fileUris.size());

                parts.add(prepareFilePart(getPath(this, singleUri)));
                mPresenter.onUploadClicked("images", parts);
            }
        }
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

    @NonNull
    private MultipartBody.Part prepareFilePart(String fileUri) {

        File file = new File(fileUri);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("image", file.getName(), requestFile);





        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
//        File file = new File(getFileName(fileUri));

        // create RequestBody instance from file
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(fileUri)),
//                        file
//                );

//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

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

    private void prepareStudents() {

        Student student1 = new Student(1, "Kidus Mamuye", "ATR/6157/07", 10.2);
        Student student2 = new Student(2, "Nathaniel Awoke", "ATR/6157/07", 10.2);
        Student student3 = new Student(3, "Solomon Shiferaw", "ATR/6157/07", 10.2);
        Student student4 = new Student(4, "Samuel Mussie", "ATR/6157/07", 10.2);
        Student student5 = new Student(5, "Yehualashet Abebe", "ATR/6157/07", 10.2);
        Student student6 = new Student(6, "Robel Tullu", "ATR/6157/07", 10.2);
        Student student7 = new Student(7, "Danawit Shimels", "ATR/6157/07", 10.2);
        Student student8 = new Student(8, "Selam Kiros", "ATR/6157/07", 10.2);
        Student student9 = new Student(9, "Yosef Abate", "ATR/6157/07", 10.2);
        Student student10 = new Student(10, "Teshale Tesema", "ATR/6157/07", 10.2);
        Student student11 = new Student(11, "Henok Bahiru", "ATR/6157/07", 10.2);
        Student student12 = new Student(12, "Dereje Matiwos", "ATR/6157/07", 10.2);
        Student student13 = new Student(13, "Temam Teshale", "ATR/6157/07", 10.2);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        students.add(student11);
        students.add(student12);
        students.add(student13);

        mAdapter.addItems(students);
    }

    @Override
    public void onItemClicked(Student student) {
        CommonUtils.toast(student.getName());
    }
}
