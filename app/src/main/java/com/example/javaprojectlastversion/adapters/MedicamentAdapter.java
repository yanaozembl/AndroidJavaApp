package com.example.javaprojectlastversion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaprojectlastversion.R;
import com.example.javaprojectlastversion.models.Medicament;

import java.util.ArrayList;
import java.util.List;

public class MedicamentAdapter extends RecyclerView.Adapter<MedicamentAdapter.MedicamentHolder> {

    private List<Medicament> medicaments = new ArrayList<>();
    private MedicamentAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public MedicamentAdapter.MedicamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicament_item, parent, false);
        return new MedicamentAdapter.MedicamentHolder(itemView);
    }

    public void getMedicaments(List<Medicament> medicaments){
        this.medicaments = medicaments;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentHolder holder, int position) {

        Medicament currentMedicament = medicaments.get(position);
        holder.textViewMedicament.setText(currentMedicament.getName());
        String date = currentMedicament.getStartDate() + "  -  " + currentMedicament.getEndDate();
        holder.textViewDates.setText(date);
    }

    @Override
    public int getItemCount() {
        return medicaments.size();
    }

    public void setNotes(List<Medicament> medicaments){
        this.medicaments = medicaments;
        notifyDataSetChanged();
    }

    public  Medicament getMedicamentAt(int position){
        return medicaments.get(position);
    }

    class MedicamentHolder extends RecyclerView.ViewHolder {
        private TextView textViewMedicament;
        private TextView textViewDates;

        public MedicamentHolder(View itemView){
            super(itemView);
            textViewMedicament = itemView.findViewById(R.id.text_view_medicament);
            textViewDates = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(medicaments.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Medicament medicament);
    }

    public void setOnItemClickListener(MedicamentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


}
