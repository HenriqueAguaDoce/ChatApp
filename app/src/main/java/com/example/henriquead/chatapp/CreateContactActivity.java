package com.example.henriquead.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.henriquead.chatapp.Data.Contact;
import com.example.henriquead.chatapp.Data.ContactDatabase;

public class CreateContactActivity extends AppCompatActivity {
    private EditText fName, lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        this.fName = findViewById(R.id.txtNewConctactFName);
        this.lName = findViewById(R.id.txtNewConctactLName);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }


    public void btnAddNewConctactToList(View view) {
        String first = this.fName.getText().toString();
        String last = this.lName.getText().toString();

        Contact contact = new Contact(0, first, last);

        ContactDatabase.getInstance(this).contactDao().insert(contact);
        finish();
    }
}
