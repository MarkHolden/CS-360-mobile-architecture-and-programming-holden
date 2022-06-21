package com.holden.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.holden.events.databinding.ActivityEventDetailsBinding;

public class EventDetailActivity extends AppCompatActivity {
    private EncryptedSharedPreferencesUtilities _encryptedSharedPreferences;
    private ImageButton edit;
    private ImageButton save;
    private TextView eventName;
    private EditText eventNameEdit;
    private TextView eventDescription;
    private EditText eventDescriptionEdit;
    private TextView eventGroup;
    private Spinner eventGroupEdit;
    private TextView eventLocation;
    private Spinner eventLocationEdit;
    private TextView eventStartDate;
    private TextView eventStartTime;
    private TextView eventEndDate;
    private TextView eventEndTime;
    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        event = (Event) getIntent().getSerializableExtra("event");
        boolean isInEditMode = getIntent().getBooleanExtra("isInEditMode", false);

        ActivityEventDetailsBinding binding = ActivityEventDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edit = binding.edit;
        _encryptedSharedPreferences = new EncryptedSharedPreferencesUtilities(this);
        edit.setVisibility((!isInEditMode && _encryptedSharedPreferences.canEditEvents()) ? View.VISIBLE : View.INVISIBLE);

        edit.setOnClickListener(v -> setEditVisibility());

        save = binding.save;
        save.setOnClickListener(v -> {
            // TODO: actually save the event.
            setSaveVisibility();
            setModelValues();
        });

        save = binding.save;
        eventNameEdit = binding.eventDetailNameEdit;
        eventNameEdit.setText(event.name);
        eventDescriptionEdit = binding.eventDetailDescriptionEdit;
        eventDescriptionEdit.setText(event.description);
        eventGroupEdit = binding.eventDetailGroupEdit;
        setSpinner(event.groupsOptions, event.group, eventGroupEdit);
        eventLocationEdit = binding.eventDetailLocationEdit;
        setSpinner(event.locationsOptions, event.location, eventLocationEdit);

        eventName = binding.eventDetailName;
        eventName.setText(event.name);
        eventDescription = binding.eventDetailDescription;
        eventDescription.setText(event.description);
        eventGroup = binding.eventDetailGroup;
        eventGroup.setText(event.group);
        eventLocation = binding.eventDetailLocation;
        eventLocation.setText(event.location);

        // TODO: add date and time pickers for edit mode
        eventStartDate = binding.eventDetailStartDate;
        eventStartDate.setText(event.getStartDateString());

        eventStartTime = binding.eventDetailStartTime;
        eventStartTime.setText(event.getStartTimeString());

        eventEndDate = binding.eventDetailEndDate;
        eventEndDate.setText(event.getEndDateString());

        eventEndTime = binding.eventDetailEndTime;
        eventEndTime.setText(event.getEndTimeString());
    }

    private void setModelValues() {
        event.name = eventNameEdit.getText().toString();
        event.description = eventDescriptionEdit.getText().toString();
        event.group = eventGroupEdit.getSelectedItem().toString();
        event.location = eventLocationEdit.getSelectedItem().toString();

        eventName.setText(eventNameEdit.getText());
        eventDescription.setText(eventDescriptionEdit.getText());
        eventGroup.setText(eventGroupEdit.getSelectedItem().toString());
        eventLocation.setText(eventLocationEdit.getSelectedItem().toString());

        // TODO: start and end times also

        Intent output = new Intent();
        output.putExtra("event", event);
        setResult(RESULT_OK, output);
        finish(); // TODO: not really a fan of just dumping the user back to the list when they save.
    }

    private void setEditVisibility() {
        edit.setVisibility(View.INVISIBLE);
        eventName.setVisibility(View.INVISIBLE);
        eventDescription.setVisibility(View.INVISIBLE);
        eventGroup.setVisibility(View.INVISIBLE);
        eventLocation.setVisibility(View.INVISIBLE);

        save.setVisibility(View.VISIBLE);
        eventNameEdit.setVisibility(View.VISIBLE);
        eventDescriptionEdit.setVisibility(View.VISIBLE);
        eventGroupEdit.setVisibility(View.VISIBLE);
        eventLocationEdit.setVisibility(View.VISIBLE);
    }

    private void setSaveVisibility() {
        edit.setVisibility(View.VISIBLE);
        eventName.setVisibility(View.VISIBLE);
        eventDescription.setVisibility(View.VISIBLE);
        eventGroup.setVisibility(View.VISIBLE);
        eventLocation.setVisibility(View.VISIBLE);

        save.setVisibility(View.INVISIBLE);
        eventNameEdit.setVisibility(View.INVISIBLE);
        eventDescriptionEdit.setVisibility(View.INVISIBLE);
        eventGroupEdit.setVisibility(View.INVISIBLE);
        eventLocationEdit.setVisibility(View.INVISIBLE);
    }

    private void setSpinner(String[] options, String current, Spinner spinner) {
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(groupAdapter);
        int spinnerPosition = groupAdapter.getPosition(current);
        spinner.setSelection(spinnerPosition);
    }
}
