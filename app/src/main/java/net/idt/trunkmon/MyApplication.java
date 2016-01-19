package net.idt.trunkmon;

import android.app.Application;

/**
 * Created by kramakrishna on 1/19/2016.
 */
public class MyApplication extends Application {

    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }
}