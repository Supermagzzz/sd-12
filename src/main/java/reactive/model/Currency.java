package reactive.model;

public enum Currency {
    RUB(1),
    USD(10),
    EUR(100);

    private double value;

    Currency(int value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
