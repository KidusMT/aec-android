package com.aait.aec.ui.create;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.network.model.exam.Answers;
import com.aait.aec.ui.addAnswerDialog.AddAnswerDialog;
import com.aait.aec.ui.addAnswerDialog.AnswerDialogCommunicator;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateExamActivity extends BaseActivity implements CreateExamMvpView, AnswerDialogCommunicator {

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date;

    @Inject
    CreateExamMvpPresenter<CreateExamMvpView> mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.exam_date_calendar)
    ImageView ivCalendar;

    @BindView(R.id.create_assessment_date)
    EditText examDate;

    @BindView(R.id.et_weight)
    EditText examWeight;

    @BindView(R.id.exam_course_name)
    Spinner examNameSpinner;

    @BindView(R.id.exam_type)
    Spinner examTypeSpinner;

    @BindView(R.id.et_number_of_question)
    EditText numberOfQuestion;
    List<Student> students = new ArrayList<>();
    private List<Answers> answerSet = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CreateExamActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CreateExamActivity.this);

        CommonUtils.hideKeyboard(this);

        setUp();

        examDate.setOnClickListener(v -> new DatePickerDialog(CreateExamActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        ivCalendar.setOnClickListener(v -> new DatePickerDialog(CreateExamActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
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

        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        examDate.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.btn_create)
    void onCreateClicked() {
        String examName = examNameSpinner.getSelectedItem().toString();
        String examType = examTypeSpinner.getSelectedItem().toString();

        // todo show messages for different scenarios
        if (answerSet.size() > 0 && students.size() > 0)
            mPresenter.createExam(examName, examType, Integer.parseInt(examWeight.getText().toString()), answerSet, students);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.lbl_attachment)
    void onAddAnswerClicked() {
        // todo can add more constraints later. enough for now
        int nOfQ = TextUtils.isEmpty(numberOfQuestion.getText()) ? 0 :
                Integer.parseInt(String.valueOf(numberOfQuestion.getText()));
        if (nOfQ > 0)
            AddAnswerDialog.newInstance(nOfQ).show(getSupportFragmentManager(), "");
        else {
            CommonUtils.toast("Please insert Number of questions before adding answers.");
            numberOfQuestion.requestFocus();
        }
    }

    @Override
    public void submitAnswers(List<String> answers) {
        Answers answers1 = getAnswerSet(answers.subList(0, answers.size() / 2));
        Answers answers2 = getAnswerSet(answers.subList((answers.size() / 2) + 1, answers.size() - 1));
        answerSet.add(answers1);
        answerSet.add(answers2);
    }

    public Answers getAnswerSet(List<String> answers) {
        Answers ans = new Answers();
        for (int i = 0; i < answers.size(); i++) {
                if (i == 0)
                    ans.set0(answers.get(i));
                if (i == 1)
                    ans.set1(answers.get(i));
                if (i == 2)
                    ans.set2(answers.get(i));
                if (i == 3)
                    ans.set3(answers.get(i));
                if (i == 4)
                    ans.set4(answers.get(i));
                if (i == 5)
                    ans.set5(answers.get(i));
                if (i == 6)
                    ans.set6(answers.get(i));
                if (i == 7)
                    ans.set7(answers.get(i));
                if (i == 8)
                    ans.set8(answers.get(i));
                if (i == 9)
                    ans.set9(answers.get(i));
                if (i == 10)
                    ans.set10(answers.get(i));
                if (i == 11)
                    ans.set11(answers.get(i));
                if (i == 12)
                    ans.set12(answers.get(i));
                if (i == 13)
                    ans.set13(answers.get(i));
                if (i == 14)
                    ans.set14(answers.get(i));
        }

        return ans;
    }

    @Override
    public void updateExam(List<Student> students) {
        this.students = students;
    }
}
