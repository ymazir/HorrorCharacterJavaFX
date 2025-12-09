package com.michael.horrorcharacterjavafx.model;

import java.time.LocalDate;

public class Zombie extends HorrorCharacter {


    /**
     * Constructor for HorrorCharacter.
     *
     * @param name   HorrorCharacters name
     * @param health HorrorCharacters health
     *               Zombies are vulnerable to FIRE, SUNLIGHT, and HOLY_WATER by default.
     */
    public Zombie(String name, int health) {
        super(name, health, new Vulnerability[]{Vulnerability.FIRE, Vulnerability.SUNLIGHT, Vulnerability.HOLY_WATER}, LocalDate.of(0001, 1, 1));
    }

    /**
     * Override the attack method to provide a specific attack message for Zombie.
     */
    @Override
    public void attack() {
        System.out.println(getName() + " lunges forward to attack!");
    }

    /**
     * Override the flee method to provide a specific flee message for Zombie.
     */
    @Override
    public void flee() {
        System.out.println(getName() + " shambles away to flee!");
    }

    /**
     * Override the toString method to provide a string representation of the Zombie.
     *
     * @return String representation of the Zombie
     */
    @Override
    public String toString() {
        String complete = "Name: " + this.getName() + ", Health: " + this.getHealth() + ", Vulnerabilities: ";
        for (Vulnerability v : this.getVulnerabilities()) {
            complete = complete + v + " ";

        }
        return complete;
    }
}