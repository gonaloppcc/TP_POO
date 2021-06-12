package Model;

/**
 * When a line of one input is invalid.
 */

public class InvalidLineExcpetion extends Exception {
    InvalidLineExcpetion(String player){
        super("Jogador inválido" + player);
    }
    public InvalidLineExcpetion(){
        super();
    }
}
