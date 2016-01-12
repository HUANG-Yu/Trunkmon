package net.idt.trunkmon;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class thresPopLegend extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threslegend);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = 400;
        int height = 200;
        getWindow().setLayout(width,height);
    }
}
