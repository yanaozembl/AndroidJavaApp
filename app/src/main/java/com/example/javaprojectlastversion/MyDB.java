package com.example.javaprojectlastversion;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;


@Database(entities = {Doctor.class, Appointment.class, Medicament.class, Diagnose.class}, version = 1)
public abstract class MyDB extends RoomDatabase {

    private static MyDB instance;

    public abstract AllDao allDao();


    public static synchronized MyDB getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDB.class, "healthDb.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private AllDao allDao;

        public InitialDataAsyncTask(MyDB database) {

            allDao = database.allDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            Doctor doctor = new Doctor("Зайцева Мария Александровна", "Стоматолог", "+375445556677");
            allDao.insert(doctor);
            allDao.insert(new Doctor("Воробьева Алина Вячеславовна", "Педиатр",  "+375294959699"));
            allDao.insert(new Doctor("Кузнецова Дарья Вадимовна", "Пульмонолог", "+375333920209"));
            allDao.insert(new Doctor("Котова Елена Александровна", "ЛОР", "+375443332211"));

            allDao.insert(new Appointment(1, "2022-12-02 14:00"));
            allDao.insert(new Appointment(2, "2023-10-08 11:00"));
            allDao.insert(new Appointment(2, "2023-12-09 15:30"));
            allDao.insert(new Appointment(3, "2023-01-01 10:20"));
            allDao.insert(new Appointment(4, "2023-09-13 19:10"));

            allDao.insert(new Medicament( "Сироп Доктор МОМ", "2023-01-02", "2023-01-10"));
            allDao.insert(new Medicament("Синглони", "2023-01-02", "2023-02-18"));
            allDao.insert(new Medicament("Антигриппин", "2022-11-08", "2022-11-18"));
            allDao.insert(new Medicament("Миг", "2022-09-13", "2022-09-23"));
            allDao.insert(new Medicament("Тонзилгон", "2022-12-02", "2022-12-22"));
            allDao.insert(new Medicament("Tooth Moose", "2022-12-02", "2023-01-02"));

            allDao.insert(new Diagnose(4, "Острый трахеит", "Боль в горле, острый кашель", "content://com.android.providers.media.documents/document/image%3A28", 1, 2, 3, null, null));
            allDao.insert(new Diagnose(1, "Глубокий кариес", "Зубная боль, кариес третьей степени", "content://com.android.providers.media.documents/document/image%3A27", 5, 6, null, null, null));

            return null;
        }
    }

}
