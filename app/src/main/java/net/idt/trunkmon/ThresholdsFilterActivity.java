package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.kaede.tagview.OnTagDeleteListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import util.MultiSelectionSpinner_thresholds;


public class ThresholdsFilterActivity extends AppCompatActivity implements Communicator1{
    private MultiSelectionSpinner_thresholds countrySpinner;
    private MultiSelectionSpinner_thresholds startCountrySpinner;
    private MultiSelectionSpinner_thresholds divisionSpinner;

    String[] countryItems = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola"};
    String[] startCountryItems = {"A", "B", "C", "D", "E", "F", "G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    String[] divisionItems = {"Gold", "USDebit", "UKDebit", "Carriers", "Silver"};

    List<String> selectionStartCountry = new ArrayList<>();
    List<String> selectionCountry = new ArrayList<>();
    List<String> selectionDivision = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thresholds_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        countrySpinner = (MultiSelectionSpinner_thresholds)findViewById(R.id.thCountrySpinner);
        countrySpinner.spinner_title = "Country";
        countrySpinner.setItems(countryItems);



        startCountrySpinner = (MultiSelectionSpinner_thresholds) findViewById(R.id.thStartCountrySpinner);
        startCountrySpinner.spinner_title = "Start Country";
        startCountrySpinner.setItems(startCountryItems);
        //startCountrySpinner.setSelection(new int[]{2, 6});


        divisionSpinner = (MultiSelectionSpinner_thresholds) findViewById(R.id.thDivisionSpinner);
        divisionSpinner.spinner_title= "Division";
        divisionSpinner.setItems(divisionItems);

        BootstrapButton btn_apply = (BootstrapButton)findViewById(R.id.tApplyButton);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String request;
                //AWSResponse resp = new AWSResponse();
                // Log.i("AWS RESPONSE", resp.e)
                try {
                    JSONObject req = new JSONObject();


                    JSONArray country = new JSONArray(selectionCountry);
                    req.put("country", country);

                    JSONArray division = new JSONArray(selectionDivision);
                    req.put("division", division);

                    JSONArray startCountry = new JSONArray(selectionStartCountry);
                    req.put("startCountry", selectionStartCountry);

                    Intent i = new Intent(getApplicationContext(), ThresholdsDataActivity.class);
                    request = req.toString();
                    i.putExtra("request", request);
                    //  startActivity(i);
                   // String response = resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/violations/violations").get();
                    // String response = resp.execute("https://rbf5ou43pa.execute-api.us-east-1.amazonaws.com/dev/thresholds").get();
                    // String response = resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/violations/violations").get();

                    // TextView tv_response = (TextView) findViewById(R.id.tv_response);
                    //tv_response.setText(response);
                    //i.putExtra("response", response);
                    //Log.i("Response", response);


                    startActivity(i);

                } catch (Exception e) {
                    System.out.println("JSON failed.");
                }
            }
        });

        BootstrapButton btn_reset = (BootstrapButton)findViewById(R.id.tResetButton);
        btn_reset.setRounded(true);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagView tagview_country = (TagView) findViewById(R.id.tagview_country);
                tagview_country.removeAllTags();
                selectionCountry.clear();
                for (int k = 0; k < countryItems.length; k++) {
                    countrySpinner.mSelection[k] = false;
                }

                TagView tagview_startCountry = (TagView) findViewById(R.id.tagview_startCountry);
                tagview_startCountry.removeAllTags();
                selectionStartCountry.clear();
                for (int k = 0; k < startCountryItems.length; k++) {
                    startCountrySpinner.mSelection[k] = false;
                }

                TagView tagview_division = (TagView) findViewById(R.id.tagview_division);
                tagview_division.removeAllTags();
                selectionDivision.clear();
                for (int k = 0; k < divisionItems.length; k++) {
                    divisionSpinner.mSelection[k] = false;
                }
            }

        });

    }

    @Override
    public void responsestartCountry(ArrayList<String> text) {


        TagView tagview_country = (TagView)findViewById(R.id.tagview_startCountry);
        tagview_country.removeAllTags();
        // List<String> selection = new ArrayList<String>(text);
        selectionStartCountry.clear();
        selectionStartCountry = new ArrayList<>(text);
        //selection.add("check1");
        //List<String> selection = startCountrySpinner.getSelectedStrings();
        for(String s:selectionStartCountry)
        {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_country.addTag(tag);
        }
        tagview_country.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for(int k=0;k<countryItems.length;k++)
                {
                    if(startCountryItems[k].contains(tag.text))
                    {
                        startCountrySpinner.mSelection[k] = false;
                    }
                }

            }
        });
    }

    @Override
    public void responseCountry(ArrayList<String> text) {

        TagView tagview_country = (TagView)findViewById(R.id.tagview_country);
        tagview_country.removeAllTags();
        // List<String> selection = new ArrayList<String>(text);
        selectionCountry.clear();
        selectionCountry = new ArrayList<>(text);
        //selection.add("check1");
        //List<String> selection = startCountrySpinner.getSelectedStrings();
        for(String s:selectionCountry)
        {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_country.addTag(tag);
        }
        tagview_country.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < countryItems.length; k++) {
                    if (countryItems[k].contains(tag.text)) {
                        countrySpinner.mSelection[k] = false;
                    }
                }

            }
        });
    }

    @Override
    public void responseDivision(ArrayList<String> text) {
        TagView tagview_division = (TagView)findViewById(R.id.tagview_division);
        tagview_division.removeAllTags();
        //List<String> selection = new ArrayList<String>(text);
        selectionDivision.clear();
        selectionDivision = new ArrayList<>(text);
        //selection.add("check1");
        //List<String> selection = startCountrySpinner.getSelectedStrings();
        for(String s:selectionDivision)
        {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_division.addTag(tag);
        }
        tagview_division.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < divisionItems.length; k++) {
                    if (divisionItems[k].contains(tag.text)) {
                        divisionSpinner.mSelection[k] = false;
                    }
                }

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

/*
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
//}
*/

