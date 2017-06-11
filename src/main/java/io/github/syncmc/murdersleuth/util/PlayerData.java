package io.github.syncmc.murdersleuth.util;

import io.github.syncmc.murdersleuth.enums.PlayerRole;
import net.minecraft.util.ResourceLocation;

public class PlayerData
{
    private String playerName;
    private ResourceLocation locationSkin;
    private PlayerRole playerRole = PlayerRole.NONE;
    private boolean hasBow;
    
    public PlayerData() {}
    public PlayerData(String playerName, ResourceLocation locationSkin, PlayerRole playerRole, boolean hasBow)
    {
        this.setPlayerName(playerName);
        this.setLocationSkin(locationSkin);
        this.setPlayerRole(playerRole);
        this.setHasBow(hasBow);
    }
    
    public String getPlayerName()
    {
        return this.playerName;
    }
    
    public PlayerData setPlayerName(String playerName)
    {
        this.playerName = playerName;
        return this;
    }
    
    public ResourceLocation getLocationSkin()
    {
        return this.locationSkin;
    }
    
    public PlayerData setLocationSkin(ResourceLocation locationSkin)
    {
        this.locationSkin = locationSkin;
        return this;
    }
    
    public PlayerRole getPlayerRole()
    {
        return this.playerRole;
    }
    
    public PlayerData setPlayerRole(PlayerRole playerRole)
    {
        this.playerRole = playerRole;
        return this;
    }
    
    public boolean hasBow()
    {
        return this.hasBow;
    }
    
    public PlayerData setHasBow(boolean hasBow)
    {
        this.hasBow = hasBow;
        return this;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof PlayerData)
        {
            PlayerData playerData = (PlayerData) obj;
            return playerData.getPlayerRole() == this.getPlayerRole() && playerData.hasBow == this.hasBow();
        }
        
        return super.equals(obj);
    }
}
