package com.example.ibra.newproject;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lucie on 12/9/15.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.PoliceHolder> {
    String number_plate,description,owner, status;
    Context context;

    public DetailsAdapter(Context context, String number_plate, String description, String owner, String status){
        this.context = context;
        this.number_plate = number_plate;
        this.description = description;
        this.owner = owner;
        this.status = status;
    }

    @Override
    public PoliceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_details, parent, false);
        PoliceHolder pHolder = new PoliceHolder(v);

        return pHolder;
    }

    @Override
    public void onBindViewHolder(PoliceHolder holder, int i) {
        holder.tv_number_plate.setText("Number plate: "+number_plate);
        holder.tv_description.setText("Description of the car: "+description);
        holder.tv_owner.setText("Car owner: "+owner);
        holder.tv_status.setText("STATUS: "+status);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    //view holder class
    public static class PoliceHolder extends RecyclerView.ViewHolder{
        CardView cardV;
        TextView tv_number_plate, tv_description,tv_owner, tv_status;

        public PoliceHolder(View v) {
            super(v);
            cardV = (CardView) v.findViewById(R.id.cardView);
            tv_number_plate = (TextView) v.findViewById(R.id.number_plate);
            tv_description = (TextView) v.findViewById(R.id.description);
            tv_owner = (TextView) v.findViewById(R.id.owner);
            tv_status = (TextView) v.findViewById(R.id.status);
        }
    }

    public interface RecyclerViewListener{
        public void recyclerViewClicked(View v, int position);
    }
}
