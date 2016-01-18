package net.idt.trunkmon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViolationsDataActivity extends AppCompatActivity {
    TableLayout tl;
    TableRow tr;
    TextView column_name, column_value;

    TableRow record_header, record_tail;
    TextView head_info;
    EditText push_edit;
    BootstrapButton push, pull;


    List<String> columns;

    JSONObject request;
    JSONObject response;

    private GoogleApiClient client;

    Set<String> legendSet = new HashSet<String>();

    /**
     * Inner class of ViolationsDataActivity
     */
    private class LegendFlag {
        boolean CCR;
        boolean ALOC;
        boolean CCRThresholds;
        boolean ALOCThresholds;
    }

    /**
     * This method decides which cell of the data table needs to be highlighted.
     *
     * @param cur the current JSONObject
     * @return the legendFlags
     */
    private LegendFlag legendHighlighterLogic(JSONObject cur) throws JSONException {
        LegendFlag flags = new LegendFlag();
        // adding all highlighted relevant column names
        legendSet.add("CCR");
        legendSet.add("ALOC");
        legendSet.add("CCRThresholds");
        legendSet.add("ALOCThresholds");
        if (cur.get("CCR").toString().compareTo((cur.get("CCR Thresholds").toString())+"9") > 0) {
            flags.CCR = true;
        }
        if (cur.get("ALOC").toString().compareTo(cur.get("ALOC Delta From Threshold %").toString()) > 0) {
            flags.ALOC = true;
        }
        // other color logics added here to be consistent with the API in the web app
        return flags;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_violations_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tl = (TableLayout) findViewById(R.id.violations_table);
        */
        try {
            recreate_json();
            //  generateColumns();
            // showResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void recreate_json() throws JSONException{
        try
        {
            AWSResponse resp = new AWSResponse();
            Intent intent = getIntent();
            request = new JSONObject(intent.getExtras().getString("request"));
            Log.i("request", intent.getExtras().getString("request"));
            //response = new JSONObject(intent.getExtras().getString("response"));
            // String res = resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/First/thresholds").get();
            resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/First/thresholds");
            /*
            response = new JSONObject(res);
            Log.i("response", res);
            */
        }
        catch(Exception e)
        {

        }
    }

    /**
     * generate the number of columns needed to be presented in the table
     *
     * @throws JSONException
     */
    public void generateColumns() throws JSONException {
        // generateing default columns
        columns = new ArrayList<String>();
        String default_columns[] = {"Account Name", "CLLI", "Location"};
        for (String each : default_columns) {
            columns.add(each);
        }
        // generate extra columns according to selected showFields
        if (request.has("showFields")) {
            JSONArray extractColumns = (JSONArray) request.get("showFields");
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
    public void showResult(ProgressDialog progressDialog) throws JSONException {
        JSONArray receivedArray = (JSONArray) response.get("records");

        for (int i = 0; i < receivedArray.length(); i++) {
            JSONObject cur = receivedArray.getJSONObject(i);
            LegendFlag flags = legendHighlighterLogic(cur);
            // adding header to each json object
            record_header = new TableRow(this);
            head_info = new TextView(this);
            head_info.setText("record " + (i + 1) + " of " + receivedArray.length());
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
                String cur_column = columns.get(j % columns.size());
                column_name = new TextView(this);
                column_name.setText(cur_column);
                column_name.setTextColor(Color.BLACK);
                column_name.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_name.setPadding(5, 5, 5, 5);
                tr.addView(column_name);
                // column value in the second column
                column_value = new TextView(this);
                column_value.setText(cur.get(cur_column).toString());
                column_value.setTextColor(Color.BLACK);
                // manipulate the representation of color highlighter in data table
                if (legendSet.contains(cur_column)) {
                    switch(cur_column) {
                        case "CCR":
                            if (flags.CCR) {
                                column_value.setBackgroundColor(Color.YELLOW);
                            }
                            break;
                        case "ALOC":
                            if (flags.ALOC) {
                                column_value.setBackgroundColor(Color.LTGRAY);
                            }
                            break;
                        // add more if needed
                    }
                }
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
            //record_tail.setWeightSum(3);

            push_edit = new EditText(this);
            push_edit.setText("5%");
            push_edit.setPadding(0, 0, 0, 0);
            push_edit.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            push_edit.setWidth(30);
            push_edit.setPadding(10,0,0,10);

            push = new BootstrapButton(this);

            push.setRounded(true);
            //push.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            //push.setLayoutParams(new LayoutParams("%", LayoutParams.WRAP_CONTENT));
            push.setWidth(80);
            push.setHeight(50);
            // push.setBootstrapMode("primary");
            //push.setBootstrapSize("1g");
            push.setBootstrapSize(DefaultBootstrapSize.SM);
            push.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
            push.setText("Push");
            push.setPadding(0, 0, 0, 0);

            pull = new BootstrapButton(this);
            pull.setText("Pull");
            pull.setRounded(true);
            pull.setWidth(80);
            pull.setHeight(50);
            pull.setBootstrapSize(DefaultBootstrapSize.SM);
            pull.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
            final int temp = i;
            push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Row number: ",temp+"");

                }
            });

/*
            pull.setPadding(10, 0, 30, 10);
            pull.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
*/
            record_tail.addView(pull);
            record_tail.addView(push_edit);
            record_tail.addView(push);

            tl.addView(record_tail, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            //tl.addView(record_tail);
        }
        progressDialog.dismiss();

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

    class AWSResponse extends AsyncTask<String, Void, String> {

        private Exception exception;
        ProgressDialog progressDialog;
        String res;
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                StringBuilder sb = new StringBuilder();

                InputStreamReader is = new InputStreamReader(connection.getInputStream());

                reader = new BufferedReader(is);

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                res = sb.toString();
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ViolationsDataActivity.this,"Loading...",
                    "Loading application View, please wait...", false, false);

        }

        protected void onPostExecute(String response1) {
            setContentView(R.layout.activity_violations_data);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            try {
                response = new JSONObject(response1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("response", res);
            tl = (TableLayout) findViewById(R.id.violations_table);
            try {
                //recreate_json();
                generateColumns();
                showResult(progressDialog);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
           // progressDialog.dismiss();
            //initialize the View

            client = new GoogleApiClient.Builder(ViolationsDataActivity.this).addApi(AppIndex.API).build();

        }
    }
}
