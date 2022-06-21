package com.holden.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private List<Event> _eventList;
    private OnListEventClickListener _listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Event event, OnListEventClickListener listener) {
            ((TextView) itemView.findViewById(R.id.event_list_name)).setText(event.name);
            ((TextView) itemView.findViewById(R.id.event_list_description)).setText(event.description);
            ((TextView) itemView.findViewById(R.id.event_list_start_time)).setText(event.getStartDateTimeString());
            ((TextView) itemView.findViewById(R.id.event_list_end_time)).setText(event.getEndDateTimeString());

            itemView.setOnClickListener(v -> listener.onEventClick(event));
        }
    }

    public EventListAdapter(List<Event> events, OnListEventClickListener listener) {
        _eventList = events;
        _listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bind(_eventList.get(position), _listener);
    }

    @Override
    public int getItemCount() {
        return _eventList.size();
    }
}
