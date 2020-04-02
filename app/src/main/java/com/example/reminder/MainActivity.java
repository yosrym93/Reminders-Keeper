package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RemindersDatabaseManager databaseManager;
    RemindersSimpleCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new RemindersDatabaseManager(this);
        databaseManager.open();

        Cursor dbCursor = databaseManager.fetchAllReminders();

        ListView remindersList = findViewById(R.id.reminders_list);
        //get text view that content will be written to
        int [] id = {R.id.reminder_text};
        //get name of column content to attach it to the text views
        String[] content = new String[] {RemindersDatabaseManager.COL_CONTENT};
        //get cursor adapter
        cursorAdapter = new RemindersSimpleCursorAdapter(this,
                R.layout.reminder_list_item,dbCursor, content, id,0);
        // Set the adapter for the list view
        remindersList.setAdapter(cursorAdapter);


        // Set on list item click event handler
        remindersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //get current item as reminder from data base through the cursor
                Cursor currentCursor = (Cursor)parent.getItemAtPosition(position);
                //currentCursor.moveToFirst();
                boolean  isImportant = false;
                if (currentCursor.getString(RemindersDatabaseManager.INDEX_IMPORTANT).equals("1")){
                    isImportant = true;
                }
                final Reminder activeReminder = new Reminder(
                        currentCursor.getInt(RemindersDatabaseManager.INDEX_ID),
                        currentCursor.getString(RemindersDatabaseManager.INDEX_CONTENT),
                        isImportant);

                // Create a pop up to edit/delete the reminder
                PopupMenu popup = new PopupMenu(MainActivity.this, view);

                // Set on popup menu item click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_reminder:
                                ReminderDialog dialog = new ReminderDialog(true, activeReminder,MainActivity.this);
                                dialog.show();
                                break;
                            case R.id.delete_reminder:
                                deleteReminder(activeReminder.getId());
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
                ReminderDialog dialog = new ReminderDialog(false, null,this);
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy () {
        databaseManager.close();
        super.onDestroy();
    }

    public void updateUI() {
        Cursor newCursor = databaseManager.fetchAllReminders();
        cursorAdapter.changeCursor(newCursor);

    }

    public void addNewReminder(Reminder newReminder){
        databaseManager.createReminder(newReminder);
        this.updateUI();
    }

    public void editReminder(Reminder editedReminder){
        databaseManager.updateReminder(editedReminder);
        this.updateUI();
    }
    public void deleteReminder(int id){
        databaseManager.deleteReminderById(id);
        updateUI();

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
