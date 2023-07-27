package com.wonjun.training14_googleapi_study.model;

import java.util.ArrayList;

public class PlaceRes {
    private ArrayList<Place> results;
    private String status;
    private String next_page_token;

    public ArrayList<Place> getResults() {
        return results;
    }

    public void setResults(ArrayList<Place> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }
}
