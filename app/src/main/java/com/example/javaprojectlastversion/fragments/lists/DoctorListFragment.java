package com.example.javaprojectlastversion.fragments.lists;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.adapters.DoctorAdapter;
import com.example.javaprojectlastversion.fragments.editing.EditDoctorFragment;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.DoctorViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class DoctorListFragment extends Fragment {

    private DoctorViewModel doctorViewModel;
    private List<Doctor> currentDoctors;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        DoctorAdapter adapter = new DoctorAdapter();
        recyclerView.setAdapter(adapter);

        doctorViewModel = ViewModelProviders.of(DoctorListFragment.this).get(DoctorViewModel.class);
        doctorViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                adapter.setNotes(doctors);
                currentDoctors = doctors;
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
                Doctor doctor = adapter.getDoctorAt(position);
                doctorViewModel.deleteDoctor(doctor);
                Toast.makeText(getContext(), "Доктор удален(-а)", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor doctor) {

                EditDoctorFragment fragment2 = new EditDoctorFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                bundle.putInt("currentId", doctor.getId());
                bundle.putString("currentName", doctor.getName());
                bundle.putString("currentSpeciality", doctor.getSpeciality());
                bundle.putString("currentPhone", doctor.getPhone());

                fragment2.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_layout, fragment2).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        EditText searchEditText = view.findViewById(R.id.edit_text_search_for_doctor);
        searchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ArrayList<Doctor> newDoctors = new ArrayList<>();
                for (Doctor doctor: currentDoctors
                     ) {
                    if(doctor.getName().toLowerCase().contains(s.toString().toLowerCase()) || doctor.getSpeciality().toLowerCase().contains(s.toString().toLowerCase()))
                        newDoctors.add(doctor);
                }
                adapter.setNotes(newDoctors);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor_list, container, false);
    }


}