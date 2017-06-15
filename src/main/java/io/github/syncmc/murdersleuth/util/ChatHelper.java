package io.github.syncmc.murdersleuth.util;

import java.util.List;
import java.util.UUID;

import io.github.syncmc.murdersleuth.enums.GameString;
import net.minecraft.util.text.TextComponentString;

public class ChatHelper
{
    private ChatHelper()
    {
    }

    private static class InstanceHolder
    {
        public static final ChatHelper INSTANCE = new ChatHelper();
    }

    public static ChatHelper getInstance()
    {
        return InstanceHolder.INSTANCE;
    }

    public void addMessage(final String msg)
    {
        if (GameHelper.getInstance().localPlayer != null)
        {
            GameHelper.getInstance().localPlayer.sendMessage(new TextComponentString(msg));
        }
    }

    public void addGameText(final GameText gameText, final boolean stripEnd)
    {
        this.addMessage(stripEnd ? gameText.getStrippedFormattedText() : gameText.getFormattedText());
    }

    public void addGameText(final GameText gameText)
    {
        this.addGameText(gameText, false);
    }

    public void addDetectiveMessage()
    {
        UUID playerUUID = GameHelper.getInstance().getDetectiveUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_DETECTIVE.getGameText());
        }
        else
        {
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            this.addGameText(new GameText("AQUA + BOLD, GRAY, AQUA, GRAY", playerData.getPlayerName() + " ", "is the ",
                    "detective", "!"));
        }
    }

    public void addMurdererMessage()
    {
        UUID playerUUID = GameHelper.getInstance().getMurdererUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_MURDERER.getGameText());
        }
        else
        {
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            this.addGameText(playerData.hasBow()
                    ? new GameText("RED + BOLD, GRAY, RED, GRAY", playerData.getPlayerName() + " ", "is the ",
                            "murderer ", "and has a bow!")
                    : new GameText("RED + BOLD, GRAY, RED, GRAY", playerData.getPlayerName() + " ", "is the ",
                            "murderer", "!"));
        }
    }

    public void addNonMurdererWithBowMessage(final UUID playerUUID)
    {
        PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
        switch (playerData.getPlayerRole())
        {
        case UNKNOWN:
            this.addGameText(new GameText("YELLOW + BOLD, GRAY, YELLOW, GRAY", playerData.getPlayerName() + " ",
                    "is an ", "unknown ", "player with a bow!"));
            break;
        case INNOCENT:
            this.addGameText(new GameText("GREEN + BOLD, GRAY, GREEN, GRAY", playerData.getPlayerName() + " ", "is an ",
                    "innocent ", "player with a bow!"));
            break;
        default:
            break;
        }
    }

    public void addNonMurdererWithBowMessages()
    {
        List<UUID> nonMurderersWithBowsUUIDList = GameHelper.getInstance().getNonMurderersWithBowsUUIDList();
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

    public void addMurdererWithBowMessage()
    {
        UUID playerUUID = GameHelper.getInstance().getMurdererUUID();
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_MURDERER.getGameText());
        }
        else
        {
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            this.addGameText(new GameText("GRAY, RED, GRAY, RED + BOLD, GRAY", "The ", "murderer", ", ",
                    playerData.getPlayerName(), ", has acquired a bow!"));
        }
    }

    public void addNonMurdererKilledMessage(final UUID playerUUID)
    {
        PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
        switch (playerData.getPlayerRole())
        {
        case UNKNOWN:
            this.addGameText(new GameText("YELLOW + BOLD, GRAY", playerData.getPlayerName() + " ", "has been killed!"));
            break;
        case INNOCENT:
            this.addGameText(new GameText("GREEN + BOLD, GRAY", playerData.getPlayerName() + " ", "has been killed!"));
            break;
        default:
            break;
        }
    }

    public void addDetectiveKilledMessage(final UUID playerUUID)
    {
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_DETECTIVE.getGameText());
        }
        else
        {
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            this.addGameText(new GameText("AQUA + BOLD, GRAY", playerData.getPlayerName() + " ", "has been killed!"));
        }
    }

    public void addMurdererKilledMessage(final UUID playerUUID)
    {
        if (playerUUID == null)
        {
            this.addGameText(GameString.NO_MURDERER.getGameText());
        }
        else
        {
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            this.addGameText(new GameText("RED + BOLD, GRAY", playerData.getPlayerName() + " ", "has been killed!"));
        }
    }
}
