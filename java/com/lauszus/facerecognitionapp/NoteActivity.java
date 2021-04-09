package com.lauszus.facerecognitionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    private String name;
    public final static String idparam="ID";
    public final static String nameparam="NAME";
    private ArrayList<String> notelist=new ArrayList<>();
    private ArrayList<String> namelist=new ArrayList<>();
    private ArrayList<String> urllist=new ArrayList<>();
    private String uriling;
    private TinyDB tinyDB;
    NoteAdapter mAdapter;
    RecyclerView recyclerview;
    ImageView imageView;
    Typeface font;
    private ArrayList<NotesBuilder> notebuilderlist=new ArrayList<>();
private TextView nametext;
    NotesBuilder notesBuilder;
    public final static String nullvale="لاتوجد ملاحظات بعد";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        font= Typeface.createFromAsset(this.getAssets(),"fonts/Droid Sans Arabic.ttf");
        TextView note=(TextView)findViewById(R.id.note);
        note.setTypeface(font);
        name= getIntent().getStringExtra(FaceRecognitionAppActivity.param);
        name=name.toLowerCase();
        tinyDB = new TinyDB(this);
        notelist=tinyDB.getListString(name);
        namelist=tinyDB.getListString("nameLabels");
        urllist=tinyDB.getListString("pathimage");

        if(notelist.isEmpty()){
            notesBuilder =new NotesBuilder(-1,nullvale);
            notebuilderlist.add(notesBuilder);
        }else{
        for(int i=0;i<notelist.size();i++){
            Log.v("notelist",notelist.get(i));
          NotesBuilder notesBuilder1 =new NotesBuilder(i,notelist.get(i));
            notebuilderlist.add(notesBuilder1);
        }}
        recyclerview=(RecyclerView)findViewById(R.id.notes);

        LinearLayoutManager lmanger= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerview.setLayoutManager(lmanger);

        mAdapter=new NoteAdapter(notebuilderlist);
        recyclerview.setAdapter(mAdapter);
        recyclerview.setNestedScrollingEnabled(false);

imageView=(ImageView)findViewById(R.id.img);
imageView.setRotation(270);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, NextActivity.class);
            tinyDB.putInt("Note",-1);
                intent.putExtra(NoteActivity.nameparam,name);
                startActivity(intent);
            }
        });
       nametext=(TextView)findViewById(R.id.nametext);
       nametext.setText(name);

for(int i=0;i<namelist.size();i++){
    if(namelist.get(i).equals(name)){
        uriling=urllist.get(i);
    }
}
        Log.v("imgnew","note img"+uriling);
if(!uriling.isEmpty()){
    Bitmap bitmap= BitmapFactory.decodeFile(uriling);
    imageView.setImageBitmap(bitmap);
}

    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
    public class NoteAdapter extends RecyclerView.Adapter<recyclerview_note_holder> {
        private ArrayList<NotesBuilder> noteadapter;

        public NoteAdapter(ArrayList<NotesBuilder> nadapter) {
            this.noteadapter = nadapter;
        }

        @Override
        public recyclerview_note_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View card= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
            return new recyclerview_note_holder(card);
        }
        @Override
        public int getItemCount() {
            return noteadapter.size();
        }
        @Override
        public void onBindViewHolder(recyclerview_note_holder holder, int position) {
            NotesBuilder c=noteadapter.get(position);

            holder.updateui(c);
        }


    }


    public class recyclerview_note_holder extends RecyclerView.ViewHolder{


        private TextView discrip;
  private LinearLayout linearLayout;
  private ImageButton delete_btn;

        public recyclerview_note_holder(View itemView) {
            super(itemView);
            discrip=(TextView)itemView.findViewById(R.id.discriprow);
         linearLayout=(LinearLayout)itemView.findViewById(R.id.linearrow);
            discrip.setTypeface(font);
            delete_btn=(ImageButton)itemView.findViewById(R.id.delete_btn);


        }
        public  void updateui(final NotesBuilder ccc){

            Log.v("updateui", ccc.getid()+"");
            discrip.setText(ccc.getContent());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=String.valueOf( ccc.getid());
                    //Creating editor to store values to shared preferences

              tinyDB.putInt("Note",ccc.getid());
                    Intent intent = new Intent(NoteActivity.this, NextActivity.class);
                    intent.putExtra("NAME",name);
                    startActivity(intent);
                }
            });

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notelist.remove(ccc.getid());
                    tinyDB.putListString(name,notelist);
                    Intent intent = getIntent();
                    finish();
                    intent.putExtra(FaceRecognitionAppActivity.param, name);
                    startActivity(intent);
                }
            });
        }
    }

}
