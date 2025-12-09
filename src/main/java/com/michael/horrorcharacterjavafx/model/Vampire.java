package com.michael.horrorcharacterjavafx.model;

import java.time.LocalDate;

public class Vampire extends HorrorCharacter implements Transformable {
    /**
     * Indicates whether the vampire is transformed into a bat or not.
     */
    private boolean transformed = false;

    /**
     * Constructor for HorrorCharacter.
     *
     * @param name            HorrorCharacters name
     * @param health          HorrorCharacters health
     * Vampires are vulnerable to SUNLIGHT, HOLY_WATER, and POISON by default.
     */
    public Vampire(String name, int health) {
        super(name, health, new Vulnerability[]{Vulnerability.SUNLIGHT, Vulnerability.HOLY_WATER, Vulnerability.POISON}, LocalDate.of(0001, 1, 1));
    }

    /**
     * Override the attack method to provide a specific attack message for Vampire.
     * If transformed, the vampire attacks as a bat, else it attacks as a vampire.
     */
    @Override
    public void attack() {
        if (transformed) {
            System.out.println(getName() + " attacks as a bat!");
        } else {
            System.out.println(getName() + " attacks as a vampire!");
        }
    }

    /**
     * Override the flee method to provide a specific flee message for Vampire.
     */
    @Override
    public void flee() {
        System.out.println(getName() + " hides in the night to flee!");
    }

    /**
     * Override the toString method to provide a string representation of the Vampire.
     * @return String representation of the Vampire
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
     * Vampires transform into bats and can transform back into vampires.
     */
    public void transform() {
        if (transformed) {
            System.out.println(getName() + " is transforming back into a vampire!");
            transformed = false;
        } else {
            transformed = true;
            System.out.println(getName() + " is transforming into a bat");
        }
    }
}