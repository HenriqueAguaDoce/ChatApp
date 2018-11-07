package com.example.henriquead.chatapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henriquead.chatapp.Data.Message;
import com.example.henriquead.chatapp.Data.MessageDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewChatActivity extends AppCompatActivity {
    private ChatAdapter adapter;
    private EditText createMessage;
    private String EXTRA = "position";
    private long pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chat);

        RecyclerView recyclerView = findViewById(R.id.messagesList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.adapter = new ChatAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Intent i = getIntent();
        long name = i.getLongExtra(EXTRA,0);
        this.pos = name;

        this.createMessage = findViewById(R.id.txtCreateMessage);
    }

    public static void start(Context context, long contactID) {
        Intent starter = new Intent(context, ViewChatActivity.class);
        starter.putExtra("position", contactID);
        context.startActivity(starter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chat, menu);

        //return super.onCreateOptionsMenu(menu);
        //Temos de devolver true, porque se não ele não mostra o menu

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_messages:
                this.getUserResponse();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog getUserResponse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Pretende apagar as mensagens ?")
                .setTitle("Apagar Mensagens");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                adapter.deleteMessages();
            }
        });
        builder.setNegativeButton("Não", null);

        builder.setCancelable(false); // Se carregar fora sai do dialog, assim somos obrigados a carregar numa opção

        return builder.show();


    }

    public void btn_imgSendMessagem(View view) {
        String newMessage = this.createMessage.getText().toString();

        Message message = new Message(0, this.pos, newMessage);
        long id = MessageDatabase.getInstance(this).messageDao().insert(message);

        this.createMessage.setText("");

        List<Message> messages = MessageDatabase.getInstance(this).messageDao().getAllMessages(this.pos);
        this.adapter.setData(messages);
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Message> messages = MessageDatabase.getInstance(this).messageDao().getAllMessages(this.pos);
        this.adapter.setData(messages);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        private TextView message;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            this.message = itemView.findViewById(R.id.txtMessageDisplay);
        }

        public void bind(Message message){
            this.message.setText(message.getTextContent());
        }
    }

    public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder>{
        List<Message> data = new ArrayList<>();



        public void deleteMessages(){
            MessageDatabase.getInstance(ViewChatActivity.this).messageDao().deleteMessagesForContact(pos);
            adapter.deleteAll();

        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_layout, viewGroup, false);

            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
            Message message = data.get(i);
            chatViewHolder.bind(message);

        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public void setData(List<Message> messages) {
            this.data = messages;
            notifyDataSetChanged();
        }

        public void deleteAll(){
            int count = data.size();
            data.clear();
            notifyItemRangeRemoved(0, count);
        }
    }
}
