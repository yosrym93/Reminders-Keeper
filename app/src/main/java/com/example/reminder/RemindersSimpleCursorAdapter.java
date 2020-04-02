package com.example.reminder;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.content.Context;
import android.database.Cursor;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.RequiresApi;

/**
 * Created by engMa_000 on 2017-04-03.
 */

public class RemindersSimpleCursorAdapter extends SimpleCursorAdapter {

    public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[]
            from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
    //to use a viewholder, you must override the following two methods and define a ViewHolder class
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.colImp = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT);
            holder.listTab = view.findViewById(R.id.reminder_importance_marker);
            view.setTag(holder);
        }


        if (cursor.getInt(holder.colImp) == 0) {
            holder.listTab.setForeground(new ColorDrawable(Color.RED));
        } else {
            holder.listTab.setForeground(new ColorDrawable(Color.GREEN));
        }



    }
    static class ViewHolder {
        //store the column index
        int colImp;
        //store the view
        ImageView listTab;
    }

}
