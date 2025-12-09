package com.michael.horrorcharacterjavafx.model;

import java.time.LocalDate;

public class Werewolf extends HorrorCharacter implements Transformable{

    /**
     * Indicates whether the werewolf is transformed into a wolf or not.
     */
    private boolean transformed = false;

    /**
     * Constructor for Werewolf.
     * @param name Werewolf's name
     * @param health Werewolf's health
     * Werewolves are vulnerable to FIRE, SILVER, HOLY_WATER, and POISON by default.
     */
    public Werewolf(String name, int health) {
        super(name, health, new Vulnerability[] {Vulnerability.FIRE, Vulnerability.SILVER, Vulnerability.HOLY_WATER, Vulnerability.POISON}, LocalDate.of(0001, 1, 1));

    }

    /**
     * Override the attack method to provide a specific attack message for Werewolf.
     * If transformed, the werewolf attacks as a wolf, else it attacks as a werewolf.
     */
    @Override
    public void attack() {
        if (transformed) {
            System.out.println(getName() + " attacks as a human!");
        } else {
            System.out.println(getName() + " attacks as a werewolf!");
        }
    }

    /**
     * Override the flee method to provide a specific flee message for Werewolf.
     */
    @Override
    public void flee() {
        System.out.println(getName() + " runs away on all fours to flee!");
    }

    /**
     * Override the toString method to provide a string representation of the Werewolf.
     * @return String representation of the Werewolf
     */
    @Override
    public String toString() {
        String complete = "Name: " + this.getName() + ", Health: " + this.getHealth() + ", Vulnerabilities: ";
        for (Vulnerability v : this.getVulnerabilities()) {
            complete = complete + v + " ";

        }
        return complete;
    }


    /**
     * Implement the transform method from Transformable interface.
     * Werewolves transform into wolves and can transform back into werewolves.
     */
    public void transform() {
        if (transformed) {
            System.out.println(getName() + " is transforming back into a werewolf!");
            transformed = false;
        } else {
            transformed = true;
            System.out.println(getName() + " is transforming into a human");
        }
    }

}