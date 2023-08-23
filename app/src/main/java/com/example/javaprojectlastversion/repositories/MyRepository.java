package com.example.javaprojectlastversion.repositories;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

import com.example.javaprojectlastversion.AllDao;
import com.example.javaprojectlastversion.MyDB;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;

public class MyRepository {

    private AllDao allDao;

    private LiveData<List<Doctor>> doctors;
    private LiveData<List<Medicament>> medicaments;
    private LiveData<List<Appointment>> appointments;
    private LiveData<List<Diagnose>> diagnosis;

    public MyRepository(Application application) {

        MyDB database = MyDB.getInstance(application);
        allDao = database.allDao();
    }

    public LiveData<List<Doctor>> getDoctors() {

        return allDao.getAllDoctors();

    }

    public List<Doctor> getDoctorList() {

        return allDao.getDoctorList();

    }

    public LiveData<List<Medicament>> getMedicaments() {

        return allDao.getAllMedicament();

    }

    public int getMedicamentId(String name) {

        return allDao.getMedicamentId(name);

    }

    public Medicament getMedicamentById(int id) {

        return allDao.getMedicamentById(id);

    }

    public List<Medicament> getPastMedicaments(String startDate, String endDate) {

        return allDao.getPastMedicaments(startDate, endDate);

    }

    public List<Medicament> getPresentMedicaments(String startDate, String endDate) {

        return allDao.getPresentMedicaments(startDate, endDate);

    }

    public List<Medicament> getFutureMedicaments(String startDate) {

        return allDao.getFutureMedicaments(startDate);

    }

    public int getAppointmentId(String date) {

        return allDao.getAppointmentId(date);

    }

    public LiveData<List<Appointment>> getAppointments() {

        return allDao.getAllAppointments();

    }

    public LiveData<List<Appointment>> getFutureAppointment(String date) {

        return allDao.getFutureAppointment(date);

    }

    public LiveData<List<Appointment>> getPastAppointment(String date) {

        return allDao.getPastAppointment(date);

    }

    public LiveData<List<Diagnose>> getDiagnosis() {

        return allDao.getAllDiagnoses();

    }


    public void insertDoctor(Doctor doctor){
        new InsertDoctorAsyncTask(allDao).execute(doctor);
    }


    private static class InsertDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private AllDao allDao;

        private InsertDoctorAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Doctor... doctors) {
            allDao.insert(doctors[0]);
            return null;
        }
    }

    public void updateDoctor(Doctor doctor){
        new UpdateDoctorAsyncTask(allDao).execute(doctor);
    }

    private static class UpdateDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private AllDao allDao;

        private UpdateDoctorAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Doctor... doctors) {
            allDao.update(doctors[0]);
            return null;
        }
    }

    public void deleteDoctor(Doctor doctor){
        new DeleteDoctorAsyncTask(allDao).execute(doctor);
    }

    private static class DeleteDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private AllDao allDao;

        private DeleteDoctorAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Doctor... doctors) {
            allDao.delete(doctors[0]);
            return null;
        }
    }

    public void deleteAllDoctors(){
        new DeleteAllDoctorsAsyncTask(allDao).execute();
    }

    private static class DeleteAllDoctorsAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private AllDao allDao;

        private DeleteAllDoctorsAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Doctor... appointments) {
            allDao.deleteAllDoctors();
            return null;
        }
    }

    public void insertMedicament(Medicament medicament){
        new InsertMedicamentAsyncTask(allDao).execute(medicament);
    }

    public void updateMedicament(Medicament medicament){
        new UpdateMedicamentAsyncTask(allDao).execute(medicament);
    }

    public void deleteMedicament(Medicament medicament){
        new DeleteMedicamentAsyncTask(allDao).execute(medicament);
    }

    public void deleteAllMedicament(){
        new DeleteAllMedicamentAsyncTask(allDao).execute();
    }

    private static class InsertMedicamentAsyncTask extends AsyncTask<Medicament, Void, Void> {
        private AllDao allDao;

        private InsertMedicamentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Medicament... medicaments) {
            allDao.insert(medicaments[0]);
            return null;
        }
    }

    private static class UpdateMedicamentAsyncTask extends AsyncTask<Medicament, Void, Void> {
        private AllDao allDao;

        private UpdateMedicamentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Medicament... medicaments) {
            allDao.update(medicaments[0]);
            return null;
        }
    }

    private static class DeleteMedicamentAsyncTask extends AsyncTask<Medicament, Void, Void> {
        private AllDao allDao;

        private DeleteMedicamentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Medicament... medicaments) {
            allDao.delete(medicaments[0]);
            return null;
        }
    }

    private static class DeleteAllMedicamentAsyncTask extends AsyncTask<Medicament, Void, Void> {
        private AllDao allDao;

        private DeleteAllMedicamentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Medicament... medicaments) {
            allDao.deleteAllMedicament();
            return null;
        }
    }

    public void insertAppointment(Appointment appointment){
        new InsertAppointmentAsyncTask(allDao).execute(appointment);
    }

    public void updateAppointment(Appointment appointment){
        new UpdateAppointmentAsyncTask(allDao).execute(appointment);
    }

    public void deleteAppointment(Appointment appointment){
        new DeleteAppointmentAsyncTask(allDao).execute(appointment);
    }

    public void deleteAllAppointments(){
        new DeleteAllAppointmentsAsyncTask(allDao).execute();
    }


    private static class InsertAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AllDao allDao;

        private InsertAppointmentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Appointment... appointments) {
            allDao.insert(appointments[0]);
            return null;
        }
    }

    private static class UpdateAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AllDao allDao;

        private UpdateAppointmentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Appointment... appointments) {
            allDao.update(appointments[0]);
            return null;
        }
    }

    private static class DeleteAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AllDao allDao;

        private DeleteAppointmentAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Appointment... appointments) {
            allDao.delete(appointments[0]);
            return null;
        }
    }

    private static class DeleteAllAppointmentsAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AllDao allDao;

        private DeleteAllAppointmentsAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Appointment... appointments) {
            allDao.deleteAllAppointments();
            return null;
        }
    }


    public void insertDiagnose(Diagnose diagnose){
        new InsertDiagnoseAsyncTask(allDao).execute(diagnose);
    }

    public void updateDiagnose(Diagnose diagnose){
        new UpdateDiagnoseAsyncTask(allDao).execute(diagnose);
    }

    public void deleteDiagnose(Diagnose diagnose){
        new DeleteDiagnoseAsyncTask(allDao).execute(diagnose);
    }

    public void deleteAllDiagnoses(){
        new DeleteAllDiagnosesAsyncTask(allDao).execute();
    }

    private static class InsertDiagnoseAsyncTask extends AsyncTask<Diagnose, Void, Void> {
        private AllDao allDao;

        private InsertDiagnoseAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Diagnose... diagnoses) {
            allDao.insert(diagnoses[0]);
            return null;
        }
    }

    private static class UpdateDiagnoseAsyncTask extends AsyncTask<Diagnose, Void, Void> {
        private AllDao allDao;

        private UpdateDiagnoseAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Diagnose... diagnoses) {
            allDao.update(diagnoses[0]);
            return null;
        }
    }

    private static class DeleteDiagnoseAsyncTask extends AsyncTask<Diagnose, Void, Void> {
        private AllDao allDao;

        private DeleteDiagnoseAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Diagnose... diagnoses) {
            allDao.delete(diagnoses[0]);
            return null;
        }
    }

    private static class DeleteAllDiagnosesAsyncTask extends AsyncTask<Diagnose, Void, Void> {
        private AllDao allDao;

        private DeleteAllDiagnosesAsyncTask(AllDao allDao){
            this.allDao = allDao;
        }
        @Override
        protected Void doInBackground(Diagnose... appointments) {
            allDao.deleteAllDiagnoses();
            return null;
        }
    }


}

