/**
 * @author Anton Changalidi
 */

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.util.Vector;

public class Manager {
    GadkinzSpot gadkinzSpot;
    private Gson gson;
    public static boolean f;

    public Manager(GadkinzSpot gadkinzSpot) {
        this.gadkinzSpot = gadkinzSpot;
        this.gson = new Gson();
        f=true;
    }

    /**
     * Method reads input file.
     *
     * @param EnvPath (String) - environment variable, that shows path to the Input file
     * @return String[], array of input strings.
     * @throws FileNotFoundException
     */
    public static String[] readFile(String EnvPath) throws FileNotFoundException {
        String string = "";
        String collectionPath = System.getenv(EnvPath);
        if(collectionPath==null){
            System.out.println("Environment variable for input \"LabInput\" does not exist.");
            f=false;
        }else {
            File inputfile = new File(collectionPath);
            if(!inputfile.exists()){
                System.out.println("File does not exist.");
                f=false;
            }else if(inputfile.isDirectory()) {
                System.out.println("File is a directory");
                f=false;
            }else{
                Scanner in = new Scanner(inputfile);
                while (in.hasNextLine()) {
                    string += in.nextLine() + "\n";
                }
                in.close();
            }
        }
        return string.split("\n");
    }

    /**
     * Method adds to Vector collection all elements, that are writed in String array.
     *
     * @param strings (String[]) - array of strings with information of objects (in JSON).
     */
    public void makeCollection(String[] strings) {
        for (String string : strings) {
            Gadkinz gadkinz = string.equals("null") ? null : readJson(string);
            if (gadkinz != null) {
                gadkinzSpot.put(gadkinz);
            }
        }
        if(f) System.out.println("The collection was readed and loaded from file.");
    }

    /**
     * Method parses String in Json-format and returns object with Gadkinz type.
     *
     * @param string (String) -  String in Json-format with object's paarmeters
     * @return gadkinz (Gadkinz)
     */
    public Gadkinz readJson(String string) {
        Gadkinz gadkinz = null;
        try {
            gadkinz = gson.fromJson(string, Gadkinz.class);
            if (gadkinz.getName() == null || gadkinz.getSharesAmount() <= 0 || gadkinz.getMoneyAmount() <= 0) {
                gadkinz = null;
                throw new Exception();
            }
        } catch (Exception e) {
            if(f) System.out.println("Incorrect JSON argument.");
        }
        return gadkinz;
    }

    /**
     * Method saves collection to file, that is located: String outputfileEnv.
     *
     * @param outputfileEnv (String) - path to file
     */
    public void saveCollection(String outputfileEnv) {
        Vector<Gadkinz> gadkinzes = gadkinzSpot.getGadkinzes();
        try {
            String outputPath = System.getenv(outputfileEnv);
            if (outputPath == null) {
                throw new EnvNameException();
            }
            File outputfile = new File(outputPath);
            try (FileOutputStream fos = new FileOutputStream(outputfile)) {
                for (Gadkinz g : gadkinzes) {
                    byte[] buffer = ((g.toString()) + "\n").getBytes();
                    fos.write(buffer, 0, buffer.length);

                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("The file has been written");
        } catch (EnvNameException ee) {
            System.out.flush();
            System.out.println("Environment variable for output \"LabOutput\" does not exist.");
        }
    }

}
