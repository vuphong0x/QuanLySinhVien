package com.example.quanlysinhvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClassroomListActivity extends AppCompatActivity {
    ListView listView;
    Database database;
    List<Classroom> classrooms;
    ClassroomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = new Database(ClassroomListActivity.this);
        classrooms = new ArrayList<>();
        listView = findViewById(R.id.lvListClassroom);

        classrooms.addAll(database.getAllClass());
        adapter = new ClassroomAdapter(classrooms);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(position);
                return false;
            }
        });
    }

    private void confirmDelete (final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setIcon(R.mipmap.ic_delete);
        alertDialog.setMessage("Bạn muốn xóa hay cập nhật lớp này?");
        alertDialog.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Dialog dialog1 = new Dialog(ClassroomListActivity.this);
                dialog1.setContentView(R.layout.add_dialog);
                // Set khi click bên ngoài, dialog không bị mất
                dialog1.setCanceledOnTouchOutside(false);
                // Ánh xạ
                final EditText txtClassCode = dialog1.findViewById(R.id.edtClassCode);
                final EditText txtClassName = dialog1.findViewById(R.id.edtClassName);
                Button btnSave  = dialog1.findViewById(R.id.btnSave);
                Button btnReset = dialog1.findViewById(R.id.btnReset);

                //  Bắt sự kiện cho nút Save
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy giá trị từ EditText
                        String classCode = String.valueOf(txtClassCode.getText());
                        String className = String.valueOf(txtClassName.getText());

                        Classroom classroom = new Classroom();
                        classroom.setClassID(classCode);
                        classroom.setClassName(className);

                        String classId = classrooms.get(position).getClassID();
                        classrooms.set(position, classroom);
                        database.deleteClass(classId);
                        database.insertClass(classroom);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ClassroomListActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                });

                // Bắt sự kiện cho nút Reset
                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtClassCode.setText(null);
                        txtClassName.setText(null);
                        // Đưa con trỏ về EitText ClassCode
                        txtClassCode.requestFocus();
                    }
                });
                dialog1.show();
            }
        });
        alertDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String classId = classrooms.get(position).getClassID();
                database.deleteClass(classId);
                classrooms.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.show();
    }
}
