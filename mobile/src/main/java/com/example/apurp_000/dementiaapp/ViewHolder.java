package com.example.apurp_000.dementiaapp;

import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder extends RecyclerView.ViewHolder {

    protected TextView titleText;
    protected TextView contentText;
    protected TextView timeText;
    protected TextView actionText;
    protected ImageView eventPhoto;
    protected CardView card;

    public ViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.title);
        contentText = (TextView) itemView.findViewById(R.id.content);
        eventPhoto = (ImageView) itemView.findViewById((R.id.event_photo));
        timeText = (TextView) itemView.findViewById((R.id.time));
        actionText = (TextView) itemView.findViewById((R.id.action));
        card = (CardView) itemView;
    }
}
