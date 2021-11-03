package vn.fpt.orderfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import vn.fpt.orderfood.config.AppDatabase;

public class MainActivity extends AppCompatActivity {

    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "information").allowMainThreadQueries().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a;
    }
}