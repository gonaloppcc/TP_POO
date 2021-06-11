package Model.Match;

import java.io.Serializable;

public class Replaces implements Serializable {
    int out;
    int in;

    public Replaces(int out, int in) {
        this.out = out;
        this.in = in;
    }

    @Override
    public String toString() {
        return "Replaces{" +
                "out=" + out +
                ", in=" + in +
                '}';
    }
}
