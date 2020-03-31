package com.example.reminder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Reminder> {
    public CustomArrayAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Reminder reminder = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.reminder_list_item, parent, false);
        }

        // TODO: starting here is what should be done with the reminders data to update the UI
        // Get the layout elements to update
        TextView reminder_text = convertView.findViewById(R.id.reminder_text);
        ImageView reminder_importance_marker = convertView.findViewById(
                R.id.reminder_importance_marker);

        // Update layout elements
        reminder_text.setText(reminder.getContent());
        if(reminder.getImportant()) {
            reminder_importance_marker.setForeground(new ColorDrawable(Color.RED));
        }
        else {
            reminder_importance_marker.setForeground(new ColorDrawable(Color.GREEN));
        }
        // TODO: up till here

        // Return the completed view to render on screen
        return convertView;
    }
}
