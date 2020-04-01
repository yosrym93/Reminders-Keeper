package com.example.reminder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ReminderDialog extends Dialog implements android.view.View.OnClickListener {
    private Reminder activeReminder;
    private boolean isEdit;

     ReminderDialog(Activity a, boolean isEdit, Reminder activeReminder) {
        super(a);
        this.isEdit = isEdit;
        this.activeReminder = activeReminder;
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

        //In edit mode, fill the input text editor and check box with current reminder data
        if(isEdit) {
            TextInputLayout reminderTextInput = findViewById(R.id.dialog_reminder_text);
            EditText textEditor = reminderTextInput.getEditText();
            if(textEditor != null)
                textEditor.setText(activeReminder.getContent());

            CheckBox isImportantCheckbox = findViewById(R.id.dialog_important_checkbox);
            isImportantCheckbox.setChecked(activeReminder.getImportant());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_save_button:
                // Get input reminder text
                TextInputLayout reminderTextInput = findViewById(R.id.dialog_reminder_text);
                EditText textEditor = reminderTextInput.getEditText();
                String inputReminderText = "";

                if(textEditor != null)
                    inputReminderText = reminderTextInput.getEditText().getText().toString();

                // Get importance status
                CheckBox isImportantCheckbox = findViewById(R.id.dialog_important_checkbox);
                boolean isImportant = isImportantCheckbox.isChecked();

                if(isEdit)
                    // TODO: Handle reminder edit here, use activeReminder, isImportant and inputReminderText
                    System.out.println("Editing reminder " + activeReminder.getId());
                else
                    // TODO: Handle new reminder here, use inputReminderText and isImportant
                    System.out.println("New reminder");
                break;
            case R.id.dialog_cancel_button:
                break;
        }
        dismiss();
    }
}
