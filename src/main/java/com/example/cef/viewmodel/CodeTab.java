package com.example.cef.viewmodel;

import javafx.scene.control.Tab;
import javafx.scene.text.TextFlow;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class CodeTab extends Tab {

    private CodeArea codeArea;
    private TextFlow textFlow;
    public CodeTab() {
        codeArea = new CodeArea();
//        codeArea.setStyle("-fx-font-family: monospace;");
//        textFlow = new TextFlow();
//        codeArea.setGraphic(textFlow);

        // Bind the code area text to the document text
//        codeArea.textProperty().bindBidirectional(viewModel.documentTextProperty());

        // Apply syntax highlighting on text changes
//        codeArea.textProperty().addListener((observable, oldValue, newValue) -> {
//            viewModel.getHighlightingManager().highlightSyntax(textFlow, newValue);
//        });

        this.setContent(codeArea);
    }
}
