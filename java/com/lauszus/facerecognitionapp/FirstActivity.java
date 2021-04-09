package com.lauszus.facerecognitionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
private Button btn1;
private Button btn2;
Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btn1=(Button)findViewById(R.id.button3);
        btn2=(Button)findViewById(R.id.btnabout);
        font=Typeface.createFromAsset(this.getAssets(),"fonts/Droid Sans Arabic.ttf");
        btn1.setTypeface(font);
        btn2.setTypeface(font);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, FaceRecognitionAppActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Log.v("about","ww");
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
                //builder.setTitle("Please enter your name:");
                View view =getLayoutInflater().inflate(R.layout.dialog_about,null);
                TextView about1=(TextView)view.findViewById(R.id.about1);
                TextView about2=(TextView)view.findViewById(R.id.about2);
                TextView about3=(TextView)view.findViewById(R.id.about3);
                about1.setTypeface(font);
                about2.setTypeface(font);
                about3.setTypeface(font);
                builder.setView(view);
                builder.setPositiveButton("موافق", null); // Set up positive button, but do not provide a listener, so we can check the string before dismissing the dialog
                builder.setCancelable(false); // User has to input a name
                AlertDialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {

                    }
                });
                // Show keyboard, so the user can start typing straight away
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();
            }
        });


    }

}
