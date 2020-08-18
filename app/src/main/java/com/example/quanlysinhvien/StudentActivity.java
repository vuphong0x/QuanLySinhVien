package com.example.quanlysinhvien;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    List<String> subject = new ArrayList<>();
    List<Student> studentList;
    Spinner spinner;
    Student student;
    EditText edtName, edtBirthday;
    Button btnAdd;
    ListView listView;
    String classroom, studentName;
    AlertDialog.Builder alertDialog;
    Database database;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        ViewBinding();

        database = new Database(StudentActivity.this);
        alertDialog = new AlertDialog.Builder(this);
        studentList = new ArrayList<>();

        subject.add("Show All");
        subject.addAll(database.getSubject());
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, subject);
        spinner.setAdapter(spinnerAdapter);

        getItemFromSpinner();

        studentList.addAll(database.getAllStudent());
        adapter = new StudentAdapter(studentList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(position);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studentName = studentList.get(position).getName();
                String birthday = studentList.get(position).getBirthday();
                edtName.setText(studentName);
                edtBirthday.setText(birthday);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addStudent(View view) {
        student = new Student();
        student.setClassroom(classroom);
        if (getValuesFromEditText()) {
            studentList.add(student);
            database.insertStudent(student);
            adapter = new StudentAdapter(studentList);
            listView.setAdapter(adapter);

        }
    }

    public void getItemFromSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    classroom = subject.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean getValuesFromEditText() {
        try {
            String name = edtName.getText().toString();
            student.setName(name);
        } catch (Exception e) {
            Toast.makeText(StudentActivity.this, "Lỗi " + e, Toast.LENGTH_SHORT).show();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            String birthday = edtBirthday.getText().toString();
            Date date = dateFormat.parse(birthday);
            student.setBirthday(birthday);
        } catch (Exception e) {
            alertDialog.setTitle("Error!");
            alertDialog.setIcon(R.drawable.ic_error);
            alertDialog.setMessage("Ngày sinh không hợp lệ!");
            alertDialog.show();
            return false;
        }
        return true;
    }

    private void confirmDelete(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setIcon(R.mipmap.ic_delete);
        alertDialog.setMessage("Bạn muốn xóa hay cập nhật sinh viên này?");
        alertDialog.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String name = studentList.get(position).getName();
                student = new Student();
                student.setName(edtName.getText().toString());
                student.setBirthday(edtBirthday.getText().toString());
                student.setClassroom(classroom);
                studentList.set(position, student);
                database.deleteStudent(studentName);
                database.insertStudent(student);
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                studentName = studentList.get(position).getName();
                database.deleteStudent(studentName);
                studentList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.show();
    }

    public void ViewBinding() {
        spinner = findViewById(R.id.spinner);
        edtName = findViewById(R.id.edtStudentName);
        edtBirthday = findViewById(R.id.edtStudentBirthday);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.lvStudent);
    }
}
