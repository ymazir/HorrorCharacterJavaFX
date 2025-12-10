package com.michael.horrorcharacterjavafx.model;

/**
 * AppState class to manage the state of the application.
 * It holds the selected HorrorCharacter and a flag to indicate if it's safe to switch characters.
 */
public class AppState {
    /** Selected HorrorCharacter in the application */
    private static HorrorCharacter selectedCharacter;
    /** Updated HorrorCharacter after modifications */
    private static HorrorCharacter updatedSelectedCharacter;


    /**
     * Flag to indicate if it's safe to switch characters
     */
    public static HorrorCharacter getSelectedCharacter() {
        return selectedCharacter;
    }
    /**
     * Flag to indicate if it's safe to switch characters
     */
    public static HorrorCharacter getUpdatedSelectedCharacter() {
        return updatedSelectedCharacter;
    }
    /**
     * Set the selected HorrorCharacter in the application
     */
    public static void setSelectedCharacter(HorrorCharacter character) {
        selectedCharacter = character;
    }
    /**
     * Set the updated HorrorCharacter after modifications
     */
    public static void setUpdatedSelectedCharacter(HorrorCharacter character) {
        updatedSelectedCharacter = character;
    }
}
