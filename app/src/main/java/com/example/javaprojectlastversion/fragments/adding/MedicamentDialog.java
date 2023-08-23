package com.example.javaprojectlastversion.fragments.adding;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Medicament;

public class MedicamentDialog extends DialogFragment {

    NumberPicker startDayNumPicker;
    NumberPicker startMonthNumPicker;
    NumberPicker startYearNumPicker;
    NumberPicker endDayNumPicker;
    NumberPicker endMonthNumPicker;
    NumberPicker endYearNumPicker;
    private MedicamentDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.medicament_dialog, null);

        builder.setView(view);

        EditText nameEditText = view.findViewById(R.id.edit_text_medicament_diagnose);

        startDayNumPicker = view.findViewById(R.id.number_picker_start_day_diagnose);
        startMonthNumPicker = view.findViewById(R.id.number_picker_start_month_diagnose);
        startYearNumPicker = view.findViewById(R.id.number_picker_start_year_diagnose);
        endDayNumPicker = view.findViewById(R.id.number_picker_end_day_diagnose);
        endMonthNumPicker = view.findViewById(R.id.number_picker_end_month_diagnose);
        endYearNumPicker = view.findViewById(R.id.number_picker_end_year_diagnose);

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
        endYearNumPicker.setMaxValue(2023);

        Button addMedicamentButton = view.findViewById(R.id.button_add_medicament_diagnose);
        addMedicamentButton.setOnClickListener(new View.OnClickListener() {
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
                listener.applyData(name, startDate, endDate);
                dismiss();

            }
        });

        return builder.create();
    }

    public void onAttachToParentFragment(MedicamentDialogListener medicamentDialogListener) {
        if (medicamentDialogListener != null) {
            try {
                this.listener = medicamentDialogListener;
            } catch (ClassCastException e) {
                throw new ClassCastException(
                        medicamentDialogListener.toString() + " must implement Callback");
            }
        }
    }

    public interface MedicamentDialogListener{
        void applyData(String name, String startDate, String endDate);
    }


}
