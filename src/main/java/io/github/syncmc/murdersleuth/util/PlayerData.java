package io.github.syncmc.murdersleuth.util;

import io.github.syncmc.murdersleuth.enums.PlayerRole;
import net.minecraft.util.math.BlockPos;

public class PlayerData
{
    private String playerName;
    private PlayerRole playerRole = PlayerRole.NONE;
    private boolean hasBow;
    private BlockPos lastPos = BlockPos.ORIGIN;

    public PlayerData()
    {
    }

    public PlayerData(final String playerName, final PlayerRole playerRole, final boolean hasBow,
            final BlockPos lastPos)
    {
        this.playerName = playerName;
        this.playerRole = playerRole;
        this.hasBow = hasBow;
        this.lastPos = lastPos;
    }

    public String getPlayerName()
    {
        return this.playerName;
    }

    public PlayerData setPlayerName(final String playerName)
    {
        this.playerName = playerName;
        return this;
    }

    public PlayerRole getPlayerRole()
    {
        return this.playerRole;
    }

    public PlayerData setPlayerRole(final PlayerRole playerRole)
    {
        this.playerRole = playerRole;
        return this;
    }

    public boolean hasBow()
    {
        return this.hasBow;
    }

    public PlayerData setHasBow(final boolean hasBow)
    {
        this.hasBow = hasBow;
        return this;
    }

    public BlockPos getLastPos()
    {
        return this.lastPos;
    }

    public PlayerData setLastPos(final BlockPos lastPos)
    {
        this.lastPos = lastPos;
        return this;
    }

    public PlayerData setLastPos(final double lastX, final double lastY, final double lastZ)
    {
        this.lastPos = new BlockPos(lastX, lastY, lastZ);
        return this;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (super.equals(obj))
        {
            return true;
        }

        if (obj instanceof PlayerData)
        {
            PlayerData playerData = (PlayerData) obj;
            return playerData.getPlayerRole() == this.getPlayerRole() && playerData.hasBow == this.hasBow();
        }

        return false;
    }
}
