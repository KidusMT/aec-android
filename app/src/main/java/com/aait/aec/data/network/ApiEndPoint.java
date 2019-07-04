package com.aait.aec.data.network;

import com.aait.aec.BuildConfig;

public final class ApiEndPoint {

    public static final String LOGIN = BuildConfig.BASE_URL
            + "Users/login";

    public static final String REGISTRATION = BuildConfig.BASE_URL
            + "Users";

    public static final String EXAM = BuildConfig.BASE_URL
            + "exams";

    public static final String COURSES = BuildConfig.BASE_URL
            + "courses";

    public static final String UPLOAD = BuildConfig.BASE_URL
            + "Containers/{container}/upload";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
