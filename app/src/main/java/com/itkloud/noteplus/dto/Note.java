package com.itkloud.noteplus.dto;

import com.itkloud.noteplus.utils.ValidationColor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by andressh on 20/11/14.
 */
public class Note implements Comparable<Note>,Serializable {

    public static String DEFAULT_COLOR = "#44A1EB";

    private int id;
    private String title;
    private String body;
    private String color;
    private boolean fav;
    private Date date;

    public Note() {}

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
        this.date = new Date();
        this.color = DEFAULT_COLOR;
    }

    public Note(int id, String title, String body, String color, boolean fav, Date date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.color = (ValidationColor.validate(color))?color:DEFAULT_COLOR;
        this.fav = fav;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = (ValidationColor.validate(color))?color:DEFAULT_COLOR;;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Note note) {
        if(this.getId() == note.getId()) return 0;
        if(this.fav && !note.fav) return 1;
        if(this.getDate() != null && note.getDate() != null) {
            if(this.getDate().getTime() > note.getDate().getTime()) return 1;
            else if(this.getDate().getTime() < note.getDate().getTime()) return -1;
        }
        return this.getId() - note.getId();
    }

    @Override
    public boolean equals(Object o) {
        // if(o == null) return false;
        Note note = (Note) o;
        return (this.getId() == note.getId());
    }
}
