package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

import me.kaede.tagview.OnTagDeleteListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import util.MultiSelectionSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBHandler;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


public class ViolationsFilterActivity extends AppCompatActivity implements Communicator {
    private MultiSelectionSpinner startCountrySpinner;
    private MultiSelectionSpinner divisionSpinner;
    private MultiSelectionSpinner additionalSpinner;
    private MultiSelectionSpinner showFieldsSpinner;
    private MultiSelectionSpinner timeDropdown;
    String[] startCountryItems = new String[26];
    String[] timeItems = new String[25];
    String[] divisionItems = {"Gold", "USDebit", "Silver", "UKDebit", "Carriers"};
    String[] additionalItems = {"review-pulled", "auto-pulled", "cross division saved", "excluded locations", "managed countries only"};
    String[] showFieldsItems = {"Switch", "Strike", "Completed", "Minutes", "CCR", "ALOC", "ALOC Delta From Threshold %"};

    List<String> selectionTime = new ArrayList<>();
    List<String> selectionCountry = new ArrayList<>();
    List<String> selectionDivision = new ArrayList<>();
    List<String> selectionAddItems = new ArrayList<>();
    List<String> selectionShowFields = new ArrayList<>();

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violations_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create database handler here
        dbHandler = new DBHandler(this, null, null, 1);

        getTimeItems();
        getCountryItems();

        timeDropdown = (MultiSelectionSpinner) findViewById(R.id.timeSpinner);
        timeDropdown.spinner_title = "Time";
        timeDropdown.setItems(timeItems);
        responseTime(dbHandler.getPreTime());

        startCountrySpinner = (MultiSelectionSpinner) findViewById(R.id.startCountrySpinner);
        startCountrySpinner.spinner_title = "Start Country";
        startCountrySpinner.setItems(startCountryItems);
        responseCountry(dbHandler.getPreStartCountry());


        divisionSpinner = (MultiSelectionSpinner) findViewById(R.id.divisionSpinner);
        divisionSpinner.spinner_title = "Division";
        divisionSpinner.setItems(divisionItems);
        responseDivision(dbHandler.getPreDivision());

        additionalSpinner = (MultiSelectionSpinner) findViewById(R.id.additionalSpinner);
        additionalSpinner.spinner_title = "Additional items";
        additionalSpinner.setItems(additionalItems);
        responseAddItems(dbHandler.getPreAdditional());


        showFieldsSpinner = (MultiSelectionSpinner) findViewById(R.id.showFieldsSpinner);
        showFieldsSpinner.spinner_title = "Showfields";
        showFieldsSpinner.setItems(showFieldsItems);
        responseShowFields(dbHandler.getPreShowFields());

        BootstrapButton btn_apply = (BootstrapButton) findViewById(R.id.vApplyButton);
        btn_apply.setRounded(true);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHandler.addVioFilterTime(selectionTime);
                dbHandler.addVioFilterStartCountry(selectionCountry);
                dbHandler.addVioFilterDivision(selectionDivision);
                dbHandler.addVioFilterAdd(selectionAddItems);
                dbHandler.addVioFilterShowFileds(selectionShowFields);

                String request;
                //AWSResponse resp = new AWSResponse();
                // Log.i("AWS RESPONSE", resp.e)
                try {
                    JSONObject req = new JSONObject();
                    req.put("time", selectionTime.get(0));

                    JSONArray country = new JSONArray(selectionCountry);
                    req.put("startCountry", country);

                    JSONArray division = new JSONArray(selectionDivision);
                    req.put("division", division);

                    JSONArray additionalItems = new JSONArray(selectionAddItems);
                    req.put("additionalItems", additionalItems);

                    JSONArray showFields = new JSONArray(selectionShowFields);
                    req.put("showFields", showFields);

                    Intent i = new Intent(getApplicationContext(), ViolationsDataActivity.class);
                    request = req.toString();
                    i.putExtra("request", request);

                    // String response = resp.execute("https://l7o8agu92l.execute-api.us-east-1.amazonaws.com/First/thresholds").get();

                    //TextView tv_response = (TextView)findViewById(R.id.tv_response);
                    //tv_response.setText(response);
                    //i.putExtra("response", response);
                    //Log.i("Response",response);

                    startActivity(i);

                } catch (Exception e) {
                    System.out.println("JSON failed.");
                }
            }
        });

        BootstrapButton btn_pre = (BootstrapButton) findViewById(R.id.vPreFilterButton);
        btn_pre.setRounded(true);
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseTime(dbHandler.getPreTime());
                responseCountry(dbHandler.getPreStartCountry());
                responseCountry(dbHandler.getPreDivision());
                responseAddItems(dbHandler.getPreAdditional());
                responseShowFields(dbHandler.getPreShowFields());
            }
        });

        BootstrapButton btn_reset = (BootstrapButton) findViewById(R.id.vResetButton);
        btn_reset.setRounded(true);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagView tagview_time = (TagView) findViewById(R.id.tagview_time);
                tagview_time.removeAllTags();
                selectionTime.clear();
                for (int k = 0; k < timeItems.length; k++) {
                    timeDropdown.mSelection[k] = false;
                }
                TagView tagview_country = (TagView) findViewById(R.id.tagview_country);
                tagview_country.removeAllTags();
                selectionCountry.clear();
                for (int k = 0; k < startCountryItems.length; k++) {
                    startCountrySpinner.mSelection[k] = false;
                }
                TagView tagview_division = (TagView) findViewById(R.id.tagview_division);
                tagview_division.removeAllTags();
                selectionDivision.clear();
                for (int k = 0; k < divisionItems.length; k++) {
                    divisionSpinner.mSelection[k] = false;
                }
                TagView tagview_addition = (TagView) findViewById(R.id.tagview_addition);
                tagview_addition.removeAllTags();
                selectionAddItems.clear();
                for (int k = 0; k < additionalItems.length; k++) {
                    additionalSpinner.mSelection[k] = false;
                }
                TagView tagview_showFields = (TagView) findViewById(R.id.tagview_showFields);
                tagview_showFields.removeAllTags();
                selectionShowFields.clear();
                for (int k = 0; k < showFieldsItems.length; k++) {
                    showFieldsSpinner.mSelection[k] = false;
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

    private void getCountryItems() {
        for (int i = 0; i < 26; i++) {
            char cur = (char) (65 + i);
            startCountryItems[i] = "" + cur;
        }
    }

    private void getTimeItems() {
        timeItems = new String[25];

        Date date = new Date();
        Date previousDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        String year = new SimpleDateFormat("yyyy").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String preYear = new SimpleDateFormat("yyyy").format(previousDate);
        String preMonth = new SimpleDateFormat("MM").format(previousDate);
        String preDay = new SimpleDateFormat("dd").format(previousDate);
        String hour = new SimpleDateFormat("HH").format(date);

        int lastHour = Integer.parseInt(hour) - 2;

        for (int i = lastHour; i < 24; i++) {
            timeItems[i - lastHour] = preYear + "-" + preMonth + "-" + preDay + " " + String.format("%02d", i) + ":00";
        }
        for (int i = 0; i <= lastHour; i++) {
            timeItems[24 - lastHour + i] = year + "-" + month + "-" + day + " " + String.format("%02d", i) + ":00";
        }
    }


    @Override
    public void responseTime(ArrayList<String> text) {
        TagView tagview_time = (TagView) findViewById(R.id.tagview_time);
        tagview_time.removeAllTags();
        selectionTime.clear();
        selectionTime = new ArrayList<>(text);

        for (String s : selectionTime) {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_time.addTag(tag);
        }
        tagview_time.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < timeItems.length; k++) {
                    if (timeItems[k].contains(tag.text)) {
                        timeDropdown.mSelection[k] = false;
                        selectionTime.remove(timeItems[k]);
                    }
                }

            }
        });
    }

    @Override
    public void responseCountry(ArrayList<String> text) {
        TagView tagview_country = (TagView) findViewById(R.id.tagview_country);
        tagview_country.removeAllTags();
        selectionCountry.clear();
        selectionCountry = new ArrayList<>(text);
        for (String s : selectionCountry) {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_country.addTag(tag);
        }
        tagview_country.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < startCountryItems.length; k++) {
                    if (startCountryItems[k].contains(tag.text)) {
                        startCountrySpinner.mSelection[k] = false;
                        selectionCountry.remove(startCountryItems[k]);
                    }
                }

            }
        });
    }

    @Override
    public void responseDivision(ArrayList<String> text) {

        TagView tagview_division = (TagView) findViewById(R.id.tagview_division);
        tagview_division.removeAllTags();
        selectionDivision.clear();
        selectionDivision = new ArrayList<>(text);
        for (String s : selectionDivision) {
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
                        selectionDivision.remove(divisionItems[k]);
                    }
                }

            }
        });
    }

    @Override
    public void responseAddItems(ArrayList<String> text) {

        TagView tagview_addition = (TagView) findViewById(R.id.tagview_addition);
        tagview_addition.removeAllTags();
        selectionAddItems.clear();
        selectionAddItems = new ArrayList<>(text);
        for (String s : selectionAddItems) {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_addition.addTag(tag);
        }
        tagview_addition.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < additionalItems.length; k++) {
                    if (additionalItems[k].contains(tag.text)) {
                        additionalSpinner.mSelection[k] = false;
                        selectionAddItems.remove(additionalItems[k]);
                    }
                }

            }
        });
    }

    @Override
    public void responseShowFields(ArrayList<String> text) {

        TagView tagview_showFields = (TagView) findViewById(R.id.tagview_showFields);
        tagview_showFields.removeAllTags();
        selectionShowFields.clear();
        selectionShowFields = new ArrayList<>(text);
        for (String s : selectionShowFields) {
            Tag tag = new Tag(s);
            tag.isDeletable = true;
            tagview_showFields.addTag(tag);
        }
        tagview_showFields.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                for (int k = 0; k < showFieldsItems.length; k++) {
                    if (showFieldsItems[k].contains(tag.text)) {
                        showFieldsSpinner.mSelection[k] = false;
                        selectionShowFields.remove(showFieldsItems[k]);
                    }
                }

            }
        });
    }


}

