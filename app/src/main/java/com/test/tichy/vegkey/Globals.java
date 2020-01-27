package com.test.tichy.vegkey;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class Globals extends Application {
    private List<String> itemSpec = new ArrayList<String>();
    private List<String> itemCheck = new ArrayList<String>();
    private String[] specList = new String[50];
    private String[] specCheck= new String[50];
    private String type;
    private int PKind;
    private int bcgr;
    private int syntLeve;
    private int lang,langLoop, langSpec, langSpecInf, langSynt;
    private String pamaedit;
    public void setList(String[] nms) {
        specList = nms;
    }
    public String[] getList() {
        return specList;
    }
//--------------------------------------------------
    public void setCheck(String[] chck) {
        specCheck = chck;
    }
    public String[] getCheck() {
        return specCheck;
    }

    //--------------------------------------------------
    public void setItemSpec(List nms) {
        itemSpec = nms;
    }
    public List getItemSpec() {
        return itemSpec;
    }
    //--------------------------------------------------
    public void setProbKey(int PK) {
        PKind = PK;
    }
    public int getProbKey() {
        return PKind;
    }
    //--------------------------------------------------
    public void setType(String tp) {type = tp;    }
    public String getType() {
        return type;
    }

    //--------------------------------------------------
    public void setBackground(int bc) {bcgr = bc;    }
    public int getBackground() {
        return bcgr;
    }

    //--------------------------------------------------
    public void setLanguage(int lg) {lang = lg;    }
    public int getLanguage() {
        return lang;
    }

    //--------------------------------------------------
    public void setLangLoop(int lglp) {langLoop = lglp;    }
    public int getLangLoop() {
        return langLoop;
    }

    //--------------------------------------------------
    public void setpamaedit(String pm) {pamaedit = pm;    }
    public String getpamaedit() {
        return pamaedit;
    }

    //--------------------------------------------------
    public void setLangSpec(int lgsp) {langSpec = lgsp;    }
    public int getLangSpec() {
        return langSpec;
    }

    //--------------------------------------------------
    public void setLangSpecInfo(int lgspin) {langSpecInf = lgspin;    }
    public int getLangSpecInfo() {
        return langSpecInf;
    }

    //--------------------------------------------------
    public void setLangSynt(int lgsy) {langSynt = lgsy;    }
    public int getLangSynt() {
        return langSynt;
    }

    //--------------------------------------------------
    public void setSyntaxa(int synta) {syntLeve = synta;    }
    public int getSyntaxa() {
        return syntLeve;
    }

    //--------------------------------------------------


}
