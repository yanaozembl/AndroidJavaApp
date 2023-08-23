package com.example.javaprojectlastversion.fragments.lists;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.adapters.AppointmentAdapter;
import com.example.javaprojectlastversion.adapters.MedicamentAdapter;
import com.example.javaprojectlastversion.fragments.editing.EditAppointmentFragment;
import com.example.javaprojectlastversion.fragments.editing.EditMedicamentFragment;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.viewmodels.MedicamentViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicamentListFragment extends Fragment {

    private MedicamentViewModel medicamentViewModel;
    private List<Medicament> currentMedicaments;
    private MedicamentAdapter adapter;
    private TextView medicamentListNameTextView;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();

        medicamentListNameTextView = view.findViewById(R.id.text_view_medicament_list_name);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_medicament);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new MedicamentAdapter();
        recyclerView.setAdapter(adapter);

        medicamentViewModel = ViewModelProviders.of(MedicamentListFragment.this).get(MedicamentViewModel.class);
        medicamentViewModel.getMedicaments().observe(getViewLifecycleOwner(), new Observer<List<Medicament>>() {
            @Override
            public void onChanged(List<Medicament> medicaments) {
                adapter.setNotes(medicaments);
                currentMedicaments = medicaments;
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Medicament medicament = adapter.getMedicamentAt(position);
                medicamentViewModel.deleteMedicament(medicament);
                Toast.makeText(getContext(), "Лекарство удалено", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MedicamentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Medicament medicament) {

                EditMedicamentFragment fragment2 = new EditMedicamentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                bundle.putInt("currentId", medicament.getId());
                bundle.putString("currentStartDate", medicament.getStartDate());
                bundle.putString("currentEndDate", medicament.getEndDate());
                bundle.putString("currentName", medicament.getName());

                fragment2.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_layout, fragment2).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicament_list, container, false);
    }

    public void showPastMedicaments(){
        String currentDate = LocalDate.now().toString();
        List<Medicament> newMedicaments = medicamentViewModel.getPastMedicaments(currentDate,currentDate);
        adapter.setNotes(newMedicaments);
        medicamentListNameTextView.setText("Прием закончен");
    }

    public void showPresentMedicaments(){
        String currentDate = LocalDate.now().toString();
        List<Medicament> newMedicaments = medicamentViewModel.getPresentMedicaments(currentDate,currentDate);
        adapter.setNotes(newMedicaments);
        medicamentListNameTextView.setText("Прием в данное время");

    }

    public void showFutureMedicaments(){
        String currentDate = LocalDate.now().toString();
        List<Medicament> newMedicaments = medicamentViewModel.getFutureMedicaments(currentDate);
        adapter.setNotes(newMedicaments);
        medicamentListNameTextView.setText("Прием назначен");

    }

    public void showAllMedicaments(){
        adapter.setNotes(currentMedicaments);
        medicamentListNameTextView.setText("Все");

    }
}