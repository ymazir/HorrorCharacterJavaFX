package com.michael.horrorcharacterjavafx.model;

/**
 * Record representing an entry for a Horror Character.
 *
 * @param pid             the unique identifier for the character
 * @param name            the name of the character
 * @param health          the health of the character
 * @param vulnerabilities the vulnerabilities of the character as a string
 * @param dateOfBirth     the date of birth of the character
 * @param subtype         the subtype of the character
 */
public record HorrorCharacterEntry(int pid, String name, int health, String vulnerabilities, String dateOfBirth, String subtype) {}

