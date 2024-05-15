package com.example.cef.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.example.cef.viewmodel.CodeEditorViewModel;

public class SidebarView extends VBox {
    public SidebarView(CodeEditorViewModel viewModel) {
        // Add components for search/replace, file structure, etc.
        getChildren().add(new Label("Sidebar"));
    }
}