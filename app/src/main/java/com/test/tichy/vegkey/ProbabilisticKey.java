package com.test.tichy.vegkey;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;

public class ProbabilisticKey extends AppCompatActivity {
    List<String> aaa = new ArrayList<String>();
    List<String> sp = new ArrayList<String>();
    List<String> sppom = new ArrayList<String>();
    String[] ck= new String[50];

    int udrzet=0, ax=0, ay=0;

     CustomAdapter cad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.probabilistic_key);
        //Toast.makeText(ProbabilisticKey.this, String.valueOf(1), Toast.LENGTH_SHORT).show();
        final Button btnAdd = findViewById(R.id.button1);
        final Button btnRem = findViewById(R.id.button2);
        final Button btnClr = findViewById(R.id.button3);
        final Button btnVeg = findViewById(R.id.button4);
        final Button btnBack = findViewById(R.id.button5);
        final ListView listView = (ListView) findViewById(R.id.listView);

        final Globals g = (Globals) getApplication();
        ck=g.getCheck();
        sp=g.getItemSpec();
        g.setProbKey(1);

        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema2);

        setTitle("Vegetation Key");
        // set the adapter to fill the data in ListView
        String [] str = sp.toArray(new String[sp.size()]);
        cad = new CustomAdapter(getApplicationContext(), str, ck);
        listView.setAdapter(cad);

        btnClr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
               if(sp.size()>0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProbabilisticKey.this);
                    builder.setMessage(R.string.PBkey_message3)
                            .setCancelable(false)
                            .setPositiveButton(R.string.PBkey_message3_A1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    sp.clear();
                                    for (int i = 0; i < 50; i++) {
                                        ck[i] = null;
                                    }
                                    String[] str = sp.toArray(new String[sp.size()]);
                                    cad = new CustomAdapter(getApplicationContext(), str, ck);
                                    listView.setAdapter(cad);
                                    g.setCheck(ck);
                                    g.setItemSpec(sp);
                                }
                            })
                            .setNegativeButton(R.string.PBkey_message3_A2, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // some code if you want
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
           }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnRem.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
  /////////////////////////////////////
                int k=0;
                sppom.clear();
                if(sp.size()>0) {
                    for(int i=0;i<sp.size();i++) {
                        if(ck[i].equals("0")) {
                            sppom.add(k, sp.get(i));
                            k=k+1;
                        }
                        ck[i]="0";
                    }
                    sp.clear();
                    for(int i=0;i<k;i++){
                        sp.add(sppom.get(i));
                    }
                    sppom.clear();
                    g.setCheck(ck);
                    g.setItemSpec(sp);
                    String[] str = sp.toArray(new String[sp.size()]);
                    cad = new CustomAdapter(getApplicationContext(), str, ck);
                    listView.setAdapter(cad);
                }
            }

        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnAdd.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ProbabilisticKey.this, SearchSpecies.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ProbabilisticKey.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        btnVeg.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                if (sp.size()>0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProbabilisticKey.this);
                    builder.setMessage(R.string.PBkey_message2)
                            .setCancelable(false)
                            .setNegativeButton(R.string.PBkey_message2_A1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    g.setType("FOR");
                                    // some code if you want
//                                    dialog.cancel();
                                    Intent intent = new Intent(ProbabilisticKey.this, SyntaxaActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            })
                            .setPositiveButton(R.string.PBkey_message2_A2, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Globals a = (Globals) getApplication();
                                    g.setType("NFR");
                                    // some code if you want
//                                    dialog.cancel();
                                    Intent intent = new Intent(ProbabilisticKey.this, SyntaxaActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProbabilisticKey.this);
                    builder.setMessage(R.string.PBkey_message1)
                            .setCancelable(false)
                            .setPositiveButton("  OK  ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
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
                        Intent intent = new Intent(this, SearchSpecies.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                    if((int) event.getX()-ax >width/2)
                    {
                        udrzet=0;
                        Intent intent = new Intent(this, MainActivity.class);
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProbabilisticKey.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
