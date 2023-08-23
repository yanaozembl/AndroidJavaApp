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

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.adapters.DiagnoseAdapter;
import com.example.javaprojectlastversion.adapters.DoctorAdapter;
import com.example.javaprojectlastversion.fragments.editing.EditDiagnoseFragment;
import com.example.javaprojectlastversion.fragments.editing.EditDoctorFragment;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.DiagnoseViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DiagnoseListFragment extends Fragment {

    private DiagnoseViewModel diagnoseViewModel;
    private List<Appointment> currentAppointment;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_diagnose);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        DiagnoseAdapter adapter = new DiagnoseAdapter();

        diagnoseViewModel = ViewModelProviders.of(DiagnoseListFragment.this).get(DiagnoseViewModel.class);

        diagnoseViewModel.getAppointments().observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                currentAppointment = appointments;
                adapter.getAppointments(currentAppointment);
                recyclerView.setAdapter(adapter);

            }
        });


        diagnoseViewModel.getDiagnoses().observe(getViewLifecycleOwner(), new Observer<List<Diagnose>>() {
            @Override
            public void onChanged(List<Diagnose> diagnoses) {
                adapter.setNotes(diagnoses);
            }
        });

        adapter.setOnItemClickListener(new DiagnoseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Diagnose diagnose) {

                EditDiagnoseFragment fragment2 = new EditDiagnoseFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                bundle.putInt("currentId", diagnose.getId());
                bundle.putString("currentDiagnoseName", diagnose.getDiagnosis());
                bundle.putString("currentDescription", diagnose.getDescription());
                bundle.putInt("currentAppointmentId", diagnose.getAppointmentId());
                bundle.putString("currentImageUri", diagnose.getAttachment());

                if(diagnose.getMedicament1() !=null)
                    bundle.putInt("currentMedicament1", diagnose.getMedicament1());
                else
                    bundle.putInt("currentMedicament1", 0);

                if (diagnose.getMedicament2() != null)
                    bundle.putInt("currentMedicament2", diagnose.getMedicament2());
                else
                    bundle.putInt("currentMedicament2", 0);

                if (diagnose.getMedicament3() != null)
                    bundle.putInt("currentMedicament3", diagnose.getMedicament3());
                else
                    bundle.putInt("currentMedicament3", 0);

                if (diagnose.getMedicament4() != null)
                    bundle.putInt("currentMedicament4", diagnose.getMedicament4());
                else
                    bundle.putInt("currentMedicament4", 0);

                if (diagnose.getMedicament5() != null)
                    bundle.putInt("currentMedicament5", diagnose.getMedicament5());
                else
                    bundle.putInt("currentMedicament5", 0);

                fragment2.setArguments(bundle);

                fragmentTransaction.replace(R.id.frame_layout, fragment2).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diagnose_list, container, false);
    }
}