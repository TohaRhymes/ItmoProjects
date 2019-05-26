/**
 * @author Anton Changalidi
 */

import java.util.Scanner;

public class InstructionsExecutor {
    GadkinzSpot gadkinzSpot;
    Manager fm;
    String outputEnv;

    public InstructionsExecutor(GadkinzSpot gadkinzSpot, Manager fm, String outputEnv) {
        this.gadkinzSpot = gadkinzSpot;
        this.fm = fm;
        this.outputEnv = outputEnv;
    }

    public void go() {
        boolean f = true;
        Scanner in = new Scanner(System.in);
        do {
            boolean check = true;
            String s = (in.nextLine()).trim();
            String[] sArray = s.split(" \\{");
            if (sArray.length == 1) {
                if (s.equals("exit")) {
                    check = false;
                    f = false;
                    fm.saveCollection(outputEnv);
                }
                if (s.equals("show")) {
                    check = false;
                    gadkinzSpot.show();
                }
                if (s.equals("clear")) {
                    check = false;
                    gadkinzSpot.clear();
                }
                if (s.equals("info")) {
                    check = false;
                    gadkinzSpot.info();
                }
                if (s.equals("sort")) {
                    check = false;
                    gadkinzSpot.sort();
                }
            } else {
                boolean check2 = true;
                sArray[1] = sArray[1].substring(0, sArray[1].length() - 1);
                String[] parameters;
                if (sArray[1].split(", ").length == 3) {
                    parameters = sArray[1].split(", ");
                } else if (sArray[1].split(",").length == 3) {
                    parameters = sArray[1].split(",");
                } else {
                    check2 = false;
                    parameters = sArray[1].split("");
                }
                if (check2 && isDigit(parameters[1]) && isDigit(parameters[2])) {
                    String name = parameters[0];
                    int money = Integer.parseInt(parameters[1]);
                    int sharesAmount = Integer.parseInt(parameters[2]);
                    if (sArray[0].equals("add")) {
                        check = false;
                        gadkinzSpot.add(name, money, sharesAmount);
                    }
                    if (sArray[0].equals("remove_greater")) {
                        check = false;
                        gadkinzSpot.removeGreater(new Gadkinz(name, money, sharesAmount));
                    }
                    if (sArray[0].equals("remove_all")) {
                        check = false;
                        gadkinzSpot.removeAll(new Gadkinz(name, money, sharesAmount));
                    }
                    if (sArray[0].equals("remove")) {
                        check = false;
                        gadkinzSpot.remove(new Gadkinz(name, money, sharesAmount));
                    }
                }
            }
            if (check) {
                System.out.println("WARNING: Entered non-existing command.");
            }

        } while (f);
        in.close();
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
