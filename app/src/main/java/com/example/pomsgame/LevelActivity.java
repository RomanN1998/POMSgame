package com.example.pomsgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener {

    private int numGlobal = 0;
    public int countText = 0;
    private String textUser;
    private int socre;

    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    ArrayImg array = new ArrayImg();
    Button btn_otv1;
    Button btn_otv2;
    Button btn_otv3;
    Button btn_otv4;
    Button btn_otv5;
    ImageView imgGlobal;
    private int questionscount = array.questansw.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getIntentMain();

        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level);

        imgGlobal = (ImageView)findViewById(R.id.img_q);
        imgGlobal.setClipToOutline(true);

        //Развернуть игру на весь экрна
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(LevelActivity.this, GamesLevels.class);
                    intent.putExtra(Constant.USER_ID, textUser);
                    startActivity(intent);
                    finish();

                }catch (Exception e) {

                }
            }
        });

        btn_otv1 = (Button)findViewById(R.id.btn_otv1);
        btn_otv2 = (Button)findViewById(R.id.btn_otv2);
        btn_otv3 = (Button)findViewById(R.id.btn_otv3);
        btn_otv4 = (Button)findViewById(R.id.btn_otv4);
        btn_otv5 = (Button)findViewById(R.id.btn_otv5);

        btn_otv1.setText(array.questansw[countText][0]);
        btn_otv2.setText(array.questansw[countText][1]);
        btn_otv3.setText(array.questansw[countText][2]);
        btn_otv4.setText(array.questansw[countText][3]);
        btn_otv5.setText(array.questansw[countText][4]);
        imgGlobal.setImageResource(array.images1[countText]);

        btn_otv1.setOnClickListener(this);
        btn_otv2.setOnClickListener(this);
        btn_otv3.setOnClickListener(this);
        btn_otv4.setOnClickListener(this);
        btn_otv5.setOnClickListener(this);

    }

    private void btnMethod(Button btn) {
        try{
                if (btn.getId() == array.questansw[countText][5]) {
                    countText += 1;
                    numGlobal += 1;

                    if (countText<questionscount) {
                    imgGlobal.setImageResource(array.images1[countText]);
                    btn_otv1.setText(array.questansw[countText][0]);
                    btn_otv2.setText(array.questansw[countText][1]);
                    btn_otv3.setText(array.questansw[countText][2]);
                    btn_otv4.setText(array.questansw[countText][3]);
                    btn_otv5.setText(array.questansw[countText][4]);

                    btn_otv1.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
                    btn_otv2.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
                    btn_otv3.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
                    btn_otv4.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
                    btn_otv5.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));

                    System.out.println("UP XP User" + countText);

                    }
                    else {
                            System.out.println("10");
                            System.out.println("User BD2");

                            DatabaseReference reference = FirebaseDatabase.getInstance()
                                    .getReference("User")
                                    .child(textUser);
                            Map<String, Object> data = new HashMap<>();
                            data.put("xpUser", socre + numGlobal);

                            reference.updateChildren(data, (databaseError, databaseReference) -> {
                                if (databaseError == null) {
                                    //всё ОК
                                    System.out.println("true 100");

                                } else {
                                    //произошла ошибка. Она тут: databaseError.toException()
                                    System.out.println("false 100");
                                }
                            });
                            Toast.makeText(this, "Вы выйграли! получили очков " + numGlobal, Toast.LENGTH_LONG).show();
                            try{
                                Intent intent = new Intent(LevelActivity.this, GamesLevels.class);
                                intent.putExtra(Constant.USER_ID, textUser);
                                startActivity(intent);
                                finish();

                            }catch (Exception e) {

                            }
                    }


                } else {
                    btn.setBackgroundTintList(getResources().getColorStateList(R.color.red_btn));
                    System.out.println("UP XP User" + countText);
                }


        }catch (Exception e) {

        }
    }


    private void getIntentMain(){
        Intent i = getIntent();
        if (i != null) {
            textUser = i.getStringExtra(Constant.USER_ID);
            socre = Integer.valueOf(i.getStringExtra(Constant.USER_XP));
            System.out.println(textUser);
        }
    }

    private void getDataFromDB() {


    }

    //Системная кнопка Назад начало
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(LevelActivity.this, GamesLevels.class);
            intent.putExtra(Constant.USER_ID, textUser);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_otv1:
                btnMethod(btn_otv1);
                break;
            case R.id.btn_otv2:
                btnMethod(btn_otv2);
                break;
            case R.id.btn_otv3:
                btnMethod(btn_otv3);
                break;
            case R.id.btn_otv4:
                btnMethod(btn_otv4);
                break;
            case R.id.btn_otv5:
                btnMethod(btn_otv5);
                break;
        }
    }
}