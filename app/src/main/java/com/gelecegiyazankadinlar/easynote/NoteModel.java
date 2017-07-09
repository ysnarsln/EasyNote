package com.gelecegiyazankadinlar.easynote;

public class NoteModel {

    private String key, title, body, noteDate, author;

    public NoteModel() {
    }

    public NoteModel(String key, String title, String body, String noteDate, String author) {
        this.key = key;
        this.title = title;
        this.body = body;
        this.noteDate = noteDate;
        this.author = author;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}