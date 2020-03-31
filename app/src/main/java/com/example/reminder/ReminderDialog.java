package com.example.reminder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ReminderDialog extends Dialog implements android.view.View.OnClickListener {
    private int reminderID;
    private boolean isEdit;

    public ReminderDialog(Activity a, boolean isEdit, int reminderID) {
        super(a);
        this.isEdit = isEdit;
        this.reminderID = reminderID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reminder_dialog);

        // Set buttons on click listener
        findViewById(R.id.dialog_save_button).setOnClickListener(this);
        findViewById(R.id.dialog_cancel_button).setOnClickListener(this);


        // Set dialog title according to mode
        TextView dialog_title = findViewById(R.id.dialog_title);
        String titleText;
        if(isEdit)
            titleText = "Edit";
        else
            titleText = "New";

        titleText += " Reminder!";
        dialog_title.setText(titleText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_save_button:
                TextInputLayout reminder_text_input = findViewById(R.id.dialog_reminder_text);
                String reminderText = reminder_text_input.getEditText().getText().toString();
                boolean isImportant = ((CheckBox)findViewById(R.id.dialog_important_checkbox)).isChecked();
                if(isEdit)
                    // TODO: Handle reminder edit here, use reminderID, isImportant and reminderText
                    System.out.println("Editing reminder " + reminderID);
                else
                    // TODO: Handle new reminder here, use reminderText and isImportant
                    System.out.println("New reminder");
                System.out.println(reminderText);
                System.out.println(isImportant);
                break;
            case R.id.dialog_cancel_button:
                break;
        }
        dismiss();
    }
}
