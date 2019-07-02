package com.aait.aec.ui.studentEdit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.di.component.ActivityComponent;
import com.aait.aec.ui.base.BaseDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aait.aec.utils.AppConstants.STUDENT_KEY;

public class EditStudentDialog extends BaseDialog implements EditStudentMvpView {

    @Inject
    EditStudentMvpPresenter<EditStudentMvpView> mPresenter;

    @BindView(R.id.dialog_std_name)
    TextView mStdName;

    @BindView(R.id.dialog_std_id)
    TextView mStdId;
    StudentCommunicator communicator;
    private Student student;

    public static EditStudentDialog newInstance(Student student) {
        EditStudentDialog fragment = new EditStudentDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STUDENT_KEY, student);
        fragment.setArguments(bundle);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static EditStudentDialog newInstance() {
        return new EditStudentDialog();
    }

    @OnClick(R.id.btn_delete_std)
    void onDeleteClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setMessage(getString(R.string.delete_dialog_student))
                .setPositiveButton(getString(R.string.btn_delete), (dialog, id) -> {
                    if (student != null)
                        mPresenter.onDeleteClicked(student);
                })
                .setNegativeButton(getString(R.string.btn_cancel), (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_student_info, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
        }

        if (getArguments() != null)
            student = (Student) getArguments().getSerializable(STUDENT_KEY);

        setUp(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (StudentCommunicator) context;
    }

    @OnClick(R.id.btn_dialog_cancel)
    void onCancelClicked() {
        mPresenter.onCancelClicked();

    }

    @OnClick(R.id.btn_dialog_update)
    void onUpdateClicked() {
        if (student != null) {
            student.setName(String.valueOf(mStdName.getText()));
            student.setStdId(String.valueOf(mStdId.getText()));
            mPresenter.onUpdateClicked(student);
        }
    }

    @Override
    protected void setUp(View view) {
        if (student != null) {
            if (!TextUtils.isEmpty(student.getName()))
                mStdName.setText(student.getName());

            if (!TextUtils.isEmpty(student.getStdId()))
                mStdId.setText(student.getStdId());

        }
    }

    @Override
    public void closeDialog() {
        communicator.updateView();
        hideLoading();
        dismiss();
    }

    @Override
    public void onDestroyView() {
//        mPresenter.onDetach();todo get back here later
        super.onDestroyView();
    }
}
