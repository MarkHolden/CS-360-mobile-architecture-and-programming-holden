package com.holden.events;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import android.view.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.*;
import java.util.stream.IntStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {
    protected List<Event> _eventList;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected EventListAdapter adapter;
    private EventRepository eventRepo;

    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventRepo = new EventRepository(this.getContext());
//        eventRepo.seed();
        _eventList = eventRepo.list();
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

        adapter = new EventListAdapter(_eventList, event -> {
            Intent eventDetailIntent = new Intent(this.getContext(), EventDetailActivity.class);
            eventDetailIntent.putExtra("event", event);
            //noinspection deprecation (the non-deprecated way is immensely complex and high risk).
            startActivityForResult(eventDetailIntent, 2);
        });

        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setAdapter(adapter);
        // END_INCLUDE(initializeRecyclerView)

        FloatingActionButton addEventButton = rootView.findViewById(R.id.add_event_fab);
        EncryptedSharedPreferencesUtilities encryptedSharedPreferences = new EncryptedSharedPreferencesUtilities(this.getContext());
        if (encryptedSharedPreferences.canAddEvents()) {
            addEventButton.setVisibility(View.VISIBLE);
            addEventButton.setOnClickListener(v -> {
                Intent eventDetailIntent = new Intent(v.getContext(), EventDetailActivity.class);
                eventDetailIntent.putExtra("isInEditMode", true);
                //noinspection deprecation (the non-deprecated way is immensely complex and high risk).
                startActivityForResult(eventDetailIntent, 2);
            });
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Event event = (Event) data.getSerializableExtra("event");
            upsertEvent(event);
        }
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

    private void upsertEvent(Event event) {
        OptionalInt position = IntStream.range(0, _eventList.size())
                .filter(i -> _eventList.get(i).id.equals(event.id))
                .findFirst();

        if (position.isPresent())
        {
            updateEvent(event, position.getAsInt());
            return;
        }

        addEvent(event);
    }

    public void addEvent(Event event) {
        eventRepo.create(event);
        _eventList.add(event);
        recyclerView.getAdapter().notifyItemChanged(_eventList.size());
    }

    private void updateEvent(Event event, int position) {
        eventRepo.update(event);
        _eventList.stream()
            .filter(e -> e.id.equals(event.id))
            .forEach(e -> {
                e.name = event.name;
                e.description = event.description;
                e.startDateTime = event.startDateTime;
                e.endDateTime = event.endDateTime;
                e.group = event.group;
                e.location = event.location;
            });

        recyclerView.getAdapter().notifyItemChanged(position);
    }
}