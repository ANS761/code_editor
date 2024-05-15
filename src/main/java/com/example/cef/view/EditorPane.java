package com.example.cef.view;

import com.example.cef.viewmodel.CodeTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import com.example.cef.viewmodel.CodeEditorViewModel;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class EditorPane extends TabPane {
    public EditorPane(CodeEditorViewModel viewModel) {
        Tab tab = new CodeTab();
//        codeArea.setStyle("-fx-font-family: monospace;");
//        textFlow = new TextFlow();
//        codeArea.setGraphic(textFlow);

        // Bind the code area text to the document text
//        codeArea.textProperty().bindBidirectional(viewModel.documentTextProperty());

        // Apply syntax highlighting on text changes
//        codeArea.textProperty().addListener((observable, oldValue, newValue) -> {
//            viewModel.getHighlightingManager().highlightSyntax(textFlow, newValue);
//        });
        this.getTabs().add(tab);
    }

//    public CodeArea getCodeArea() {
//        return codeArea;
//    }
}
