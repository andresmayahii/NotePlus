package com.itkloud.noteplus.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itkloud.noteplus.EditActivity;
import com.itkloud.noteplus.MainActivity;
import com.itkloud.noteplus.R;
import com.itkloud.noteplus.dao.NoteDao;
import com.itkloud.noteplus.dto.Note;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by andressh on 26/11/14.
 */
public class NoteAdapter extends BaseAdapter implements ListAdapter, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Activity parent;
    private NoteDao noteDao;
    private List<Note> notes;

    public NoteAdapter(Activity parent, NoteDao noteDao) {
        this.parent = parent;
        this.noteDao = noteDao;
        update();
    }

    public void addNewNote(Note note){
        noteDao.saveOrUpdate(note);
        update();
    }

    public void deleteNote(int idNote) {
        Note n = new Note();
        n.setId(idNote);
        noteDao.delete(n);
        update();
    }

    private void update() {
        notes = noteDao.getAll();

        notifyDataSetChanged();

        TextView noNotes = (TextView) this.parent.findViewById(R.id.textViewNotNotes);
        if(notes.size() == 0) {
            noNotes.setVisibility(View.VISIBLE);
        } else {
            noNotes.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        deleteNote(this.parent,position);
        return false;
    }

    public void deleteNote(final Context context,final int position) {

        final NoteAdapter na = this;

        try {
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            ad.setTitle(parent.getString(R.string.hello_world));
            ad.setMessage("Estas seguro que quieres eliminar la nota");
            ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    na.deleteNote((int)na.getItemId(position));
                    Toast.makeText(context, "Se elimino la nota " + position, Toast.LENGTH_SHORT).show();

                }
            });
            ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ad.show();
        } catch(Exception e) {
            Toast.makeText(parent,"No se pudo eliminar la nota",Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "No se espera una exception", e);
        }

        /*
            new AlertDialog.Builder(context).
                setTitle().
                setMessage().
                ....
                show();
        */


    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = this.parent.getLayoutInflater().inflate(R.layout.list_view_note,null);
        }

        Note n = notes.get(position);

        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayoutNote);
        relativeLayout.setBackgroundColor(Color.parseColor(n.getColor()));

        TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
        titleView.setText(n.getTitle());

        TextView bodyView = (TextView) convertView.findViewById(R.id.bodyView);
        bodyView.setText(n.getBody());

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note n = (Note) getItem(position);

        Intent intent = new Intent(this.parent,EditActivity.class);
        intent.putExtra("title",n.getTitle());
        intent.putExtra("body",n.getBody());
        intent.putExtra("id",n.getId());
        this.parent.startActivityForResult(intent, MainActivity.ACTIVITY_NEW_NOTE);

    }
}
