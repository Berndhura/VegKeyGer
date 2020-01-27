package com.test.tichy.vegkey;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button btnBack = findViewById(R.id.button1);
        final RadioButton rbRice=findViewById(R.id.radioButton1);
        final RadioButton rbYST=findViewById(R.id.radioButton2);
        final RadioButton rbBlack=findViewById(R.id.radioButton3);
        final RadioButton rbCZ=findViewById(R.id.radioButton21);
        final RadioButton rbEN=findViewById(R.id.radioButton22);
        final RadioButton rbSpecCZ=findViewById(R.id.radioButton31);
        final RadioButton rbSpecLT=findViewById(R.id.radioButton32);
        final RadioButton rbSyntCZ=findViewById(R.id.radioButton41);
        final RadioButton rbSyntLT=findViewById(R.id.radioButton42);
        final RadioButton rbSpecInfoCZ=findViewById(R.id.radioButton51);
        final RadioButton rbSpecInfoLT=findViewById(R.id.radioButton52);
        final RadioButton rbSyntaxaAssoc=findViewById(R.id.radioButton61);
        final RadioButton rbSyntaxaAllia=findViewById(R.id.radioButton62);

        final Globals g = (Globals) getApplication();
        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) {vi.setBackgroundResource(R.drawable.tema);rbYST.setChecked(true);}
        if(g.getBackground()==1) {vi.setBackgroundResource(R.drawable.tema3);rbRice.setChecked(true);}
        if(g.getBackground()==2) {vi.setBackgroundResource(R.drawable.tema2);rbBlack.setChecked(true);}
        if(g.getLanguage()==1) {rbCZ.setChecked(true);}
        if(g.getLanguage()==2) {rbEN.setChecked(true);}
        if(g.getLangSpec()==1) {rbSpecCZ.setChecked(true);}
        if(g.getLangSpec()==2) {rbSpecLT.setChecked(true);}
        if(g.getLangSpecInfo()==1) {rbSpecInfoCZ.setChecked(true);}
        if(g.getLangSpecInfo()==2) {rbSpecInfoLT.setChecked(true);}
        if(g.getLangSynt()==1) {rbSyntCZ.setChecked(true);}
        if(g.getLangSynt()==2) {rbSyntLT.setChecked(true);}
        if(g.getSyntaxa()==1) {rbSyntaxaAssoc.setChecked(true);}
        if(g.getSyntaxa()==2) {rbSyntaxaAllia.setChecked(true);}

        btnBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbBlack.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                vi.setBackgroundResource(R.drawable.tema2);
                vi.setBackgroundColor(Color.BLACK);
                g.setBackground(2);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbYST.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                vi.setBackgroundResource(R.drawable.tema);
                g.setBackground(0);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbRice.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                vi.setBackgroundResource(R.drawable.tema3);
                g.setBackground(1);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSpecCZ.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSpec(1);
                generateVegKeyIni();
                List <String> sp = new ArrayList<String>();
                String[] ck = new String[50];
                ck = g.getCheck();
                sp = g.getItemSpec();
                sp.clear();
                for (int i = 0; i < 50; i++) {
                    ck[i] = null;
                }
                g.setCheck(ck);
                g.setItemSpec(sp);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSpecLT.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSpec(2);
                generateVegKeyIni();
                List <String> sp = new ArrayList<String>();
                String[] ck = new String[50];
                ck = g.getCheck();
                sp = g.getItemSpec();
                sp.clear();
                for (int i = 0; i < 50; i++) {
                    ck[i] = null;
                }
                g.setCheck(ck);
                g.setItemSpec(sp);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSpecInfoCZ.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSpecInfo(1);
                generateVegKeyIni();
           }
        });
        rbSpecInfoLT.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSpecInfo(2);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSyntaxaAssoc.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setSyntaxa(1);
                generateVegKeyIni();
            }
        });
        rbSyntaxaAllia.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setSyntaxa(2);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSyntCZ.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSynt(1);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbSyntLT.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                g.setLangSynt(2);
                generateVegKeyIni();
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbCZ.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
                if(g.getLanguage()!=1) {
                    g.setLanguage(1);
                    generateVegKeyIni();
                    Locale locale = new Locale("cs");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    overridePendingTransition( 0, 0);
                    finish();
                    startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                    overridePendingTransition( 0, 0);
                }
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        rbEN.setOnClickListener(new RadioButton.OnClickListener() {
            public void onClick(View v)
            {
              if(g.getLanguage()!=2) {
                  g.setLanguage(2);
                  generateVegKeyIni();
                  Locale locale = new Locale("en");
                  Locale.setDefault(locale);
                  Configuration config = new Configuration();
                  config.locale = locale;
                  getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                  overridePendingTransition( 0, 0);
                  finish();
                  startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                  overridePendingTransition( 0, 0);
              }
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    }



    public void generateVegKeyIni() {
        final Globals g = (Globals) getApplication();
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "VegKey");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "VegKey.ini");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append("Background::"+String.valueOf(g.getBackground())+"\r\n");
            writer.append("Language::"+String.valueOf(g.getLanguage())+"\r\n");
            writer.append("Species_names::"+String.valueOf(g.getLangSpec())+"\r\n");
            writer.append("Syntaxa_names::"+String.valueOf(g.getLangSynt())+"\r\n");
            writer.append("Species_n_info::"+String.valueOf(g.getLangSpecInfo())+"\r\n");
            writer.append("Syntaxa_Level::"+String.valueOf(g.getSyntaxa())+"\r\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
    }
}
