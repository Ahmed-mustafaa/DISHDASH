package com.example.dishdash.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.R;
import com.example.dishdash.view.SearchAvtivity.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreatePlanActivity extends AppCompatActivity {
    FloatingActionButton create;
    FloatingActionButton create1;
    FloatingActionButton create2;
NumberPicker hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
       create = findViewById(R.id.fab_breakfast); // Ensure you initialize this
      //  create1 = findViewById(R.id.create1); // Ensure you initialize this
       // create2 = findViewById(R.id.create2); // Ensure you initialize this

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlanActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


       /* create1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create1.playAnimation();
                Intent intent = new Intent(CreatePlanActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        create2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create2.playAnimation();
                Intent intent = new Intent(CreatePlanActivity.this, SearchActivity.class);
                startActivity(intent);
            }*/
      //  });
    }

    // Define the method outside of any OnClickListener
    private void showTimePickerDialog(final TextView timeTextView) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(CreatePlanActivity.this);
        View dialogView = inflater.inflate(R.layout.activity_create_plan, null);

        // Initialize the NumberPickers
        final NumberPicker pickerHours = dialogView.findViewById(R.id.picker_hours);
        final NumberPicker pickerMinutes = dialogView.findViewById(R.id.picker_minutes);
        final NumberPicker pickerAmPm = dialogView.findViewById(R.id.picker_am_pm);

        // Set up NumberPickers
        pickerHours.setMinValue(1);
        pickerHours.setMaxValue(12);
        pickerMinutes.setMinValue(0);
        pickerMinutes.setMaxValue(59);
        pickerAmPm.setMinValue(0);
        pickerAmPm.setMaxValue(1);
        pickerAmPm.setDisplayedValues(new String[] {"AM", "PM"});

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePlanActivity.this);
        builder.setTitle("Select Time");
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour = pickerHours.getValue();
                int minute = pickerMinutes.getValue();
                String amPm = pickerAmPm.getValue() == 0 ? "AM" : "PM";
                String timeString = String.format("%02d:%02d %s", hour, minute, amPm);
                timeTextView.setText(timeString);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
