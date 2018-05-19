// -----------------------------------------------------------------------------
// Version v0.1.0. Created February 2018
// No implied warranty.
// -----------------------------------------------------------------------------

package interfaces;

import fileengine.FileEngine;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GraphicalInterface {
  public static JFrame frame = new JFrame();
  public static FileEngine api = new FileEngine();
  public static Container pane = frame.getContentPane();
  public static JLabel title = new JLabel();
  public static JLabel[] directoryLabels = new JLabel[api.getDirectories().length];
  public static char key;
  public static int code;
  public static KeyListener keyIn = new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        key = e.getKeyChar();
        code = e.getKeyCode();
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
  };

  public static void createFrame() {
    frame.setTitle("File Engine");
    frame.setSize(800, 200);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pane.setBackground(Color.BLACK);
    pane.setLayout(new GridLayout(10,2));
    title.setForeground(Color.ORANGE);
    pane.add(title);
    frame.addKeyListener(keyIn);
    for (int i = 0;i < api.getDirectories().length;i++) {
      directoryLabels[i]=new JLabel();
      directoryLabels[i].setForeground(Color.ORANGE);
      pane.add(directoryLabels[i]);
    }
  }
  public static boolean doAction() {
    key = '.';
    while (key == '.') {
      try {
            Thread.sleep(250);
        }
      catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    switch (key) {
      case 'b':
        clearFrame();
        title.setText("Running Backup");
        api.gitSync();
        showDirectories();
        break;
      case 'a':
        for (int i = 0; i < api.getDirectories()[0].listFiles().length; i++) {
          if (api.getDirectories()[0].listFiles()[i].toString().equals(".git")) continue;
          clearFrame();
          title.setText("Archive: "+api.getDirectories()[0].listFiles()[i]+"?");
          key = '.';
          while (key == '.') {
            try {
                  Thread.sleep(250);
              }
            catch(InterruptedException ex) {
                  Thread.currentThread().interrupt();
              }
          }
          if (key == 'y') {
            if (api.getDirectories()[0].listFiles()[i].toString().indexOf("`") > 0) {
              for (int j = 0; j < api.getDirectories().length;j++) {
                if (api.getDirectories()[j].toString().indexOf(api.getDirectories()[0].listFiles()[i].toString().substring(0,api.getDirectories()[0].listFiles()[i].toString().indexOf("`")))>0) {
                  api.getDirectories()[0].listFiles()[i].move(api.getDirectories()[j]);
                  break;
                }
              }
            }
          }
        }
        break;
      case 't':
        for (int i = 0; i < api.getDirectories()[0].listFiles().length; i++) {
          if (api.getDirectories()[0].listFiles()[i].toString().equals(".git")) continue;
          clearFrame();
          String newTag = "";
          key = '.';
          code = 0;
          while (code != KeyEvent.VK_ENTER) {
            title.setText("TAG: "+api.getDirectories()[0].listFiles()[i].toString()+": "+newTag);
            while (key == '.') {
              try {
                    Thread.sleep(50);
                }
              catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            if (code == KeyEvent.VK_BACK_SPACE && newTag.length() > 0) {
              newTag = newTag.substring(0, newTag.length() - 1);
              code = 0;
              key = '.';
            } else if (key != '.' && code != KeyEvent.VK_SHIFT && code != KeyEvent.VK_ENTER) {
              newTag = newTag+key;
              key = '.';
            }
          }
          api.getDirectories()[0].listFiles()[i].tag(newTag);
        }
        break;
      case 'o':
          title.setText("Enter a number: ");
          key = '.';
          while (key == '.') {
            try {
                  Thread.sleep(250);
              }
            catch(InterruptedException ex) {
                  Thread.currentThread().interrupt();
              }
          }
          try {
            api.getDirectories()[Character.getNumericValue(key)].open();
          } catch (Exception e) {
            System.out.println("NotGood");
          }
        break;
      case 'e':
        System.exit(0);
        break;
      default:
        System.out.println("Invalid command");
    }
    return true;
  }
  public static void showDirectories() {
    title.setText("Directories:");
    for (int i = 0;i < api.getDirectories().length;i++) {
      directoryLabels[i].setText(i+" : "+api.getDirectories()[i].getAbsolutePath());
    }
  }
  public static void clearFrame() {
    for (int i = 0;i<directoryLabels.length;i++) {
      directoryLabels[i].setText("");
    }
    title.setText("");
  }
  /** Primitive interface, asks commands and uses api to complete them. */
  public static void main(String[] args) {
    createFrame();
    while (true) {
      showDirectories();
      doAction();
    }
  }
}
