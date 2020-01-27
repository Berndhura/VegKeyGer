package com.test.tichy.vegkey;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.*;

public class descriptionActivity extends AppCompatActivity {
    Scanner input;
    int x;
    String line;
    String[] syntaxa = new String[600];
    List<String> list = new ArrayList<String>();
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_syntaxa_description);
        final ListView lv = (ListView) findViewById(R.id.listView);
        final Button btnVeg = findViewById(R.id.button4);
        final ImageButton btnFind = findViewById(R.id.ImageButton1);
        final Globals g = (Globals) getApplication();
        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema2);

        super.onCreate(savedInstanceState);
        g.setProbKey(0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> arg0,
                                                              View arg1, int arg2, long arg3) {
                                          // TODO Auto-generated method stub
                                         // Toast.makeText(descriptionActivity.this, "Wait...", Toast.LENGTH_LONG).show();
                                          String vyber=String.valueOf(lv.getItemAtPosition(arg2));
                                          g.setType(vyber);
                                          startActivity(new Intent(descriptionActivity.this, VegetationInfo.class));
                                          overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                                      }
                                  }
        );

        btnVeg.setOnClickListener(new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(descriptionActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
    });
        btnFind.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                LayoutInflater inflater = LayoutInflater.from(descriptionActivity.this);
                View subView = inflater.inflate(R.layout.edit_dialog, null);
                final EditText edt = (EditText)subView.findViewById(R.id.username);
                try {
                    if (g.getpamaedit().length()>0) edt.setText(g.getpamaedit().toString().toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                edt.selectAll();
                AlertDialog.Builder builder = new AlertDialog.Builder(descriptionActivity.this);
                builder.setMessage("Zadej hledaný řetězec:")
                        .setView(subView)
                        .setCancelable(false)

                        .setPositiveButton("  OK  ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int u=lv.getFirstVisiblePosition()+2;
                                String ledt=edt.getText().toString().toUpperCase();
                                if (u<=list.size()) {
                                    for (int ii = u; ii < list.size(); ii++) {
                                        if (list.get(ii - 1).toUpperCase().contains(ledt)) {
                                            lv.setSelection(ii - 1);
                                            g.setpamaedit(ledt);
                                            break;
                                        }
                                        for (int iii = 1; iii < u-1; iii++)
                                            if (list.get(iii - 1).toUpperCase().contains(ledt)) {
                                                lv.setSelection(iii - 1);
                                                g.setpamaedit(ledt);
                                                break;
                                            }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("  Zrušit  ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // some code if you want
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();

            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx




        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

        if(g.getSyntaxa()==1) {
            if (g.getLangSynt() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2));
            }
        } else {
            if (g.getLangSynt() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz_alliances));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2_alliances));
            }
        }

        while (input.hasNextLine()) {
            line = input.nextLine();
            line=line.replace(";;;"," ");
            syntaxa[x] = line;
            list.add(line);
        }
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        Collections.sort(list);
        adapter = new ArrayAdapter<String>(this, R.layout.syntaxa_item, R.id.row, list);
        lv.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(descriptionActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}
