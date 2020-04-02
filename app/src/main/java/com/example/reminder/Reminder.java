package com.example.reminder;

/**
 * Created by engMa_000 on 2017-04-03.
 */

public class Reminder {
    private int mId;
    private String mContent;
    private boolean mImportant;
    Reminder(int id, String content, boolean important) {
        mId = id;
        mImportant = important;
        mContent = content;
    }
    public int getId() {
        return mId;
    }
    public void setId(int id) {
        mId = id;
    }
    boolean getImportant() {
        return mImportant;
    }
    String getContent() {
        return mContent;
    }
}
