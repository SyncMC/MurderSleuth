package io.github.syncmc.murdersleuth.enums;

import io.github.syncmc.murdersleuth.util.GameText;

public enum GameString
{
    GAME_START("YELLOW, RED, YELLOW", "The game starts in ", "1", " second!"),

    KNIFE_NAME("GREEN", "Knife"),
    KNIFE_LORE_ONE("GRAY", "Use your Knife to kill"),
    KNIFE_LORE_TWO("GRAY", "players."),

    BOW_NAME("GREEN", "Bow"),
    DETECTIVE_BOW_LORE_ONE("GRAY", "Use your Bow to kill the"),
    DETECTIVE_BOW_LORE_TWO("GRAY", "murderer."),
    NON_DETECTIVE_BOW_LORE_ONE("GRAY", "Use this bow to defend"),
    NON_DETECTIVE_BOW_LORE_TWO("GRAY", "yourself from the Murderer!"),

    NO_MURDERER("GRAY", "No murderer found."),
    NO_DETECTIVE("GRAY", "No detective found."),
    NO_NON_MURDERERS_WITH_BOWS("GRAY", "No non-murderers with bows found."),

    PLAYER_DATA_CLEARED("GRAY", "All player data has been cleared."),

    PLAYER_VIEW_ALL("GRAY", "[1/7] All players are now shown."),
    PLAYER_VIEW_ALIVE("GRAY", "[2/7] Only alive players are now shown."),
    PLAYER_VIEW_NON_MURDERERS("GRAY", "[3/7] Only non-murderers are now shown."),
    PLAYER_VIEW_HOLDING_BOWS("GRAY", "[4/7] Only players holding bows are now shown."),
    PLAYER_VIEW_NON_MURDERERS_HOLDING_BOWS("GRAY", "[5/7] Only non-murderers holding bows are now shown."),
    PLAYER_VIEW_DETECTIVE("GRAY", "[6/7] Only the detective is now shown."),
    PLAYER_VIEW_MURDERER("GRAY", "[7/7] Only the murderer is now shown."),

    GOLD_VIEW_ALL("GRAY", "[1/2] Gold ingots are now shown."),
    GOLD_VIEW_NONE("GRAY", "[2/2] Gold ingots are no longer shown.");

    private final GameText gameText;

    private GameString(final String commaStyleStrings, final String... messages)
    {
        this.gameText = new GameText(commaStyleStrings, messages);
    }

    public GameText getGameText()
    {
        return this.gameText;
    }
}
