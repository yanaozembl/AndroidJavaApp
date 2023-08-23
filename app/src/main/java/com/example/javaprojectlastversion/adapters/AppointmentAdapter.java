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
import com.example.javaprojectlastversion.models.Doctor;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {

    private List<Appointment> appointments = new ArrayList<>();
    private static List<Doctor> doctors;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_item, parent, false);
        return new AppointmentHolder(itemView);
    }

    public void getDoctors(List<Doctor> doctors){
        this.doctors = doctors;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment currentAppointment = appointments.get(position);
        String date = currentAppointment.getDate();
        holder.textViewDateAndTime.setText(date);

        int doctorId = currentAppointment.getDoctorId();
        for (Doctor doctor:  doctors) {
            if (doctor.getId()==doctorId)
                holder.textViewDoctor.setText(doctor.getName());
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setNotes(List<Appointment> appointments){
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    public  Appointment getAppointmentAt(int position){
        return appointments.get(position);
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewDoctor;
        private TextView textViewDateAndTime;

        public AppointmentHolder(View itemView){
            super(itemView);
            textViewDoctor = itemView.findViewById(R.id.text_view_doctor);
            textViewDateAndTime = itemView.findViewById(R.id.text_view_time_and_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(appointments.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public void setOnItemClickListener(AppointmentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
