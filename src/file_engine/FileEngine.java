// -----------------------------------------------------------------------------
// Version 1. Created February 2018
// Samuel Lando, samuel.lando@mail.concordia.ca
// Free for non commercial use
// -----------------------------------------------------------------------------

package file_engine;

/*
    General description:
    * This class allows for the representation of a workspace on a computer as
    * an object with a list of Directory objects. REQUIRES the Philes and
    * Directory classes of the representation package.

    Method descriptions:
    * FileEngine:
    ** Run the loadDirectories method and return an error if it fails.

    * loadDirecrories (Private):
    ** Scan DB_NAME and create Directory object in directories instance variable
    ** for each line.

    * addDirectory:
    ** Add new directory of database and loadDirectories.

    * removeDirectory:
    ** Remove directory from database and loadDirectories.

    * gitSync:
    ** Run Directory.gitSync method on each Directory object in the directories
    ** instance variable.

    * listFiles:
    ** Run Directory.listFiles method on a specific Directory object in the
    ** directories instance variable and return the Phile list.

    * updateFiles:
    ** Run Directory.updateFile method on each Directory object in the
    ** directories instance variable.

    Test this class:
    * Run the FileEngineDriver in the tests folder of the repository.
*/

import java.io.File; // Used by loadDirectories.
import java.io.FileInputStream; // Used by loadDirectories.
import java.io.FileNotFoundException; // Used by loadDirectories.
import java.io.FileWriter;
import java.io.IOException; // Used by loadDirectories.
import java.util.Scanner; // Used by loadDirectories.
import file_engine.reprisentation.*;

public class FileEngine {

    public String DB_NAME = "fileEngine.db"; // Name of db.
    private Directory[] directories;

    public FileEngine() {
        // Run the loadDirectories method and return an error if it fails.
        if (!loadDirectories())
            System.out.print("ERROR on FileEngine CODE:0");
    }

    private boolean loadDirectories() {
        // Scan DB_NAME and create Directory object in directories instance variable for each line.
        Scanner fileIn;
        int size = 0; // Size of db.

        try
        {
            // Attempt to open the db file and scanner on it.
            fileIn = new Scanner(new FileInputStream(DB_NAME));
        } catch (FileNotFoundException e) {
            // If the file could not be found then it is created.
            File db = new File(DB_NAME);
            try {
                db.createNewFile();
            } catch (Exception e2) {
                System.out.print("ERROR on FileEngine CODE:1");
                return false;
            }
            try {
                // Open scanner on new file
                fileIn = new Scanner(new FileInputStream(DB_NAME));
            } catch (Exception e3) {
                System.out.print("ERROR on FileEngine CODE:2");
                return false;
            }
        }
        // Determine the size of the database.
        while(fileIn.hasNextLine()) {
            size++;
            fileIn.nextLine();
        }
        // Reinitialize the scanner on the file.
        try {
            fileIn = new Scanner(new FileInputStream(DB_NAME));
        } catch (Exception e4) {
            System.out.println("ERROR on FileEngine CODE:3");
            fileIn.close();
            return false;
        }
        // Change the delimiter so that it works with the file format used for db.
        fileIn.useDelimiter(",");
        // Initialize empty array for directories of size that was determined.
        directories = new Directory[size];
        // Initialize Directory object for each entry in the database.
        for(int i = 0;fileIn.hasNextLine();i++) {
            try {
                directories[i] = new Directory(fileIn.next(),fileIn.nextLine().equals(",true")?true:false);
            } catch (Exception e5) {
                System.out.println("ERROR on FileEngine CODE:4");
                fileIn.close();
                return false;
            }
        }
        fileIn.close();
        return true;
    }

    public boolean addDirectory(String path, String git) {
        // Add new directory of database and loadDirectories.
        try{
            FileWriter fw = new FileWriter(DB_NAME,true); //the true will append the new data
            fw.write(path+","+git);//appends the string to the file
            fw.close();
            loadDirectories();
            return true;
        }
        catch(IOException e){
            System.out.println("ERROR on FileEngine CODE:5");
            return false;
        }
    }

    public boolean removeDirectory(int num) {
        // Remove directory from database and loadDirectories.
        Scanner fileIn;
        String dbContents = "";

        try
        {
            // Attempt to open the db file and scanner on it.
            fileIn = new Scanner(new FileInputStream(DB_NAME));
        } catch (FileNotFoundException e) {
            // If the file could not be found then it is created.
            File db = new File(DB_NAME);
            try {
                db.createNewFile();
            } catch (Exception e2) {
                System.out.print("ERROR on FileEngine CODE:6");
                return false;
            }
            try {
                // Open scanner on new file
                fileIn = new Scanner(new FileInputStream(DB_NAME));
            } catch (Exception e3) {
                System.out.print("ERROR on FileEngine CODE:7");
                return false;
            }
        }
        // copy db to dbContents except line 'num'
        for (int i = 0;i<=directories.length;i++) {
            if (i!=num) {
                dbContents += fileIn.nextLine()+"\n";
            }
        }
        fileIn.close(); // No longer needed.
        // Delete and recreate db.
        File db = new File(DB_NAME);
        try {
            db.delete();
            db.createNewFile();
        } catch (Exception e2) {
            System.out.print("ERROR on FileEngine CODE:8");
            return false;
        }
        // Rewrite data to file.
        try{
            FileWriter fw = new FileWriter(DB_NAME,true); //the true will append the new data
            fw.write(dbContents);//appends the string to the file
            fw.close();
            loadDirectories();
            return true;
        }
        catch(IOException e){
            System.out.println("ERROR on FileEngine CODE:9");
            return false;
        }
    }

    public boolean GitSync() {
        // Run Directory.gitSync method on each Directory object in the directories instance variable.
        for (int i = 0; i < directories.length; i++) {
            directories[i].gitSync();
        }
        return true;
    }

    public Phile[] listFiles(int i) {
        // Run Directory.listFiles method on a specific Directory object in the directories instance variable and return the Phile list.
        return directories[i].listFiles();
    }

    public boolean updateFiles() {
        // Run Directory.updateFile method on each Directory object in the directories instance variable.
        for (int i = 0;i<directories.length;i++)
            // Try and run update File on Directory object and return false if it fails.
            if (!directories[i].updateFiles())
                return false;
        return true;
    }
}
