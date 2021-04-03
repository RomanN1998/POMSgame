package com.example.pomsgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Register extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    private EditText edName, edSecondName, edEmail, edPass;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    private boolean b = false;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<NewUser> listTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        init();

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e) {

                }

            }
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void init() {
        edName = findViewById(R.id.editName);
        edSecondName = findViewById(R.id.editSecond);
        edEmail = findViewById(R.id.editEmail);
        edPass = findViewById(R.id.editpassword);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickCreate(View view) {
        String id = mDataBase.getKey();
        String name = edName.getText().toString();
        String sec_name = edSecondName.getText().toString();
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        NewUser user = new NewUser(id, name, pass, sec_name, email, 0);

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sec_name) && !TextUtils.isEmpty(email)) {
            mDataBase.push().setValue(user);
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Пустое поле", Toast.LENGTH_SHORT).show();
        }

    }

    //Системная кнопка Назад начало
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }

    }
    //Системная кнопка Назад конец
}