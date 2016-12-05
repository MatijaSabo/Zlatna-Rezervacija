package com.example.matija.zlatnarezervacija.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matija.zlatnarezervacija.R;
import com.example.webservice.ReservationItemDetails;
import com.example.webservice.ReservationTableItemDetails;

import java.util.ArrayList;

/**
 * Created by Lovro on 3.12.2016..
 */

public class UserReservationsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ReservationItemDetails> reservations;

    private Integer[] status_image_id = {R.mipmap.ic_hourglass_empty_black_18dp, R.mipmap.ic_done_black_18dp, R.mipmap.ic_block_black_18dp,
            R.mipmap.ic_report_problem_black_18dp, R.mipmap.ic_clear_black_18dp};

    public UserReservationsRecycleAdapter(ArrayList<ReservationItemDetails> reservations) {
        this.reservations = reservations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_reservations, parent, false);
        UserReservationsRecycleAdapter.MyReservationViewHolder item = new UserReservationsRecycleAdapter.MyReservationViewHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String tables="";
        ReservationItemDetails item = reservations.get(position);
        for (ReservationTableItemDetails i : item.getTables()){
            tables=tables + i.getLabel()+ ", ";
        }
        if(tables.isEmpty())tables="-";
        tables = tables.replaceAll(", $", "");
        ((MyReservationViewHolder) holder).reservationTime_Arrival.setText("Vrijeme dolaska: " + item.getTime_arrival());
        ((MyReservationViewHolder) holder).reservationId.setText("Broj rezervacije: " + item.getId());
        ((MyReservationViewHolder) holder).reservationDate.setText("Datum: " + item.getDate());
        ((MyReservationViewHolder) holder).reservationTime_Checkout.setText("Vrijeme odlaska: " + item.getTime_checkout());
        ((MyReservationViewHolder) holder).reservationPersons.setText("Broj ljudi: " + item.getPersons());
        ((MyReservationViewHolder) holder).reservationMeals.setText("Broj jela: " + item.getMeals());
        ((MyReservationViewHolder) holder).reservationTable.setText("Stolovi: " + tables);
        ((MyReservationViewHolder) holder).reservationDescription.setText("Opis: " + item.getDescription());
        ((MyReservationViewHolder) holder).reservationRemark.setText("Napomena: " + item.getRemark());
        ((MyReservationViewHolder) holder).status_image.setImageResource(status_image_id[item.getStatus()-1]);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
    public class MyReservationViewHolder extends RecyclerView.ViewHolder{

        TextView reservationStatus;
        TextView reservationDate;
        TextView reservationPersons;
        TextView reservationMeals;
        TextView reservationTime_Arrival;
        TextView reservationTime_Checkout;
        TextView reservationRemark;
        TextView reservationDescription;
        TextView reservationId;
        TextView reservationTable;
        ImageView status_image;

        public MyReservationViewHolder(View itemView) {
            super(itemView);
            reservationTime_Arrival = (TextView) itemView.findViewById(R.id.reservation_detail_time_arrival);
            reservationRemark = (TextView) itemView.findViewById(R.id.reservation_detail_remark);
            reservationDate = (TextView) itemView.findViewById(R.id.reservation_detail_date);
            reservationId = (TextView) itemView.findViewById(R.id.reservation_detail_id);
            reservationTime_Checkout = (TextView) itemView.findViewById(R.id.reservation_detail_time_checkout);
            reservationPersons = (TextView) itemView.findViewById(R.id.reservation_detail_number_persons);
            reservationMeals = (TextView) itemView.findViewById(R.id.reservation_detail_number_meals);
            reservationTable = (TextView) itemView.findViewById(R.id.reservation_detail_tables);
            reservationDescription = (TextView) itemView.findViewById(R.id.reservation_detail_description);
            status_image = (ImageView) itemView.findViewById(R.id.reservation_detail_status);
        }
    }
}
