package com.example.cef.view;

import com.example.cef.viewmodel.CodeEditorViewModel;
import com.example.cef.viewmodel.CodeTab;
import com.example.cef.viewmodel.MenuBarModel;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class CodeEditorView extends BorderPane {
    private MenuBar menuBar;
    private EditorPane editorPane;
//    private SidebarView sidebarView;
    private CodeEditorViewModel viewModel;

    public CodeEditorView() {
        menuBar = new MenuBar();
        initMenuBar();
        viewModel = new CodeEditorViewModel();
        editorPane = new EditorPane(viewModel);
//        sidebarView = new SidebarView(viewModel);

        setTop(menuBar);
        setCenter(editorPane);
//        setRight(sidebarView);

    }

    private void handleMenuItemAction(ActionEvent event) {
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String menuItemText = selectedMenuItem.getText();

        switch (menuItemText) {
            case "New":
                editorPane.newTab();
                break;
            case "Open":
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    String fileName = selectedFile.getName();
                    String fileContent;
                    try {
                        fileContent = Files.readString(selectedFile.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    Tab tab = new CodeTab(fileName, fileContent);
                    editorPane.getTabs().add(tab);
                    editorPane.getSelectionModel().select(tab);
                }
                break;
            case "Save":

//                if (selectedTab != null) {
                    String fileContent = editorPane.getCurrentCodeArea().getText();

                    fileChooser = new FileChooser();
                    selectedFile = fileChooser.showSaveDialog(null);

                    if (selectedFile != null) {
                        try {
                            Files.write(selectedFile.toPath(), fileContent.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    }
                }
                break;
            default:
                break;
        }
    }

    private void initMenuBar(){
        Menu file = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(this::handleMenuItemAction);
        file.getItems().add(newFile);
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(this::handleMenuItemAction);
        file.getItems().add(openFile);
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(this::handleMenuItemAction);
        file.getItems().add(saveFile);

        menuBar.getMenus().add(file);
    }

    public Parent getRoot(){
        return this;
    }
}
