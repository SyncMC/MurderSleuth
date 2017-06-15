package io.github.syncmc.murdersleuth.events;

import java.util.UUID;

import io.github.syncmc.murdersleuth.util.PlayerData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GamePlayerEvent extends Event
{
    private final UUID clientPlayerUUID;
    private final PlayerData clientPlayerData;

    public GamePlayerEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
    {
        this.clientPlayerUUID = clientPlayerUUID;
        this.clientPlayerData = clientPlayerData;
    }

    /**
     * Gets the {@link UUID} of the {@link AbstractClientPlayer} involved in this
     * event.
     * 
     * @return the {@link UUID}
     */
    public UUID getClientPlayerUUID()
    {
        return this.clientPlayerUUID;
    }

    /**
     * Gets the {@link PlayerData} of the {@link AbstractClientPlayer} involved in
     * this event.
     * 
     * @return the {@link PlayerData}
     */
    public PlayerData getClientPlayerData()
    {
        return this.clientPlayerData;
    }

    @SideOnly(Side.CLIENT)
    public static class NewDetectiveDetectedEvent extends GamePlayerEvent
    {
        public NewDetectiveDetectedEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class NewMurdererDetectedEvent extends GamePlayerEvent
    {
        public NewMurdererDetectedEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class NonMurdererAcquiredBowEvent extends GamePlayerEvent
    {
        public NonMurdererAcquiredBowEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class MurdererAcquiredBowEvent extends GamePlayerEvent
    {
        public MurdererAcquiredBowEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class NonMurdererKilledEvent extends GamePlayerEvent
    {
        public NonMurdererKilledEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class DetectiveKilledEvent extends GamePlayerEvent
    {
        public DetectiveKilledEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class MurdererKilledEvent extends GamePlayerEvent
    {
        public MurdererKilledEvent(final UUID clientPlayerUUID, final PlayerData clientPlayerData)
        {
            super(clientPlayerUUID, clientPlayerData);
        }
    }
}
