package io.github.syncmc.murdersleuth.util;

import java.util.List;
import java.util.UUID;

import io.github.syncmc.murdersleuth.enums.GameString;
import net.minecraft.util.text.TextComponentString;

public class ChatHelper
{
    private final GameHelper gameHelper;
    public ChatHelper(GameHelper gameHelper)
    {
        this.gameHelper = gameHelper;
    }
    
    public void addMessage(String msg)
    {
        if (gameHelper.player != null)
        {
            gameHelper.player.sendMessage(new TextComponentString(msg));
        }
    }
    
    public void addGameText(GameText gameText, boolean stripEnd)
    {
        this.addMessage(stripEnd ? gameText.getStrippedFormattedText() : gameText.getFormattedText());
    }
    
    public void addGameText(GameText gameText)
    {
        this.addGameText(gameText, false);
    }
    
    public void addNonMurdererWithBowMessage(UUID playerUUID)
    {
        PlayerData playerData = gameHelper.getPlayerData(playerUUID);
        switch (playerData.getPlayerRole())
        {
        case INNOCENT:
            this.addGameText(new GameText("GREEN + BOLD, GRAY, GREEN, GRAY", playerData.getPlayerName() + " ", "is an ", "innocent ", "player with a bow!"));
            break;
        case UNKNOWN:
            this.addGameText(new GameText("YELLOW + BOLD, GRAY, YELLOW, GRAY", playerData.getPlayerName() + " ", "is an ", "unknown ", "player with a bow!"));
            break;
        default:
            break;
        }
    }
    
    public void addNonMurdererWithBowMessages()
    {
        List<UUID> nonMurderersWithBowsUUIDList = gameHelper.getNonMurderersWithBowsUUIDList();
        if (nonMurderersWithBowsUUIDList.isEmpty())
        {
            this.addGameText(GameString.NO_NON_MURDERERS_WITH_BOWS.getGameText());
        }
        else
        {
            for (UUID playerUUID : nonMurderersWithBowsUUIDList)
            {
                this.addNonMurdererWithBowMessage(playerUUID);
            }
        }
    }
    
    public void addDetectiveMessage()
    {
        UUID playerUUID = gameHelper.getDetectiveUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_DETECTIVE.getGameText());
        }
        else
        {
            PlayerData playerData = gameHelper.getPlayerData(playerUUID);
            this.addGameText(new GameText("AQUA + BOLD, GRAY, AQUA, GRAY", playerData.getPlayerName() + " ", "is the ", "detective", "!"));
        }
    }
    
    public void addMurdererMessage()
    {
        UUID playerUUID = gameHelper.getMurdererUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_MURDERER.getGameText());
        }
        else
        {
            PlayerData playerData = gameHelper.getPlayerData(playerUUID);
            this.addGameText(playerData.hasBow() ? new GameText("RED + BOLD, GRAY, RED, GRAY", playerData.getPlayerName() + " ", "is the ", "murderer ", "and has a bow!") : new GameText("RED + BOLD, GRAY, RED, GRAY", playerData.getPlayerName() + " ", "is the ", "murderer", "!"));
        }
    }
    
    public void addMurdererWithBowMessage()
    {
        UUID playerUUID = gameHelper.getMurdererUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_MURDERER.getGameText());
        }
        else
        {
            PlayerData playerData = gameHelper.getPlayerData(playerUUID);
            this.addGameText(new GameText("GRAY, RED, GRAY, RED + BOLD, GRAY", "The ", "murderer", ", ", playerData.getPlayerName(), ", has aquired a bow!"));
        }
    }
}
