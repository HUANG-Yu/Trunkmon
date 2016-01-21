package net.idt.trunkmon;

import java.util.ArrayList;

/**
 * Implemented by ViolationsFilterActivity
 */
public interface Communicator {

    public void responseTime(ArrayList<String> s);
    public void responseCountry(ArrayList<String> s);
    public void responseDivision(ArrayList<String> s);
    public void responseAddItems(ArrayList<String> s);
    public void responseShowFields(ArrayList<String> s);

}
