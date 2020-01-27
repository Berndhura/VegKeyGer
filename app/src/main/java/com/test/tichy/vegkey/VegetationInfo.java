package com.test.tichy.vegkey;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TimingLogger;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Scanner;

public class VegetationInfo extends AppCompatActivity {

    private String[] separated;
    private String line, line2;
    private Scanner input;
    private int jana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetation_info);
        final Button btnBack = findViewById(R.id.button4);
        final Button btnPLAD = findViewById(R.id.button5);
        final TextView tx1 = findViewById(R.id.textView1);
        final TextView txBio = findViewById(R.id.textViewBiotope);
        final TextView txMin = findViewById(R.id.textViewMin);
        final TextView tx2 = findViewById(R.id.textView3);
        final TextView tx3 = findViewById(R.id.textView5);
        final TextView tx4 = findViewById(R.id.textView7);
        final TextView tx1b = findViewById(R.id.textView9);//characteristics
        final TextView px1b = findViewById(R.id.textView8);
        final TextView px2 = findViewById(R.id.textView2);
        final TextView px3 = findViewById(R.id.textView4);
        final TextView px4 = findViewById(R.id.textView6);
        final LinearLayout vi = (LinearLayout) findViewById(R.id.db1_root);
        final Globals g = (Globals) getApplication();
        if(g.getBackground()==0) vi.setBackgroundResource(R.drawable.tema);
        if(g.getBackground()==1) vi.setBackgroundResource(R.drawable.tema3);
        if(g.getBackground()==2) vi.setBackgroundResource(R.drawable.tema2);
        tx2.setText("");
        tx3.setText("");
        tx4.setText("");
        
        btnPLAD.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(VegetationInfo.this,descriptionActivity.class);
                //startActivity(intent);
                String vege=g.getType();
                if (g.getSyntaxa()==1)vege=vege.substring(6,vege.length());
                if (g.getSyntaxa()==2)vege=vege.substring(4,vege.length());
                vege=vege.replace(" ","%20");
                String vege2="http://www.pladias.cz/vegetation/description/"+vege;

        //Toast.makeText(VegetationInfo.this, vege, Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(vege2));
                startActivity(i);
            }
        });
        btnBack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(VegetationInfo.this,descriptionActivity.class);
                finish();
                if(g.getProbKey()==0) {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else{
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
        String vyber=g.getType();
        String vege;


        if(g.getSyntaxa()==1) {
            jana=5;
            vege=vyber.substring(0,5);
            if (g.getLangSynt() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2));
            }
        }else{
            vege=vyber.substring(0,3);
            jana=3;
            if (g.getLangSynt() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz_alliances));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2_alliances));
            }
        }
        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vege)) {
                separated = line.split(";;;");
                tx1.setText(separated[0] + " " + separated[1]);
                break;
            }
        }


        if(g.getSyntaxa()==1) {
            vege=vyber.substring(0,5);// změnit na 0,5
            if (g.getLanguage() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.asso_biot_cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.asso_biot_en));
            }
        }else{
            vege=vyber.substring(0,3);
            if (g.getLanguage() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.alli_biot_cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.alli_biot_en));
            }
        }
        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vege)) {
                separated = line.split(";;;");
                txBio.setText(Html.fromHtml(separated[1]));
                break;
            }
        }

            if (g.getSyntaxa() == 1) {
                vege = vyber.substring(0, 5);
                if (g.getLangSynt() == 2) {
                    input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz));
                } else {
                    input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2));
                }
            } else {
                vege = vyber.substring(0, 3);
                if (g.getLangSynt() == 2) {
                    input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2cz_alliances));
                } else {
                    input = new Scanner(this.getResources().openRawResource(R.raw.syntaxa2_alliances));
                }
            }
            while (input.hasNextLine()) {
                line = input.nextLine();
                line2 = line.substring(0, jana);
                if (line2.equals(vege)) {
                    separated = line.split(";;;");
                    if(!(g.getLanguage()==2 && g.getLangSynt() == 2)) {
                        txMin.setText(Html.fromHtml(separated[1]));
                    }else{
                        txMin.setText("");
                    }
                    break;
                }
            }

        String vyber2;
        if(g.getSyntaxa()==2){
            vyber2=vyber.substring(0,3);
        }else{
            vyber2=vyber.substring(0,5);
        }

        if(g.getSyntaxa()==1) {
            if (g.getLangSpecInfo() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.dgdr2_cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.dgdr2));
            }
        }else{
            if (g.getLangSpecInfo() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.all_dgdr2_cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.all_dgdr2));
            }
        }

        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vyber2)) {
                separated = line.split(";;;");
                tx2.setText(Html.fromHtml(separated[1]));
                break;
                }
            }

        if(g.getSyntaxa()==1) {
            if(g.getLangSpecInfo()==1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.codr2_cz));
            }else{
                input = new Scanner(this.getResources().openRawResource(R.raw.codr2));
            }
        }else{
            if(g.getLangSpecInfo()==1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.all_codr2_cz));
            }else{
                input = new Scanner(this.getResources().openRawResource(R.raw.all_codr2));
            }
        }

            String dr="";
//        Toast.makeText(VegetationInfo.this, vyber2, Toast.LENGTH_LONG).show();
        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vyber2)) {
                separated = line.split(";;;");
                tx3.setText(Html.fromHtml(separated[1]));
                break;
            }
        }

        if(g.getSyntaxa()==1) {
            if (g.getLangSpecInfo() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.dmdr2_cz));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.dmdr2));
            }
        }
        dr="";
//        Toast.makeText(VegetationInfo.this, vyber2, Toast.LENGTH_LONG).show();
        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vyber2)) {
                separated = line.split(";;;");
                tx4.setText(Html.fromHtml(separated[1]));
                break;
            }
        }
        if(g.getSyntaxa()==1) {
            if (g.getLanguage() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.assoc_descrcz2));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.assoc_descr2));
            }
        }else{
            if (g.getLanguage() == 1) {
                input = new Scanner(this.getResources().openRawResource(R.raw.alliances_descrcz2));
            } else {
                input = new Scanner(this.getResources().openRawResource(R.raw.alliances_descr2));
            }
        }

        while(input.hasNextLine())  {
            line=input.nextLine();
            line2=line.substring(0,jana);
            if (line2.equals(vyber2)) {
                separated = line.split(";;;");
                tx1b.setText(Html.fromHtml(separated[1]));
                //dr=String.valueOf(separated[0]);
                break;
            }
        }
        //Toast.makeText(VegetationInfo.this, String.valueOf(dr), Toast.LENGTH_LONG).show();
        if(tx1b.getText()==null || tx1b.getText()==""||tx1b.getText().length()<5){
            px1b.setText("");
            px1b.setVisibility(View.INVISIBLE);
        }else{
            px1b.setVisibility(View.VISIBLE);
            px1b.setText(R.string.Veginfo_Char);
        }
        if(tx2.getText()==null || tx2.getText()==""||tx2.getText().length()<5){
            px2.setText("");
            px2.setVisibility(View.INVISIBLE);
        }else{
            px2.setVisibility(View.VISIBLE);
            px2.setText(R.string.Veginfo_Dg);
        }
        if(tx3.getText()==null || tx3.getText()==""||tx3.getText().length()<5){
            px3.setText("");
            px3.setVisibility(View.INVISIBLE);
        }else{
            px3.setText(R.string.Veginfo_Co);
            px3.setVisibility(View.VISIBLE);
        }
        if(tx4.getText()==null || tx4.getText()==""||tx4.getText().length()<5){
            px4.setText("");
            px4.setVisibility(View.INVISIBLE);
        }else{
            px4.setVisibility(View.VISIBLE);
            px4.setText(R.string.Veginfo_Do);
        }
    }
    @Override
    public void onBackPressed() {
        final Globals g = (Globals) getApplication();
        finish();
        if(g.getProbKey()==0) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else{
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
