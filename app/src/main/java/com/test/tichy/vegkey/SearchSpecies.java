
package com.test.tichy.vegkey;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchSpecies extends AppCompatActivity {

    List<String> list = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    List<String> list5 = new ArrayList<>();
    List<String> list6 = new ArrayList<>();

    String line;
    TextView bigNameTextField, smallTextField;
    Scanner input;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    int totalNumberOfSpecies;
    List<String> sp = new ArrayList<>();
    private EditText searchField;

    private int searchAlreadyRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_species);
        bigNameTextField = findViewById(R.id.bigNameTextField);
        smallTextField = findViewById(R.id.smallTextField);
        searchField = findViewById(R.id.editText1);

        final ListView lv = findViewById(R.id.listView);
        final Globals g = (Globals) getApplication();
        final LinearLayout llSearchView = findViewById(R.id.linear_layout_search_view);

        if (g.getBackground() == 0) llSearchView.setBackgroundResource(R.drawable.tema);
        if (g.getBackground() == 1) llSearchView.setBackgroundResource(R.drawable.tema3);
        if (g.getBackground() == 2) llSearchView.setBackgroundResource(R.drawable.tema2);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // now what?
                Log.i("TAG", "Build.VERSION.SDK_INT >= 23 -> True");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> arg0,
                                                              View arg1, int arg2, long arg3) {
                                          String vyber = String.valueOf(lv.getItemAtPosition(arg2));
                                          bigNameTextField.setText(vyber);
                                          if (g.getLangSpec() == 1) {
                                              input = new Scanner(getResources().openRawResource(R.raw.speclist3));
                                          } else {
                                              input = new Scanner(getResources().openRawResource(R.raw.speclist3cz));
                                          }
                                          while (input.hasNextLine()) {
                                              line = input.nextLine();
                                              String[] separated = line.split("\t");
                                              if (Integer.parseInt(separated[2]) == Integer.parseInt(list6.get(arg2))) {
                                                  smallTextField.setText(separated[1]);
                                                  break;
                                              }
                                          }
                                      }
                                  }
        );
        if (g.getLangSpec() == 1) {
            input = new Scanner(this.getResources().openRawResource(R.raw.speclist3cz));
        } else {
            input = new Scanner(this.getResources().openRawResource(R.raw.speclist3));
        }

        totalNumberOfSpecies = 0;
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] separated = line.split("\t");
            list.add(separated[1]);
            list2.add(separated[0]);
            list4.add(separated[1]);
            list5.add(separated[2]);
            list6.add(separated[2]);
            totalNumberOfSpecies = totalNumberOfSpecies + 1;
        }
        //Toast.makeText(SeachSpecies.this, String.valueOf(x), Toast.LENGTH_LONG).show();
        adapter = new ArrayAdapter<>(this, R.layout.search_species_row, R.id.row, list);
        lv.setAdapter(adapter);

        final Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Back to previous
                Intent intent = new Intent(SearchSpecies.this, ProbabilisticKey.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        final Button clearButton = findViewById(R.id.button3);
        clearButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                searchField.setText("");
            }
        });

        final Button addButton = findViewById(R.id.button1);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!String.valueOf(bigNameTextField.getText()).isEmpty()) {
                    String[] ck;
                    ck = g.getCheck();
                    sp = g.getItemSpec();
                    String zita = String.valueOf(bigNameTextField.getText()).trim();
                    int j = 0;
                    for (int i = 0; i < sp.size(); i++) {
                        if (sp.get(i).equals(zita)) {
                            j = 1;
                            break;
                        }
                    }
                    if (j == 0) {
                        sp.add((String) bigNameTextField.getText());
                        ck[sp.size() - 1] = "0";
                    }
                    g.setItemSpec(sp);
                    g.setCheck(ck);

                    Intent intent = new Intent(SearchSpecies.this, ProbabilisticKey.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchAlreadyRunning == 0) {
                    searchAlreadyRunning = 1;
                    String lala = searchField.getText().toString().toUpperCase();

                    int lens = lala.length();
                    if (lens > 6) {
                        return;
                    }
                    String last;
                    list3.clear();
                    list6.clear();
                    String veka;
                    int pom = 0;
                    for (int i = 0; i < totalNumberOfSpecies; i++) {
                        last = list2.get(i).trim().substring(0, lens);
                        if (last.equals(lala.trim())) {
                            veka = list4.get(i);
                            list3.add(veka);
                            list6.add(list5.get(i));
                            pom = 1;
                        } else {
                            if (pom == 1) {
                                i = totalNumberOfSpecies + 1;
                            }
                        }
                    }
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);
                    adapter2 = new ArrayAdapter<>(SearchSpecies.this, R.layout.search_species_row, R.id.row, list3);
                    lv.setAdapter(adapter2);
                }
                searchAlreadyRunning = 0;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchSpecies.this, ProbabilisticKey.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
    }
}


