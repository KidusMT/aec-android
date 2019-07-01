package com.aait.aec.ui.studentEdit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private Student student;

    public static EditStudentDialog newInstance(Student student) {
        EditStudentDialog fragment = new EditStudentDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(STUDENT_KEY, student);
        fragment.setArguments(bundle);
        fragment.setArguments(bundle);
        return fragment;
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

    @OnClick(R.id.btn_dialog_cancel)
    void onCancelClicked() {
        mPresenter.onCancelClicked();

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
        dismiss();
    }

    @Override
    public void onDestroyView() {
//        mPresenter.onDetach();todo get back here later
        super.onDestroyView();
    }
}
