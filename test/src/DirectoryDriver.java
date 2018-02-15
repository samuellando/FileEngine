import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import file_engine.reprisentation.Directory; // This is where this driver is testing

public class DirectoryDriver {
    public static void main(String[] args) {

        boolean failed = false;

        // Test the constructior.
        Directory testDir = new Directory("./testWorkspace/testDir", false);
        Directory testDirGit = new Directory("./testWorkspace/testDirWithGit",true);
        if (testDir.listFiles()==null||testDirGit.listFiles()==null) {
            System.out.println("The constructior Failed");
            failed = true;
        }
        // Test toString
        if (testDir.toString().equals("")||testDirGit.toString().equals("")) {
            System.out.println("The toString Failed");
            failed = true;
        }
        // Test listFiles
        for (int i = 0;i<testDir.listFiles().length;i++) {
            if (testDir.listFiles().toString().equals("")) {
                System.out.println("The listFiles Failed");
                failed = true;
            }
        }
        for (int i = 0;i<testDirGit.listFiles().length;i++) {
            if (testDirGit.listFiles().toString().equals("")) {
                System.out.println("The listFiles Failed");
                failed = true;
            }
        }
        // Test absolute getAbsolutePath
        if (!testDir.getAbsolutePath().substring(testDir.getAbsolutePath().lastIndexOf("/")+1).equals("testDir")) {
            System.out.println("The getAbsolutePath Failed");
            failed = true;
        }
        if (!testDirGit.getAbsolutePath().substring(testDirGit.getAbsolutePath().lastIndexOf("/")+1).equals("testDirWithGit")) {
            System.out.println("The getAbsolutePath Failed");
            failed = true;
        }
        // Test file update.
        String ext;
        File oldFile = new File(testDir.getAbsolutePath()+"/"+testDir.listFiles()[0].toString());
        if ((testDir.getAbsolutePath()+"/"+testDir.listFiles()[0].toString()).indexOf(".txt") > 0)
            ext = "gxg";
        else
            ext = "txt";
        File newFile = new File((testDir.getAbsolutePath()+"/"+testDir.listFiles()[0].toString()).substring(0,(testDir.getAbsolutePath()+"/"+testDir.listFiles()[0].toString()).lastIndexOf(".")+1)+ext);
        oldFile.renameTo(newFile);
        oldFile.delete();
        testDir.updateFiles();
        if ((testDir.getAbsolutePath()+"/"+testDir.listFiles()[0].toString()).indexOf(ext) < 0) {
            System.out.println("The updateFiles Failed");
            failed = true;
        }
        // Test Git Sync (alrady has a good testing framework.)
        try {
            FileWriter fw = new FileWriter((testDir.getAbsolutePath()+"/"+testDir.listFiles()[0]).toString(),true);
            fw.write("\nNew Line");//appends the string to the file
            fw.close();
            fw = new FileWriter((testDirGit.getAbsolutePath()+"/"+testDirGit.listFiles()[0]).toString(),true);
            fw.write("\nOther New Line");//appends the string to the file
            fw.close();
        } catch (Exception e) {
            System.out.print("Driver error");
        }
        if (testDir.gitSync()) {
            System.out.println("The gitSync Failed on non git dir");
            failed = true;
        }
        if (!testDirGit.gitSync()) {
            System.out.println("The gitSync Failed on git dir");
            failed = true;
        }
        // Return passed message if everything is ok.
        if (!failed)
            System.out.println("\tPassed");
    }
}