package com.lauszus.facerecognitionapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
private String name=null;
    public final static String nameparam="NAME";
private int id=-1;
    private TinyDB tinyDB;
    private EditText editText;
    private ArrayList<String> notelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        getSupportActionBar().setTitle("    ");
      Typeface  font= Typeface.createFromAsset(this.getAssets(),"fonts/Droid Sans Arabic.ttf");
        editText = (EditText) findViewById(R.id.EditText1);
        editText.setTypeface(font);
        tinyDB = new TinyDB(this);
        name=getIntent().getStringExtra("NAME");
    notelist=tinyDB.getListString(name);

            if(tinyDB.getInt("Note")!=-1) {
               id = tinyDB.getInt("Note");
               editText.setText(notelist.get(id));
               tinyDB.putString("Note","");

}



    }

    public void Save() {

      if(id==-1){
          notelist=tinyDB.getListString(name);
          notelist.add(editText.getText().toString());
          tinyDB.putListString(name,notelist);
          Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
      }else{
          notelist.remove(id);
          notelist.add(id,editText.getText().toString());
          tinyDB.putListString(name,notelist);
          Toast.makeText(this, "تم الحفظ!", Toast.LENGTH_SHORT).show();
      }
        Intent intent = new Intent(NextActivity.this, NoteActivity.class);

        intent.putExtra(NextActivity.nameparam,name);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            Save();
            return true;
        }else if(id==R.id.back){
            Intent intent = new Intent(NextActivity.this, NoteActivity.class);

            intent.putExtra(NextActivity.nameparam,name);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
