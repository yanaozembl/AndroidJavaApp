package com.example.javaprojectlastversion.fragments.editing;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.fragments.adding.MedicamentDialog;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;
import com.example.javaprojectlastversion.viewmodels.DiagnoseViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EditDiagnoseFragment extends Fragment implements MedicamentDialog.MedicamentDialogListener {

    private DiagnoseViewModel diagnoseViewModel;
    public ArrayList<String> appointmentsInfo = new ArrayList<>();
    private ArrayList<Medicament> currentMedicaments = new ArrayList<>();
    private List<Doctor> currentDoctors;
    private Spinner doctorSpinner;
    private ImageView IVPreviewImage;
    private String imageString;

    final int SELECT_PICTURE = 200;
    ListView medicamentListView;
    EditText diagnoseEditText, descriptionEditText;

    String oldDiagnoseName, oldDescription;
    int currentId, oldAppointmentId;
    Integer oldMedicament1, oldMedicament2, oldMedicament3, oldMedicament4, oldMedicament5;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        diagnoseViewModel = ViewModelProviders.of(com.example.javaprojectlastversion.fragments.editing.EditDiagnoseFragment.this).get(DiagnoseViewModel.class);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentId = bundle.getInt("currentId", 0);
            oldDiagnoseName =  bundle.getString("currentDiagnoseName");
            oldDescription =  bundle.getString("currentDescription");
            imageString =  bundle.getString("currentImageUri");
            oldAppointmentId = bundle.getInt("currentAppointmentId");
            oldMedicament1 = bundle.getInt("currentMedicament1");
            oldMedicament2 = bundle.getInt("currentMedicament2");
            oldMedicament3 = bundle.getInt("currentMedicament3");
            oldMedicament4 = bundle.getInt("currentMedicament4");
            oldMedicament5 = bundle.getInt("currentMedicament5");

        }

        medicamentListView = view.findViewById(R.id.list_view_edit_medicaments);
        doctorSpinner = (Spinner) view.findViewById(R.id.spinner_edit_diagnose_doctors);
        diagnoseEditText = view.findViewById(R.id.edit_text_edit_diagnose);
        descriptionEditText = view.findViewById(R.id.edit_text_edit_description);

        diagnoseEditText.setText(oldDiagnoseName);
        descriptionEditText.setText(oldDescription);

        IVPreviewImage = view.findViewById(R.id.IVPreviewImage_edit);
        if(imageString != null){
            Uri imageUri = Uri.parse(imageString);
            IVPreviewImage.setImageURI(imageUri);
        }

        if(oldMedicament1 != 0)
            currentMedicaments.add(diagnoseViewModel.getMedicamentById(oldMedicament1));
        if(oldMedicament2 != 0)
            currentMedicaments.add(diagnoseViewModel.getMedicamentById(oldMedicament2));
        if(oldMedicament3 != 0)
            currentMedicaments.add(diagnoseViewModel.getMedicamentById(oldMedicament3));
        if(oldMedicament4 != 0)
            currentMedicaments.add(diagnoseViewModel.getMedicamentById(oldMedicament4));
        if(oldMedicament5 != 0)
            currentMedicaments.add(diagnoseViewModel.getMedicamentById(oldMedicament5));

        ArrayAdapter<Medicament> adapter = new ArrayAdapter<Medicament>(getActivity(), android.R.layout.simple_list_item_1, currentMedicaments);
        medicamentListView.setAdapter(adapter);
        medicamentListView.setVisibility(View.VISIBLE);

        currentDoctors = diagnoseViewModel.getDoctorList();

        String currentDate = LocalDate.now().toString();
        String currentTime = LocalTime.now().toString();
        String currentDateAndTime = currentDate + " " + currentTime.substring(0, 5);

        diagnoseViewModel.getPastAppointment(currentDateAndTime).observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {

                ArrayList<Integer> doctorIds = new ArrayList<>();
                for (Appointment appointment: appointments
                ) {
                    doctorIds.add(appointment.getDoctorId());
                }

                ArrayList<Doctor> doctorsOnAppointment = new ArrayList<>();
                String appointmentData;
                int appointmentIndex = 0;

                for (Integer id: doctorIds
                ) {
                    for (Doctor doctor: currentDoctors
                    ) {
                        if(doctor.getId() == id){
                            doctorsOnAppointment.add(doctor);
                            appointmentData = doctor.getName() + "\n" + appointments.get(appointmentIndex).getDate();
                            appointmentsInfo.add(appointmentData);
                            ++appointmentIndex;
                            break;
                        }
                    }
                }

                changeSpinner(appointmentsInfo);

                int appointmentIdInCurrentAppointments = 0;
                for (Appointment appointment: appointments
                     ) {
                    if(appointment.getId() == oldAppointmentId)
                        appointmentIdInCurrentAppointments = appointments.indexOf(appointment);
                }
                doctorSpinner.setSelection(appointmentIdInCurrentAppointments);
            }
        });


        Button editDiagnoseButton = view.findViewById(R.id.button_edit_diagnose);
        editDiagnoseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Integer> medicamentIds = new ArrayList<>();
                for (Medicament medicament: currentMedicaments
                ) {
                    medicamentIds.add(diagnoseViewModel.getMedicamentId(medicament.getName()));
                }

                String appointmentInfo = doctorSpinner.getSelectedItem().toString();
                int charIndex = appointmentInfo.indexOf("\n");
                String appointmentDate = appointmentInfo.substring(charIndex + 1);
                int appointmentId = diagnoseViewModel.getAppointmentId(appointmentDate);

                String diagnoseName = diagnoseEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                Integer medicament1, medicament2, medicament3, medicament4, medicament5;
                switch (medicamentIds.size()){
                    case (1):
                        medicament1 = medicamentIds.get(0);
                        medicament2 = medicament3 = medicament4 = medicament5 = null;
                        break;
                    case (2):
                        medicament1 = medicamentIds.get(0);
                        medicament2 = medicamentIds.get(1);
                        medicament3 = medicament4 = medicament5 = null;
                        break;
                    case (3):
                        medicament1 = medicamentIds.get(0);
                        medicament2 = medicamentIds.get(1);
                        medicament3 = medicamentIds.get(2);
                        medicament4 = medicament5 = null;
                        break;
                    case (4):
                        medicament1 = medicamentIds.get(0);
                        medicament2 = medicamentIds.get(1);
                        medicament3 = medicamentIds.get(2);
                        medicament4 = medicamentIds.get(3);
                        medicament5 = null;
                        break;
                    case (5):
                        medicament1 = medicamentIds.get(0);
                        medicament2 = medicamentIds.get(1);
                        medicament3 = medicamentIds.get(2);
                        medicament4 = medicamentIds.get(3);
                        medicament5 = medicamentIds.get(4);
                        break;
                    default:
                        medicament1 = medicament2 = medicament3 = medicament4 = medicament5 = null;
                        break;
                }

                Diagnose diagnose = new Diagnose(appointmentId, diagnoseName, description, imageString, medicament1, medicament2, medicament3, medicament4, medicament5);
                diagnose.setId(currentId);
                diagnoseViewModel.updateDiagnose(diagnose);
                Toast.makeText(getContext(), "Диагноз "+ diagnoseName + " изменен", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });

        view.findViewById(R.id.linear_layout_edit_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        view.findViewById(R.id.linear_layout_edit_medicament).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMedicaments.size() < 5 ){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    MedicamentDialog medicamentDialog = new MedicamentDialog();
                    if (medicamentDialog != null) {
                        medicamentDialog.onAttachToParentFragment(com.example.javaprojectlastversion.fragments.editing.EditDiagnoseFragment.this);
                    }
                    medicamentDialog.show(fm, "Dialog Fragment");
                }
                else
                    Toast.makeText(getContext(), "Может быть добавлено не более пяти лекарств", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_diagnose, container, false);
    }

    public void changeSpinner(ArrayList<String> doctorNames){
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, doctorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        doctorSpinner.setAdapter(adapter);
    }

    public void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                imageString = selectedImageUri.toString();

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


    @Override
    public void applyData(String name, String startDate, String endDate) {

        Medicament currentMedicament = new Medicament(name, startDate, endDate);
        currentMedicaments.add(currentMedicament);
        diagnoseViewModel.insertMedicament(currentMedicament);

        Toast.makeText(getContext(), "Лекарство "+ name + " добавлено", Toast.LENGTH_SHORT).show();

        ArrayAdapter<Medicament> adapter = new ArrayAdapter<Medicament>(getActivity(), android.R.layout.simple_list_item_1, currentMedicaments);
        medicamentListView.setAdapter(adapter);
        medicamentListView.setVisibility(View.VISIBLE);

    }
}