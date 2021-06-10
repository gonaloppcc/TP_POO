package View;

public class PreStatus {
    public void welcomeAndLoad(){
        System.out.println("Hello, should we load a previous game or create a new one?");
        System.out.println("To load, write a path to the file with temas and games");
        System.out.println("To a new one, press enter");
    }
    public void start(boolean load){
        if (load) System.out.println("Has been loaded sucessfully");
        else System.out.printf("Let's create a new one");
    }
    public void invalidChoice(){
        System.out.println("Please enter a valid path ");
    }
}
