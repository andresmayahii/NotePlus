package com.itkloud.noteplus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import com.itkloud.noteplus.adapter.NoteAdapter;
import com.itkloud.noteplus.dao.NoteDao;
import com.itkloud.noteplus.dao.impl.file.NoteDaoFileImpl;
import com.itkloud.noteplus.dto.Note;

import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private NoteDao noteDao;
    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"Start onCreate");
        noteDao = new NoteDaoFileImpl(this);

        noteAdapter = new NoteAdapter(this,noteDao);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(noteAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteDao.flush();
    }

    public void addNewNote(View v){
        Note n = new Note();
        n.setId(38);
        n.setBody("Nota roja");
        n.setTitle("Nueva");
        n.setColor("#FF0000");
        n.setDate(new Date());
        noteAdapter.addNewNote(n);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
