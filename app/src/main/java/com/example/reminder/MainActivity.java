package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import  com.example.reminder.RemindersDbAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private int activeReminderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        RemindersDbAdapter rDb = new RemindersDbAdapter(this);
        rDb.open();
        rDb.createReminder("lolll",true);
        rDb.fetchReminderById(1);



        //
        // Test adapter, TODO: replace with cursor adapter
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, getTestReminders());

        // Set the adapter for the list view
        ListView remindersList = findViewById(R.id.reminders_list);
        remindersList.setAdapter(adapter);

        // Set on list item click event handler
        remindersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked reminder id
                Reminder reminder = (Reminder) parent.getItemAtPosition(position);
                activeReminderID = reminder.getId();

                // Create a pop up to edit/delete the reminder
                PopupMenu popup = new PopupMenu(MainActivity.this, view);

                // Set on popup menu item click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_reminder:
                                System.out.println("Edit Reminder!");
                                ReminderDialog dialog = new ReminderDialog(
                                        MainActivity.this, true, activeReminderID);
                                dialog.show();
                                break;
                            case R.id.delete_reminder:
                                System.out.println("Delete Reminder!");
                                //TODO: add delete_reminder handling, use activeReminderID
                                break;
                        }
                        return true;
                    }
                });

                popup.inflate(R.menu.reminder_popup_menu);
                popup.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu option selection
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                break;
            case R.id.new_reminder:
                ReminderDialog dialog = new ReminderDialog(this, false, -1);
                dialog.show();
                break;
        }
        return true;
    }

    // Generates a list of reminders for testing
    private ArrayList<Reminder> getTestReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            boolean is_important = i % 2 == 0;
            reminders.add(new Reminder(i, "Reminder "+i, is_important));
        }
        reminders.add(new Reminder(10, "This is a specially long reminder, it has to be very long.", true));
        return reminders;
    }
}
