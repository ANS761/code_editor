package com.example.cef;

import com.example.cef.view.CodeEditorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CodeEditorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        CodeEditorView codeEditorView = new CodeEditorView();
        Scene scene = new Scene(codeEditorView.getRoot(),800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Code Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}