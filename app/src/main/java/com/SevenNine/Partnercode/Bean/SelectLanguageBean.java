package com.SevenNine.Partnercode.Bean;

public class SelectLanguageBean {

    String vendor,imageicon,lang_letter;

    int languageid;


    public String getVendor() {
        return vendor;
    }

    public String getImageicon() {
        return imageicon;
    }

    public int getLanguageid() {
        return languageid;
    }

    public String getLang_letter() {
        return lang_letter;
    }

    public SelectLanguageBean(String vendor, int languageid, String lang_letter) {
        this.languageid=languageid;
        this.vendor = vendor;
        this.lang_letter=lang_letter;
    }
}


