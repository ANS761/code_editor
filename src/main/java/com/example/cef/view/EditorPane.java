package com.example.cef.view;

import com.example.cef.model.Language;
import com.example.cef.viewmodel.CodeTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import com.example.cef.viewmodel.CodeEditorViewModel;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.util.Objects;

public class EditorPane extends TabPane {
    private CodeArea codeArea;
    public EditorPane(CodeEditorViewModel viewModel) {
        codeArea = new CodeArea("""
                <html>
                    <head>
                        <title>Example</title>
                    </head>
                    <body>
                        <h1>Hello, World!</h1>
                    </body>
                </html>
                """);
//        codeArea = this.getCurrentCodeArea();
        codeArea.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/cef/styles.css")).toExternalForm());
        newTab(codeArea.getText());
    }

    public void newTab(){
        Tab tab = new CodeTab("Untitled", "", Language.HTML);
        this.getTabs().add(tab);
        this.getSelectionModel().select(tab);
    }

    public void newTab(String text){
        Tab tab = new CodeTab("Untitled", text, Language.HTML);
        this.getTabs().add(tab);
        this.getSelectionModel().select(tab);
    }

    public CodeArea getCurrentCodeArea() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        if (selectedTab instanceof CodeTab) {
            return ((CodeTab) selectedTab).getCodeArea();
        }
        return null;
    }
}
