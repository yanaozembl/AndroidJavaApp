package com.example.javaprojectlastversion.fragments.adding;

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
import android.widget.Toast;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.viewmodels.DoctorViewModel;

import java.util.ArrayList;

public class AddDoctorFragment extends Fragment {

    private DoctorViewModel doctorViewModel;
    public String[] specialities =new String[]{"Стоматолог", "ЛОР", "Офтальмолог", "Кардиолог", "Педиатр", "Терапевт", "Офтальмолог"};
    private Spinner specialitySpinner;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        EditText nameEditText = view.findViewById(R.id.edit_text_add_doctor_name);
        EditText phoneEditText = view.findViewById(R.id.edit_text_add_doctor_phone);
        specialitySpinner = (Spinner) view.findViewById(R.id.spinner_add_speciality_of_doctor);
        changeSpinner(specialities);

        doctorViewModel = ViewModelProviders.of(AddDoctorFragment.this).get(DoctorViewModel.class);

        Button addDoctorButton = view.findViewById(R.id.button_add_doctor);
        addDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String speciality = specialitySpinner.getSelectedItem().toString();

                addPhoneToContacts(name, phone);

                doctorViewModel.insertDoctor(new Doctor(name, speciality, phone));
                Toast.makeText(getContext(), "Доктор "+ name + " добавлен(-а)", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_doctor, container, false);
    }

    public void changeSpinner(String[] specialities){
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, specialities);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        specialitySpinner.setAdapter(adapter);
    }

    public void addPhoneToContacts(String name, String phone){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        try {
            getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception ex) {
            Log.d("Content provider", "onClick: " + ex.getMessage());
        }


    }
}