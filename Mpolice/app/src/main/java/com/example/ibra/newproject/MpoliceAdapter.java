package com.example.ibra.newproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by lucie on 12/9/15.
 */
public class MpoliceAdapter extends RecyclerView.Adapter<MpoliceAdapter.PoliceHolder> {
    ParseObject obj;
    List<String> number_plate,description,owner, status = Collections.emptyList();
    Context context;

    public MpoliceAdapter(Context context, List<String> number_plate,List<String> description,List<String> owner, List<String> status){
        this.context = context;
        this.number_plate = number_plate;
        this.description = description;
        this.owner = owner;
        this.status = status;
    }

    @Override
    public PoliceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mpolice_card, parent, false);
        PoliceHolder pHolder = new PoliceHolder(v);
        return pHolder;
    }

    @Override
    public void onBindViewHolder(PoliceHolder holder, int i) {
        holder.tv_number_plate.setText("Number plate: "+number_plate.get(i));
        holder.tv_description.setText("Description of the car: "+description.get(i));
        holder.tv_owner.setText("Car owner: "+owner.get(i));
        holder.tv_status.setText("STATUS: "+status.get(i));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        Log.d("police adapter", "size: " + number_plate.size());
        return number_plate.size();
    }

    //view holder class
    public static class PoliceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardV;
        TextView tv_number_plate, tv_description,tv_owner, tv_status;

        public PoliceHolder(View v) {
            super(v);
            cardV = (CardView) v.findViewById(R.id.cardView);
            tv_number_plate = (TextView) v.findViewById(R.id.number_plate);
            tv_description = (TextView) v.findViewById(R.id.description);
            tv_owner = (TextView) v.findViewById(R.id.owner);
            tv_status = (TextView) v.findViewById(R.id.status);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("position", ""+position);
            Context con = itemView.getContext();
                Intent i = new Intent(con,CarDetails.class);
                con.startActivity(i);

        }
    }
}
