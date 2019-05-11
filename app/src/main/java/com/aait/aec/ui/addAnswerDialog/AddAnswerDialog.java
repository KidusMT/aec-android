package com.aait.aec.ui.addAnswerDialog;

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
import com.aait.aec.ui.base.BaseDialog;
import com.aait.aec.ui.main.MainActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAnswerDialog extends BaseDialog implements AddAnswerMvpView, AnswerAdapter.Callback {

    private static int numOfQuest;

    @Inject
    AddAnswerMvpPresenter<AddAnswerMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.answer_recycler)
    RecyclerView mRecyclerView;

    @Inject
    AnswerAdapter mAdapter;


    private String[] answerList;

    public static AddAnswerDialog newInstance(int numberOfQuestions) {
        numOfQuest = numberOfQuestions;
        AddAnswerDialog fragment = new AddAnswerDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_insert_answer, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
        }

        setUp(view);

        return view;
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
        answerList = new String[numOfQuest];
        setUpRecyclerView();
        mAdapter.addItems(numOfQuest);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void openConfirmReturnDialog() {
        // todo has to update the list in the home activity
        // todo has to send the list of answers to the api when the api is ready
        dismiss();
        startActivity(MainActivity.getStartIntent(getBaseActivity()));
        getBaseActivity().finish();// finishing the parent activity's activity rather than opening a new one

    }

    @Override
    public void onItemClicked(int position, String answer) {
        //the position will help on updating the value when a new value is introduced
//        answerList.add(position, answer);
        answerList[position] = answer;
        Log.e("---->position", String.valueOf(position));
        Log.e("---->answer", answer);
    }
}
