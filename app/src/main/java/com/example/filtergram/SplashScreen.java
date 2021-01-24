package com.example.filtergram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread welcomeThread=new Thread(){
            @Override
            public void run(){
                try{
                    super.run();
                    sleep(1000);//900
                }catch (Exception e){

                }finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}