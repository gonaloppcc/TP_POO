package Model;

/**
 * When a line of one file is invalid.
 */
public class NotValidException extends Exception {
    NotValidException(String player){
        super("Jogador inválido" + player);
    }
}
