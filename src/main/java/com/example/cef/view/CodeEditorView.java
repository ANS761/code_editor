package com.example.cef.view;

import com.example.cef.viewmodel.CodeEditorViewModel;
import com.example.cef.viewmodel.MenuBarModel;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;


public class CodeEditorView extends BorderPane {
    private MenuBarModel menuBar;
    private EditorPane editorPane;
//    private SidebarView sidebarView;
    private CodeEditorViewModel viewModel;

    public CodeEditorView() {
        menuBar = new MenuBarModel();
        viewModel = new CodeEditorViewModel();
        editorPane = new EditorPane(viewModel);
//        sidebarView = new SidebarView(viewModel);

        setTop(menuBar);
        setCenter(editorPane);
//        setRight(sidebarView);

    }


    public Parent getRoot(){
        return this;
    }
}
