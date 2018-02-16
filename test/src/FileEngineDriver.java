
import file_engine.FileEngine;

public class FileEngineDriver {
    public static void main(String[] args) {
        boolean failed = false;
        // Test constructor
        FileEngine api = new FileEngine();
        // Test add directory also test load directories and listFiles
        if (!(api.addDirectory("testWorkspace/testDir", false))) {
            System.out.println("The addDir failed");
            failed = true;
        }
        if (!(api.addDirectory("testWorkspace/otherTestDir", false))) {
            System.out.println("The addDir failed");
            failed = true;
        }
        if (!(api.listFiles(0)[1].toString().equals("testFile.txt"))) {
            System.out.println("The load failed");
            failed = true;
        }
        // Test remove dir
        try {
            api.listFiles(1);
        } catch (Exception e) {
            System.out.println("The add dir failed");
            failed = true;
        }
        if (!api.removeDirectory(1)) {
            System.out.println("here");
            System.out.println("The remove dir failed");
            failed = true;
        }
        try {
            api.listFiles(1);
            System.out.println("The remove dir failed");
            failed = true;
        } catch (Exception e) {
            int s; // do nothing
        }
        // Test git sync (assume to work)
        // Test update files (assume to work)
        if (!failed)
            System.out.println("\tPassed");
    }
}
