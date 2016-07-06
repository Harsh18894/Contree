package com.contree.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.contree.R;
import com.contree.database.DBActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 7/6/2016.
 */
public class DashboardActivity extends AppCompatActivity {

    @Bind(R.id.etPlatform)
    EditText etPlatform;
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnAdd)
    Button btnAdd;
    @Bind(R.id.btnSearch)
    Button btnSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        populate();
    }

    private void populate() {
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.dashboard_activity));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnAdd.setBackgroundResource(R.drawable.ripple);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnSearch.setBackgroundResource(R.drawable.ripple);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBActivity db = new DBActivity(DashboardActivity.this);
                db.open();
                db.insertDB(etPlatform.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString());
                Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                db.close();
                etPlatform.getText().clear();
                etUsername.getText().clear();
                etPassword.getText().clear();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                startActivity(intent);


            }
        });

    }
}
