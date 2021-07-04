package com.test.tichy.vegkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SyntaxaActivity extends AppCompatActivity {

    Scanner input;//, input2, input3;
    int x;
    String line;
    String[] syntaxa = new String[600];
    String[] alliances = new String[200];
    List <String> list = new ArrayList <String>();
    ArrayAdapter <String> adapter;
    List <String> sp = new ArrayList <String>();
    String[] ck = new String[50];
    int[] spi = new int[50];
    String[] separated, spec_nic;
    double c, d, e;
    int a, b, k;
    double[] finVal = new double[600];
    double suma=0;
    //String vegType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syntaxa);
        final Button btnVeg = findViewById(R.id.button4);
        final ListView lv = (ListView) findViewById(R.id.listView);
        final Globals g = (Globals) getApplication();
        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema2);
        ck = g.getCheck();
        sp = g.getItemSpec();
        //vegType=g.getType();
        //Select one item from the list and show the info
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> arg0,
                                                              View arg1, int arg2, long arg3) {
                                          String vyber=String.valueOf(list.get(arg2));
                                          //Toast.makeText(SyntaxaActivity.this, vyber, Toast.LENGTH_LONG).show();
                                          vyber=vyber.substring(10,vyber.length());
                                          g.setType(vyber);
                                          startActivity(new Intent(SyntaxaActivity.this, VegetationInfo.class));
                                          overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                      }
                                  }
        );




        for (int i = 0; i < finVal.length; i++) {
            finVal[i] = 1;
        }

        //import of all associations
        if(g.getLangSynt()==1) {
            input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz));
        }else {
            input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2));
        }
        x = 0;
        while (input.hasNextLine()) {
            line = input.nextLine();
            line=line.replace(";;;"," ");
            syntaxa[x] = line;
            list.add(line);
            x = x + 1;
        }
        // import of all alliances
        if(g.getLangSynt()==1) {
            input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz_alliances));
        }else {
            input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2_alliances));
        }
        x = 0;
        while (input.hasNextLine()) {
            line = input.nextLine();
            line=line.replace(";;;"," ");
            alliances[x] = line;
            x = x + 1;
        }
        //import of the species list
        int l = 1;
        InputStream inputStream;
        if(g.getLangSpec()==1) {
            inputStream = this.getResources().openRawResource(R.raw.speclist3cz);
        }else {
            inputStream = this.getResources().openRawResource(R.raw.speclist3);
        }
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputreader);
        try {
            line = reader.readLine();
            while (line != null) {
                separated = line.split("\t");
                for (k = 0; k < sp.size(); k++) {
                    if (String.valueOf(separated[1]).equals(sp.get(k).toString())) {
                        spi[k] = Integer.parseInt(separated[2]);
                        break;
                    }
                }
                l = l + 1;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        Toast.makeText(SyntaxaActivity.this, String.valueOf(l), Toast.LENGTH_LONG).show();

        //import of the synoptic table in its binary form (It speeds up the algorithm)
        k = 0;
        try {
            InputStream in =  this.getResources().openRawResource(R.raw.ttbl_25);
            final byte[] buffer = new byte[4940]; // 512Mb
            while (true) {
                final int read = in.read(buffer);
                if (read < 0) {
                    break;
                }
                k++;
                l = convInt(buffer[0], buffer[1]);
                for (int kk = 0; kk < sp.size(); kk++) {
       //             Toast.makeText(SyntaxaActivity.this, sp.get(k).toString(), Toast.LENGTH_LONG).show();
                    if (spi[kk] == l) {
 //                       Toast.makeText(SyntaxaActivity.this, String.valueOf(l), Toast.LENGTH_LONG).show();

                        for (int ll = 0; ll < 494; ll++) {
                            int lll = ll * 10;
                           a = convInt(buffer[lll], buffer[lll + 1]);
                            b = convInt(buffer[lll+2], buffer[lll + 3]);
                            c = convInt(buffer[lll+4], buffer[lll + 5]);
                            d = convInt(buffer[lll+6], buffer[lll + 7]);
                            e = convInt(buffer[lll+8], buffer[lll + 9]);
                            if(ck[kk]=="1")
                            {
                                if(e>0) {
                                    finVal[b] = finVal[b] * e/ c;
                                }else{
                                    finVal[b] = finVal[b] * 0.0001;
                                }
                            }
                            else
                            {
                                if(d>0) {
                                    finVal[b] = finVal[b] * d / c;
                                }else{
                                    finVal[b] = finVal[b] * 0.0001;
                                }

                            }
                            //Toast.makeText(SyntaxaActivity.this, String.valueOf(b), Toast.LENGTH_LONG).show();
                        }
                   break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Test for forest and non-forest vegetation types
        suma=0;
        String tuk2;
        for (int ll = 0; ll < list.size(); ll++) {
            try {
                String tuk=list.get(ll).toString();
                tuk2=tuk;
                if(tuk.length()>7) tuk2=tuk.substring(0,3);
                if(tuk.length()>5) tuk=tuk.substring(0,1);
                /*if (vegType.contains("FOR")) {
                    if (tuk.contains("L") || tuk.contains("K")||tuk2.contains("ADC")) {
                        suma = suma+finVal[ll+1];
                    }
                }
                else
                {
                    if (!(tuk.contains("L") || tuk.contains("K")||tuk2.contains("ADC"))) {
                        suma = suma+ finVal[ll+1];
                    }
                } */
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            }

        for (int ll = 0; ll < list.size(); ll++) {
                finVal[ll+1]=100*finVal[ll+1]/suma;
        }

    //sum of values in case of alliances
    if (g.getSyntaxa() == 2) {
        String phc;
        for (int i = 0; i < list.size(); i++) {
            phc = list.get(i).substring(0, 3);
            for (int j = 0; j < alliances.length; j++) {
                try {
                    if (alliances[j].substring(0, 3).equals(phc)) {
                        phc = alliances[j];
                        list.set(i, phc);
                        break;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        for (int ll = 0; ll < list.size() - 1; ll++) {
            for (int mm = ll + 1; mm < list.size(); mm++) {
                if (list.get(ll) == list.get(mm)) {
                    finVal[ll+1] = finVal[ll+1] + finVal[mm+1];
                    finVal[mm+1] = 0;
                }
            }
        }
    }
    // format of displayed values
    for (int ll = 1; ll < list.size()+1; ll++) {
        String ggg="    " + String.format("%.2f", finVal[ll]);
        ggg=ggg.substring(ggg.length()-6,ggg.length());
        list.set(ll-1, ggg + "% - " + list.get(ll-1));
    }

    for (int ll = list.size(); ll > -1; ll--) {
        try {
            String tuk=list.get(ll).toString();
            tuk2=tuk;
            if(tuk.length()>13) tuk2=tuk.substring(10,13);
            if(tuk.length()>11) tuk=tuk.substring(10,11);
            /*if (vegType.contains("NFR")) {
                if (tuk.contains("L") || tuk.contains("K")||tuk2.contains("ADC")) {
                    list.remove(ll);
                }
            }
            else
            {
                if (!(tuk.contains("L") || tuk.contains("K")||tuk2.contains("ADC"))) {
                    list.remove(ll);
                }
            }*/
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
        for (int ll = list.size(); ll > -1; ll--) {
            try {
                String tuk=list.get(ll).toString();
                if(tuk.length()>11) {tuk=tuk.substring(0,6);} else {tuk="0";}
                tuk=tuk.replace(",",".");
                if (Double.parseDouble(tuk)<1||tuk.contains("NaN")) list.remove(ll);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        //Sorting of vegetation types by its probability (the number is the first)
        Collections.sort(list);
        Collections.reverse(list);
//        adapter = new ArrayAdapter <String>(this, R.layout.syntaxa_item, R.id.row, list);
        //ArrayAdapter adapter = new  ArrayAdapter<String>(this, R.layout.syntaxa_item, R.id.row, list);
        //lv.setAdapter(adapter);
        ArrayList<HashMap<String,String>> detail = new ArrayList<>();
        HashMap<String, String> n = new HashMap<>();
        String shc;
        for(int i=0;i<list.size();i++) {
            n = new HashMap <String, String>();
            shc = list.get(i).substring(10, 13);
            for (int j = 0; j < alliances.length; j++) {
                try {
                    if (alliances[j].substring(0, 3).equals(shc)) {
                        shc = alliances[j];
                        break;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            String aia, bib;
            aia = list.get(i).toString().substring(0, 10);
            bib = list.get(i).toString().substring(11, list.get(i).toString().length());

            if (g.getSyntaxa() == 1) {
                    n.put("line1", list.get(i).toString());
                    n.put("line2", shc);
            } else {
                    n.put("line1", aia + shc);
                    n.put("line2", bib);
            }

            detail.add(i, n);
        }
        if(list.size()<1){
            n.put("line1", "Unexpected species combination !!!");
            n.put("line2", "");
            detail.add(0, n);
        }
// Display of the list
        SimpleAdapter adapter = new SimpleAdapter(this, detail,
                R.layout.syntaxa_item,
                new String[] { "line1","line2" },
                new int[] {R.id.row, R.id.row2});
        lv.setAdapter(adapter);

// Button bellow the display
        btnVeg.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SyntaxaActivity.this, ProbabilisticKey.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

    }

    public int convInt(byte ba, byte bb) {
        int finInt2 = (bb & 0xFF);
        finInt2 = finInt2 * 256;
        finInt2 = finInt2 + (ba & 0xFF);
        return finInt2;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SyntaxaActivity.this, ProbabilisticKey.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}