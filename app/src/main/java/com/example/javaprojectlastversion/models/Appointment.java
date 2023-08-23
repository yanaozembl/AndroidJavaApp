package com.example.javaprojectlastversion.models;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Appointment",
        foreignKeys = @ForeignKey(entity = Doctor.class, parentColumns = "id", childColumns = "doctorId", onUpdate = CASCADE, onDelete = CASCADE))
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "doctorId")
    private int doctorId;
    @ColumnInfo(name = "date")
    private String date;

    public Appointment(int doctorId, String date) {
        this.doctorId = doctorId;
        this.date = date;
    }

    public Appointment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
