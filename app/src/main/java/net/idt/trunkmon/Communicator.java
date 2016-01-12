package net.idt.trunkmon;

import java.util.ArrayList;

/**
 * Created by Rama on 1/10/2016.
 */
public interface Communicator {

    public void responseTime(ArrayList<String> s);
    public void responseCountry(ArrayList<String> s);
    public void responseDivision(ArrayList<String> s);
    public void responseAddItems(ArrayList<String> s);
    public void responseShowFields(ArrayList<String> s);

}
