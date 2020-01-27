package com.test.tichy.vegkey;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeachSpecies extends AppCompatActivity {
    int udrzet = 0, ax = 0, ay = 0;
    private EditText edt;
    List<String> list = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();
    List<String> list4 = new ArrayList<String>();
    List<String> list5 = new ArrayList<String>();
    List<String> list6 = new ArrayList<String>();
    InputStream in;
    BufferedReader reader;
    String line;
    TextView text, text2;
    protected static String jmeno;
    Scanner input;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    int x;
    private int matka;
    List<String> sp = new ArrayList<String>();
    List<String> ch = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_species);
        text = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textViewB);
        edt = (EditText) findViewById(R.id.editText1);
        final ListView lv = (ListView) findViewById(R.id.listView);
        final Globals g = (Globals) getApplication();
        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema2);


        String TAG="";

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> arg0,
                                                              View arg1, int arg2, long arg3) {
                                          // TODO Auto-generated method stub
                                          String vyber = String.valueOf(lv.getItemAtPosition(arg2));
                                          text.setText(vyber);
                                              if (g.getLangSpec() == 1) {
                                                  input = new Scanner(getResources().openRawResource(R.raw.speclist3));
                                              } else {
                                                  input = new Scanner(getResources().openRawResource(R.raw.speclist3cz));
                                              }
                                              while (input.hasNextLine()) {
                                                  line = input.nextLine();
                                                  String[] separated = line.split("\t");
                                                  if (Integer.parseInt(separated[2]) == Integer.parseInt(list6.get(arg2))) {
                                                      text2.setText(separated[1]);
                                                      break;
                                                  }
                                              }

                                          //Toast.makeText(SeachSpecies.this, String.valueOf(list4.size()), Toast.LENGTH_LONG).show();
//                                          for(int i=0;i<list4.size();i++)  {
//                                              if(vyber==list4.get(i))
//                                              {
//                                                  edt.setText(String.valueOf(list2.get(i)));
//                                                  break;

//                                              }
//                                          }
                                      }
                                  }
        );
        if(g.getLangSpec()==1) {
            input = new Scanner(this.getResources().openRawResource(R.raw.speclist3cz));
        }else {
            input = new Scanner(this.getResources().openRawResource(R.raw.speclist3));
        }

        x=0;
        while(input.hasNextLine())  {
            line=input.nextLine();
            String[] separated = line.split("\t");
            list.add(separated[1]);
            list2.add(separated[0]);
            list4.add(separated[1]);
            list5.add(separated[2]);
            list6.add(separated[2]);
            x=x+1;
        }
        //Toast.makeText(SeachSpecies.this, String.valueOf(x), Toast.LENGTH_LONG).show();
        adapter = new ArrayAdapter<String>(this, R.layout.search_species_row, R.id.row, list);
        lv.setAdapter(adapter);

        final Button leftButton = (Button) findViewById(R.id.button2);
            leftButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                // Back to previous
                Intent intent = new Intent(SeachSpecies.this, ProbabilisticKey.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        final Button CentBut = (Button) findViewById(R.id.button3);
        CentBut.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                edt.setText("");
            }
        });

        final Button RigBut = (Button) findViewById(R.id.button1);
        RigBut.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                if(!String.valueOf(text.getText()).isEmpty())
                {
                    String[] ck = new String[50];
                    String[] species = new String[50];
                    ck = g.getCheck();
                    species = g.getList();
                    sp = g.getItemSpec();
                    String zita = String.valueOf(text.getText()).trim();
                    int j = 0;
                    for (int i = 0; i < sp.size(); i++) {
                        if (sp.get(i).equals(zita)) {
                            j = 1;
                            break;
                        }
                    }
                    if (j == 0) {
                        sp.add((String) text.getText());
                        ck[sp.size() - 1] = "0";
                    }
                    g.setItemSpec(sp);
                    g.setCheck(ck);

                    Intent intent = new Intent(SeachSpecies.this, ProbabilisticKey.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        edt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(matka==0)
                {
                    matka=1;
                    String lala=edt.getText().toString().toUpperCase();
//                    edt.setSelection(edt.getText().length());

                    int lens=lala.length();
                    if (lens>6) {return;}
                    String last = null;
                    list3.clear();
                    list6.clear();
                    String veka = null;
                    int pom=0;
                    for(int i=0;i<x;i++)
                    {
                        last=list2.get(i).toString().trim().substring(0, lens);
                        if(last.equals(lala.trim()))
                        {
                            veka=list4.get(i).toString();
                            list3.add(veka);
                            list6.add(list5.get(i).toString());
                            pom=1;
                        }
                        else
                        {
                            if(pom==1){i=x+1;}
                        }
                    }
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);
                    adapter2 = new ArrayAdapter<String>(SeachSpecies.this ,R.layout.search_species_row, R.id.row, list3);
                    lv.setAdapter(adapter2);
                }
                matka=0;
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SeachSpecies.this, ProbabilisticKey.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
// ------------------------------------------------------------------


