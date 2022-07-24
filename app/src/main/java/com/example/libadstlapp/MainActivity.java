package com.example.libadstlapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ads.AppIronSource;
import com.example.ads.funtion.AdCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppIronSource.getInstance().init(MainActivity.this, "85460dcd", true);

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppIronSource.getInstance().loadAndShowInter(MainActivity.this, 3000, "", new AdCallback());
            }
        });
    }
}