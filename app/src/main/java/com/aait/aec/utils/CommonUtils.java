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

package com.aait.aec.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aait.aec.MvpApp;
import com.aait.aec.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by KidusMT.
 */

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressBar showLoadingDialog(Context context) {
        if (context != null) {
//            ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.show();
//            if (progressDialog.getWindow() != null) {
//                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            }
//            progressDialog.setContentView(R.layout.progress_dialog);
//            progressDialog.setIndeterminate(true);
////        progressDialog.setCancelable(false);//todo cancel this when needed
////        progressDialog.setCanceledOnTouchOutside(false);
//            return progressDialog;
            return new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);

        } else {
            return null;
        }
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void toast(String msg) {
        Toast.makeText(MvpApp.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static RequestBody toRequestBody(String request) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), request);
    }

    public static String getErrorMessage(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return "Please try again.";
        } else if (throwable instanceof IOException) {
            return "Please connect to the internet.";
        } else if (throwable instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException) {
            int code = ((com.jakewharton.retrofit2.adapter.rxjava2.HttpException) throwable).response().code();
            if (code >= 400 && code < 404) { //4xx Client Errors
                return MvpApp.getContext().getString(R.string.error_invalid_credential);
            } else if (code == 404) { //4xx Client Errors
                return MvpApp.getContext().getString(R.string.error_file_does_not_exist);
            } else if (code == 500) {
                return MvpApp.getContext().getString(R.string.error_server_error);
            } else if (code == 503) {
                return MvpApp.getContext().getString(R.string.error_server_unreachable);
            } else {

                return "Something wrong happened. Please try again later.";
//                ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
//                try {//should display the correct error message form the http protocol
//                    if (responseBody != null) {
//                        JSONObject jObjError = new JSONObject(responseBody.toString());
//                        return jObjError.toString();
//                    }
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
            }
        }
        //todo find out if this is the right way of handling this condition
//        else {
//            return throwable.getMessage();
//        }
        return "";
    }
}
