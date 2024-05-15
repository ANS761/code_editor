package com.example.cef.viewmodel;

import com.example.cef.*;
import com.example.cef.model.Document;
import com.example.cef.model.Language;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TabPane;

public class CodeEditorViewModel {
    private final Document document;
    private final HighLightingManager highlightingManager;
    private final AutoCompleteManager autoCompleteManager;
    private final SearchReplaceManager searchReplaceManager;

    private final StringProperty documentText;

    public CodeEditorViewModel() {
        document = new Document();
        highlightingManager = new HighLightingManager(document);
        autoCompleteManager = new AutoCompleteManager(document);
        searchReplaceManager = new SearchReplaceManager(document);

        documentText = new SimpleStringProperty(document.getText());
        documentText.addListener((obs, oldText, newText) -> document.setText(newText));

        // Set the initial language for the document
//        setDocumentLanguage(Language.JAVASCRIPT); // or any other language you prefer
    }

    public StringProperty documentTextProperty() {
        return documentText;
    }

    public void setDocumentLanguage(Language language) {
        document.setLanguage(language);
        highlightingManager.setLanguage(language);
    }

    public HighLightingManager getHighlightingManager() {
        return highlightingManager;
    }

    // Getters for other managers
//    public AutoCompleteManager getAutoCompleteManager() { }
//    public SearchReplaceManager getSearchReplaceManager() {}

}