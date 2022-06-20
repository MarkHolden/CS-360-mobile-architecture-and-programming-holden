package com.holden.events;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private Event[] eventList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setTextView(Event event) {
            ((TextView) itemView.findViewById(R.id.event_list_name)).setText(event.name);
            ((TextView) itemView.findViewById(R.id.event_list_description)).setText(event.description);
            ((TextView) itemView.findViewById(R.id.event_list_start_time)).setText(event.getStartTimeString());
            ((TextView) itemView.findViewById(R.id.event_list_end_time)).setText(event.getEndTimeString());
        }
    }

    public EventListAdapter(Event[] events) {
        eventList = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        view.setOnClickListener(v -> {
//            Intent eventEditIntent = new Intent(this, LoginActivity.class);
//            startActivity(loginIntent);
//            loadingProgressBar.setVisibility(View.VISIBLE);
//            Login();
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setTextView(eventList[position]);
    }

    @Override
    public int getItemCount() {
        return eventList.length;
    }
}
