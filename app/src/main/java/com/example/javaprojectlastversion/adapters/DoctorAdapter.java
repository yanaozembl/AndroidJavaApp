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
import com.example.javaprojectlastversion.models.Doctor;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    private List<Doctor> doctors = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_item, parent, false);
        return new DoctorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor currentDoctor = doctors.get(position);
        holder.textViewDoctor.setText(currentDoctor.getName());
        holder.textViewSpeciality.setText(currentDoctor.getSpeciality());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setNotes(List<Doctor> doctors){
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    public  Doctor getDoctorAt(int position){
        return doctors.get(position);
    }

    class DoctorHolder extends RecyclerView.ViewHolder {
        private TextView textViewDoctor;
        private TextView textViewSpeciality;

        public DoctorHolder(View itemView){
            super(itemView);
            textViewDoctor = itemView.findViewById(R.id.text_view_doctor);
            textViewSpeciality = itemView.findViewById(R.id.text_view_speciality);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(doctors.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
