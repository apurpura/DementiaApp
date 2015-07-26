package com.example.apurp_000.dementiaapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<EventModel> eventModels;

    public RecyclerAdapter(List<EventModel> eventModels) {
        this.eventModels = new ArrayList<EventModel>();
        this.eventModels.addAll(eventModels);
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

            viewHolder.timeText.setText(event.StartTime);
            viewHolder.actionText.setText(event.Action);
        }
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }
}