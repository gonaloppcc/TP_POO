package Controller;

import View.PreStatus;

import java.io.File;
import java.util.Scanner;

public class CtrlPreStatus {
    public String readStatus() {
        Scanner in = new Scanner(System.in);
        PreStatus errors = new PreStatus();
        while (true) {
            String s = in.nextLine();
            if (s.length() == 0) {
                return null;
            }
            if (s.compareTo("meh") == 0) return "src/Files/profFicheiro";
            if (isValidPath(s)) return s;
            else {
                errors.invalidChoice();
            }
        }
    }


    public static boolean isValidPath(String path) {
        File temp = new File(path);
        boolean exists = temp.exists();
        System.out.println("Temp file exists : " + exists);
        return  exists;
    }
}
