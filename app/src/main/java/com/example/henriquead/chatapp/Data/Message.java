package com.example.henriquead.chatapp.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "messages", foreignKeys ={ @ForeignKey(entity = Contact.class,
                                                            parentColumns = "id",
                                                            childColumns = "contactID", onDelete = CASCADE)})

public class Message {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long contactID;
    private String textContent;

    public Message(long id, long contactID, String textContent) {
        this.id = id;
        this.contactID = contactID;
        this.textContent = textContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


}
