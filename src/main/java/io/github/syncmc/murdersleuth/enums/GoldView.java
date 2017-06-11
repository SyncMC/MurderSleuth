package io.github.syncmc.murdersleuth.enums;

public enum GoldView
{
    ALL, NONE;
    private static final GoldView[] VALUES = values();
    
    public GoldView next()
    {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }
}
