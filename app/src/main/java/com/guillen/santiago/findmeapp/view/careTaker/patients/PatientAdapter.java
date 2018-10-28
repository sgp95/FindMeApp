package com.guillen.santiago.findmeapp.view.careTaker.patients;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.PatientModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<PatientModel> patientList;
    private AdapterListener listener;

    public PatientAdapter(AdapterListener listener) {
        this.listener = listener;
        patientList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        final PatientModel patient = patientList.get(pos);

        holder.bind(patient);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rvPatientContainer)
        RelativeLayout rvPatientContainer;
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAge)
        TextView tvAge;
        @BindView(R.id.tvSex)
        TextView tvSex;
        @BindView(R.id.tvSickness)
        TextView tvSickness;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final PatientModel patient){
            if(patient != null){
                tvName.setText(patient.getSurname()+", "+patient.getName());
                tvAge.setText(String.valueOf(patient.getAge()));
                tvSex.setText(patient.getSex());
                tvSickness.setText(patient.getSickness());

                rvPatientContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(patient);
                    }
                });
            }
        }
    }

    public void addPatient(PatientModel patientModel){
        patientList.add(patientModel);
        notifyItemInserted(patientList.size()-1);
    }

    public void modifyPatient(PatientModel patientModel){
        int positionChanged = -1;
        for(int i = 0; i<patientList.size(); i++){
                if(patientList.get(i).getId().equals(patientModel.getId())){
                    patientList.set(i, patientModel);
                    positionChanged = i;
                    break;
                }
        }
        if(positionChanged >= 0){
            notifyItemChanged(positionChanged);
        }
    }

    public void removePatient(PatientModel patientModel){
        int positionRemoved = -1;
        for(int i = 0; i<patientList.size(); i++){
            if(patientList.get(i).getId().equals(patientModel.getId())){
                patientList.remove(i);
                positionRemoved = i;
                break;
            }
        }
        if(positionRemoved >= 0){
            notifyItemRemoved(positionRemoved);
        }
    }

    interface AdapterListener{
        void onClick(PatientModel patient);
    }
}
