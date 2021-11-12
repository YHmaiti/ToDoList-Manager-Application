/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Yohan Hmaiti
 */

package ucf.assignments;

// import all the tools needed
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import org.controlsfx.control.WorldMapView;

// create a class related to the manipulation and control of the Hello ToDoList manager GUI App
// the class will implement Initializable
public class HelloController implements Initializable{

    // create a dropdown comboBox that will be used to display the available display options
    // the comboBox is going to be of type String and used after the @FXML tag
    @FXML
    private ComboBox<String> displayOptionsBox;
    // create a TextField and DatePicker based Variables
    @FXML private TextField desTextField;
    @FXML private DatePicker DatePicker;

    // first we need to create a model as it will be of type Object of the class ToDoListCode
    ToDoListCode list1 = new ToDoListCode();
    // We need to create a list that will match the type expected to be used in order to display on the GUI
    // in a table, so we will create an ObservableList of type Item, the ObservableList will be taken from the
    // FXCollections and we will do that using: ObservableList<Item> varname = FXCollections.observableArrayList();
    ObservableList<Item> list2 = FXCollections.observableArrayList();

    // create a VBox to represent the Buttons used for the load work and save work buttons
    // the VBox will be private and supported with a tag: @FXML
    @FXML
    private VBox fBox;
    // create a label to be used for the file load
    // this will be achieved through the use of a private Label after the tag @FXML
    @FXML
    private Label fLabel;
    // create a file chooser that will be used along with the VBox for Save work and Load work
    // this will be declared using FileChooser varname  = new FileChooser(); after the tag @FXML
    FileChooser fileChooser = new FileChooser();

    // Build a table and set its view and columns to be assigned to variables that will be of type Item and for the column
    // that will be one TableColumn variables of type Item and String for the descriptions
    // one TableColumn variable of Type Item and LocalDate for the Due Dates column
    // one Table Column of type Item and CheckBox for the status column
    // each of these will be written after its assigned @FXML tag
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, LocalDate> dueDateColumn;
    @FXML
    private TableColumn<Item, CheckBox> statusColumn;
    @FXML
    private TableColumn<Item, String> desColumn;

    // use the override tag for the used initialize method
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Generate a file label through the use of .text("") approach
        fLabel.setText("");
        // use the setCellValueFactory command for each of the table's columns for
        // the description, for the due date and for the status
        desColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Item, LocalDate>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Item, CheckBox>("Status"));

        // create the options for the dropdown comboBox that has the display options, either All items
        // or by status, to display either the complete or incomplete items
        displayOptionsBox.getItems().addAll("All items!", "Incomplete!", "Complete!");
        // assign a folder in the desktop that will be named using ToDo_yohan in order to mark it
        // as proper to the GUI app and proper to the author
        // use the System.getProperty in the process and create a FILE type variable to hold the directory
        // then create it using the .mkdirs() command and create a fileChooser using the directory
        String User = System.getProperty("user.home");
        String dir = User + File.separator + "Desktop";
        String Folder = dir + File.separator + "ToDo_Yohan";
        File newDir = new File(Folder);
        newDir.mkdirs();

        // set a default directory at the beginning...
        fileChooser.setInitialDirectory(newDir);
        
        // disable the user fromentering a string as date
        DatePicker.getEditor().setDisable(true);
        DatePicker.getEditor().setOpacity(1);
        
        // convert the format of the Due date to yyyy-mm-dd as requested by the assignment
        // using a setConverter and a DateTimeFormatter along with two methods to change the date from a String and
        // to a String, the methods will use the .format() and the .parse() approaches
        DatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dF = DateTimeFormatter.ofPattern(pattern);
            {
                DatePicker.setPromptText(pattern.toLowerCase());
            }
            @Override public String toString(LocalDate date) {
                if (date != null)
                    return dF.format(date);
                else
                    return "";
            }
            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty())
                    return LocalDate.parse(string, dF);
                else
                    return null;
            }
        });

        // Generate an Observable List from an arraylist using the FXCollections.observableArrayList(pass all items of the array list);
        // Important: call the sort by date method created!!
        // set the items on the table where they will be displayed sorted by date using the tablevariabletobecreated.setItems(observableListVarName);
        // Create the option to have a selection mode available
        // which allows the user to choose what item they want to delete edit or manipulate
        // based on the options available using .getSelectionModel() and the related methods
        list2 = FXCollections.observableArrayList(list1.getItemsAsAWhole());
        sortByDate();// sorted by date as desired by the assignment prompt
        tableView.setItems(list2);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // allow one selection at a time
    }

    // create a method that will support the display dropdown comboBox and the options it has
    // either to display all or to display the complete items or to display by incomplete items
    // the method has a passed parameter as ActionEvent
    public void displayOpt(ActionEvent e){

        // store in a string the option selected from the comboBox
        String selected = displayOptionsBox.getValue();
        // use conditionals to check which option was selected and call the appropriate
        // display method and print a message to the user console to verify that the display is successful
        // and what options of display is being used
        if(selected.equalsIgnoreCase("All items!")) {
            displayAll();
            System.out.println("Items will be displayed as a whole...");
        } else if (selected.equalsIgnoreCase("Incomplete!")) {
            displayIncomplete();
            //tableView.refresh();
            System.out.println("Incomplete Items will be displayed...");
        } else if (selected.equalsIgnoreCase("Complete!")) {
            displayComplete();
            //tableView.refresh();
            System.out.println("Complete Items will be displayed...");
        }
        System.out.println("The chosen display was successfully made....!!");

    }

    // create a method that will help us achieve the sorting of the current observable lists
    // by date
    public void sortByDate(){

        // we will use the FXCollections.sort() and use a comparator
        // which compares the date objects and then sorts each
        // the method that will be created will be an int method based on the FXCollections documentation
        FXCollections.sort(list2, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {

                return o1.getDueDate().compareTo(o2.getDueDate());
            }
        });

    }

    // create a method that displays all the items of a todolist when the user wishes to the display All items option
    public void displayAll(){

        // get all the items of the list then convert them to observable list
        // sort the items by date through the sortByDate() method then display on the table
        // using the .setItems() command
        list2 = FXCollections.observableArrayList(list1.getItemsAsAWhole());
        sortByDate();
        tableView.setItems(list2);

    }

    // create a method to display the incomplete items only
    public void displayIncomplete(){

        // use a console message to print the incomplete items only through the call of incompleteElements() method
        // convert to observable list for an easier handling when it comes to table view
        // call the sortByDate() method to sort the incomplete list of items by date
        // display on the table on the GUI using the .setItems() command
        System.out.print(list1.incompleteElements());
        list2 = FXCollections.observableArrayList(list1.incompleteElements());
        sortByDate();
        //tableView.refresh();
        tableView.setItems(list2);
    }

    // create a method to display the complete items only
    public void displayComplete(){

        // use a console message to print the complete items only through the call of completeElements() method
        // convert to observable list for an easier handling when it comes to table view
        // call the sortByDate() method to sort the complete elements list of items by date
        // display on the table on the GUI using the .setItems() command
        System.out.print(list1.completedElements());
        list2 = FXCollections.observableArrayList(list1.completedElements());
        sortByDate();
        //tableView.refresh();
        tableView.setItems(list2);

    }

    // create a method to represent the functionality for the saving process through the save work button click
    public void saveWorkBtnFunction(ActionEvent e){

        // create a new window to store the file and help
        // prompt the user for their desired file name
        Window currStage = fBox.getScene().getWindow();

        // create a date formatter that will help set the date to the desired format
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH.mm.ss");
        LocalDateTime curDate1 = LocalDateTime.now();
        String updated = dtf.format(curDate1);
        // modify the save dialogue aspect and check for a csv file using an extension filter
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("ToDoFor-" + updated); // set a default name at first that can be changed
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));
        // implement a try catch approach in order to handle any Exception
        try {

            // create a File variable f and update the initial directory
            // using a getParentFile() command
            File f = fileChooser.showSaveDialog(currStage);
            fileChooser.setInitialDirectory(f.getParentFile());
            // call the save to directory that will allow saving the file
            // while also writing to it and handling the changes
            if(f != null)
            list1.saveToDirectory(f);

            // print an error message to the screen if an exception occurs
        }catch (Exception ex){

            System.out.println("Recheck the process, a logical error happened...!!!");

        }

    }

    // create a method to represent the functionality for the Load Work process through the Load Work button click
    public void loadWorkBtnFunction(ActionEvent e){

        // create a Window type variable to help load a window that will play the role of a load file dialogue
        // we will use the .getWindow() command to do so
        // use a new File Chooser to implement an ExtensionFilter in order to get only csv type files
        Window stage = fBox.getScene().getWindow();
        fileChooser.setTitle("Loading Work...");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));
       // implement a try catch approach to catch any exception that can occur while loading a file
        try {
            // set an initial directory and then get the file name and display it on the gui
            File file = fileChooser.showOpenDialog(stage);
            // fLabel.setText(file.getName());
            fileChooser.setInitialDirectory(file.getParentFile());
            // check if the file exists then:

            // load the list and then display it through sorting it by date by calling the sortWithDate method and set the tableview using the .setItems()
            // with the observable list passed to it after being converted from an arraylist
            if(file != null) {
                list2 = FXCollections.observableArrayList(list1.loadToDoList(file));
                sortByDate();
                tableView.setItems(list2);
            }
        }
        catch(Exception exception){
            System.out.println("Problem happened....");
        }
    }

    // create a method to display one selected row by the user
    public void selectionOfRowByUser(){

        // get the item by using : .getSelectionModel().getSelectedItem();
        // call the display method and pass the item selected
        Item current = tableView.getSelectionModel().getSelectedItem();
        displayCurrentItem(current);

    }

    // create a method to just clear the text fields to give the user the chance to re-enter new input or remove a mistake or so
    public void fieldClearing(){

        desTextField.setText("");
        DatePicker.setValue(null);

    }

    // create a method that deletes everything when the clear all button gets clicked by the user
    public void clearAllTriggered(ActionEvent e){

        // print to the console a message announcing the clear all process
        // call the .clearAll() method from the ToDoListCode class
        // refresh the table and clear it from all elements through the use of .getItems().clear()
        System.out.println("All Cleared...");
        list1.clearAll();
        tableView.getItems().clear();
    }

    // create a method that is responsible for displaying the items by description first then due date
    public void displayCurrentItem(Item currItem){

        // this method sets the description and due date fields to their appropriate elements and values
        desTextField.setText(currItem.getDescription());
        DatePicker.setValue(currItem.getDueDate());

    }

    // create a method to handle the edit item button when the user wants to edit n item instead of deleting it and creating a new one
    public void editItemBtn(ActionEvent e){

        // create an Item type variable that will store the selected item from the table
        // using the getSelectionModel().getSelectedItem() command
        Item curr = tableView.getSelectionModel().getSelectedItem();
        String newDes = desTextField.getText();
        LocalDate newDate = DatePicker.getValue();
        // call the update item method from the ToDoListCode class that will take as parameters variables that stored the new changes
        // made by the user and then sort the list by date again after the change and that through the call of sortByDate() method
        // then refresh the table display and clear the description text area and date Picker
        list1.editPriorItem(curr,newDes,newDate);
        sortByDate();
        tableView.refresh(); // refresh
        //list2 = FXCollections.observableArrayList(list1.getItemsAsAWhole());
        //tableView.setItems(list2);
        fieldClearing(); // clear the fields again after refreshing
    }

    // create a method to show the help menu set in the case the user wants more description of what the app can do
    // and what options are available to their use
    // the method handles input and output exceptions
    public void helpMenuNeed(ActionEvent e) throws IOException {

        // create a stage
        Stage helpMn = new Stage();
        // load the help menu window of the app GUI
        // using a Parent type variable and FXMLLoader.load(getClass().getResource("HelpMenu.fxml"));
        Parent helpScr = FXMLLoader.load(getClass().getResource("HelpMenu.fxml"));

        // implement the option to have a window with a scrolling tool as the help menu is large and will require that
        // using a ScrollPane through the use of the .setContent() command and passing to it the Parent variable created
        // as described by the documentation
        ScrollPane s = new ScrollPane();
        s.setContent(helpScr);

        // mark the size of the Help Menu window then use the stage.show() to display
        helpMn.setTitle("Help Menu!");
        helpMn.setScene(new Scene(s, 800, 600));
        helpMn.show();
        // end of the function after the .show command was used
        // to display the help window

    }

    // create a method that deletes a precise selected item by the user
    public void deleteSelectedItem(ActionEvent e) {

        // create an Item type variable that will store the selected item from the table
        // using the getSelectionModel().getSelectedItem() command
        Item currItem = tableView.getSelectionModel().getSelectedItem();

        // send a message to the output screen displaying what will be deleted
        // display the item to user before the deletion by calling the displayCurrentItem method
        System.out.println("Deleting: "+currItem.getDescription()+" "+currItem.getDueDate()+"\n");
        displayCurrentItem(currItem);

        // remove the item from the list using the deleteItem() method
        // then remove item from the table using .getItems().remove(item that will be selected by the user)
        list1.deleteItem(currItem);
        tableView.getItems().remove(currItem);

        // call the sortByDate() method to resort everything in the list by date then start over by refreshing the table
        // to make the update noticeable to the user and remove anything from the date picker and text field to be ready
        // for new input through the use of clearAll() method
        sortByDate();
        tableView.refresh();
        fieldClearing();

    }

    // create a method responsible for the add of newly made items by the user whenever the Add Item button is clicked
    public void addItemBtn(ActionEvent e) {

        // use the print to console approach to print the description and dude date that will be added
        System.out.println("Adding the following....: "+desTextField.getText()+" "+DatePicker.getValue()+"\n");
        // implement conditionals to check if either the due date was not picked or the description was left blank
        // then send an error message to the user to notify them about the missing component adequately
        // using an Alert type variable with a setHeaderText(error message here and note)
        // Alert var name = new Alert(Alert.AlertType.ERROR);
        if(DatePicker.getValue() == (null) || desTextField.getText().equals("")) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Fill All the required spaces for the description/due date!!!");
            error.setContentText("Enter a description between 1 and 256 characters --- Enter a valid date.");
            error.showAndWait();

        }
        // implement a conditional to check if an item already exists
        // then use the same approach using an Alert type variable
        // and print a header text error message adequately for this case and use a hold screen command .showAndWait()
        else if(list1.checkExist(desTextField.getText())){

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Already Present Item!!!!");
            errorAlert.setContentText("Item Already in the list, cannot add identical items....");
            errorAlert.showAndWait();

        }
        // implement a last else statement that is executed whenever the description and due date are not left empty and
        // when the item was not already stored previously
        else {

            Item newI = new Item(desTextField.getText(), DatePicker.getValue());
            list1.addItem(newI);
            tableView.getItems().add(newI);
            sortByDate();
            tableView.refresh();
            fieldClearing();
        }

    }

    // end of the HelloController class
}
