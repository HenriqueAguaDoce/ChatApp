package com.example.henriquead.chatapp.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.henriquead.chatapp.Data.dao.ContactDao;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)

public abstract class ContactDatabase extends RoomDatabase {
    public static ContactDatabase instance = null;

    public static ContactDatabase getInstance(Context context){
        context = context.getApplicationContext();

        if (instance == null){
            // criar instância

            instance = Room.databaseBuilder(context, ContactDatabase.class, "contacts_db")
                    .allowMainThreadQueries()  //
                    .fallbackToDestructiveMigration() // se o modelo mudar apagamos a tabela e criamos uma nova
                    .build();
        }

        return instance;
    }

    public abstract ContactDao contactDao();

}
