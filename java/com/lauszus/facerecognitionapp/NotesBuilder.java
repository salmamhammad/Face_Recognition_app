package com.lauszus.facerecognitionapp;

public class NotesBuilder {
    private String  content;
private int id;
    public NotesBuilder() {
    }

    public NotesBuilder(int id, String content) {
             this.content = content;
        this.id=id;
    }
    public int getid() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
