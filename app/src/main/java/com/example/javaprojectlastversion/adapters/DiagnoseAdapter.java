package com.example.javaprojectlastversion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Appointment;
import com.example.javaprojectlastversion.models.Diagnose;
import com.example.javaprojectlastversion.models.Doctor;
import com.example.javaprojectlastversion.models.Medicament;

public class DiagnoseAdapter extends RecyclerView.Adapter<DiagnoseAdapter.DiagnoseHolder> {

    private List<Diagnose> diagnoses = new ArrayList<>();
    private static List<Appointment> appointments;
    private static List<Medicament> medicaments;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public DiagnoseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diagnose_item, parent, false);
        return new DiagnoseHolder(itemView);
    }

    public void getAppointments(List<Appointment> appointments){
        this.appointments = appointments;
    }

    public void getMedicaments(List<Medicament> medicaments){
        this.medicaments = medicaments;
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnoseHolder holder, int position) {
        Diagnose currentDiagnose = diagnoses.get(position);
        holder.textViewDiagnose.setText(currentDiagnose.getDiagnosis());

        int appointmentId = currentDiagnose.getAppointmentId();
        for (Appointment appointment: appointments
             ) {
            if(appointment.getId() == appointmentId)
                holder.textViewDate.setText(appointment.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return diagnoses.size();
    }

    public void setNotes(List<Diagnose> diagnoses){
        this.diagnoses = diagnoses;
        notifyDataSetChanged();
    }

    public  Diagnose getDiagnoseAt(int position){
        return diagnoses.get(position);
    }

    class DiagnoseHolder extends RecyclerView.ViewHolder {
        private TextView textViewDiagnose;
        private TextView textViewDate;

        public DiagnoseHolder(View itemView){
            super(itemView);
            textViewDiagnose = itemView.findViewById(R.id.text_view_diagnose);
            textViewDate = itemView.findViewById(R.id.text_view_diagnose_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(diagnoses.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Diagnose diagnose);
    }

    public void setOnItemClickListener(DiagnoseAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
