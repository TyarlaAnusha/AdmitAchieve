package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // Delay in milliseconds
    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        setContentView(R.layout.activity_splash);
        topAnim = AnimationUtils.loadAnimation(this, R. anim. top_animation);

        bottomAnim = AnimationUtils.loadAnimation(this, R. anim. bottom_animation);

        logo=findViewById(R.id.splash_logo);
        name=findViewById(R.id.splash_app_name);

        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);


        final RelativeLayout splashLayout = findViewById(R.id.splashLayout);
        final ConstraintLayout mainLayout = findViewById(R.id.mainLayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                // Start MainActivity or navigate to another activity/fragment
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the splash activity so it's not accessible via the back button
            }
        }, SPLASH_DELAY);
    }
}
