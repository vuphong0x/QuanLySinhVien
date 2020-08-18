package com.example.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "QLSV.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Classroom ( Class_Id TEXT PRIMARY KEY NOT NULL, Class_Name TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Student ( Name TEXT PRIMARY KEY NOT NULL, Birthday TEXT NOT NULL, Classroom TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertClass(Classroom classroom){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Class_Id", classroom.getClassID());
        contentValues.put("Class_Name", classroom.getClassName());
        long result = sqLiteDatabase.insert("Classroom", null, contentValues);
        return result;
    }

    public long insertStudent (Student student) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", student.getName());
        contentValues.put("Birthday", student.getBirthday());
        contentValues.put("Classroom", student.getClassroom());
        long result = sqLiteDatabase.insert("Student", null, contentValues);
        return result;
    }

    public long updateClass(Classroom classroom){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Class_Id", classroom.getClassID());
        contentValues.put("Class_Name", classroom.getClassName());
        long result = sqLiteDatabase.update("Classroom", contentValues, "Class_Id = ?", new String[]{classroom.getClassID()});
        return result;
    }

    public List<Classroom> getAllClass(){
        List<Classroom> classroomList = new ArrayList<>();
        String SELECT = "SELECT * FROM Classroom";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Classroom classroom = new Classroom();
                classroom.setClassID(cursor.getString(cursor.getColumnIndex("Class_Id")));
                classroom.setClassName(cursor.getString(cursor.getColumnIndex("Class_Name")));
                classroomList.add(classroom);
            }
            cursor.close();
        }
        return classroomList;
    }

    public List<Student> getAllStudent(){
        List<Student> studentList = new ArrayList<>();
        String SELECT = "SELECT * FROM Student";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Student student = new Student();
                student.setName(cursor.getString(cursor.getColumnIndex("Name")));
                student.setBirthday(cursor.getString(cursor.getColumnIndex("Birthday")));
                student.setClassroom(cursor.getString(cursor.getColumnIndex("Classroom")));
                studentList.add(student);
            }
            cursor.close();
        }
        return studentList;
    }

    public List<String> getSubject () {
        List<String> subjectList = new ArrayList<>();
        String SELECT = "SELECT * FROM Classroom";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               String subject = cursor.getString(cursor.getColumnIndex("Class_Id"));
               subjectList.add(subject);
            }
        }
        return subjectList;
    }
    public void deleteClass(String Class_Id){
        SQLiteDatabase sql = getWritableDatabase();
        sql.delete("Classroom", "Class_Id = ?", new String[]{Class_Id});
    }

    public void deleteStudent(String Name){
        SQLiteDatabase sql = getWritableDatabase();
        sql.delete("Student", "Name = ?", new String[]{Name});
    }
}
