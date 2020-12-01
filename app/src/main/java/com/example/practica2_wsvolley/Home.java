package com.example.practica2_wsvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class Home extends AppCompatActivity {
    private TextView lblCountrySelected;
    private TextView lblLastUpdate;
    private TextView lblConfirmed;
    private TextView lblRecovered;
    private TextView lblDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblCountrySelected = findViewById(R.id.lblCountrySelected);
        lblLastUpdate = findViewById(R.id.lblLastUpdate);
        lblConfirmed = findViewById(R.id.lblConfirmed);
        lblRecovered = findViewById(R.id.lblRecovered);
        lblDeaths = findViewById(R.id.lblDeaths);
        Bundle b = this.getIntent().getExtras();
        lblCountrySelected.setText(b.getString("country_selected"));
        lblLastUpdate.setText(b.getString("last_update"));
        lblConfirmed.setText(b.getString("confirmed"));
        lblRecovered.setText(b.getString("recovered"));
        lblDeaths.setText(b.getString("deaths"));
    }
}