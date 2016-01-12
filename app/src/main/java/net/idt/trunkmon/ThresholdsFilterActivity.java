package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import util.MultiSelectionSpinner;

public class ThresholdsFilterActivity extends AppCompatActivity {
    private MultiSelectionSpinner countrySpinner;
    private MultiSelectionSpinner startCountrySpinner;
    private MultiSelectionSpinner divisionSpinner;

    String[] countryItems = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola"};
    String[] startCountryItems = {"A", "B", "C", "D", "E", "F", "G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    String[] divisionItems = {"Gold", "USDebit", "UKDebit", "Carriers", "Silver"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thresholds_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button applyBt = (Button)findViewById(R.id.tApplyButton);
        applyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), ThresholdsDataActivity.class);
                startActivity(intent);
            }
        });

        countrySpinner = (MultiSelectionSpinner) findViewById(R.id.thCountrySpinner);
        countrySpinner.spinner_title = "Country";
        countrySpinner.setItems(countryItems);

        startCountrySpinner = (MultiSelectionSpinner) findViewById(R.id.thStartCountrySpinner);
        startCountrySpinner.spinner_title = "Country Starts From";
        startCountrySpinner.setItems(startCountryItems);

        divisionSpinner = (MultiSelectionSpinner) findViewById(R.id.thDivisionSpinner);
        divisionSpinner.spinner_title = "Division";
        divisionSpinner.setItems(divisionItems);
    }

}
