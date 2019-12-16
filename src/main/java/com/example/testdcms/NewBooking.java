package com.example.testdcms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewBooking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sp;
    List<String> list;
    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);

        sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);

        list = new ArrayList<>();
        list.add("1st slot(Time: 6 PM)");
        list.add("qeqweq(Time: 6 PM)");

        adp = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String locat = parent.getSelectedItem().toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + locat, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(arg0.getContext(), "Please Select Your Pickup Location", Toast.LENGTH_LONG).show();

    }
}
