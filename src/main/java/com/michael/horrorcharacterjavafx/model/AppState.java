package com.michael.horrorcharacterjavafx.model;

public class AppState {
    private static HorrorCharacter selectedCharacter;
    private static HorrorCharacter updatedSelectedCharacter;

    public static boolean safeToSwitch = false;

    public static HorrorCharacter getSelectedCharacter() {
        return selectedCharacter;
    }
    public static HorrorCharacter getUpdatedSelectedCharacter() {
        return updatedSelectedCharacter;
    }

    public static void setSelectedCharacter(HorrorCharacter character) {
        selectedCharacter = character;
    }
    public static void setUpdatedSelectedCharacter(HorrorCharacter character) {
        updatedSelectedCharacter = character;
    }
}
