package com.holden.events;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import android.view.*;

import java.time.LocalDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {
    protected Event[] events;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected EventListAdapter adapter;

    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);

        // BEGIN_INCLUDE(initializeRecyclerView)
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        setScrollPosition();

        adapter = new EventListAdapter(events);
        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setAdapter(adapter);
        // END_INCLUDE(initializeRecyclerView)

        return rootView;
    }

    /**
     * Set RecyclerView's scroll position.
     */
    public void setScrollPosition() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        recyclerView.scrollToPosition(scrollPosition);
    }

    // TODO: Remove fake data
    private void initDataset() {
        events = new Event[60];
        for (int i = 0; i < 60; i++) {
            Event stuff = new Event(getContext());
            stuff.name = "This is event #" + i;
            stuff.description = "This is event #" + i + "description";
            stuff.startTime = LocalDateTime.now().plusHours(1);
            stuff.endTime = LocalDateTime.now().plusHours(2);

            events[i] = stuff;
        }
    }
}