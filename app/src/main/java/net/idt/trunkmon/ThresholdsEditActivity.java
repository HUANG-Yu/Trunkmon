package net.idt.trunkmon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class ThresholdsEditActivity extends AppCompatActivity {

    TableLayout tl;
    TableRow tr;
    TextView column_name, column_value;
    TableRow record_header, record_tail;
    TextView head_info;
    EditText edit;
    BootstrapButton reset, save;
    int index;

    JSONObject response;
    JSONObject copy;

    String[] columns = {"Location", "Division", "Tod", "Auto CCR", "Auto ALOC",
            "Auto Attempts", "Auto Memo", "Rev CCR", "Rev ALOC", "Rev Attempts",
            "Rev Memo"};
    Set<String> fixFields = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threshold_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tl = (TableLayout) findViewById(R.id.edit_table);

        try {
            recreate_json();
            showRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recreate_json () throws JSONException {
        Intent intent = getIntent();
        response = new JSONObject(intent.getExtras().getString("response"));
        copy = new JSONObject(intent.getExtras().getString("response"));
        Log.i("request", intent.getExtras().getString("response"));
        index = Integer.parseInt(intent.getExtras().getString("index"));
    }

    public void showRecord() throws JSONException {
        JSONArray receivedArray = (JSONArray) response.get("records");
        JSONObject cur = receivedArray.getJSONObject(index);
        Log.i("current record", cur.toString());
        //setting editable fields in editable set
        fixFields.add("Location");
        fixFields.add("Division");
        fixFields.add("Tod");
        // adding header to each json object
        record_header = new TableRow(this);

        head_info = new TextView(this);
        head_info.setText("record " + (index + 1) + " of " + receivedArray.length());
        head_info.setTextColor(Color.BLUE);
        head_info.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        head_info.setPadding(5, 5, 5, 5);
        record_header.addView(head_info);

        tl.addView(record_header, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < columns.length; i++) {
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // column name in the first column
            column_name = new TextView(this);
            column_name.setText(columns[i]);
            column_name.setTextColor(Color.BLACK);
            column_name.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            column_name.setPadding(5, 5, 5, 5);
            tr.addView(column_name);
            if (fixFields.contains(columns[i])) {
                // add an textview as second column in that row
                column_value = new TextView(this);
                column_value.setText(cur.get(columns[i]).toString());
                column_value.setTextColor(Color.BLACK);
                column_value.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                column_value.setPadding(5, 5, 5, 5);
                tr.addView(column_value);
            }
            else {
                // add an edit view as second column in that row
                edit = new EditText(this);
                edit.setText(cur.get(columns[i]).toString());
                edit.setTextColor(Color.BLACK);
                edit.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                edit.setPadding(5, 5, 5, 5);
                tr.addView(edit);

            }

            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
        // adding reset and save buttons to the last row
        record_tail = new TableRow(this);

        reset = new BootstrapButton(this);
        reset.setRounded(true);
        reset.setWidth(80);
        reset.setHeight(50);
        reset.setBootstrapSize(DefaultBootstrapSize.SM);
        reset.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        reset.setText("Reset");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), ThresholdsDataActivity.class);
                intent.putExtra("response", response.toString());
                startActivity(intent);
            }
        });
        reset.setPadding(0, 0, 0, 0);

        save = new BootstrapButton(this);
        save.setRounded(true);
        save.setMaxWidth(80);
        save.setHeight(60);
        save.setBootstrapSize(DefaultBootstrapSize.SM);
        save.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        save.setText("Save");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), ThresholdsDataActivity.class);
                intent.putExtra("response", response.toString());
                startActivity(intent);
            }
        });
        save.setPadding(0, 0, 0, 0);

        record_tail.addView(reset);
        record_tail.addView(save);

        tl.addView(record_tail, new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
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
