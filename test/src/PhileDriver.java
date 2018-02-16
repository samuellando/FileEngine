
import file_engine.reprisentation.Phile; // This is where this driver is testing

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
        if (!(testFile.tag(""))) {
            System.out.println("The tag failed");
            failed = true;
        }
        if (!(testFile.toString().equals("testFile.txt"))) {
            System.out.println("The tag failed");
            failed = true;
        }
        // Test rename
        if (!(testFile.rename("newFileName.txt"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        if (!(testFile.toString().equals("newFileName.txt"))) {
            System.out.println("The rename failed");
            failed = true;
        }
        if (!(testFile.rename("testFile.txt"))) {
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
        tesDirGit.updateFiles();
        failed = true;
        for (int i = 0;testDirGit.listFiles();i++) {
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
