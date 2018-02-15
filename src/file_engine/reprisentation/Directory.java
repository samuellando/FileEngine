// -----------------------------------------------------------------------------
// Version 1. Created February 2018
// Samuel Lando, samuel.lando@mail.concordia.ca
// Free for non commercial use. No implied warranty.
// -----------------------------------------------------------------------------

package file_engine.reprisentation;

/*
    General description:
    * This class allows for the representation of directories on a computer as
    * objects with a path, files and an option to sync with remote git. REQUIRES
    * the Philes class of this package.

    Method descriptions:
    * Directory:
    ** Take inputed path and git and assign to instance variables.

    * toString:
    ** Return the name of the directory.

    * listFiles:
    ** Return the files instance variable.

    * getAbsolutePath:
    ** Takes relative path and returns absolute path.

    * updateFiles:
    ** Update the files instance variable with all the files in the path.

    * gitSync:
    ** Sync path with remote git directory.

    Test this class:
    * Run the DricetoryDriver in the tests folder of the repository.
*/

import java.io.File; // Used by update Files.
import java.io.BufferedReader; // Used by gitSync.
import java.io.InputStreamReader; // Used by gitSync.

public class Directory {

    private String path;
    private Phile[] files;
    private boolean git; // Whether or not to git sync.

    public Directory(String path, boolean git) {
        // Initialize all instance variables.
        // Use user input to set path and git.
        this.path = path;
        this.git = git;
        // Run update files and return an error if it fails.
        if (!updateFiles())
            System.out.println("ERROR on Directory CODE:1");
    }

    public String toString() {
        // Return the name of the folder in absolute path.
        try {
            return (getAbsolutePath().substring(getAbsolutePath().lastIndexOf("/")+1));
        } catch (Exception e) {
            System.out.println("ERROR on Directory CODE:2");
            return "";
        }
    }

    public Phile[] listFiles() {
        // Return the files instance variable.
        try {
            return files;
        } catch (Exception e) {
            System.out.println("ERROR on Directory CODE:3");
            return null;
        }
    }

    public String getAbsolutePath() {
        // Get the absolute path form the relative path and return it.
        try {
            return Thread.currentThread().getContextClassLoader().getResource(path).toString().substring(5);
        } catch (Exception e) {
            System.out.println("ERROR on Directory CODE:4");
            return "";
        }
    }

    public boolean updateFiles() {
        // Update the files instance variable with all the files in the path.
        try {
            // load the directory as a File object, load all files as File objects.
            File folder = new File(getAbsolutePath());
            File[] wrongType = folder.listFiles();
            // Initialize files as Phile object of length of files in directory.
            files = new Phile[wrongType.length];
            // Convert types
            for (int i =0; i < files.length; i++) {
                // create Phile object.
                files[i] = new Phile(wrongType[i].getName(),this);
            }
            return true;
        } catch (Exception e) {
            System.out.println("ERROR on Directory CODE:5");
            return false;
        }
    }

    public boolean gitSync() {
        // Sync path with remote git directory.
        try {
            if (git) {
                // Start process builder.
                ProcessBuilder builder = new ProcessBuilder();
                builder.directory(new File(getAbsolutePath()));
                // Run through sync algorithm by setting command, starting and waiting for each setp.
                builder.command("git","pull");
                Process process = builder.start();
                process.waitFor();
                builder.command("git","add",".");
                process = builder.start();
                process.waitFor();
                builder.command("git","commit","-am","\"fileEngine\"");
                process = builder.start();
                process.waitFor();
                builder.command("git","push");
                process = builder.start();
                process.waitFor();
                // Load git status to check if everything is okay.
                builder.command("git","status");
                process = builder.start();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s = "";
                String line;
                process.waitFor();
                while ((line = stdInput.readLine()) != null)
                    s += line;
                if (s.indexOf("Your branch is up to date with")<0||s.indexOf("nothing to commit, working tree clean")<0) {
                    System.out.println("ERROR on Directory CODE:6");
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
