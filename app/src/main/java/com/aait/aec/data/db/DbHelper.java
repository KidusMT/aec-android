package com.aait.aec.data.db;

import com.aait.aec.data.db.model.Student;
import com.aait.aec.data.db.model.User;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<Long> insertUser(final User user);

    Observable<Boolean> insertStudents(List<Student> students);

    Observable<Boolean> insertStudent(Student student);

    Observable<Boolean> deleteStudent(Student student);

    Observable<Boolean> updateStudent(Student student);

    Observable<Boolean> deleteAllStudents();

    Observable<List<User>> getAllUsers();

    Observable<List<Student>> getStudents();

}
