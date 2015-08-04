package com.example.apurp_000.dementiaapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<EventModel> eventModels;
    Context mactivity;

    public RecyclerAdapter(List<EventModel> eventModels, Context mactivity) {
        this.eventModels = new ArrayList<EventModel>();
        this.eventModels.addAll(eventModels);
        this.mactivity = mactivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        EventModel event = eventModels.get(i);
        if(event != null) {
            viewHolder.titleText.setText(event.Summary);
            viewHolder.contentText.setText(event.Description);
            if (event.Action != null){
                switch (event.Action) {
                    case "Text Message":
                        viewHolder.eventPhoto.setImageResource(R.drawable.mailthumb);
                        break;
                    case "Image Carousel":
                        viewHolder.eventPhoto.setImageResource(R.drawable.carouselthumb);
                        break;
                    case "Simon Says Game":
                        viewHolder.eventPhoto.setImageResource(R.drawable.simonthumb);
                        break;
                    case "Memory Matching Game":
                        viewHolder.eventPhoto.setImageResource(R.drawable.memorythumb);
                        break;
                    case "Pills - Sequence":
                        viewHolder.eventPhoto.setImageResource(R.drawable.pillthumb);
                        break;
                    case "Female Dressed - Sequence":
                        viewHolder.eventPhoto.setImageResource(R.drawable.dressedthumb);
                        break;

                }
             }
            viewHolder.timeText.setText(event.StartTime);
            viewHolder.actionText.setText(event.Action);
        }
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }
}