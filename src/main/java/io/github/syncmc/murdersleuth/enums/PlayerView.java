package io.github.syncmc.murdersleuth.enums;

public enum PlayerView
{
    ALL, ALIVE, NON_MURDERERS, HOLDING_BOWS, NON_MURDERERS_HOLDING_BOWS, DETECTIVE, MURDERER;
    private static final PlayerView[] VALUES = values();
    
    public PlayerView next()
    {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}
