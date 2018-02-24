
import fileengine.reprisentation.Phile; // This is where this driver is testing
import fileengine.reprisentation.Directory;

public class PhileDriver {
    public static void main(String[] args) {

        boolean failed = false;

        // Test the constructor.
        Directory testDir = new Directory("./testWorkspace/testDir", false);
        Directory testDirGit = new Directory("./testWorkspace/otherTestDir",false);
        Phile testFile = new Phile("testFile.txt", testDir);

        if (!(testFile.toString().equals("testFile.txt"))) {
            System.out.println("The constructior Failed");
            failed = true;
        }
        // Test tag
        if (!(testFile.tag("TAG"))) {
            System.out.println("The tag failed");
            failed = true;
        }
        if (!(testFile.toString().equals("TAG`testFile.txt"))) {
            System.out.println("The tag failed");
            failed = true;
        }
        if (!(testFile.tag("rem"))) {
            System.out.println("The tag failed");
            failed = true;
        }
        if (!(testFile.toString().equals("testFile.txt"))) {
            System.out.println("The tag failed");
            failed = true;
        }
        // Test rename
        if (!(testFile.rename("newFileName"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        if (!(testFile.toString().equals("newFileName.txt"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        if (!(testFile.rename("testFile"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        if (!(testFile.toString().equals("testFile.txt"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        // Test move
        if (!(testFile.move(testDirGit))) {
            System.out.println("The move failed");
            failed = true;
        }
        testDirGit.updateFiles();
        failed = true;
        for (int i = 0;i<2;i++) {
            if (testDirGit.listFiles()[i].toString().equals("testFile.txt")) {
                failed = false;
            }
        }
        if (failed)
            System.out.println("The move failed");
        if (!(testFile.move(testDir))) {
            System.out.println("The move failed");
            failed = true;
        }
        // Reaturn passed message if everything is okay.
        if (!failed)
            System.out.println("\tPassed");
    }
}
