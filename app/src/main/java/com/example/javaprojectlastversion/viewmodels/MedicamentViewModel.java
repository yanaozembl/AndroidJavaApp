package com.example.javaprojectlastversion.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.repositories.MyRepository;

import java.util.List;

public class MedicamentViewModel extends AndroidViewModel {
    private MyRepository repository;
    private LiveData<List<Medicament>> allMedicaments;

    public MedicamentViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public LiveData<List<Medicament>> getMedicaments(){
        allMedicaments = repository.getMedicaments();
        return allMedicaments;
    }

    public List<Medicament> getPastMedicaments(String startDate, String endDate){
        return repository.getPastMedicaments(startDate, endDate);
    }

    public List<Medicament> getPresentMedicaments(String startDate, String endDate){
        return repository.getPresentMedicaments(startDate, endDate);
    }

    public List<Medicament> getFutureMedicaments(String startDate){
        return repository.getFutureMedicaments(startDate);
    }

    public void insertMedicament (Medicament medicament){
        repository.insertMedicament(medicament);

    }

    public void updateMedicament(Medicament medicament){
        repository.updateMedicament(medicament);    }

    public void deleteMedicament(Medicament medicament){
        repository.deleteMedicament(medicament);
    }
}
