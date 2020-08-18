package com.example.quanlysinhvien;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Student getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_view, parent, false);

        TextView tvSerial = convertView.findViewById(R.id.tvSerial);
        TextView tvStudentName = convertView.findViewById(R.id.tvStudentName);
        TextView tvBirthday = convertView.findViewById(R.id.tvBirthday);
        TextView tvClass = convertView.findViewById(R.id.tvClass);

        Student student = getItem(position);
        // Error
        tvSerial.setText(String.valueOf(position+1));
        tvStudentName.setText(student.getName());
        tvBirthday.setText(student.getBirthday());
        tvClass.setText(student.getClassroom());

        return convertView;
    }
}
