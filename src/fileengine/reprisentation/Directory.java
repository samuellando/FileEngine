// -----------------------------------------------------------------------------
// Version v0.1.0. Created February 2018
// No implied warranty.
// -----------------------------------------------------------------------------

package fileengine.reprisentation;

import java.io.BufferedReader; // Used by gitSync.
import java.io.File; // Used by update Files.
import java.io.InputStreamReader; // Used by gitSync.

public class Directory {

  private String path;
  private Phile[] files;
  private boolean git; // Whether or not to git sync.

  /** Initialize all instance variables. */
  public Directory(String path, boolean git) {
    // Use user input to set path and git.
    this.path = path;
    this.git = git;
    // Run update files and return an error if it fails.
    if (!updateFiles()) {
      System.out.println("ERROR on Directory CODE:1");
    }
  }

  /** Return the name of the folder in absolute path. */
  public String toString() {
    try {
      return (getAbsolutePath().substring(getAbsolutePath().lastIndexOf("/") + 1));
    } catch (Exception e) {
      System.out.println("ERROR on Directory CODE:2");
      return "";
    }
  }

  /** Return the files instance variable. */
  public Phile[] listFiles() {
    try {
      return files;
    } catch (Exception e) {
      System.out.println("ERROR on Directory CODE:3");
      return null;
    }
  }

  /** Get the absolute path form the relative path and return it. */
  public String getAbsolutePath() {
    try {
      return new File(path).getCanonicalPath();
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("ERROR on Directory CODE:4");
      return "";
    }
  }

  /** Update the files instance variable with all the files in the path. */
  public boolean updateFiles() {
    try {
      // load the directory as a File object, load all files as File objects.
      File folder = new File(getAbsolutePath());
      File[] wrongType = folder.listFiles();
      // Initialize files as Phile object of length of files in directory.
      files = new Phile[wrongType.length];
      // Convert types
      for (int i = 0; i < files.length; i++) {
        // create Phile object.
        files[i] = new Phile(wrongType[i].getName(),this);
      }
      return true;
    } catch (Exception e) {
      System.out.println("ERROR on Directory CODE:5");
      return false;
    }
  }

  /** Sync path with remote git directory. */
  public boolean gitSync() {
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
        while ((line = stdInput.readLine()) != null) {
          s += line;
        }
        if (s.indexOf("Your branch is up to date with") < 0 || s.indexOf("nothing to commit, "
                                                                      + "working tree clean") < 0) {
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
  public boolean open() {
    try {
      ProcessBuilder builder = new ProcessBuilder();
      builder.directory(new File(getAbsolutePath()));
      builder.command("nautilus",".");
      builder.start();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
