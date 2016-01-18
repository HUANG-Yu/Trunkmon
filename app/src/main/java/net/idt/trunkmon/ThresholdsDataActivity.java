package net.idt.trunkmon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.DialogInterface;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.graphics.Color;
import android.widget.TableRow.LayoutParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ThresholdsDataActivity extends AppCompatActivity {
    TableLayout tl;
    TableRow tr;
    TextView column_name, column_value;

    TableRow record_header, record_tail;
    TextView head_info;
    Button edit;
    private GoogleApiClient client;
    String[] columns = {"Location", "Division", "Tod", "Auto CCR", "Auto ALOC",
        "Auto Attempts", "Auto Memo", "Rev CCR", "Rev ALOC", "Rev Attempts",
        "Rev Memo"};

    JSONObject request;
    JSONObject response;

    Set<String> legendSet = new HashSet<String>();

    private class LegendFlag {
        boolean CCR;
        boolean ALOC;
    }

    private LegendFlag legendHighlighterLogic(JSONObject cur) throws JSONException {
        LegendFlag flags = new LegendFlag();
        legendSet.add("Auto CCR");
        legendSet.add("Auto ALOC");
        if (cur.get("Auto CCR").toString().compareTo((cur.get("Rev CCR").toString())) > 0) {
            flags.CCR = true;
        }
        if (cur.get("Auto ALOC").toString().compareTo(cur.get("Rev ALOC").toString()) > 0) {
            flags.ALOC = true;
        }
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
        /*
        Button edit = (Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(ThresholdsDataActivity.this);
                popup.setTitle("Edit");
                popup.setMessage("Edit Fields");

                popup.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                                Intent myIntent1 = new Intent(view.getContext(), LoginActivity.class);
                            }
                        });

                popup.setNegativeButton("Reset",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                popup.show();
            }
        });
        */

    }

    public void recreate_json() throws JSONException {
        AWSResponse resp = new AWSResponse();
        Intent intent = getIntent();
        request = new JSONObject(intent.getExtras().getString("request"));
        Log.i("request", intent.getExtras().getString("request"));
        //response = new JSONObject(intent.getExtras().getString("response"));
        //Log.i("response", intent.getExtras().getString("response"));
        resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/violations/violations");
    }

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

            for (int j = 0; j < columns.length; j++) {
                tr = new TableRow(this);
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                // column name in the first column
                column_name = new TextView(this);
                column_name.setText(columns[j]);
                column_name.setTextColor(Color.BLACK);
                column_name.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                column_name.setPadding(5, 5, 5, 5);
                tr.addView(column_name);
                // column value in the second column
                column_value = new TextView(this);
                column_value.setText(cur.get(columns[j]).toString());
                column_value.setTextColor(Color.BLACK);
                // manipulate the representation of color highlighter in data table
                if (legendSet.contains(columns[j])) {
                    switch(columns[j]) {
                        case "Auto CCR":
                            if (flags.CCR) {
                                column_value.setBackgroundColor(Color.LTGRAY);
                            }
                            break;
                        case "Auto ALOC":
                            if (flags.ALOC) {
                                column_value.setBackgroundColor(Color.DKGRAY);
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
            edit = new Button(this);
            edit.setText("Edit");
            edit.setVisibility(View.VISIBLE);

            edit.setPadding(0, 0, 0, 0);
            edit.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            record_tail.addView(edit);

            tl.addView(record_tail, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));


        }
        progressDialog.dismiss();
    }

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
        } else if (id == R.id.vioLegendBt) {
            startActivity(new Intent(this, thresPopLegend.class));
            return true;
        } else {
            //id == R.id.action_about
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            progressDialog = ProgressDialog.show(ThresholdsDataActivity.this,"Loading...",
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
                //generateColumns();
                showResult(progressDialog);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            // progressDialog.dismiss();
            //initialize the View

            client = new GoogleApiClient.Builder(ThresholdsDataActivity.this).addApi(AppIndex.API).build();

        }
    }
}
