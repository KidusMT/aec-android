package com.aait.aec.data.network;

import com.aait.aec.data.network.model.BlogResponse;
import com.aait.aec.data.network.model.Course;
import com.aait.aec.data.network.model.Image.ImageUploadResponse;
import com.aait.aec.data.network.model.LoginRequest;
import com.aait.aec.data.network.model.LoginResponse;
import com.aait.aec.data.network.model.LogoutResponse;
import com.aait.aec.data.network.model.OpenSourceResponse;
import com.aait.aec.data.network.model.RegistrationRequest;
import com.aait.aec.data.network.model.Student;
import com.aait.aec.data.network.model.correct.CorrectRequest;
import com.aait.aec.data.network.model.exam.Exam;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private ApiCall mApiCall;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, ApiCall apiCall) {
        mApiHeader = apiHeader;
        mApiCall = apiCall;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest request) {
        return mApiCall.login(request);
    }

    @Override
    public Observable<RegistrationRequest> doRegistrationApiCall(RegistrationRequest request) {
        return mApiCall.register(request);
    }

    @Override
    public Observable<ImageUploadResponse> upload(String container, List<MultipartBody.Part> files) {
        return mApiCall.upload(container, files);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return null;
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return null;
    }

    @Override
    public Observable<List<Exam>> getExams() {
        return mApiCall.loadExams();
    }

    @Override
    public Observable<Exam> createExam(Exam exam) {
        return mApiCall.createExam(exam);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return null;
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return mApiCall.loadCourses();
    }

    @Override
    public Observable<List<Student>> getStudentsResult() {
        return mApiCall.getStudents();
    }

    @Override
    public Observable<String> correct(CorrectRequest request) {
        return mApiCall.correct(request);
    }
}

