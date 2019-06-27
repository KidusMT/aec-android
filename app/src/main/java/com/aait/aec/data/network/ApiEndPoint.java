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

package com.aait.aec.data.network;

import com.aait.aec.BuildConfig;

/**
 * Created by amitshekhar on 01/02/17.
 */

public final class ApiEndPoint {

    public static final String LOGIN = BuildConfig.BASE_URL
            + "Users/login";

    public static final String REGISTRATION = BuildConfig.BASE_URL
            + "Users";

    public static final String EXAM = BuildConfig.BASE_URL
            + "exams";

    public static final String UPLOAD = BuildConfig.BASE_URL
            + "Containers/{container}/upload";
    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
