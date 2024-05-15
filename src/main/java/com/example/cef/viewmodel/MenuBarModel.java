package com.example.cef.viewmodel;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarModel extends MenuBar {

    public MenuBarModel(){
        Menu file = new Menu("File");
        file.getItems().add(new MenuItem("New"));
        this.getMenus().add(file);
    }

}
