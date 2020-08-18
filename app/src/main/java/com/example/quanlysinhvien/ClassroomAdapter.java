package com.example.quanlysinhvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends BaseAdapter {
    List<Classroom> list = new ArrayList<>();

    public ClassroomAdapter(List<Classroom> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Classroom getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_view, parent, false);

        TextView tvSerial = convertView.findViewById(R.id.tvSerial);
        TextView tvClassCode = convertView.findViewById(R.id.tvClassCode);
        TextView tvClassName = convertView.findViewById(R.id.tvClassName);

        Classroom classroom = getItem(position);
        tvSerial.setText(String.valueOf(position+1));
        tvClassCode.setText(classroom.getClassID());
        tvClassName.setText(classroom.getClassName());

        return convertView;
    }
}
