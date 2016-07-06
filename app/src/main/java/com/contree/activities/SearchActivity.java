package com.contree.activities;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.contree.R;
import com.contree.adapters.SearchAdapter;
import com.contree.database.DBActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 7/7/2016.
 */
public class SearchActivity extends AppCompatActivity {

    @Bind(R.id.listSearchItems)
    ListView listSearchItems;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private SearchAdapter adapter;
    DBActivity db;
    Cursor cursor;
    private static final String TAG = SearchActivity.class.getName().toString();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.search_hint));

        db = new DBActivity(SearchActivity.this);
        db.open();
        cursor = db.getData();
        db.close();
        if (cursor == null){
            Toast.makeText(SearchActivity.this, "No Data in Database", Toast.LENGTH_LONG).show();
        }
        adapter = new SearchAdapter(SearchActivity.this, cursor, 0);
        listSearchItems.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    db.open();
                    cursor = db.getDataByPlatform(s);
                    if (cursor == null) {
                        Toast.makeText(SearchActivity.this, "No records found!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SearchActivity.this, cursor.getCount() + " records found!", Toast.LENGTH_LONG).show();
                    }
                    adapter.swapCursor(cursor);
                    db.close();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    db.open();
                    cursor = db.getDataByPlatform(s);
                    if (cursor != null) {
                        adapter.swapCursor(cursor);
                    }
                    db.close();
                    return false;
                }

            });

        }

        return true;

    }

}

