package com.itkloud.noteplus.dao.impl.file;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.itkloud.noteplus.dao.NoteDao;
import com.itkloud.noteplus.dto.Note;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by andressh on 20/11/14.
 */
public class NoteDaoFileImpl implements NoteDao{

    private static final String TAG = "NoteDaoFileImpl";

    private Activity parentActivity;
    private TreeSet<Note> notes = new TreeSet<Note>();
    private int maxId = 0;

    public NoteDaoFileImpl(Activity parentActivity) {
        this.parentActivity = parentActivity;

        try {
            BufferedReader bRead = new BufferedReader(new InputStreamReader(parentActivity.openFileInput("notes.json")));

            JSONArray jNotes = new JSONArray(bRead.readLine());
            for (int i = 0; i < jNotes.length(); ++i) {
                Note n = new Note();
                JSONObject jn = jNotes.getJSONObject(i);
                n.setTitle(jn.getString("title"));
                n.setBody(jn.getString("body"));
                n.setColor(jn.getString("color"));
                n.setDate(new Date(jn.getLong("date")));
                n.setFav(jn.getBoolean("fav"));
                n.setId(jn.getInt("_id"));
                if(n.getId() > maxId) maxId = n.getId();
                notes.add(n);
            }

            bRead.close();

        } catch(FileNotFoundException e) {
            Log.d(TAG,"Init default notes");
        } catch(Exception e){
            Log.e(TAG,"Read Exception",e);
        }


    }

    public List<Note> getAll() {
        return new ArrayList<Note>(notes);
    }

    public void saveOrUpdate(Note note) {
        if(note.getId() == 0) note.setId(++maxId);
        notes.add(note);
    }

    public void delete(Note note){
        notes.remove(note);
    }

    @Override
    public void flush() {
        try {
            BufferedWriter bWrite = new BufferedWriter(new OutputStreamWriter(parentActivity.openFileOutput("notes.json", Context.MODE_PRIVATE)));


            JSONArray jsonArray = new JSONArray();

            for(Note n:notes){
                JSONObject obj = new JSONObject();
                obj.put("_id",n.getId());
                obj.put("title",n.getTitle());
                obj.put("body",n.getTitle());
                obj.put("date",n.getDate().getTime());
                obj.put("color",n.getColor());
                obj.put("fav",n.isFav());
                jsonArray.put(obj);
            }

            bWrite.write(jsonArray.toString());
            bWrite.flush();
            bWrite.close();
        } catch(Exception e){
            Log.e(TAG,"Read Exception",e);
        }

    }
}
