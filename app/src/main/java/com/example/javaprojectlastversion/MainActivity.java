package com.example.javaprojectlastversion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.javaprojectlastversion.databinding.ActivityMainBinding;
import com.example.javaprojectlastversion.fragments.adding.AddAppointmentFragment;
import com.example.javaprojectlastversion.fragments.adding.AddDiagnoseFragment;
import com.example.javaprojectlastversion.fragments.adding.AddDoctorFragment;
import com.example.javaprojectlastversion.fragments.adding.AddMedicamentFragment;
import com.example.javaprojectlastversion.fragments.lists.DiagnoseListFragment;
import com.example.javaprojectlastversion.fragments.lists.DoctorListFragment;
import com.example.javaprojectlastversion.fragments.lists.AppointmentListFragment;
import com.example.javaprojectlastversion.fragments.lists.MedicamentListFragment;
import com.example.javaprojectlastversion.fragments.PersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public int menuToChoose = R.menu.appointment_menu;
    private MedicamentListFragment medicamentListFragment = new MedicamentListFragment();
    private static final int REQUEST_CODE_READ_CONTACTS=1;
    private static boolean READ_CONTACTS_GRANTED =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AppointmentListFragment());

        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true;
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                /*case R.id.showPersonInfo:
                    replaceFragment(new PersonFragment());
                    break;*/
                case R.id.showDiagnose:
                    replaceFragment(new DiagnoseListFragment());
                    menuToChoose = R.menu.diagnose_menu;
                    break;
                case R.id.showEvents:
                    replaceFragment(new AppointmentListFragment());
                    menuToChoose = R.menu.appointment_menu;
                    break;
                case R.id.showMedicament:
                    medicamentListFragment = new MedicamentListFragment();
                    replaceFragment(medicamentListFragment);
                    menuToChoose = R.menu.medicament_menu;
                    break;
                case R.id.showDoctors:
                    replaceFragment(new DoctorListFragment());
                    menuToChoose = R.menu.doctor_menu;
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(menuToChoose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_doctor:
                replaceFragment(new AddDoctorFragment());
                break;
            case R.id.item_add_appointment:
                replaceFragment(new AddAppointmentFragment());
                break;
            case R.id.item_add_medicament:
                replaceFragment(new AddMedicamentFragment());
                break;
            case R.id.item_add_diagnose:
                replaceFragment(new AddDiagnoseFragment());
                break;
            case R.id.past:
                medicamentListFragment.showPastMedicaments();
                break;
            case R.id.present:
                medicamentListFragment.showPresentMedicaments();
                break;
            case R.id.future:
                medicamentListFragment.showFutureMedicaments();
                break;
            case R.id.allMedicaments:
                medicamentListFragment.showAllMedicaments();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true;
            }
        }
    }


}