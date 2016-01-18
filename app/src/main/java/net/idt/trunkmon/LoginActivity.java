package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "login log message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "login activity onCreate");
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BootstrapEditText et_boot_username = (BootstrapEditText)findViewById(R.id.et_boot_username);
        et_boot_username.setRounded(true);

        BootstrapEditText et_boot_password = (BootstrapEditText)findViewById(R.id.et_boot_password);
        et_boot_password.setRounded(true);

        BootstrapButton btn_boot_login = (BootstrapButton)findViewById(R.id.boot_btn_login);


        btn_boot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelectionsActivity.class);
                Log.i(TAG, "login button clicked");
                startActivity(intent);
            }
        });
    }



}
