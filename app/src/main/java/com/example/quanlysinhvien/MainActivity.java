package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(MainActivity.this);
    }

    // Phương thức thêm một lớp vào CSDL
    public void addClass(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        // Set khi click bên ngoài, dialog không bị mất
        dialog.setCanceledOnTouchOutside(false);
        // Ánh xạ
        final EditText txtClassCode = dialog.findViewById(R.id.edtClassCode);
        final EditText txtClassName = dialog.findViewById(R.id.edtClassName);
        Button btnSave  = dialog.findViewById(R.id.btnSave);
        Button btnReset = dialog.findViewById(R.id.btnReset);

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

                database.insertClass(classroom);
                Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
        dialog.show();
    }

    // Xem danh sách lớp
    public void showClassroom(View view){
        Intent intent = new Intent(MainActivity.this, ClassroomListActivity.class);
        startActivity(intent);
    }

    // Quản lí sinh viên
    public void manageStudent(View view){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
