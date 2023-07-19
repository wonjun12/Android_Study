package com.wonjun.training12_papagoapi.model;

import java.io.Serializable;

public class Papago implements Serializable {
    private String text;
    private String translatedText;
    private String target;

    public Papago(String text, String translatedText, String target) {
        this.text = text;
        this.translatedText = translatedText;
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
