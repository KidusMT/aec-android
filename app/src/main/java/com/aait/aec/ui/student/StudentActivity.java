package com.aait.aec.ui.student;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.studentEdit.EditStudentDialog;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.FileDialog;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StudentActivity extends BaseActivity implements StudentMvpView, StudentAdapter.Callback {

    public static final String TAG = "StudentActivity";

    @Inject
    StudentMvpPresenter<StudentMvpView> mPresenter;

    @Inject
    StudentAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    File file;
    ArrayList<String> pathHistory;
    int count = 0;
    List<Student> importedStudentList;
    ListView lvInternalStorage;

    @BindView(R.id.import_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.import_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_students)
    TextView tvNoStudents;
    FileDialog fileDialog;
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, StudentActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StudentActivity.this);
        mAdapter.setOnLongClick(this);

        CommonUtils.hideKeyboard(this);

        setUp();

    }

    @OnClick(R.id.btn_import)
    void onSaveClicked() {
        mPresenter.onSaveClicked(importedStudentList);
        hideLoading();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setUpRecyclerView();

        //need to check the permissions
        checkFilePermissions();

        importedStudentList = new ArrayList<>();

    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void loadStudentsFromExcelFile(String filePath) {
        showLoading();//loading
        readExcelData(filePath);
        showStudents(importedStudentList);
    }

    @Override
    public void showStudents(List<Student> students) {
        if (students.size() > 0) {
            if (tvNoStudents != null && tvNoStudents.getVisibility() == View.VISIBLE)
                tvNoStudents.setVisibility(View.GONE);
            if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addItems(students);
        } else {
            if (tvNoStudents != null && tvNoStudents.getVisibility() == View.GONE) {
                tvNoStudents.setVisibility(View.VISIBLE);
                tvNoStudents.setText("No student list found");
            }
            if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                mRecyclerView.setVisibility(View.GONE);
        }

        hideLoading();
    }

    /**
     * reads the excel file columns then rows. Stores data as ExcelUploadData object
     *
     * @return
     */
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outer loop, loops through rows
            for (int r = 0; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if (c > 2) {
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! ");
                        CommonUtils.toast("ERROR: Excel File Format is incorrect!");
                        break;
                    } else {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value).append(", ");
                    }
                }
                sb.append(":");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

            parseStringBuilder(sb);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage());
        }
    }

    /**
     * Returns the cell as a string from the excel file
     *
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = "" + numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage());
        }
        return value;
    }

    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder) {
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");

        //Add to the ArrayList<XYValue> row by row
        for (int i = 0; i < rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try {
//                double x = Double.parseDouble(columns[0]);
//                double y = Double.parseDouble(columns[1]);

                String x = String.valueOf(columns[0]);
                String y = String.valueOf(columns[1]);

                String cellInfo = "(x,y): (" + x + "," + y + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the importedStudentList ArrayList String name, String score, String stdId
                importedStudentList.add(new Student(x, String.valueOf(0), y));

//                mAdapter.addItems(importedStudentList);

            } catch (NumberFormatException e) {

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }

        }
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
            selectFile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectFile() {
        File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
        fileDialog = new FileDialog(this, mPath, ".xlsx");
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
            public void fileSelected(File file) {
//                CommonUtils.toast(String.format( "selected file %s" , file.getAbsolutePath()));
                Log.d(getClass().getName(), "selected file " + file.toString());
                loadStudentsFromExcelFile(file.getAbsolutePath());
            }
        });
        //fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
        //  public void directorySelected(File directory) {
        //      Log.d(getClass().getName(), "selected dir " + directory.toString());
        //  }
        //});
        //fileDialog.setSelectDirectoryOption(false);
        fileDialog.showDialog();
    }

    @Override
    public void onItemClicked(Student student) {
        EditStudentDialog.newInstance(student).show(getSupportFragmentManager(), "");
    }
}
