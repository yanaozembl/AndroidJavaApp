package com.example.javaprojectlastversion.fragments.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaprojectlastversion.adapters.AppointmentAdapter;
import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.adapters.DoctorAdapter;
import com.example.javaprojectlastversion.fragments.editing.EditAppointmentFragment;
import com.example.javaprojectlastversion.fragments.editing.EditDoctorFragment;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.AppointmentViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AppointmentListFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    private List<Doctor> currentDoctors;
    private List<Appointment> futureAppointments, pastAppointments;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();

        RecyclerView futureRecyclerView = view.findViewById(R.id.recycler_view_future_appointment);
        futureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        futureRecyclerView.setHasFixedSize(true);

        RecyclerView pastRecyclerView = view.findViewById(R.id.recycler_view_past_appointment);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        pastRecyclerView.setHasFixedSize(true);

        AppointmentAdapter futureAdapter = new AppointmentAdapter();
        AppointmentAdapter pastAdapter = new AppointmentAdapter();

        appointmentViewModel = ViewModelProviders.of(AppointmentListFragment.this).get(AppointmentViewModel.class);

        appointmentViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                currentDoctors = doctors;
                futureAdapter.getDoctors(currentDoctors);
                pastAdapter.getDoctors(currentDoctors);
                futureRecyclerView.setAdapter(futureAdapter);
                pastRecyclerView.setAdapter(pastAdapter);
            }
        });

        String currentDate = LocalDate.now().toString();
        String currentTime = LocalTime.now().toString();
        String currentDateAndTime = currentDate + " " + currentTime.substring(0, 5);

        appointmentViewModel.getFutureAppointment(currentDateAndTime).observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                futureAdapter.setNotes(appointments);
            }
        });

        appointmentViewModel.getPastAppointment(currentDateAndTime).observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                pastAdapter.setNotes(appointments);
            }
        });

        futureAdapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Appointment appointment) {

                EditAppointmentFragment fragment2 = new EditAppointmentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                bundle.putInt("currentId", appointment.getId());
                bundle.putInt("currentDoctorId", appointment.getDoctorId());
                bundle.putString("currentDate", appointment.getDate());

                fragment2.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_layout, fragment2).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        pastAdapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Appointment appointment) {

                EditAppointmentFragment fragment2 = new EditAppointmentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                bundle.putInt("currentId", appointment.getId());
                bundle.putInt("currentDoctorId", appointment.getDoctorId());
                bundle.putString("currentDate", appointment.getDate());

                fragment2.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_layout, fragment2).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_list, container, false);
    }
}