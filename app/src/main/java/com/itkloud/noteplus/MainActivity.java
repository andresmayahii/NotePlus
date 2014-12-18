package com.itkloud.noteplus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.itkloud.noteplus.adapter.NoteAdapter;
import com.itkloud.noteplus.dao.NoteDao;
import com.itkloud.noteplus.dao.impl.file.NoteDaoFileImpl;
import com.itkloud.noteplus.dto.Note;

import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private NoteDao noteDao;
    private NoteAdapter noteAdapter;

    public static final int ACTIVITY_NEW_NOTE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"Start onCreate");
        noteDao = new NoteDaoFileImpl(this);

        noteAdapter = new NoteAdapter(this,noteDao);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(noteAdapter);
        listView.setOnItemLongClickListener(noteAdapter);
        //listView.setOnitem//
        //listView.setOnClickListener(this);
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "* Click al elemento " + item.toString(), Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteDao.flush();
    }

    public void addNewNote(View v){

        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("id",0);
        startActivityForResult(intent,ACTIVITY_NEW_NOTE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ACTIVITY_NEW_NOTE) {
            Note n = new Note();
            n.setId(data.getExtras().getInt("id"));
            n.setBody(data.getExtras().getString("body"));
            n.setTitle(data.getExtras().getString("title"));
            n.setColor("#00FF00");
            n.setDate(new Date());
            n.setFav(false);

            noteAdapter.addNewNote(n);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
