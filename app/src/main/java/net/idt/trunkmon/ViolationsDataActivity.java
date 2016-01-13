package net.idt.trunkmon;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class ViolationsDataActivity extends AppCompatActivity {
    // need to change later to add filter data columns
    // String columns[] = {"Division", "Account Names", "CLLI", "Location"};

    // simulated data part
    String values[] = {"Gold", "Verizon", "NP1", "USA", "Attemps", "Failed", "CCR",
            "Gold", "ATT", "NP4", "UK", "Attemps", "Failed", "CCR",
            "Silver", "Tmobile", "NP3", "Canada", "Attemps", "Failed", "CCR",
            "USDebit", "Sprint", "NP2", "Nigeria", "Attemps", "Failed", "CCR",
            "UKDebit", "Ultramobile", "NP5", "Japan", "Attemps", "Failed", "CCR"};

    // decided by the JSON length passed back from the server
    int JSON_count = 86; // changed with received.length()

    TableLayout tl;
    TableRow tr;
    TextView column_name, column_value;

    TableRow record_header, record_tail;
    TextView head_info;
    EditText push_edit;
    Button push, pull;

    JSONObject JColumns;
    List<String> columns;

    JSONObject received;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violations_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tl = (TableLayout) findViewById(R.id.violations_table);
        try {
            generateColumns();
            showResult();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // showData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * generate the number of columns needed to be presented in the table
     *
     * @throws JSONException
     */
    public void generateColumns() throws JSONException {
        // generateing default columns
        columns = new ArrayList<String>();
        String default_columns[] = {"Division", "Account Names", "CLLI", "Location"};
        for (String each : default_columns) {
            columns.add(each);
        }
        //simulate the receiving filtered data in JSON object
        JColumns = new JSONObject();
        JSONArray jArray = new JSONArray();
        jArray.put("Attemps");
        jArray.put("Failed");
        jArray.put("CRR");
        JColumns.put("Time", "XX-XX-XX");
        JColumns.put("showFields", jArray);
        if (JColumns.has("showFields")) {
            JSONArray extractColumns = (JSONArray) JColumns.get("showFields");
            for (int i = 0; i < extractColumns.length(); i++) {
                columns.add(extractColumns.get(i).toString());
            }
        }
    }

    /**
     * simulate receiving data using JSON - will be used with the AWS in further developement
     *
     * @throws JSONException
     */
    public void showResult() throws JSONException {
        // simulate received JSONObject by using String array values
        received = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < values.length / columns.size(); i++) {
            JSONObject cur = new JSONObject();
            for (int j = 0; j < columns.size(); j++) {
                cur.put(columns.get(j), values[j + i * columns.size()]);
            }
            array.put(cur);
        }
        received.put("Records", array);
        // parsing the simulated received JSON data
        JSONArray receivedArray = (JSONArray)received.get("Records");
        for (int i = 0; i < receivedArray.length(); i++) {
            JSONObject cur = receivedArray.getJSONObject(i);
            // adding header to each json object
            record_header = new TableRow(this);
            head_info = new TextView(this);
            head_info.setText("record " + (i + 1) + " of " + JSON_count);
            head_info.setTextColor(Color.BLUE);
            head_info.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            head_info.setPadding(5, 5, 5, 5);
            record_header.addView(head_info);

            tl.addView(record_header, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < columns.size(); j++) {
                tr = new TableRow(this);
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                // column name in the first column
                column_name = new TextView(this);
                column_name.setText(columns.get(j % columns.size()));
                column_name.setTextColor(Color.BLACK);
                column_name.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_name.setPadding(5, 5, 5, 5);
                tr.addView(column_name);
                // column value in the second column
                column_value = new TextView(this);
                column_value.setText(cur.get(columns.get(j % columns.size())).toString());
                column_value.setTextColor(Color.BLACK);
                column_value.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_value.setPadding(5, 5, 5, 5);
                tr.addView(column_value);

                tl.addView(tr, new TableLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }

            // adding buttons to modify the current record
            record_tail = new TableRow(this);
            push_edit = new EditText(this);
            push_edit.setText("5%");
            push_edit.setPadding(0, 0, 0, 0);
            push_edit.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            push = new Button(this);
            push.setText("Push");
            push.setPadding(0, 0, 0, 0);
            push.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            pull = new Button(this);
            pull.setText("Pull");
            pull.setPadding(0, 0, 0, 0);
            pull.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            record_tail.addView(pull);
            record_tail.addView(push_edit);
            record_tail.addView(push);

            tl.addView(record_tail, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * simulate receiving data using array and arrayList
     */
    public void showData() {
        for (int i = 0; i < values.length / columns.size(); i++) {
            // adding header to each json object
            record_header = new TableRow(this);
            head_info = new TextView(this);
            head_info.setText("record " + (i + 1) + " of " + JSON_count);
            head_info.setTextColor(Color.BLUE);
            head_info.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            head_info.setPadding(5, 5, 5, 5);
            record_header.addView(head_info);

            tl.addView(record_header, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < columns.size(); j++) {
                tr = new TableRow(this);
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                // column name in the first column
                column_name = new TextView(this);
                column_name.setText(columns.get(j % columns.size()));
                column_name.setTextColor(Color.BLACK);
                column_name.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_name.setPadding(5, 5, 5, 5);
                tr.addView(column_name);
                // column value in the second column
                column_value = new TextView(this);
                column_value.setText(values[j + i * columns.size()]);
                column_value.setTextColor(Color.BLACK);
                column_value.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_value.setPadding(5, 5, 5, 5);
                tr.addView(column_value);

                tl.addView(tr, new TableLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }
            // adding buttons to modify the current record
            record_tail = new TableRow(this);
            push_edit = new EditText(this);
            push_edit.setText("5%");
            push_edit.setPadding(0, 0, 0, 0);
            push_edit.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            push = new Button(this);
            push.setText("Push");
            push.setPadding(0, 0, 0, 0);
            push.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            pull = new Button(this);
            pull.setText("Pull");
            pull.setPadding(0, 0, 0, 0);
            pull.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            record_tail.addView(pull);
            record_tail.addView(push_edit);
            record_tail.addView(push);

            tl.addView(record_tail, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    // override onclicklistener on push button

    // override gettext on push_edit edittext

    // override gettext on pull button

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
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
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (id == R.id.action_search) {
            // implemented later
            return true;
        } else if (id == R.id.vioLegendBt) {
            startActivity(new Intent(this, vioPopLegend.class));
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
                "ViolationsData Page", // TODO: Define a title for the content shown.
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
                "ViolationsData Page", // TODO: Define a title for the content shown.
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
