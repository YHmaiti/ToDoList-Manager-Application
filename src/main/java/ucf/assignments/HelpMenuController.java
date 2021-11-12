/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Yohan Hmaiti
 */

package ucf.assignments;

// grab all the imports needed
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

// build a class to control the help menu scene that loads whenever the user needs help
// navigating the options available through the menu
public class HelpMenuController {

    // Build a button that will be used to close the Help Menu Window
    @FXML
    private Button closeBtn;

    // create a public void method that will represent the functionality of the close button
    // of the help menu screen
    public void closeBtnHelpMenu(ActionEvent e) {

        // generate an event to close the help menu window whenever the close button gets clicked
        // first we get the current stage by using the close button instance with .getScene().getWindow()
        // then we use the current stage .close() to close the window/scene
        Stage curStage = (Stage) closeBtn.getScene().getWindow();
        curStage.close();

    }

}
