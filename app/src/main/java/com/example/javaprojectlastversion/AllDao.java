package com.example.javaprojectlastversion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;

@Dao
public interface AllDao {

    @Insert
    void insert(Appointment appointment);
    @Insert
    void insert(Doctor doctor);
    @Insert
    void insert(Medicament medicament);
    @Insert
    void insert(Diagnose diagnose);

    @Update
    void update(Appointment appointment);
    @Update
    void update(Doctor doctor);
    @Update
    void update(Medicament medicament);
    @Update
    void update(Diagnose diagnose);

    @Delete
    void delete(Appointment appointment);
    @Delete
    void delete(Doctor doctor);
    @Delete
    void delete(Medicament medicament);
    @Delete
    void delete(Diagnose diagnose);

    @Query("DELETE FROM Appointment")
    void deleteAllAppointments();

    @Query("DELETE FROM Doctor")
    void deleteAllDoctors();

    @Query("DELETE FROM Medicament")
    void deleteAllMedicament();

    @Query("DELETE FROM Diagnose")
    void deleteAllDiagnoses();


    @Query("SELECT * FROM Doctor")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM Doctor")
    List<Doctor> getDoctorList();

    @Query("SELECT * FROM Appointment")
    LiveData<List<Appointment>> getAllAppointments();

    @Query("SELECT id FROM Appointment WHERE date = :date")
    int getAppointmentId(String date);

    @Query("SELECT * FROM Appointment WHERE date > :date")
    LiveData<List<Appointment>> getFutureAppointment(String date);

    @Query("SELECT * FROM Appointment WHERE date <= :date")
    LiveData<List<Appointment>> getPastAppointment(String date);

    @Query("SELECT * FROM Medicament")
    LiveData<List<Medicament>> getAllMedicament();

    @Query("SELECT * FROM Medicament WHERE id = :id")
    Medicament getMedicamentById(int id);

    @Query("SELECT id FROM Medicament WHERE name = :name")
    int getMedicamentId(String name);

    @Query("SELECT * FROM Medicament WHERE startDate < :startDate and endDate < :endDate")
    List<Medicament> getPastMedicaments(String startDate, String endDate);

    @Query("SELECT * FROM Medicament WHERE startDate <= :startDate and endDate >= :endDate")
    List<Medicament> getPresentMedicaments(String startDate, String endDate);

    @Query("SELECT * FROM Medicament WHERE startDate > :startDate")
    List<Medicament> getFutureMedicaments(String startDate);

    @Query("SELECT * FROM Diagnose")
    LiveData<List<Diagnose>> getAllDiagnoses();
}
