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
        newTab();
    }

    public void newTab(){
        Tab tab = new CodeTab("Untitled", "");
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
