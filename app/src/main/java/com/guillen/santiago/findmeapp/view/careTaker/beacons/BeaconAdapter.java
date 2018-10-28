package com.guillen.santiago.findmeapp.view.careTaker.beacons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.ViewHolder> {
    private List<BeaconModel> beaconModelList;
    private AdapterListener listener;
    private boolean isPosition = false;

    public BeaconAdapter(AdapterListener listener) {
        this.listener = listener;
        beaconModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_beacon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        final BeaconModel beacon = beaconModelList.get(pos);

        holder.bind(beacon);
    }

    @Override
    public int getItemCount() {
        return beaconModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rvBeaconContainer)
        RelativeLayout rvBeaconContainer;
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvRoomName)
        TextView tvRoomName;
        @BindView(R.id.tvRoomFloor)
        TextView tvRoomFloor;
        @BindView(R.id.tvRoomDescription)
        TextView tvRoomDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final BeaconModel beacon){
            if(beacon != null){
                tvRoomName.setText(beacon.getRoomName());
                tvRoomFloor.setText("Piso: "+beacon.getFloorLevel());
                if(!isPosition){
                    tvRoomDescription.setText(beacon.getDescription());
                }else {
                    tvRoomDescription.setText("Distancia: "+beacon.getPatientDistance()+" metros");
                }

                rvBeaconContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(beacon);
                    }
                });
            }
        }
    }

    public void addBeacon(BeaconModel beacon){
        beaconModelList.add(beacon);
        notifyItemInserted(beaconModelList.size()-1);
    }

    public void modifyBeacon(BeaconModel beacon){
        int positionChanged = -1;
        for(int i = 0; i<beaconModelList.size(); i++){
            if(beaconModelList.get(i).getId().equals(beacon.getId())){
                beaconModelList.set(i, beacon);
                positionChanged = i;
                break;
            }
        }
        if(positionChanged >= 0){
            notifyItemChanged(positionChanged);
        }
    }

    public void removeBeacon(BeaconModel beacon){
        int positionRemoved = -1;
        for(int i = 0; i<beaconModelList.size(); i++){
            if(beaconModelList.get(i).getId().equals(beacon.getId())){
                beaconModelList.remove(i);
                positionRemoved = i;
                break;
            }
        }
        if(positionRemoved >= 0){
            notifyItemRemoved(positionRemoved);
        }
    }

    public void setForPosition(boolean isPosition){
        this.isPosition = isPosition;
    }

    public interface AdapterListener{
        void onClick(BeaconModel beacon);
    }
}
