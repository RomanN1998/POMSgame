package com.example.pomsgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<NewUser> listTemp;

    private EditText edName, edSecondName, edEmail;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        getDataFromDB();
        setOnClickItem();

    }

    private void init() {
        listView = (ListView)findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    private void getDataFromDB() {

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (listData.size() > 0)
                    listData.clear();

                if (listTemp.size() > 0)
                    listTemp.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NewUser user = ds.getValue(NewUser.class);
                    assert  user != null;
                    listData.add(user.name + "  " + user.xpUser);
                    listTemp.add(user);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDataBase.addValueEventListener(vListener);
    }

    //Системная кнопка Назад начало
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(ReadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }

    }
    //Системная кнопка Назад конец

    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewUser user = listTemp.get(position);
                Intent i = new Intent(ReadActivity.this, ShowActivity.class);
                i.putExtra(Constant.USER_NAME, user.name);
                i.putExtra(Constant.USER_SECOND, user.sec_name);
                i.putExtra(Constant.USER_EMAIL, user.email);
                i.putExtra(Constant.USER_XP, String.valueOf(user.xpUser));
                startActivity(i);
            }
        });
    }
}
