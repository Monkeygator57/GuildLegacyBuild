package com.Database;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import static java.lang.String.valueOf;


public class Database {

    //Global Variables
    public static ArrayList<ArrayList<String>> Database = new ArrayList<ArrayList<String>>();
    public String filename;
    public File path;


    public Database(String filetxt, File Path) throws IOException {
        filename = filetxt;
        path = Path;
        File newFile = new File(path, filename);
        if (!newFile.exists()){
            newFile.createNewFile();
        }




    }

    public static void database(String[] args) throws FileNotFoundException {

    }

    //Query method
    // Use Query to retrieve any information in a record. returnNum is the index of the value you want
    // Note** INDEX LOCATIONS FOR QUERY METHOD
    // 0:Username|1:Password|2:ClassLevel|3:EquippedWeapon|4:EquippedArmor|5:EquippedTrinket
    // After these, every even number is an item and the following odd number is the weight
    public String Query(String UserID, int returnNum) {
        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(UserID)) {
                return Record.get(returnNum);
            }
        }
        return null;
    }

    // Changing the Class Levels
    //Username and level to set class level to
    public void SetLevel(String ID, int value){
        Database.get(GetIndex(ID)).set(2, valueOf(value));
    }
    //Increases Class Level by one
    public void LevelUp(String ID){
        int value = Integer.parseInt(Database.get(GetIndex(ID)).get(2));
        value++;
        Database.get(GetIndex(ID)).set(2, valueOf(value));
    }

    //New user method
    public void NewRecord(String ID, String password) throws FileNotFoundException {
        //Checks if this ID exists
        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(valueOf(ID))) {
                //Does not add a new user if the user already exists
                return;
            }
        }
        ArrayList<String> newUser = new ArrayList<String>();
        newUser.add(ID);
        newUser.add(password);
        newUser.add("0");  //Class Level
        newUser.add("NA"); //Equipped Weapon
        newUser.add("NA"); //Equipped Armor
        newUser.add("NA"); //Equipped Trinket
        Database.add(newUser);
        UpdateData();
    }

    //Writes database onto file and updates database array
    public void UpdateData() throws FileNotFoundException {
        File file = new File(path, filename);
        Scanner myReader = new Scanner(file);
        try {
            FileWriter myfWriter = new FileWriter(file);
            BufferedWriter myWriter = new BufferedWriter(myfWriter);
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
    // Index of 3,4,5 Is Equipped Item

    //Adds a list of items to the inventory
    public void InventoryAddList(String ID, ArrayList<String> items) {
        if (items.size() % 2 == 0) { //Checking that there are an even number of items in the list to insure each inventory item as a weight
            for (String item : items) {
                Database.get(GetIndex(ID)).add(item);
            }
        }
    }
    //Adds an individual Item to the Inventory
    public void InventoryAddItem(String ID, String item, int weight) {
        Database.get(GetIndex(ID)).add(item);
        Database.get(GetIndex(ID)).add(valueOf(weight));
    }
    public void InventoryRemoveItem(String ID, String item) {
        int i = Database.get(GetIndex(ID)).indexOf(item);
        Database.get(GetIndex(ID)).remove(item);
        Database.get(GetIndex(ID)).remove(i + 1);
    }
    //Returns Currently Equipped Item
    public String GetEquippedItem(String ID, String Item) {
        int equippedItemIndex = -1;
        // Used for QoL, Allows you to enter the location you want changed rather than a number
        switch(Item){
            case "hand":
                equippedItemIndex = 2;
                break;
            case "armour":
                equippedItemIndex = 3;
                break;
            case "trinket":
                equippedItemIndex = 4;
                break;
        }
        if (Database.get(GetIndex(ID)).get(equippedItemIndex).equalsIgnoreCase("NA")){ //Checks if there is an equipped item
            return null;
        } else{
            return Database.get(GetIndex(ID)).get(equippedItemIndex);
        }

    }
    //Sets Equipped Item
    public void SetEquippedItem(String ID, String item, String EquipItem) {
        int equippedItemIndex = -1;
        // Used for QoL, Allows you to enter the location you want changed rather than a number
        switch(item){
            case "hand":
                equippedItemIndex = 2;
                break;
            case "armour":
                equippedItemIndex = 3;
                break;
            case "trinket":
                equippedItemIndex = 4;
                break;
        }

        for (ArrayList<String> Record : Database) {
            if (Record.get(0).equalsIgnoreCase(valueOf(ID))) {
                for (String invItem : Record) {
                    if (invItem.equalsIgnoreCase(EquipItem)) {
                        Record.set(equippedItemIndex, EquipItem);

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
    public int GetIndex(String ID) {
        int index = 0;
        for (ArrayList<String> Record : Database){
            if (Record.get(0).equalsIgnoreCase(ID)){
                return index;
            }
            index++;
        }
        return -1;
    }
}

/*
  //Testing Database

        //Adds Database for each class
        File path = getApplicationContext().getFilesDir();
        Database warrior;
        Database mage;
        Database cleric;
        Database ranger;

        try {
            warrior = new Database("warrior.txt", path);
            mage = new Database("mage.txt", path);
            cleric = new Database("cleric.txt", path);
            ranger = new Database("ranger.txt", path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Testing Database
        try {
            String USER = "TEST";
            String USERPASS = "PASS";

            //Creation of a new user, must run all 4, one per class.

            warrior.NewRecord(USER, "PASS");
            mage.NewRecord(USER, USERPASS);
            cleric.NewRecord(USER, USERPASS);
            ranger.NewRecord(USER, USERPASS);

            //Variable(warrior) is for which database/class to pull from
            //'USER' is which user you want to pull from. *ALL* method parameters start with 'USER'
            warrior.InventoryAddItem(USER, "sword", 5);
            warrior.SetEquippedItem(USER, "hand", "sword");
            String testing = warrior.GetEquippedItem(USER, "hand");
            String USERSPASSWORD = warrior.Query(USER, 1);
            String testing2 = "Random extra line for debug screen to stop on";
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        //END TEST
 */