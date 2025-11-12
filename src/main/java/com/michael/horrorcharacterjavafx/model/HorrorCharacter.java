package com.michael.horrorcharacterjavafx.model;

public class HorrorCharacter {

    /**
     * Attributes of HorrorCharacter
     * String name - name of the character
     * int health - health of the character
     * Vulnerability[] vulnerabilities - array of vulnerabilities of the character
     */
    private String name;
    private int health = 100;
    private Vulnerability[] vulnerabilities;


    /**
     * Constructor for HorrorCharacter.
     * @param name HorrorCharacters name
     * @param health HorrorCharacters health
     * @param vulnerabilities HorrorCharacters vulnerabilities
     */
    public HorrorCharacter(String name, int health, Vulnerability[] vulnerabilities) {
        this.name = name;
        this.health = health;
        this.vulnerabilities = vulnerabilities;
    }


    /**
     * Default attack method that prints out an attack message.
     */
    public void attack() {
        System.out.println(name + " attacks!");
    }


    /**
     * Default flee method that prints out a flee message.
     */
    public void flee() {
        System.out.println(name + " flees!");
    }


    /**
     * Get the name of the character.
     * @return the name of the character
     */
    public String getName() {
        return name;
    }

    /**
     * Get the health of the character.
     * @return the health of the character
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the vulnerabilities of the character.
     * @return the vulnerabilities of the character
     */
    public Vulnerability[] getVulnerabilities() {
        return vulnerabilities;
    }

    /**
     * Set the name of the character.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the health of the character.
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Set the vulnerabilities of the character.
     * @param vulnerabilities the vulnerabilities to set
     */
    public void setVulnerabilities(Vulnerability[] vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    /**
     * String representation of all the attributes.
     * @return String representation of all the attributes
     */

    @Override
    public String toString() {
        String complete = "Name: " + name + ", Health: " + health + ", Vulnerabilities: ";
        for (Vulnerability v : vulnerabilities) {
            complete = complete + v + " ";

        }
        return complete;
    }
}