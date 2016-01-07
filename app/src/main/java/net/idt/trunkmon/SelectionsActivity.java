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
import android.widget.Button;
import android.content.Intent;
import android.util.Log;

public class SelectionsActivity extends AppCompatActivity {
    private static final String TAG = "selection log message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "selection activity onCreate");
        setContentView(R.layout.activity_selections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button vbutton = (Button)findViewById(R.id.vbutton);
        vbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),ViolationsFilterActivity.class);
                startActivity(intent);
                Log.i(TAG, "violations button clicked");
            }
        });

        Button tbutton = (Button)findViewById(R.id.tbutton);
        tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(),ThresholdsFilterActivity.class);
                startActivity(intent);
                Log.i(TAG, "thresholds button clicked");
            }
        });
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
