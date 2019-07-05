package com.aait.aec.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aait.aec.R;
import com.aait.aec.di.component.ActivityComponent;
import com.aait.aec.ui.addAnswerDialog.AnswerAdapter;
import com.aait.aec.ui.addAnswerDialog.AnswerDialogCommunicator;
import com.aait.aec.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamDetailDialog extends BaseDialog implements ExamDetailMvpView {

    @Inject
    ExamDetailMvpPresenter<ExamDetailMvpView> mPresenter;

    private String[] answerList;

    AnswerDialogCommunicator communicator;

    public static ExamDetailDialog newInstance(int numberOfQuestions) {
//        numOfQuest = numberOfQuestions;
        ExamDetailDialog fragment = new ExamDetailDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exam_detail, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
        }

        setUp(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (AnswerDialogCommunicator) context;
    }

    @OnClick(R.id.btn_dialog_cancel)
    void onCancelClicked() {
        mPresenter.onCancelClicked();
    }

    @OnClick(R.id.btn_dialog_decline)
    void onConfirmClicked() {
        mPresenter.onConfirmClicked();
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void openConfirmReturnDialog() {
        communicator.submitAnswers(new ArrayList<>(Arrays.asList(answerList)));
        dismiss();
    }

}
