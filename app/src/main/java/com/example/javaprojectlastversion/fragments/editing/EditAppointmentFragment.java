package com.example.javaprojectlastversion.fragments.editing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.AppointmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditAppointmentFragment extends Fragment {

    private AppointmentViewModel appointmentViewModel;
    public ArrayList<String> doctorNames = new ArrayList<>();
    private List<Doctor> currentDoctors;
    private Spinner doctorSpinner;
    String oldDate;
    int currentId, oldDoctorId, oldStatus;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentId = bundle.getInt("currentId", 0);
            oldDate =  bundle.getString("currentDate");
            oldDoctorId =  bundle.getInt("currentDoctorId");
        }

        doctorSpinner = (Spinner) view.findViewById(R.id.spinner_appointment_doctors_edit);

        appointmentViewModel = ViewModelProviders.of(com.example.javaprojectlastversion.fragments.editing.EditAppointmentFragment.this).get(AppointmentViewModel.class);
        appointmentViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                currentDoctors = doctors;
                for (Doctor doctor: doctors
                ) {
                    String name = doctor.getName();
                    doctorNames.add(name);
                }
                changeSpinner(doctorNames);

                Doctor currentDoctor = currentDoctors.get(oldDoctorId - 1);
                for (String name: doctorNames
                     ) {
                    if(currentDoctor.getName() == name)
                        doctorSpinner.setSelection(doctorNames.indexOf(name));
                }
            }
        });

        NumberPicker dayNumPicker = view.findViewById(R.id.number_picker_edit_day);
        NumberPicker monthNumPicker = view.findViewById(R.id.number_picker_edit_month);
        NumberPicker yearNumPicker = view.findViewById(R.id.number_picker_edit_year);

        dayNumPicker.setMinValue(0);
        dayNumPicker.setMaxValue(30);
        String[] dayValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31"};
        dayNumPicker.setDisplayedValues(dayValues);

        monthNumPicker.setMinValue(0);
        monthNumPicker.setMaxValue(11);
        String[] monthValues = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        monthNumPicker.setDisplayedValues(monthValues);

        yearNumPicker.setMinValue(2023);
        yearNumPicker.setMaxValue(2023);

        NumberPicker hoursNumPicker = view.findViewById(R.id.number_picker_edit_hours);
        NumberPicker minutesNumPicker = view.findViewById(R.id.number_picker_edit_minutes);

        hoursNumPicker.setMinValue(0);
        hoursNumPicker.setMaxValue(16);
        String[] hoursValues = new String[]{"06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
        hoursNumPicker.setDisplayedValues(hoursValues);

        minutesNumPicker.setMinValue(0);
        minutesNumPicker.setMaxValue(11);
        String[] minuteValues = new String[]{"00", "05", "10",
                "15", "20", "25",
                "30", "35", "40",
                "45", "50", "55"};
        minutesNumPicker.setDisplayedValues(minuteValues);

        String year = oldDate.substring(0, 4);
        String month = oldDate.substring(5, 7);
        String day = oldDate.substring(8, 10);
        String hours = oldDate.substring(11, 13);
        String minutes = oldDate.substring(14, 16);

        int dayInt = Integer.parseInt(day);
        dayNumPicker.setValue(dayInt - 1);
        int monthInt = Integer.parseInt(month);
        monthNumPicker.setValue(monthInt - 1);
        int yearInt = Integer.parseInt(year);
        yearNumPicker.setValue(yearInt - 1);

        int hoursInt = Integer.parseInt(hours);
        hoursNumPicker.setValue(hoursInt - 6);
        switch (minutes){
            case "05":
                minutesNumPicker.setValue(1);
                break;
            case "10":
                minutesNumPicker.setValue(2);
                break;
            case "15":
                minutesNumPicker.setValue(3);
                break;
            case "20":
                minutesNumPicker.setValue(4);
                break;
            case "25":
                minutesNumPicker.setValue(5);
                break;
            case "30":
                minutesNumPicker.setValue(6);
                break;
            case "35":
                minutesNumPicker.setValue(7);
                break;
            case "40":
                minutesNumPicker.setValue(8);
                break;
            case "45":
                minutesNumPicker.setValue(9);
                break;
            case "50":
                minutesNumPicker.setValue(10);
                break;
            case "55":
                minutesNumPicker.setValue(11);
                break;
            default:
                minutesNumPicker.setValue(0);
                break;
        }


        Button editDoctorButton = view.findViewById(R.id.button_edit_appointment);
        editDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int dayIndex = dayNumPicker.getValue();
                String day = dayValues[dayIndex];

                int monthIndex = monthNumPicker.getValue();
                String month =  monthValues[monthIndex];

                int yearIndex = yearNumPicker.getValue();
                String year = String.valueOf(yearIndex);

                int hoursIndex = hoursNumPicker.getValue();
                String hours = hoursValues[hoursIndex];

                int minutesIndex = minutesNumPicker.getValue();
                String minutes = minuteValues[minutesIndex];

                String date = year + "-" + month + "-" + day + " " + hours + ":" + minutes;

                String doctorName = doctorSpinner.getSelectedItem().toString();
                int doctorId = 1;
                for (Doctor doctor: currentDoctors
                ) {
                    if(doctor.getName().equals(doctorName))
                        doctorId=doctor.getId();
                }

                Appointment appointment = new Appointment(doctorId, date);
                appointment.setId(currentId);
                appointmentViewModel.updateAppointment(appointment);

                Toast.makeText(getContext(), "Запись изменена", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_appointment, container, false);
    }

    public void changeSpinner(ArrayList<String> doctorNames){
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(adapter);
    }
}