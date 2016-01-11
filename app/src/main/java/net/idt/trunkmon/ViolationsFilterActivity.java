package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;
import util.MultiSelectionSpinner;

public class ViolationsFilterActivity extends AppCompatActivity {
    private static final String TAG = "vio filter log message";
    private MultiSelectionSpinner multiSelectionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "vio filter onCreate");
        setContentView(R.layout.activity_violations_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button applyBt = (Button)findViewById(R.id.vApplyButton);
        applyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), ViolationsDataActivity.class);
                Log.i(TAG, "apply button clicked");
                startActivity(intent);
            }
        });

        Spinner timeDropdown = (Spinner)findViewById(R.id.timeSpinner);
        String[] timeItems = new String[]{"none","1/4/2015 17:00", "1/5/2015 17:00", "1/6/2015 17:00","1/7/2015 17:00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeItems);
        timeDropdown.setAdapter(adapter);

        String[] startCountryItems = {"none","A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.startCountrySpinner);
        multiSelectionSpinner.setItems(startCountryItems);
        multiSelectionSpinner.setSelection(new int[]{2, 6});

        String[] divisionItems = {"none", "Gold", "USDebit", "Silver", "UKDebit", "Carriers"};
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.divisionSpinner);
        multiSelectionSpinner.setItems(divisionItems);
//        multiSelectionSpinner.setSelection(new int[]{2, 6});

        String[] additionalItems = {"none","review-pulled", "auto-pulled", "cross division saved", "excluded locations", "managed countries only"};
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.additionalSpinner);
        multiSelectionSpinner.setItems(additionalItems);
//        multiSelectionSpinner.setSelection(new int[]{2, 6});

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
