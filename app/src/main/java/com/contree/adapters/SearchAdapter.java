package com.contree.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.contree.R;
import com.contree.database.DBActivity;

/**
 * Created by Harsh on 7/6/2016.
 */
public class SearchAdapter extends CursorAdapter {

    TextView txtPlatform, txtUsername, txtPassword;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.activity_search_list_single_row, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.txtPlatform = (TextView) view.findViewById(R.id.txtPlatform);
        holder.txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        holder.txtPassword = (TextView) view.findViewById(R.id.txtPassword);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtPlatform.setText(cursor.getString(cursor.getColumnIndex(DBActivity.KEY_PLATFORM)));
        holder.txtUsername.setText(cursor.getString(cursor.getColumnIndex(DBActivity.KEY_USERNAME)));
        holder.txtPassword.setText(cursor.getString(cursor.getColumnIndex(DBActivity.KEY_PASSWORD)));
    }

    static class ViewHolder {
        TextView txtPlatform;
        TextView txtUsername;
        TextView txtPassword;
    }
}
