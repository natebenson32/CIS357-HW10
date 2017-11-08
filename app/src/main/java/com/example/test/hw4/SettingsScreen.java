package com.example.test.hw4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;

public class SettingsScreen extends AppCompatActivity {

    private String distSelection = "Kilometers";
    private String bearSelection = "Degrees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("distChoice", distSelection);
                intent.putExtra("bearChoice", bearSelection);
                setResult(MainScreen.SETTINGS_RESULT, intent);

                //Intent doneIntent = new Intent(SettingsScreen.this, MainScreen.class);
                //startActivity(doneIntent);
                finish();
            }
        });

        Spinner distSpinner = (Spinner) findViewById(R.id.distChoices);
        Spinner bearSpinner = (Spinner) findViewById(R.id.bearChoices);

        ArrayAdapter<CharSequence> distAdapter = ArrayAdapter.createFromResource(this,
                R.array.distUnits, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> bearAdapter = ArrayAdapter.createFromResource(this,
                R.array.bearUnits, android.R.layout.simple_spinner_item);

        distAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distSpinner.setAdapter(distAdapter);
        bearSpinner.setAdapter(bearAdapter);
        distSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                distSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                bearSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
