package com.example.pomsgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GamesLevels extends AppCompatActivity {

    private TextView textXP;
    private String textUser;
    private String socre;

    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        getIntentMain();
        getDataFromDB();

        Button buttonLevel = (Button)findViewById(R.id.button_back);

        buttonLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GamesLevels.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e) {

                }

            }
        });

        Button btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(GamesLevels.this, LevelActivity.class);
                    intent.putExtra(Constant.USER_ID, textUser);
                    intent.putExtra(Constant.USER_XP, socre);
                    startActivity(intent);
                    finish();

                }catch (Exception e) {

                }

            }
        });

    }

    private void init() {
        textXP = (TextView)findViewById(R.id.textXP);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);

    }

    private void getIntentMain(){
        Intent i = getIntent();
        if (i != null) {
            textUser = i.getStringExtra(Constant.USER_ID);
        }
    }

    private void getDataFromDB() {

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NewUser user = ds.getValue(NewUser.class);
                    assert  user != null;

                    if (ds.getKey().equals(textUser)) {
                        System.out.println("XPUser " + user.xpUser);
                        textXP.setText(Integer.toString(user.xpUser));
                        socre = String.valueOf(user.xpUser);
                        break;

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDataBase.addValueEventListener(vListener);
//        mDataBase.removeEventListener(vListener);
    }

    //Системная кнопка Назад начало
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(GamesLevels.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }

    }
    //Системная кнопка Назад конец

}