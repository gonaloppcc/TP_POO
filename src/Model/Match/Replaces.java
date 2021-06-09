package Model.Match;

public class Replaces {
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
