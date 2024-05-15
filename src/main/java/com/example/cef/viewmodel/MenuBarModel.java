package com.example.cef.viewmodel;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarModel extends MenuBar {

    public MenuBarModel(){
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(this::handleMenuItemAction);
        file.getItems().add(newFile);
        this.getMenus().add(file);
    }

    private void handleMenuItemAction(ActionEvent event) {
        // Код, который будет выполняться при выборе пункта меню
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String menuItemText = selectedMenuItem.getText();

        // Выполните необходимые действия в зависимости от выбранного пункта меню
        switch (menuItemText) {
            case "New":

                break;
            case "Save":
                // Код для сохранения файла
                break;
            // Добавьте другие случаи для других пунктов меню
            default:
                break;
        }
    }

}
