package com.aait.aec.data;

import android.content.Context;

import com.aait.aec.data.db.DbHelper;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.db.model.User;
import com.aait.aec.data.network.ApiHeader;
import com.aait.aec.data.network.ApiHelper;
import com.aait.aec.data.network.model.BlogResponse;
import com.aait.aec.data.network.model.Course;
import com.aait.aec.data.network.model.Image.ImageUploadResponse;
import com.aait.aec.data.network.model.LoginRequest;
import com.aait.aec.data.network.model.LoginResponse;
import com.aait.aec.data.network.model.LogoutResponse;
import com.aait.aec.data.network.model.OpenSourceResponse;
import com.aait.aec.data.network.model.RegistrationRequest;
import com.aait.aec.data.network.model.correct.CorrectRequest;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.data.prefs.PreferencesHelper;
import com.aait.aec.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Observable<RegistrationRequest> doRegistrationApiCall(RegistrationRequest request) {
        return mApiHelper.doRegistrationApiCall(request);
    }

    @Override
    public Observable<ImageUploadResponse> upload(String container, List<MultipartBody.Part> files) {
        return mApiHelper.upload(container, files);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> insertStudents(List<Student> students) {
        return mDbHelper.insertStudents(students);
    }

    @Override
    public Observable<Boolean> insertStudent(Student student) {
        return mDbHelper.insertStudent(student);
    }

    @Override
    public Observable<Boolean> deleteStudent(Student student) {
        return mDbHelper.deleteStudent(student);
    }

    @Override
    public Observable<Boolean> updateStudent(Student student) {
        return mDbHelper.updateStudent(student);
    }

    @Override
    public Observable<Boolean> deleteAllStudents() {
        return mDbHelper.deleteAllStudents();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return mDbHelper.getStudents();
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(String accessToken) {
        mApiHelper.getApiHeader().setAccessToken(accessToken);//.setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String userName,
            String email) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);

        updateApiHeader(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null);
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Observable<List<Exam>> getExams() {
        return mApiHelper.getExams();
    }

    @Override
    public Observable<Exam> createExam(Exam exam) {
        return mApiHelper.createExam(exam);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return mApiHelper.getCourses();
    }

    @Override
    public Observable correct(CorrectRequest request) {
        return mApiHelper.correct(request);
    }

    @Override
    public String getCurrentLanguage() {
        return mPreferencesHelper.getCurrentLanguage();
    }

    @Override
    public void setCurrentLanguage(String language) {
        mPreferencesHelper.setCurrentLanguage(language);
    }
}
