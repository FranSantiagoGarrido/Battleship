package com.codeoftheweb.salvo.src;

public enum ShipType {
    Carrier(5),
    Battleship(4),
    Submarine(3),
    Destroyer(3),
    Patrolboat(2);

    private final int lenght;

    ShipType(int lenght) {
        this.lenght = lenght;
    }

    public int getLenght() {
        return this.lenght;
    }
}
