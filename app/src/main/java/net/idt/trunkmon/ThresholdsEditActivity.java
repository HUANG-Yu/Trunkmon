package net.idt.trunkmon;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    // the JSONObject index in the JSONArray that needs to be edited
    int index;

    // the JSONObject received from ThresholdsDataActivity
    JSONObject response;
    // the unmodified JSONObject
    JSONObject copy;

    String[] columns = {"Location", "Division", "Tod", "Auto CCR", "Auto ALOC",
            "Auto Attempts", "Auto Memo", "Rev CCR", "Rev ALOC", "Rev Attempts",
            "Rev Memo"};
    // the hashSet to remember which fields are not editable
    Set<String> fixFields = new HashSet<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * The moethod is used to get the whole JSONObject from ThresholdsDataActivity and get the index
     * of the JSONObject that needs to be modified.
     * @throws JSONException
     */
    public void recreate_json() throws JSONException {
        Intent intent = getIntent();
        response = new JSONObject(intent.getExtras().getString("response"));
        copy = new JSONObject(intent.getExtras().getString("response"));
        // Log.i("request", intent.getExtras().getString("response"));
        index = Integer.parseInt(intent.getExtras().getString("index"));
    }

    /**
     * The method is used to output the JSONObject in table format that represents a record.
     * @throws JSONException
     */
    public void showRecord() throws JSONException {
        JSONArray receivedArray = (JSONArray) response.get("records");
        final JSONObject cur = receivedArray.getJSONObject(index);
        // Log.i("current record", cur.toString());
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
        // create the table using the JSONObject
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
            } else {
                // add an edit view as second column in that row
                edit = new EditText(this);
                edit.setText(cur.get(columns[i]).toString());
                edit.setTextColor(Color.BLACK);
                edit.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                edit.setPadding(5, 5, 5, 5);
                edit.setId(i);
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
                for (int i = 0; i < columns.length; i++) {
                    if (!fixFields.contains(columns[i])) {
                        EditText init = (EditText)findViewById(i);
                        try {
                            init.setText(cur.get(columns[i]).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
                // record all the changes in the table
                for (int i = 0; i < columns.length; i++) {
                    if (!fixFields.contains(columns[i])) {
                        EditText bind = (EditText)findViewById(i);
                        try {
                            cur.put(columns[i], bind.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Log.i("testing", bind.getText().toString());

                    }
                }
                Intent intent = new Intent(getApplicationContext(), ThresholdsDataActivity.class);
                intent.putExtra("response", response.toString());
                intent.putExtra("prevActivity", "ThresholdsEditActivity");
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

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ThresholdsEdit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.idt.trunkmon/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ThresholdsEdit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.idt.trunkmon/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
