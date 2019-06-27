package com.aait.aec.data.network;

import com.aait.aec.data.network.model.BlogResponse;
import com.aait.aec.data.network.model.Image.ImageUploadResponse;
import com.aait.aec.data.network.model.LoginRequest;
import com.aait.aec.data.network.model.LoginResponse;
import com.aait.aec.data.network.model.LogoutResponse;
import com.aait.aec.data.network.model.OpenSourceResponse;
import com.aait.aec.data.network.model.RegistrationRequest;
import com.aait.aec.data.network.model.exam.Exam;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<LoginResponse> doServerLoginApiCall(LoginRequest request);

    Observable<RegistrationRequest> doRegistrationApiCall(RegistrationRequest request);

    Observable<ImageUploadResponse> upload(String container, List<MultipartBody.Part> files);

    Single<LogoutResponse> doLogoutApiCall();

    Single<BlogResponse> getBlogApiCall();

    Observable<List<Exam>> getExams();

    Single<OpenSourceResponse> getOpenSourceApiCall();
}
