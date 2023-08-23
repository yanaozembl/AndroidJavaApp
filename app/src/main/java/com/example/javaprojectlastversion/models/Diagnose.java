package com.example.javaprojectlastversion.models;
import static androidx.room.ForeignKey.CASCADE;

import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Diagnose",
        indices = @Index(value = {"diagnosis"}, unique = true),
        foreignKeys = {@ForeignKey(entity = Appointment.class, parentColumns = "id", childColumns = "appointmentId", onUpdate = CASCADE, onDelete = CASCADE),
                @ForeignKey(entity = Medicament.class, parentColumns = {"id"}, childColumns = {"medicament1"}, onUpdate = CASCADE, onDelete = CASCADE),
                @ForeignKey(entity = Medicament.class, parentColumns = {"id"}, childColumns = {"medicament2"}, onUpdate = CASCADE, onDelete = CASCADE),
                @ForeignKey(entity = Medicament.class, parentColumns = {"id"}, childColumns = {"medicament3"}, onUpdate = CASCADE, onDelete = CASCADE),
                @ForeignKey(entity = Medicament.class, parentColumns = {"id"}, childColumns = {"medicament4"}, onUpdate = CASCADE, onDelete = CASCADE),
                @ForeignKey(entity = Medicament.class, parentColumns = {"id"}, childColumns = {"medicament5"}, onUpdate = CASCADE, onDelete = CASCADE)
        })

public class Diagnose {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "appointmentId")
    private int appointmentId;
    @ColumnInfo(name = "diagnosis")
    private String diagnosis;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "attachment")
    private String attachment;
    @ColumnInfo(name = "medicament1")
    private Integer medicament1;
    @ColumnInfo(name = "medicament2")
    private Integer medicament2;
    @ColumnInfo(name = "medicament3")
    private Integer medicament3;
    @ColumnInfo(name = "medicament4")
    private Integer medicament4;
    @ColumnInfo(name = "medicament5")
    private Integer medicament5;

    public Diagnose(int appointmentId, String diagnosis, String description, String attachment, Integer medicament1, Integer medicament2, Integer medicament3, Integer medicament4, Integer medicament5) {
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.description = description;
        this.attachment = attachment;
        this.medicament1 = medicament1;
        this.medicament2 = medicament2;
        this.medicament3 = medicament3;
        this.medicament4 = medicament4;
        this.medicament5 = medicament5;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public Integer getMedicament1() {
        return medicament1;
    }

    public void setMedicament1(Integer medicament1) {
        this.medicament1 = medicament1;
    }
    public Integer getMedicament2() {
        return medicament2;
    }

    public void setMedicament2(Integer medicament2) {
        this.medicament2 = medicament2;
    }
    public Integer getMedicament3() {
        return medicament3;
    }

    public void setMedicament3(Integer medicament3) {
        this.medicament3 = medicament3;
    }
    public Integer getMedicament4() {
        return medicament4;
    }

    public void setMedicament4(Integer medicament4) {
        this.medicament4 = medicament4;
    }
    public Integer getMedicament5() {
        return medicament5;
    }

    public void setMedicament5(Integer medicament5) {
        this.medicament5 = medicament5;
    }
}
