package com.example.henriquead.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henriquead.chatapp.Data.Contact;
import com.example.henriquead.chatapp.Data.MessageDatabase;

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.contacts_map:
                ConctactsMapActivity.start(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Contact> contacts = MessageDatabase.getInstance(this).contactDao().getAllContacts();
        this.adapter.setData(contacts);
    }

    public void btnCreateContact(View view) {
        CreateContactActivity.start(MainActivity.this);
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView id;
        private TextView coordID;
        private ImageView delete;

        public ContactViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.txtContactName);
            this.id = itemView.findViewById(R.id.txtListContactID);
            this.delete = itemView.findViewById(R.id.imgDeleteConctact);
            this.coordID = itemView.findViewById(R.id.txtCoordinatesID);

            this.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    adapter.delete(pos);
                }
            });

            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    adapter.delete(pos);

                    return true; // É para saber se tratei ou não do evento, digo sempre que sim :)
                }
            });


            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long contactID = adapter.getContactID(getAdapterPosition());
                    ViewChatActivity.start(MainActivity.this, contactID);

                    //Toast.makeText(MainActivity.this, "Item clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(Contact contact){
            this.name.setText(contact.getFullName());
            this.id.setText(Integer.toString((int) contact.getId()));
            this.coordID.setText(contact.getCoordinates().getLatitude() +", " + contact.getCoordinates().getLongitude());
        }
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>{
        List<Contact> data = new ArrayList<>();

        public long getContactID(int pos){
            Contact contact = data.get(pos);
            return contact.getId();
        }


        public void delete(int position){
            Contact contact = data.get(position);
            MessageDatabase.getInstance(MainActivity.this).contactDao().delete(contact);
            data.remove(position);
            notifyItemRemoved(position);
        }


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
