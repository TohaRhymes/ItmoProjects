/**
 * @author Anton Changalidi
 */

import java.io.FileNotFoundException;

public class LauncherApp {
    public static void main(String[] args) throws FileNotFoundException {
        GadkinzSpot gadkinzSpot = new GadkinzSpot();
        Manager fm = new Manager(gadkinzSpot);
        String[] resStrings = fm.readFile("LabInput");
        fm.makeCollection(resStrings);
        InstructionsExecutor instructionsExecutor = new InstructionsExecutor(gadkinzSpot, fm, "LabOutput");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> fm.saveCollection("LabOutput")));
        instructionsExecutor.go();
    }
}
