package com.example.test.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.test.hw4.dummy.HistoryContent;

public class HistoryActivity extends AppCompatActivity
        implements HistoryFragment.OnListFragmentInteractionListener {

    @Override
    public void onListFragmentInteraction(HistoryContent.HistoryItem item) {
        System.out.println("Interact!");
        Intent intent = new Intent();
        String[] vals = {item.origLat, item.origLng, item.destLat, item.destLng};
        intent.putExtra("item", vals);
        setResult(MainScreen.HISTORY_RESULT,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
