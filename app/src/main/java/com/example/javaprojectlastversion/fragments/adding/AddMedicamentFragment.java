package com.example.javaprojectlastversion.fragments.adding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.viewmodels.DoctorViewModel;
import com.example.javaprojectlastversion.viewmodels.MedicamentViewModel;

public class AddMedicamentFragment extends Fragment {

    private MedicamentViewModel medicamentViewModel;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        EditText nameEditText = view.findViewById(R.id.edit_text_medicament);

        NumberPicker startDayNumPicker = view.findViewById(R.id.number_picker_start_day);
        NumberPicker startMonthNumPicker = view.findViewById(R.id.number_picker_start_month);
        NumberPicker startYearNumPicker = view.findViewById(R.id.number_picker_start_year);
        NumberPicker endDayNumPicker = view.findViewById(R.id.number_picker_end_day);
        NumberPicker endMonthNumPicker = view.findViewById(R.id.number_picker_end_month);
        NumberPicker endYearNumPicker = view.findViewById(R.id.number_picker_end_year);

        startDayNumPicker.setMinValue(1);
        startDayNumPicker.setMaxValue(31);
        startMonthNumPicker.setMinValue(1);
        startMonthNumPicker.setMaxValue(12);
        startYearNumPicker.setMinValue(2022);
        startYearNumPicker.setMaxValue(2023);

        endDayNumPicker.setMinValue(1);
        endDayNumPicker.setMaxValue(31);
        endMonthNumPicker.setMinValue(1);
        endMonthNumPicker.setMaxValue(12);
        endYearNumPicker.setMinValue(2022);
        endYearNumPicker.setMaxValue(2023);

        medicamentViewModel = ViewModelProviders.of(AddMedicamentFragment.this).get(MedicamentViewModel.class);

        Button addMedicamentButton = view.findViewById(R.id.button_add_medicament);
        addMedicamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String startDay = String.valueOf(startDayNumPicker.getValue()).toString();
                String startMonth = String.valueOf(startMonthNumPicker.getValue()).toString();
                String startYear = String.valueOf(startYearNumPicker.getValue()).toString();
                String endDay = String.valueOf(endDayNumPicker.getValue()).toString();
                String endMonth = String.valueOf(endMonthNumPicker.getValue()).toString();
                String endYear = String.valueOf(endYearNumPicker.getValue()).toString();

                String startDate = startYear + "-" + startMonth + "-" + startDay;
                String endDate = endYear + "-" + endMonth + "-" + endDay;

                medicamentViewModel.insertMedicament(new Medicament(name, startDate, endDate));
                Toast.makeText(getContext(), "Лекарство "+ name + " добавлено", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_medicament, container, false);
    }
}