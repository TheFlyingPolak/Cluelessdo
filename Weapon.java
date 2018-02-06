package com.cluelessdo;

/*
 * The Weapon class is a subclass of Token and specifies the type of weapon this token should represent.
 *
 * @author Jakub Gajewski
 */

public class Weapon extends Token{
    private WeaponTypes type;

    public Weapon(int x, int y, WeaponTypes type){
        super(x, y);
        this.type = type;
    }

    public WeaponTypes getType() {
        return type;
    }
}