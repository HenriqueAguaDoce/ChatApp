package com.example.henriquead.chatapp.Data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.henriquead.chatapp.Data.dao.MessageDao;

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {

    public static MessageDatabase instance = null;

    public static MessageDatabase getInstance(Context context){
        context = context.getApplicationContext();

        if (instance == null){
            // criar instância

            instance = Room.databaseBuilder(context, MessageDatabase.class, "messages_db")
                    .allowMainThreadQueries()  //
                    .fallbackToDestructiveMigration() // se o modelo mudar apagamos a tabela e criamos uma nova
                    .build();
        }

        return instance;
    }

    public abstract MessageDao messageDao();
}
