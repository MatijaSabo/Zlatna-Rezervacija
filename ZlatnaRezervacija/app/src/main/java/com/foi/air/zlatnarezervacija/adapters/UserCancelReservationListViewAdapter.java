package com.foi.air.zlatnarezervacija.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.foi.air.zlatnarezervacija.R;
import com.foi.webservice.responses.ReservationItemDetails;

import java.util.ArrayList;

/**
 * Created by Lovro on 26.12.2016..
 */

public class UserCancelReservationListViewAdapter extends BaseAdapter {

    private ArrayList<ReservationItemDetails> listData;
    private LayoutInflater layoutInflater;

    public UserCancelReservationListViewAdapter(Context context, ArrayList<ReservationItemDetails> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.reservation_cancel, null);
            holder = new ViewHolder();
            holder.rb = (RadioButton) convertView.findViewById(R.id.radioButtonReservation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rb.setText("Rezervacija " + listData.get(position).getId().toString());

        return convertView;
    }

    static class ViewHolder {
        RadioButton rb;
    }
}
