package net.idt.trunkmon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import database.UserDBHandler;
import database.VioDBHandler;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "login log message";
    UserDBHandler userDbHandler;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDbHandler = new UserDBHandler(this, null, null, 1);
        myApplication =(MyApplication)getApplicationContext();

        Log.i(TAG, "login activity onCreate");
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final BootstrapEditText et_boot_username = (BootstrapEditText)findViewById(R.id.et_boot_username);
        et_boot_username.setRounded(true);

        final BootstrapEditText et_boot_password = (BootstrapEditText)findViewById(R.id.et_boot_password);
        et_boot_password.setRounded(true);

        BootstrapButton btn_boot_login = (BootstrapButton)findViewById(R.id.boot_btn_login);



        btn_boot_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Check if input username and password matches, react to the result.
                 */
                int role = userDbHandler.checkUser(et_boot_username.getText().toString(),et_boot_password.getText().toString());
                if(role == -1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Username and password do not match.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    et_boot_username.setText("");
                    et_boot_password.setText("");
                }else if(role == 1){
                    myApplication.setUserRole("manager");
                    System.out.println("role is manager");
                    Intent intent = new Intent(getApplicationContext(),SelectionsActivity.class);
                    Log.i(TAG, "login button clicked");
                    startActivity(intent);
                }else{
                    myApplication.setUserRole("visitor");
                    System.out.println("role is visitor");
                    Intent intent = new Intent(getApplicationContext(),SelectionsActivity.class);
                    Log.i(TAG, "login button clicked");
                    startActivity(intent);
                }
            }
        });
    }



}
