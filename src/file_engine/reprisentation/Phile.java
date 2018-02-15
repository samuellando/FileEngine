// -----------------------------------------------------------------------------
// Version 1. Created Febuary 2018
// Samuel Lando, samuel.lando@mail.concordia.ca
// Free for non commercial use
// -----------------------------------------------------------------------------

package file_engine.reprisentation;

/*
    General description:
    * This class allows for the representation of directories on a computer as
    * objects with a name and directory, class this integrates with the
    * TAG`filename naming convention. REQUIRES the Directory class of this
    * package.

    Method descriptions:
    * Phile:
    ** Take inputed name and dir object and assign to instance variables.

    * toString:
    ** Return the name instance variable.

    * tag:
    ** Take inputed tag and either swap the TAG in TAG`name or add a new one.

    * rename:
    ** Take name input and swap with instance variable, keeping the same TAG.

    * move:
    ** Take inputed directory, update the directory instance variable and and
    ** physically move the file on the computer.

    Test this class:
    * Run the PhileDriver in the tests folder of the repository.
*/

import java.io.File; // Used for tag, rename and move classes.

public class Phile {

    private String name = ""; // Initialized so no conflict occurs with rename when initializing object.
    private Directory dir;

    public Phile(String name, Directory dir){
        // Take inputed name and dir object and assign to instance variables.
        this.dir = dir; // Since we know dir is a valid Directory object no test is needed.
        // Run rename and return an error code if it fails.
        if (!(rename(name)))
            System.out.print("ERROR on Phile CODE:1");
    }

    public String toString() {
        // Return the name instance variable.
        return name;
    }

    public boolean tag(String newTag) {
        // Take inputed tag and either swap the TAG in TAG`name or add a new one.
        try {
            // Create file object for the file with current name with old TAG.
            File oldFile = new File(dir.getAbsolutePath()+"/"+name);
            // Update instance variable with new TAG.
            name = newTag.toUpperCase()+(newTag.equals("")?"":"`")+name.substring(name.indexOf("`")+1);
            // Create file object for the file with current name and new TAG.
            File newFile = new File(dir.getAbsolutePath()+"/"+name);
            // Rename the physical file in the directory.
            oldFile.renameTo(newFile);
            return true;
        } catch (Exception e) {
            System.out.print("ERROR on Phile CODE:2");
            return false;
        }
    }

    public boolean rename(String name) {
        // Take name input and swap with instance variable, keeping the same TAG.
        try {
            // Create file object for the file with current name with TAG.
            File oldFile = new File(dir.getAbsolutePath()+"/"+this.name);
            // Update the instance variable, keeping current tag.
            this.name = this.name.substring(0,this.name.indexOf("`")+1)+name+
                        (this.name.lastIndexOf(".")!=-1?this.name.substring(this.name.lastIndexOf(".")):"");
            // Create file object for the file with new name with TAG.
            File newFile = new File(dir.getAbsolutePath()+"/"+this.name);
            // Rename the physical file in the directory.
            oldFile.renameTo(newFile);
            return true;
        } catch (Exception e) {
            System.out.print("ERROR on Phile CODE:3");
            return false;
        }
    }

    public boolean move(Directory newDir) {
        // Take inputed directory, update the directory instance variable and and physically move the file on the computer.
        try {
            // Create file object for the file at the old directory.
            File oldFile = new File(dir.getAbsolutePath()+"/"+name);
            // Update the instance variable.
            dir = newDir;
            // Create file object for the file at the new directory.
            File newFile = new File(newDir.getAbsolutePath()+"/"+name);
            // Rename the physical file, this will move it to the new directory.
            oldFile.renameTo(newFile);
            return true;
        } catch (Exception e) {
            System.out.print("ERROR on Phile CODE:4");
            return false;
        }
    }
}
