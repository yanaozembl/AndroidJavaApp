package com.example.javaprojectlastversion.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.javaprojectlastversion.MyDB;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.repositories.MyRepository;

import java.util.List;

public class DiagnoseViewModel extends AndroidViewModel {

    private MyRepository repository;
    private LiveData<List<Diagnose>> allDiagnoses;
    public static LiveData<List<Appointment>> allAppointments;
    public static LiveData<List<Medicament>> allMedicaments;
    public static LiveData<List<Doctor>> allDoctors;
    public static List<Doctor> doctorList;
    public static int medicamentId;
    public static int appointmentId;

    public DiagnoseViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public LiveData<List<Diagnose>> getDiagnoses(){
        allDiagnoses = repository.getDiagnosis();
        return allDiagnoses;
    }

    public List<Doctor> getDoctorList(){
        doctorList = repository.getDoctorList();
        return doctorList;
    }

    public LiveData<List<Appointment>> getAppointments(){
        allAppointments = repository.getAppointments();
        return allAppointments;
    }

    public LiveData<List<Appointment>> getPastAppointment(String date){
        return repository.getPastAppointment(date);
    }

    public int getMedicamentId(String name){
        medicamentId = repository.getMedicamentId(name);
        return medicamentId;
    }

    public Medicament getMedicamentById(int id){
        return repository.getMedicamentById(id);
    }

    public int getAppointmentId(String date){
        appointmentId = repository.getAppointmentId(date);
        return appointmentId;
    }

    public void insertMedicament (Medicament medicament){
        repository.insertMedicament(medicament);

    }
    public void insertDiagnose(Diagnose diagnose){
        repository.insertDiagnose(diagnose);
    }

    public void updateDiagnose(Diagnose diagnose){
        repository.updateDiagnose(diagnose);    }

    public void deleteDiagnose(Diagnose diagnose){
        repository.deleteDiagnose(diagnose);
    }

    public void deleteAllDiagnoses(){
        repository.deleteAllDiagnoses();
    }

}
