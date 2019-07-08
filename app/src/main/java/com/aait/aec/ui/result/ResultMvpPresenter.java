package com.aait.aec.ui.result;

import com.aait.aec.data.network.model.correct.CorrectRequest;
import com.aait.aec.di.PerActivity;
import com.aait.aec.ui.base.MvpPresenter;

import java.util.List;

import okhttp3.MultipartBody;

@PerActivity
public interface ResultMvpPresenter<V extends ResultMvpView> extends MvpPresenter<V> {

    void onUploadClicked(String container, List<MultipartBody.Part> parts, CorrectRequest request);

    void loadStudentsFromDb();

    void onCorrect(CorrectRequest request);

    void getStudentsResult();
}