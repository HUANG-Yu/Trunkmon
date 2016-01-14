package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import util.MultiSelectionSpinner;

public class ThresholdsFilterActivity extends AppCompatActivity {
    private MultiSelectionSpinner countrySpinner;
    private MultiSelectionSpinner startCountrySpinner;
    private MultiSelectionSpinner divisionSpinner;

    String[] countryItems = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola"};
    String[] startCountryItems = new String[26];
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

        // generate startCountry drop down content
        for (int i = 0; i < 26; i++) {
            char cur = (char)(65 + i);
            startCountryItems[i] = "" + cur;
        }

        countrySpinner = (MultiSelectionSpinner) findViewById(R.id.timeSpinner);
        countrySpinner.spinner_title = "Country";
        countrySpinner.setItems(countryItems);

        startCountrySpinner = (MultiSelectionSpinner) findViewById(R.id.startCountrySpinner);
        startCountrySpinner.spinner_title = "Country Starts From";
        startCountrySpinner.setItems(startCountryItems);

        divisionSpinner = (MultiSelectionSpinner) findViewById(R.id.divisionSpinner);
        divisionSpinner.spinner_title = "Division";
        divisionSpinner.setItems(divisionItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_content) {
            startActivity(new Intent(this, SelectionsActivity.class));
        } else if (id == R.id.action_violations) {
            startActivity(new Intent(this, ViolationsFilterActivity.class));
        } else if (id == R.id.action_thresholds) {
            startActivity(new Intent(this, ThresholdsFilterActivity.class));
        } else if (id == R.id.action_logout) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            //id == R.id.action_about
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
