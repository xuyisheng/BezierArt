package com.xys.animationart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xys.animationart.activities.CalculateBezierPointActivity;
import com.xys.animationart.activities.CircleMorphingActivity;
import com.xys.animationart.activities.DrawPadActivity;
import com.xys.animationart.activities.PathBezierActivity;
import com.xys.animationart.activities.PathMorphActivity;
import com.xys.animationart.activities.QuadraticBezierActivity;
import com.xys.animationart.activities.CubicBezierActivity;
import com.xys.animationart.activities.WaveBezierActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secondBezier(View view) {
        startActivity(new Intent(MainActivity.this, QuadraticBezierActivity.class));
    }

    public void threeBezier(View view) {
        startActivity(new Intent(MainActivity.this, CubicBezierActivity.class));
    }

    public void pathMorphBezier(View view) {
        startActivity(new Intent(MainActivity.this, PathMorphActivity.class));
    }

    public void drawPadBezier(View view) {
        startActivity(new Intent(MainActivity.this, DrawPadActivity.class));
    }

    public void waveBezier(View view) {
        startActivity(new Intent(MainActivity.this, WaveBezierActivity.class));
    }

    public void pathBezier(View view) {
        startActivity(new Intent(MainActivity.this, PathBezierActivity.class));
    }

    public void calculateBezier(View view) {
        startActivity(new Intent(MainActivity.this, CalculateBezierPointActivity.class));
    }

    public void circleMorphBezier(View view) {
        startActivity(new Intent(MainActivity.this, CircleMorphingActivity.class));
    }
}
