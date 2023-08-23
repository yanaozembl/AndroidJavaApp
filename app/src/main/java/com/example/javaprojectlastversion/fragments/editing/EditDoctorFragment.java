package com.example.javaprojectlastversion.fragments.editing;

import android.content.ContentProviderOperation;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.DoctorViewModel;

import java.util.ArrayList;

public class EditDoctorFragment extends Fragment {

    private DoctorViewModel doctorViewModel;
    public String[] specialities =new String[]{"Стоматолог", "ЛОР", "Офтальмолог", "Кардиолог", "Педиатр", "Терапевт", "Офтальмолог"};
    private Spinner specialitySpinner;
    private int currentId;
    private String oldName, oldSpeciality, oldPhone;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentId = bundle.getInt("currentId", 0);
            oldName =  bundle.getString("currentName");
            oldSpeciality =  bundle.getString("currentSpeciality");
            oldPhone =  bundle.getString("currentPhone");
        }

        doctorViewModel = ViewModelProviders.of(EditDoctorFragment.this).get(DoctorViewModel.class);

        TextView mainLabelTextView = view.findViewById(R.id.text_view_edit_label);
        EditText editNameEditText = view.findViewById(R.id.edit_text_edit_doctor_name);
        EditText phoneEditText = view.findViewById(R.id.edit_text_edit_doctor_phone);

        mainLabelTextView.setText(oldName);
        editNameEditText.setText(oldName);
        phoneEditText.setText(oldPhone);

        specialitySpinner = (Spinner) view.findViewById(R.id.spinner_edit_speciality_of_doctor);
        changeSpinner(specialities);

        int currentPosition = 0;

        for (String speciality:specialities) {

            if(speciality.equals(oldSpeciality)){
                specialitySpinner.setSelection(currentPosition);
                break;
            }
            else ++currentPosition;
        }


        Button editDoctorButton = view.findViewById(R.id.button_edit_doctor);
        editDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String newSpeciality = specialitySpinner.getSelectedItem().toString();

                Doctor newDoctor = new Doctor(newName, newSpeciality, phone);
                newDoctor.setId(currentId);
                doctorViewModel.updateDoctor(newDoctor);

                mainLabelTextView.setText(newName);

                Toast.makeText(getContext(), "Данные изменены", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_doctor, container, false);
    }

    public void changeSpinner(String[] specialities){
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, specialities);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        specialitySpinner.setAdapter(adapter);
    }
}