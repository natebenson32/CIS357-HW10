package com.example.test.hw4;

import android.content.Intent;
import android.media.audiofx.BassBoost;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;

import com.example.test.hw4.dummy.HistoryContent;

import org.joda.time.DateTime;

public class MainScreen extends AppCompatActivity {

    public static final int HISTORY_RESULT = 1;
    public static final int SETTINGS_RESULT = 2;

    EditText latP1;
    EditText latP2;
    EditText lonP1;
    EditText lonP2;

    TextView dist;
    TextView bear;
    float[] a;

    public String distVar = "Kilometers";
    public String bearVar = "Degrees";

    public double dVal = 0;
    public double bVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Text fields
        latP1 = (EditText) findViewById(R.id.LatP1);
        latP2 = (EditText) findViewById(R.id.LatP2);
        lonP1 = (EditText) findViewById(R.id.LonP1);
        lonP2 = (EditText) findViewById(R.id.LonP2);

        // Buttons
        Button calcButton = (Button) findViewById(R.id.CalculateButton);
        Button clearButton = (Button) findViewById(R.id.ClearButton);

        // Labels
        dist = (TextView) findViewById(R.id.DistanceLabel);
        bear = (TextView) findViewById(R.id.BearingLabel);

        calcButton.setOnClickListener(v -> {
            if(latP1.length() != 0 && latP2.length() != 0
                    && lonP1.length() != 0 && lonP2.length() != 0) {

                double radius = 6371000.0;      // Earth's radius (m)
                double p1lt = Double.parseDouble(latP1.getText().toString());
                double p2lt = Double.parseDouble(latP2.getText().toString());
                double p1ln = Double.parseDouble(lonP1.getText().toString());
                double p2ln = Double.parseDouble(lonP2.getText().toString());

                a = doCalculation(p1lt, p1ln, p2lt, p2ln);

                dist.setText("Distance: " + String.format("%.2f", a[0] / 1000.0)
                        + " " + distVar, TextView.BufferType.EDITABLE);
                bear.setText("Bearing: " + String.format("%.2f", a[1]) + " "
                        + bearVar, TextView.BufferType.EDITABLE);


                HistoryContent.HistoryItem item = new HistoryContent.HistoryItem(latP1.getText().toString(),
                        lonP1.getText().toString(), latP2.getText().toString(), lonP2.getText().toString(), DateTime.now());
                HistoryContent.addItem(item);
            }
        });

        clearButton.setOnClickListener(v -> {
            latP1.setText("", TextView.BufferType.EDITABLE);
            latP2.setText("", TextView.BufferType.EDITABLE);
            lonP1.setText("", TextView.BufferType.EDITABLE);
            lonP2.setText("", TextView.BufferType.EDITABLE);
            dist.setText("Distance: ", TextView.BufferType.EDITABLE);
            bear.setText("Bearing: ", TextView.BufferType.EDITABLE);
        });
    }

    public float[] doCalculation(double latp1, double lonp1, double latp2, double lonp2) {
        float[] arr = new float[2];

        Location.distanceBetween(latp1, lonp1, latp2, lonp2, arr);

        float distConvert = arr[0];
        float bearConvert = arr[1];

        // Distance coefficient
        if(distVar.equals("Miles"))
            distConvert *= 0.621371f;
        else
            distConvert *= 1.0f;

        // Bearing coefficient
        if(bearVar.equals("Mils"))
            bearConvert *= 17.777778f;
        else
            bearConvert *= 1.0f;

        return arr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        dist = (TextView) findViewById(R.id.DistanceLabel);
        bear = (TextView) findViewById(R.id.BearingLabel);

        if(resultCode == SETTINGS_RESULT){
            distVar = data.getStringExtra("distChoice");
            bearVar = data.getStringExtra("bearChoice");
        }else if (resultCode == HISTORY_RESULT) {
            String[] vals = data.getStringArrayExtra("item");
            this.latP1.setText(vals[0]);
            this.lonP1.setText(vals[1]);
            this.latP2.setText(vals[2]);
            this.lonP2.setText(vals[3]);
            a = this.doCalculation(Double.parseDouble(latP1.getText().toString()), Double.parseDouble(lonP1.getText().toString()),
                    Double.parseDouble(latP2.getText().toString()), Double.parseDouble(lonP2.getText().toString()));

            dist.setText("Distance: " + String.format("%.2f", a[0] / 1000.0)
                    + " " + distVar, TextView.BufferType.EDITABLE);
            bear.setText("Bearing: " + String.format("%.2f", a[1]) + " "
                    + bearVar, TextView.BufferType.EDITABLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.dropSettings) {
            Intent intent = new Intent(MainScreen.this,
                    SettingsScreen.class);
            startActivityForResult(intent, SETTINGS_RESULT );
            return true;
        } else if(item.getItemId() == R.id.action_history) {
            Intent intent = new Intent(MainScreen.this, HistoryActivity.class);
            startActivityForResult(intent, HISTORY_RESULT );
            return true;
        }
        return false;
    }
}
