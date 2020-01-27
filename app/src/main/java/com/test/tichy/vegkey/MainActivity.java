package com.test.tichy.vegkey;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    int udrzet=0, ax=0, ay=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Globals g = (Globals) getApplication();
        readVegKeyIni();
        if(g.getLangLoop()!=1 && g.getLanguage()>0) {
            g.setLangLoop(1);
            if (g.getLanguage() == 1) {
                Locale locale = new Locale("cs");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                restartActivity();
            }
            if (g.getLanguage() == 2) {
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                restartActivity();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Button btnContinue = findViewById(R.id.button2);
        final Button btnProb = findViewById(R.id.button3);
        final ImageButton btnSett = findViewById(R.id.button4);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            }
        }

        LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema_main);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema_main3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema_main2);

        btnContinue.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, descriptionActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnProb.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ProbabilisticKey.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnSett.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    }
    //--------------posunutí tam i zpět --------------------------------------------------------------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                if (udrzet==0)
                {
                    ax = (int) event.getX();
                    ay = (int) event.getY();
                    udrzet=1;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                udrzet=0;
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                if (udrzet==1)
                {
                    Display display = getWindowManager().getDefaultDisplay(); Point size = new Point(); display.getSize(size); int width = size.x;
                    if(ax-(int) event.getX()>width/2)
                    {
                        udrzet=0;
                        Intent intent = new Intent(this, ProbabilisticKey.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                    if((int) event.getX()-ax >width/2)
                    {
                        udrzet=0;
                        Intent intent = new Intent(this, descriptionActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    }
                    break;
                }
            }
        }
        return true;
    }


    public void readVegKeyIni() {
        final Globals g = (Globals) getApplication();

        StringBuilder text = new StringBuilder();
        g.setBackground(0);
        g.setLanguage(1);
        g.setLangSpec(2);
        g.setLangSpecInfo(2);
        g.setLangSynt(2);
        g.setSyntaxa(1);
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "VegKey");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root,"VegKey.ini");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("::");
                if (separated[0].contains("Background")) {
                    g.setBackground(Integer.parseInt(separated[1]));
                }
                if (separated[0].contains("Language")) {
                    g.setLanguage(Integer.parseInt(separated[1]));
                }
                if (separated[0].contains("Species_names")) {
                    g.setLangSpec(Integer.parseInt(separated[1]));
                }
                if (separated[0].contains("Species_n_info")) {
                    g.setLangSpecInfo(Integer.parseInt(separated[1]));
                }
                if (separated[0].contains("Syntaxa_names")) {
                    g.setLangSynt(Integer.parseInt(separated[1]));
                }
                if (separated[0].contains("Syntaxa_Level")) {
                    g.setSyntaxa(Integer.parseInt(separated[1]));
                }
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void restartActivity() {
        Intent intent = getIntent();
        overridePendingTransition( 0, 0);
        finish();
        startActivity(getIntent());
        overridePendingTransition( 0, 0);    }

}
