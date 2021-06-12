package Controller;

import View.PreStatusView;

import java.io.File;
import java.util.Scanner;

/**
 * This class is user to load a correct path to a file.
 * It checks if the file exist.
 */
public class CtrlPreStatus {

    /**
     * Returns a String with a path to a file that exists.
     * @return String with path
     */
    public String readStatus() {
        Scanner in = new Scanner(System.in);
        PreStatusView errors = new PreStatusView();
        while (true) {
            String s = in.nextLine();
            if (s.length() == 0) {
                return null;
            }
            if (s.compareTo("default") == 0) return "src/Files/profFicheiro";
            if (isValidPath(s)) {
                return s;
            }
            else {
                errors.invalidPath();
            }
        }
    }


    public static boolean isValidPath(String path) {
        File temp = new File(path);
        boolean exists = temp.exists();
        if (exists) PreStatusView.validPath(path);
        return  exists;
    }
}
