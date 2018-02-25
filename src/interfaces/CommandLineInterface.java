// -----------------------------------------------------------------------------
// Version v0.1.0. Created February 2018
// No implied warranty.
// -----------------------------------------------------------------------------

package interfaces;

import fileengine.FileEngine;
import java.util.Scanner;

public class CommandLineInterface {
  private static boolean kill = false;
  private static String command;
  private static Scanner keyboard = new Scanner(System.in);
  private static FileEngine api = new FileEngine();

  /** Primitive interface, asks commands and uses api to complete them. */
  public static void main(String[] args) {
    while (!kill) {
      System.out.print("\033[34;1mFE> \033[37;0m");
      command = keyboard.nextLine().toLowerCase();
      api.updateFiles();
      switch (command) {
        case "exit":
        case "e":
          kill = true;
          break;
        case "add":
          add();
          break;
        case "remove":
        case "r":
          remove();
          break;
        case "tag":
        case "t":
          tag(0);
          tag(1);
          break;
        case "archive":
        case "a":
          archive(0);
          archive(1);
          break;
        case "backup":
        case "b":
          if (api.listFiles(1).length > 0) {
            work();
          }
          api.gitSync();
          break;
        case "work":
        case "w":
          work();
          break;
        case "list":
        case "ls":
          list();
          break;
        case "open":
        case "o":
          open();
          break;
        case "clear":
        case "c":
          clear();
          break;
        case "":
          break;
        default:
          System.out.println("Command not recongnized.");
          break;
      }
    }
    keyboard.close();
  }

  private static boolean add() {
    String stringBuffer;
    boolean boolBuffer;
    System.out.print("Enter directory path: ");
    stringBuffer = keyboard.nextLine();
    System.out.print("Sync with a remote git (y/n): ");
    if (keyboard.nextLine().equalsIgnoreCase("y")) {
      boolBuffer = true;
    } else {
      boolBuffer = false;
    }
    api.addDirectory(stringBuffer,boolBuffer);
    return true;
  }

  private static boolean remove() {
    System.out.print("Enter directory index: ");
    api.removeDirectory(keyboard.nextInt());
    keyboard.nextLine();
    return true;
  }

  private static boolean tag(int dir) {
    String stringBuffer;
    boolean allFilled = true;
    for (int i = 0;i < api.listFiles(dir).length;i++) {
      if (api.listFiles(dir)[i].toString().indexOf("`") < 0) {
        allFilled = false;
      }
    }
    if (allFilled) {
      for (int i = 0;i < api.listFiles(dir).length;i++) {
        System.out.println(api.listFiles(dir)[i]);
        System.out.print("Enter tag: ");
        stringBuffer = keyboard.nextLine();
        api.listFiles(dir)[i].tag(stringBuffer);
      }
    } else {
      for (int i = 0;i < api.listFiles(dir).length;i++) {
        if (api.listFiles(dir)[i].toString().indexOf("`") < 0) {
          System.out.println(api.listFiles(dir)[i]);
          System.out.print("Enter tag: ");
          stringBuffer = keyboard.nextLine();
          api.listFiles(dir)[i].tag(stringBuffer);
        }
      }
    }
    return true;
  }

  public static boolean archive(int dir) {
    for (int i = 0;i < api.listFiles(dir).length;i++) {
      System.out.println(api.listFiles(dir)[i]);
      System.out.print("Archive (y/n): ");
      if (keyboard.nextLine().equalsIgnoreCase("y")) {
        for (int k = 0;k < api.getDirectories().length;k++) {
          if (api.getDirectories()[k].getAbsolutePath().indexOf(api.listFiles(dir)[i].toString().substring(0,api.listFiles(dir)[i].toString().indexOf("`"))) > 0) {
            api.listFiles(dir)[i].tag("rem");
            api.listFiles(dir)[i].move(api.getDirectories()[k]);
          }
        }
      }
    }
    return true;
  }

  private static boolean work() {
    if (api.listFiles(1).length > 0) {
      for (int i = 0;i < api.listFiles(1).length;i++) {
        api.listFiles(1)[i].move(api.getDirectories()[0]);
      }
    } else {
      for (int i = 0;i < api.listFiles(0).length;i++) {
        if (api.listFiles(0)[i].toString().indexOf(".git") < 0) {
          api.listFiles(0)[i].move(api.getDirectories()[1]);
        }
      }
    }
    return true;
  }

  private static boolean list() {
    for (int i = 2;i < api.getDirectories().length;i++) {
      System.out.println(api.getDirectories()[i].toString());
    }
    return true;
  }

  private static boolean open() {
    String stringBuffer;
    ProcessBuilder builder = new ProcessBuilder();
    Process process;
    System.out.print("Enter directory index: ");
    stringBuffer = api.getDirectories()[keyboard.nextInt()].getAbsolutePath();
    keyboard.nextLine();
    builder.command("nautilus",stringBuffer);
    try {
      process = builder.start();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private static boolean clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    return true;
  }
}
