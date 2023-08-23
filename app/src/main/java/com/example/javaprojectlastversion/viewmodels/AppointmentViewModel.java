package com.example.javaprojectlastversion.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.javaprojectlastversion.MyDB;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.repositories.MyRepository;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {

    private MyRepository repository;
    private LiveData<List<Appointment>> allAppointments;
    public static LiveData<List<Doctor>> allDoctors;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public LiveData<List<Appointment>> getAppointments(){
        allAppointments = repository.getAppointments();
        return allAppointments;
    }

    public LiveData<List<Appointment>> getFutureAppointment(String date){
        return repository.getFutureAppointment(date);
    }

    public LiveData<List<Appointment>>getPastAppointment(String date){
        return repository.getPastAppointment(date);
    }

    public LiveData<List<Doctor>> getDoctors(){
        allDoctors = repository.getDoctors();
        return allDoctors;
    }

    public void insertAppointment(Appointment appointment){
        repository.insertAppointment(appointment);
    }

    public void updateAppointment(Appointment appointment){
        repository.updateAppointment(appointment);    }

    public void deleteAppointment(Appointment appointment){
        repository.deleteAppointment(appointment);
    }

    public void deleteAllAppointments(){
        repository.deleteAllAppointments();
    }

}
