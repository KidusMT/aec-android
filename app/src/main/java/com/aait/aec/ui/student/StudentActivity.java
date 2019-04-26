/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.aait.aec.ui.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.SheetValue;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by janisharali on 27/01/17.
 */

public class StudentActivity extends BaseActivity implements StudentMvpView {

    @Inject
    StudentMvpPresenter<StudentMvpView> mPresenter;

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    ArrayList<SheetValue> uploadData;

    ListView lvInternalStorage;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, StudentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(StudentActivity.this);

        CommonUtils.hideKeyboard(this);

        setUp();
    }

    @OnClick(R.id.btn_import)
    void onImportClicked() {
        CommonUtils.toast("Import clicked");
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        uploadData = new ArrayList<>();
    }

    @Override
    public void loadStudentsFromExcelFile() {

    }
}
