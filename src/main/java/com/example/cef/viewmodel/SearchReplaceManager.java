package com.example.cef.viewmodel;

import com.example.cef.model.Document;
public class SearchReplaceManager {
    private Document document;

    public SearchReplaceManager(Document document) {
        this.document = document;
    }

    // Methods for searching and replacing text in the document
    public void findText(String query) {}
    public void replaceText(String query, String replacement) {}
}
