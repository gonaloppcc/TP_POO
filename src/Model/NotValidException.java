package Model;

public class NotValidException extends Exception {
    NotValidException(String player){
        super("Jogador inválido" + player);
    }
}
