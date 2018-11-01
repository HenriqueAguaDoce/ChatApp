package com.example.henriquead.chatapp.Data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.henriquead.chatapp.Data.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insert(Contact contact);

    @Update
    int update(Contact contact);

    @Delete
    int delete(Contact contact);

    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();

}
