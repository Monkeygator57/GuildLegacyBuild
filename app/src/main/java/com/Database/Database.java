package com.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.String.valueOf;

public class Database {

    //Global Variables
    public static ArrayList<ArrayList<String>> Database = new ArrayList<ArrayList<String>>();
    public static File myObj = new File("Data.txt");

    public static void database(String[] args) throws FileNotFoundException {

    }

    //Query method
    // Use Query to retrieve any information in a record. returnNum is the index of the value you want
    // 0:UserID, 1:Warrior Level, 2:Mage Level, 3:Cleric Level, 4:Rogue Level, 5:Equipped Item
    // After these, every even number is an item and the following odd number is the weight
    static String Query(int UserID, int returnNum) {
        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(valueOf(UserID))) {
                return Record.get(returnNum);
            }
        }
        return null;
    }

    // Changing the Class Levels
    // Index of 1   2      3          4
    // Is Warrior, Mage, Cleric, and Rogue
    // Int Level refers to the index of the level, Value refers to the actual value of it
    static void SetLevel(int ID, int Level, int value){
        Database.get(GetIndex(ID)).set(Level, valueOf(value));
    }
    static void LevelUp(int ID, int level){
        int value = Integer.parseInt(Database.get(GetIndex(ID)).get(level));
        value++;
        Database.get(GetIndex(ID)).set(level, valueOf(value));
    }

    //New user method
    static void NewRecord(int ID) {
        //Checks if this ID exists
        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(valueOf(ID))) {
                //Does not add a new user if the user already exists
                return;
            }
        }
        ArrayList<String> newUser = new ArrayList<String>();
        newUser.add(valueOf(ID));
        newUser.add("0"); //Warrior Level
        newUser.add("0"); //Mage Level
        newUser.add("0"); //Cleric Level
        newUser.add("0"); //Rogue Level
        newUser.add("NA"); //Equipped Item
        Database.add(newUser);
    }

    //Writes database onto file and updates database array
    static void UpdateData() throws FileNotFoundException {
        Scanner myReader = new Scanner(myObj);
        try {
            FileWriter myWriter = new FileWriter("Data.txt");
            for (int d = 0; d < Database.size(); d++) {
                for (int k = 0; k < Database.get(d).size(); k++) {
                    myWriter.write(Database.get(d).get(k) + '|');
                }
                myWriter.write("\r\n");
            }
            myWriter.close();
            System.out.println("successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("unsuccessfully wrote to the file.");
            e.printStackTrace();
        }
        Database.clear();
        while (myReader.hasNextLine()) {
            ArrayList<String> data = Tokenizer(myReader.nextLine(), '|');
            Database.add(data);
        }
    }


    //Note: Inventory will start at an index of 6
    // Index of 5 Is Equipped Item

    //Adds a list of items to the inventory
    static void InventoryAddList(int ID, ArrayList<String> items) {
        if (items.size() % 2 == 0) { //Checking that there are an even number of items in the list to insure each inventory item as a weight
            for (String item : items) {
                Database.get(GetIndex(ID)).add(item);
            }
        }
    }
    //Adds an individual Item to the Inventory
    static void InventoryAddItem(int ID, String item, int weight) {
        Database.get(GetIndex(ID)).add(item);
        Database.get(GetIndex(ID)).add(valueOf(weight));
    }
    //Returns Currently Equipped Item
    static String GetEquippedItem(int ID) {
        return Database.get(GetIndex(ID)).get(5);
    }
    //Sets Equipped Item
    static void SetEquippedItem(int ID, String item) {
        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(valueOf(ID))) {
                for (String invItem : Record) {
                    if (invItem.equalsIgnoreCase(item)) {
                        Record.set(5, item);
                    }
                }
            }
        }
    }


    // UTILITY

    //Takes in a Line from the Database and turns it into a list of strings
    static ArrayList<String> Tokenizer(String Line, char Token) {
        ArrayList<String> result = new ArrayList<String>();
        String Temp = "";
        for (int i = 0; i < Line.length(); i++) {
            if (Line.charAt(i) != Token) {
                Temp = Temp + Line.charAt(i);
            } else {
                result.add(Temp);
                Temp = "";
            }
        }
        return result;
    }

    //Used to get the Index of the User's Record
    static int GetIndex(int ID) {
        int index = 0;
        for (ArrayList<String> Record : Database){
            if (Record.get(0).equalsIgnoreCase(valueOf(ID))){
                return index;
            }
            index++;
        }
        return -1;
    }
}