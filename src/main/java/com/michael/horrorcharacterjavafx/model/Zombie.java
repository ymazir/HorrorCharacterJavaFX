package com.michael.horrorcharacterjavafx.model;

import java.time.LocalDate;

public class Zombie extends HorrorCharacter {


    /**     * Constructor for Zombie class.
     *
     * @param name            Name of the Zombie
     * @param health          Health of the Zombie
     * @param vulnerabilities Array of Vulnerabilities of the Zombie
     * @param dateOfBirth     Date of Birth of the Zombie
     */
    public Zombie(String name, int health, Vulnerability[] vulnerabilities, LocalDate dateOfBirth) {
        super(name, health, vulnerabilities, dateOfBirth);
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