// -----------------------------------------------------------------------------
// Version v0.1.0. Created February 2018
// No implied warranty.
// -----------------------------------------------------------------------------

package interfaces;

import fileengine.FileEngine;
import java.util.Scanner;

public class CommandLineInterface {
  /** Primitive interface, asks commands and uses api to complete them. */
  public static void main(String[] args) {
    boolean kill = false;
    String command;
    Scanner keyboard = new Scanner(System.in);
    FileEngine api = new FileEngine();
    String stringBuffer;
    boolean boolBuffer;
    ProcessBuilder builder = new ProcessBuilder();
    Process process;
    mainLoop:
    while (!kill) {
      System.out.print("FE> ");
      command = keyboard.nextLine().toLowerCase();
      api.updateFiles();
      switch (command) {
        case "exit":
        case "e":
          kill = true;
          break;
        case "add":
          System.out.print("Enter directory path: ");
          stringBuffer = keyboard.nextLine();
          System.out.print("Sync with a remote git (y/n): ");
          if (keyboard.nextLine().equalsIgnoreCase("y")) {
            boolBuffer = true;
          } else {
            boolBuffer = false;
          }
          api.addDirectory(stringBuffer,boolBuffer);
          break;
        case "remove":
        case "r":
          System.out.print("Enter directory index: ");
          api.removeDirectory(keyboard.nextInt());
          keyboard.nextLine();
          break;
        case "tag":
        case "t":
          for (int i = 0;i < api.listFiles(0).length;i++) {
            System.out.println(api.listFiles(0)[i]);
            System.out.print("Enter tag: ");
            stringBuffer = keyboard.nextLine();
            api.listFiles(0)[i].tag(stringBuffer);
          }
          break;
        case "archive":
        case "a":
          for (int i = 0;i < api.listFiles(0).length;i++) {
            System.out.println(api.listFiles(0)[i]);
            System.out.print("Archive (y/n): ");
            if (keyboard.nextLine().equalsIgnoreCase("y")) {
              for (int k = 0;k < api.getDirectories().length;k++) {
                if (api.getDirectories()[k].getAbsolutePath().indexOf(api.listFiles(0)[i].toString().substring(0,api.listFiles(0)[i].toString().indexOf("`"))) > 0) {
                  api.listFiles(0)[i].move(api.getDirectories()[k]);
                }
              }
            }
          }
          break;
        case "backup":
        case "b":
          api.gitSync();
          break;
        case "work":
        case "w":
          if (api.listFiles(1).length > 0) {
            for (int i = 0;i < api.listFiles(1).length;i++) {
              api.listFiles(1)[i].move(api.getDirectories()[0]);
            }
          } else {
            for (int i = 0;i < api.listFiles(0).length;i++) {
              api.listFiles(0)[i].move(api.getDirectories()[1]);
            }
          }
          break;
        case "list":
        case "ls":
          for (int i = 2;i < api.getDirectories().length;i++) {
            System.out.println(api.getDirectories()[i].toString());
          }
          break;
        case "open":
        case "o":
          System.out.print("Enter directory index: ");
          stringBuffer = api.getDirectories()[keyboard.nextInt()].getAbsolutePath();
          builder.command("nautilus",stringBuffer);
          try {
            process = builder.start();
          } catch (Exception e) {
            System.out.println("Error opening file manager.");
          }
          keyboard.nextLine();
          break;
        default:
          System.out.println("Command not recongnized.");
          break;
        case "clear":
        case "c":
          clear();
          break;
      }
    }
    keyboard.close();
  }
  private static boolean clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    return true;
  }
}
