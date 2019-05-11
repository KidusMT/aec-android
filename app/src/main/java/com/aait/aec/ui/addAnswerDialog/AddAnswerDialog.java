package com.aait.aec.ui.addAnswerDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aait.aec.R;
import com.aait.aec.di.component.ActivityComponent;
import com.aait.aec.ui.base.BaseDialog;
import com.aait.aec.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAnswerDialog extends BaseDialog implements AddAnswerMvpView {

    @Inject
    AddAnswerMvpPresenter<AddAnswerMvpView> mPresenter;

    public static AddAnswerDialog newInstance() {
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

    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void openConfirmReturnDialog() {
        dismiss();
        startActivity(MainActivity.getStartIntent(getBaseActivity()));
        getBaseActivity().finish();// finishing the parent activity's activity rather than opening a new one
    }

}
