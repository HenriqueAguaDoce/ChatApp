package com.example.henriquead.chatapp.Data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.henriquead.chatapp.Data.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    long insert(Message message);

    @Update
    int update(Message message);

    @Delete
    int delete(Message message);

    @Query("SELECT * FROM messages WHERE contactID = :contactID")
    List<Message> getAllMessages(long contactID);

}
