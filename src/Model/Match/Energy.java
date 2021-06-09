package Model.Match;

public class Energy {
    private double energy; // Has to be in this range [0, 100]
    private double decreaseRate; // Normal value: 1
    // decreaseRate is the amount of energy that drops per minute
    // decreaseRate is linear

    public Energy(double energy) {
        if (energy >= 100) this.energy = 100;
        else if (energy <= 0) this.energy = 0;
        else this.energy = energy;
        this.decreaseRate = 1;
    }

    public Energy(double energy, double decreaseRate) {
        this.energy = energy;
        this.decreaseRate = decreaseRate;
    }

    public double getEnergy() {
        return this.energy;
    }

    public void setEnergy(double energy) {
        if (energy >= 100) this.energy = 100;
        else if (energy <= 0) this.energy = 0;
        else this.energy = energy;
    }

    public double getDecreaseRate() {
        return decreaseRate;
    }

    public void setDecreaseRate(double decreaseRate) {
        this.decreaseRate = decreaseRate;
    }

    public Energy clone() {
        return new Energy(energy, decreaseRate);
    }

    public void decrease() {
        this.energy -= decreaseRate;
    }
}
