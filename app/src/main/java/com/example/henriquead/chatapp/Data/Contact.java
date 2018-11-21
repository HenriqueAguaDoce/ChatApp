package com.example.henriquead.chatapp.Data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String fristName;
    private String lastName;

    @Embedded //Vai pegar nos campos que têm e toma-los como presentes na entidade
    private Coordinates coordinates;

    public Contact(long id, String fristName, String lastName, Coordinates coordinates) {
        this.id = id;
        this.fristName = fristName;
        this.lastName = lastName;
        this.coordinates = coordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getFullName(){
        return this.fristName + " " + this.lastName;
    }

}
