package com.example.javaprojectlastversion.fragments.editing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.fragments.adding.AddMedicamentFragment;
import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.viewmodels.MedicamentViewModel;

public class EditMedicamentFragment extends Fragment {


    private MedicamentViewModel medicamentViewModel;
    private int currentId, oldStatus;
    private String oldStartDate, oldEndDate, oldName;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentId = bundle.getInt("currentId", 0);
            oldStartDate =  bundle.getString("currentStartDate");
            oldEndDate =  bundle.getString("currentEndDate");
            oldName =  bundle.getString("currentName");
        }

        EditText nameEditText = view.findViewById(R.id.edit_text_edit_medicament);
        nameEditText.setText(oldName);

        NumberPicker startDayNumPicker = view.findViewById(R.id.number_picker_edit_start_day);
        NumberPicker startMonthNumPicker = view.findViewById(R.id.number_picker_edit_start_month);
        NumberPicker startYearNumPicker = view.findViewById(R.id.number_picker_edit_start_year);
        NumberPicker endDayNumPicker = view.findViewById(R.id.number_picker_edit_end_day);
        NumberPicker endMonthNumPicker = view.findViewById(R.id.number_picker_edit_end_month);
        NumberPicker endYearNumPicker = view.findViewById(R.id.number_picker_edit_end_year);

        startDayNumPicker.setMinValue(0);
        startDayNumPicker.setMaxValue(30);
        String[] dayValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31"};
        startDayNumPicker.setDisplayedValues(dayValues);

        startMonthNumPicker.setMinValue(0);
        startMonthNumPicker.setMaxValue(11);
        String[] monthValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        startMonthNumPicker.setDisplayedValues(monthValues);

        startYearNumPicker.setMinValue(2023);
        startYearNumPicker.setMaxValue(2023);

        endDayNumPicker.setMinValue(0);
        endDayNumPicker.setMaxValue(30);
        endDayNumPicker.setDisplayedValues(dayValues);

        endMonthNumPicker.setMinValue(0);
        endMonthNumPicker.setMaxValue(11);
        endMonthNumPicker.setDisplayedValues(monthValues);

        endYearNumPicker.setMinValue(2023);
        endYearNumPicker.setMaxValue(2023);

        String startYear = oldStartDate.substring(0, 4);
        String startMonth = oldStartDate.substring(5, 7);
        String startDay = oldStartDate.substring(8, 10);

        String endYear = oldEndDate.substring(0, 4);
        String endMonth = oldEndDate.substring(5, 7);
        String endDay = oldEndDate.substring(8, 10);

        int startDayInt = Integer.parseInt(startDay);
        startDayNumPicker.setValue(startDayInt - 1);
        int startMonthInt = Integer.parseInt(startMonth);
        startMonthNumPicker.setValue(startMonthInt - 1);
        int startYearInt = Integer.parseInt(startYear);
        startYearNumPicker.setValue(startYearInt - 1);

        int endDayInt = Integer.parseInt(endDay);
        endDayNumPicker.setValue(endDayInt - 1);
        int endMonthInt = Integer.parseInt(endMonth);
        endMonthNumPicker.setValue(endMonthInt - 1);
        int endYearInt = Integer.parseInt(endYear);
        endYearNumPicker.setValue(endYearInt - 1);

        medicamentViewModel = ViewModelProviders.of(com.example.javaprojectlastversion.fragments.editing.EditMedicamentFragment.this).get(MedicamentViewModel.class);

        Button editMedicamentButton = view.findViewById(R.id.button_edit_medicament);
        editMedicamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();

                int startDayIndex = startDayNumPicker.getValue();
                String startDay = dayValues[startDayIndex];

                int startMonthIndex = startMonthNumPicker.getValue();
                String startMonth =  monthValues[startMonthIndex];

                int startYearIndex = startYearNumPicker.getValue();
                String startYear = String.valueOf(startYearIndex);

                int endDayIndex = endDayNumPicker.getValue();
                String endDay = dayValues[endDayIndex];

                int endMonthIndex = endMonthNumPicker.getValue();
                String endMonth =  monthValues[endMonthIndex];

                int endYearIndex = endYearNumPicker.getValue();
                String endYear = String.valueOf(endYearIndex);

                String startDate = startYear + "-" + startMonth + "-" + startDay;
                String endDate = endYear + "-" + endMonth + "-" + endDay;

                Medicament medicament = new Medicament(name, startDate, endDate);
                medicament.setId(currentId);
                medicamentViewModel.updateMedicament(medicament);

                Toast.makeText(getContext(), "Лекарство "+ name + " изменено", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_medicament, container, false);
    }

}