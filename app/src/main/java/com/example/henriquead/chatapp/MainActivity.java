package com.example.henriquead.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henriquead.chatapp.Data.Contact;
import com.example.henriquead.chatapp.Data.ContactDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.contactList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.adapter = new ContactAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Contact> contacts = ContactDatabase.getInstance(this).contactDao().getAllContacts();
        this.adapter.setData(contacts);
    }

    public void btnCreateContact(View view) {
        CreateContactActivity.start(MainActivity.this);
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView id;

        public ContactViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.txtContactName);
            this.id = itemView.findViewById(R.id.txtListContactID);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    ViewChatActivity.start(MainActivity.this, position);

                    //Toast.makeText(MainActivity.this, "Item clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(Contact contact){
            this.name.setText(contact.getFullName());
            this.id.setText(Integer.toString((int) contact.getId()));
        }
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>{
        List<Contact> data = new ArrayList<>();

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_layout, viewGroup,false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
            Contact contact = data.get(i);
            contactViewHolder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public void setData(List<Contact> contacts) {
            this.data = contacts;
            notifyDataSetChanged();
        }
    }

}
