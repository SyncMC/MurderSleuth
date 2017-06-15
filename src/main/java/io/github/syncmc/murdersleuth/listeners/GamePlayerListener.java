package io.github.syncmc.murdersleuth.listeners;

import java.util.UUID;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.enums.PlayerRole;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.MurdererAcquiredBowEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.MurdererKilledEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.NewDetectiveDetectedEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.NewMurdererDetectedEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.NonMurdererAcquiredBowEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.NonMurdererKilledEvent;
import io.github.syncmc.murdersleuth.events.GamePlayerEvent.DetectiveKilledEvent;
import io.github.syncmc.murdersleuth.util.ChatHelper;
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.PlayerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GamePlayerListener
{
    @SubscribeEvent
    public void onNewDetectiveDetected(final NewDetectiveDetectedEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setPlayerRole(PlayerRole.DETECTIVE).setHasBow(true);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addDetectiveMessage();
    }

    @SubscribeEvent
    public void onNewMurdererDetected(final NewMurdererDetectedEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setPlayerRole(PlayerRole.MURDERER);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addMurdererMessage();
    }

    @SubscribeEvent
    public void onNonMurdererAcquiredBow(final NonMurdererAcquiredBowEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setHasBow(true);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addNonMurdererWithBowMessage(clientPlayerUUID);
    }

    @SubscribeEvent
    public void onMurdererAcquiredBow(final MurdererAcquiredBowEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setHasBow(true);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addMurdererWithBowMessage();
    }

    @SubscribeEvent
    public void onNonMurdererKilled(final NonMurdererKilledEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setPlayerRole(PlayerRole.DEAD).setHasBow(false);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addNonMurdererKilledMessage(clientPlayerUUID);
    }

    @SubscribeEvent
    public void onDetectiveKilled(final DetectiveKilledEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setPlayerRole(PlayerRole.DEAD).setHasBow(false);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addDetectiveKilledMessage(clientPlayerUUID);
    }

    @SubscribeEvent
    public void onMurdererKilled(final MurdererKilledEvent event)
    {
        final UUID clientPlayerUUID = event.getClientPlayerUUID();
        final PlayerData clientPlayerData = event.getClientPlayerData();

        clientPlayerData.setPlayerRole(PlayerRole.DEAD).setHasBow(false);
        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
        ChatHelper.getInstance().addMurdererKilledMessage(clientPlayerUUID);
        GameHelper.getInstance().clearTrackedPlayerData();
        ChatHelper.getInstance().addGameText(GameString.PLAYER_DATA_CLEARED.getGameText());
    }
}
