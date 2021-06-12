package View;

/**
 * Class used to print information related to the loading of file.
 */

public class PreStatusView {

    public void welcomeAndLoad(){
        System.out.println("Hello, should we load a previous game or create a new one?");
        System.out.println("To load, write a path to the file with temas and games");
        System.out.println("To a new one, press enter");
    }

    public void invalidPath(){
        System.out.println("Please enter a valid path ");
    }

    public static void validPath(String path){
        System.out.println("The file exists "+ path);
    }
}
