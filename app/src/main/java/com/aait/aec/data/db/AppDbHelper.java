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

package com.aait.aec.data.db;

import com.aait.aec.data.db.model.DaoMaster;
import com.aait.aec.data.db.model.DaoSession;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.db.model.User;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(() -> mDaoSession.getUserDao().insert(user));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(() -> mDaoSession.getUserDao().loadAll());
    }

    @Override
    public Observable<List<Student>> getStudents() {
        return Observable.fromCallable(()-> mDaoSession.getStudentDao().loadAll());
    }

    @Override
    public Observable<String> insertStudents(List<Student> students) {
        mDaoSession.getStudentDao().insertOrReplaceInTx(students);
        return Observable.fromCallable(() -> "successful");
    }

}
