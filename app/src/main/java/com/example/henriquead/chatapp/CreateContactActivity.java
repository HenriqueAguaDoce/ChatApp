package com.example.henriquead.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }


    public void btnAddNewConctactToList(View view) {

    }
}
