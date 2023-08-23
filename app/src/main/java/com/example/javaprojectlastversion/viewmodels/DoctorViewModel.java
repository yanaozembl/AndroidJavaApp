package com.example.javaprojectlastversion.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.repositories.MyRepository;

import java.util.List;

public class DoctorViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<List<Doctor>> allDoctors;

    public DoctorViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public LiveData<List<Doctor>> getDoctors(){
        allDoctors = repository.getDoctors();
        return allDoctors;
    }

    public void insertDoctor(Doctor doctor){
        repository.insertDoctor(doctor);

    }

    public void updateDoctor(Doctor doctor){
        repository.updateDoctor(doctor);    }

    public void deleteDoctor(Doctor doctor){
        repository.deleteDoctor(doctor);
    }

    public void deleteAllDoctors(){
        repository.deleteAllDoctors();
    }

}
