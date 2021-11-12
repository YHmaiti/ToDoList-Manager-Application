/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Yohan Hmaiti
 */

package ucf.assignments;

import javafx.application.Application;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.Test;


// test all the functionalities of the GUI along with tasks from #6-#20 as mentioned by the assignment
public class ToDoListCodeTest {


    // build a helped method to convert the loaded work to a String
    public String Helper(ArrayList<Item> cur) {
        String formed = "Description,DueDate,Status\n";
        for(Item i: cur){
            formed += i.getDescription() + "," + i.getDueDate().toString() + ",";
            if(i.getStatus().isSelected()){
                formed += "Complete\n";
            }else{
                formed += "Incomplete\n";
            }
        }

        return formed; // return the transformed string
    }
    private JFXPanel panel = new JFXPanel();

    // create a private instance that will be used to hold the list at each case
    private ToDoListCode list;

    @Test
    @DisplayName("Test if the functionality to get all items at once of a todo list works!")
    public void getItemsAsAWhole() {

        // create a ToDoListCode object called currentList
        // create an arraylist of type Item called testingList
        // create two sample Items type variables with sample data
        list = new ToDoListCode();
        ArrayList<Item> itemsTest = new ArrayList<>();
        Item i1 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 10));
        Item i2 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 11));

        // create a third Item type arraylist that will be made through the call of the .getItemsAsAWhole() method
        // ArrayList<Item> allList = currentList.getItemsAsAWhole(); later
        ArrayList<Item> curr;
        // add the two sample items to the currentList by calling the .addItem() method and pass the item to it
        // add the two sample items to the testingList using the .add() arraylist command and pass at each time the item to it
        list.addItem(i1);
        list.addItem(i2);
        itemsTest.add(i1);
        itemsTest.add(i2);

        curr = list.getItemsAsAWhole();

        // test that the generated allList is correct by using assertArrayEquals() command as follows
        // to check that both the testing array and the allList are identical
        assertArrayEquals(itemsTest.toArray(), curr.toArray());
    }

    @Test
    @DisplayName("Test if the user can mark items as complete or Incomplete successfully!")
    public void markAsCompleteTest() {

        // create a ToDoListCode Object that will be called currentList
        // create an Item object called item1 that will have a random sample data for testing purpose
        // create a checkBox variable called checkBox1 and set it to true using checkBox1.setSelected(true) command
        // call the setStatus method from the item.java class using: item1.setStatus(checkBox1)
        list = new ToDoListCode();
        Item i = new Item("test12345", LocalDate.of(2021, Month.NOVEMBER, 15));
        CheckBox test = new CheckBox();
        test.setSelected(true);
        i.setStatus(test);

        // administer a test to check if the status reflects a completed item since the passed checkBox was set to clicked meaning true
        assertTrue(i.getStatus().isSelected());

    }

    @Test
    @DisplayName("Test if the user can mark items as complete or Incomplete successfully!")
    public void markAsCompleteTest2() {

        // create a ToDoListCode Object that will be called currentList
        // create an Item object called item1 that will have a random sample data for testing purpose
        // create a checkBox variable called checkBox1 and set it to false using checkBox1.setSelected(false) command
        // call the setStatus method from the item.java class using: item1.setStatus(checkBox1)
        list = new ToDoListCode();
        Item i = new Item("test12345", LocalDate.of(2021, Month.NOVEMBER, 15));
        CheckBox test = new CheckBox();
        test.setSelected(false);
        i.setStatus(test);
        // administer a test to check if the status reflects a non completed item since the passed checkBox was set to not clicked meaning false
        assertFalse(i.getStatus().isSelected());

    }

    @Test
    @DisplayName("Test that only the completed items are obtained when the user wants just those!")
    public void completedElements() {
        // seed the list and create an array list for testing purposes
        list = new ToDoListCode();
        ArrayList<Item> test = new ArrayList<>();
        // create tmp checkbox
        // to test completed status we need to set tmp to true
        CheckBox tmp = new CheckBox();
        tmp.setSelected(true);
        // generate some test data and add them to the list
        Item i1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item i2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 5));
        Item i3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 10));
        list.addItem(i1);
        list.addItem(i2);
        list.addItem(i3);

        // update the status of i2 and i3
        i2.setStatus(tmp);
        i3.setStatus(tmp);
        // add true items only
        test.add(i2);
        test.add(i3);

        // test the completedElements() method and compare it to what is expected
        ArrayList<Item> cur = list.completedElements();
        assertArrayEquals(test.toArray(), cur.toArray());

    }

    @Test
    @DisplayName("Test that only the incomplete items are obtained when the user wants just those!")
    public void incompleteElements() {

        // seed the list and create an array list for testing purposes
        list = new ToDoListCode();
        ArrayList<Item> test = new ArrayList<>();
        // create tmp checkbox
        // to test incomplete status we need to set tmp to false
        CheckBox tmp = new CheckBox();
        tmp.setSelected(false);
        // generate some test data and add them to the list
        Item i1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item i2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 5));
        Item i3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 10));
        list.addItem(i1);
        list.addItem(i2);
        list.addItem(i3);

        // update the status of i2 and i3
        i2.setStatus(tmp);
        i3.setStatus(tmp);

        tmp.setSelected(false);
        i1.setStatus(tmp);

        // add false items only
        test.add(i1);
        test.add(i2);
        test.add(i3);


        // test the incompleteElements() method and compare it to what is expected
        ArrayList<Item> cur = list.incompleteElements();
        assertArrayEquals(test.toArray(), cur.toArray());

    }

    @Test
    @DisplayName("Test if addItem method adds the items successfully!")
    public void addItem() {

        // create a ToDoListCode Object currentList and create an arraylist testList of type Item
        // create three Item objects item1, item2, item3 with random sample data
        // add each to both lists using .addItem for the currentList and .add for the testList by passing each item at each time
        list = new ToDoListCode();
        ArrayList<Item> curr = new ArrayList<>();
        Item item1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 3));
        Item item3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 25));

        list.addItem(item1);
        list.addItem(item2);
        list.addItem(item3);
        curr.add(item1);
        curr.add(item2);
        curr.add(item3);

        // check if both lists match to confirm the success of the addItem method:
        assertArrayEquals(curr.toArray(), list.getItemsAsAWhole().toArray());

    }

    @Test
    @DisplayName("Test if the the delete by Item method works perfectly using two deletions and comparison")
    public void deleteItem() {

        list = new ToDoListCode();
        ArrayList<Item> curr= new ArrayList<>();
        // test data
        Item item1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 3));
        Item item3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 25));
        // add the test data to the list and to the curr array list
        list.addItem(item1);
        list.addItem(item2);
        list.addItem(item3);
        curr.add(item1);
        curr.add(item2);
        curr.add(item3);

        // make the appropriate call of delete item method
        list.deleteItem(item2);
        // remove the identical item
        curr.remove(item2);
        // test the similitude
        assertArrayEquals(curr.toArray(), list.getItemsAsAWhole().toArray());

    }

    @Test
    @DisplayName("Test if the the delete by Item method works perfectly using two deletions and comparison")
    public void deleteItem2() {
        Boolean isRight;
        list = new ToDoListCode();
        ArrayList<Item> curr = new ArrayList<>();
        // test data
        Item item1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 3));
         // add the test data to the list and to the curr array list
        list.addItem(item1);
        list.addItem(item2);
        curr.add(item1);
        curr.add(item2);

        // make the appropriate call of delete item method
        list.deleteItem(item2);
        // remove the identical item
        curr.remove(item1);
        // test that we get a false boolean as different items were removed that are not identical
        isRight = (list.getItemsAsAWhole()).equals(curr);
        assertFalse(isRight);

    }

    @Test
    @DisplayName("Test if items selected can be edited successfully for either the description or due date")
    public void editPriorItem() {

        // *******test that the update of the date is working:

        list = new ToDoListCode();
        // test data
        Item item = new Item("test", LocalDate.of(2021, Month.NOVEMBER, 1));
        list.addItem(item);
        // change the due date and restore it
        list.editPriorItem(item, "test", LocalDate.of(2021, Month.NOVEMBER, 1));
        // evaluate the edit
        assertEquals(item.getDueDate(), LocalDate.of(2021, Month.NOVEMBER, 1));

        // *****test that the update of the description is working:

        list = new ToDoListCode();
        // test data
        Item item2 = new Item("test", LocalDate.of(2021, Month.NOVEMBER, 1));
        list.addItem(item2);
        // change the description and store it
        list.editPriorItem(item2, "test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        // evaluate the edit
        assertEquals(item2.getDescription(), "test123");

    }


    @Test
    @DisplayName("Test if clearing everything works perfectly when the user clicks the Clear All button")
    public void clearAll() {

        // create a ToDoListCode Object currentList and an arrayList of type Item called testingList
        // create three random sample data of type Item that we will assign to each using the .addItem method for currentList
        // and .add for testingList by passing the items to each
        list = new ToDoListCode();
        ArrayList<Item> curr = new ArrayList<>();
        Item item1 = new Item("test", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("Test123", LocalDate.of(2021, Month.NOVEMBER, 4));
        Item item3 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 6));
        list.addItem(item1);
        list.addItem(item2);
        list.addItem(item3);
        curr.add(item1);
        curr.add(item2);
        curr.add(item3);

        // call the appropriate method and clear the arraylist curr made for testing then check if both are equal
        list.clearAll();
        curr.clear();
        assertArrayEquals(curr.toArray(), list.getItemsAsAWhole().toArray());

    }

    @Test
    @DisplayName("Test that when the user clicks Save Work the work is saved correctly after calling the saveToDirectory method")
    public void saveToDirectory() {

        // create a ToDoListCode Object called currentList:
        list = new ToDoListCode();

        // create a string that will be used later for testing
        // String str ="";
        // create sample Item Objects with random data
        // item1, item2, item3
        // Item item1 = new Item("sampleDescription1", LocalDate.of(2021, Month.OCTOBER, 2));
        // Item item2 = new Item("sampleDescription2", LocalDate.of(2021, Month.OCTOBER, 8));
        // Item item3 = new Item("sampleDescription3", LocalDate.of(2021, Month.OCTOBER, 25));
        String str = "";
        Item item1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 3));
        Item item3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 25));
        list.addItem(item1);
        list.addItem(item2);
        list.addItem(item3);
        // next add the items to currentList using the addItems() method by passing the items to it one by one
        // create a checkbox and make it for item1 status as complete meaning set it to true
        CheckBox tmp = new CheckBox();
        tmp.setSelected(true);
        item1.setStatus(tmp);

        // generate a file and assign to it the desired directory and pass the FILE variable to the currentList.saveToDirectory()
        // set a variable to hold the directory:
        String dir = System.getProperty("user.home") + "/Desktop/ToDo_Yohan/fileForTesting.csv";
        File directory = new File(dir);
        list.saveToDirectory(directory);

        // use a buffered reader to read from teh file and store all the elements being read until the end of the file
        // in the String previously declared called str using a loop that goes until the end of the file
        // catch any IO or file not found exceptions along the process to throw an error adequately
        // generate a test String that will contain the expected elements scanned from the file called test
        // compare it with the str string from that stored what was read from teh file using assertEquals:
        String tested = """
                Description,DueDate,Status
                test123,2021-11-01,Complete
                test321,2021-11-03,Incomplete
                TEST,2021-11-25,Incomplete
                """;
        try{
            BufferedReader br = new BufferedReader(new FileReader(directory));
            String current = "";
            while((current = br.readLine()) != null){
                str += current + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(tested, str);

    }

    @Test
    @DisplayName("Test that when the user clicks load work the ToDoList is loaded successfully and that its corresponding csv file was found!")
    public void loadToDoList() {

        list = new ToDoListCode();

        // create a string that will be used later for testing
        // String str ="";
        // create sample Item Objects with random data
        // item1, item2, item3
        // Item item1 = new Item("sampleDescription1", LocalDate.of(2021, Month.OCTOBER, 2));
        // Item item2 = new Item("sampleDescription2", LocalDate.of(2021, Month.OCTOBER, 8));
        // Item item3 = new Item("sampleDescription3", LocalDate.of(2021, Month.OCTOBER, 25));
        String str = "";
        Item item1 = new Item("test123", LocalDate.of(2021, Month.NOVEMBER, 1));
        Item item2 = new Item("test321", LocalDate.of(2021, Month.NOVEMBER, 3));
        Item item3 = new Item("TEST", LocalDate.of(2021, Month.NOVEMBER, 25));
        list.addItem(item1);
        list.addItem(item2);
        list.addItem(item3);
        // next add the items to currentList using the addItems() method by passing the items to it one by one
        // create a checkbox and make it for item1 status as complete meaning set it to true
        CheckBox tmp = new CheckBox();
        tmp.setSelected(true);
        item1.setStatus(tmp);

        // generate a file and assign to it the desired directory and pass the FILE variable to the currentList.saveToDirectory()
        // set a variable to hold the directory:
        String dir = System.getProperty("user.home") + "/Desktop/ToDo_Yohan/fileForTesting.csv";
        File directory = new File(dir);
        list.saveToDirectory(directory);

        // set a string containing the right expected elements
        String tested = """
                Description,DueDate,Status
                test123,2021-11-01,Complete
                test321,2021-11-03,Incomplete
                TEST,2021-11-25,Incomplete
                """;

        // create an array list that will hold the loaded work
        ArrayList<Item> loadedWork = list.loadToDoList(directory);
        // to simplify the process let's convert the elements to a String for comparison purposes
        String loaded = Helper(loadedWork);
        // check that both Strings are equal
        assertEquals(tested, loaded);

    }

}