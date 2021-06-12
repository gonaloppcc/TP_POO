package Model.Match;

import java.io.Serializable;

/**
 * Class that stores when a player is replaced by other.
 */
public class Replaces implements Serializable {
    int out;
    int in;

    /*--------------- Constructor  ----------------*/

    public Replaces(int out, int in) {
        this.out = out;
        this.in = in;
    }

    /*---------------------- Other methods ---------------*/

    @Override
    public String toString() {
        return "Replaces{" +
                "out=" + out +
                ", in=" + in +
                '}';
    }
}
