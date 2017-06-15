package io.github.syncmc.murdersleuth.listeners;

import java.util.Map.Entry;
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
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ClientTickListener
{
    @SubscribeEvent
    public void onClientTick(final ClientTickEvent event)
    {
        if (event.phase == Phase.END && (GameHelper.getInstance().localWorld = Minecraft.getMinecraft().world) != null
                && (GameHelper.getInstance().localPlayer = Minecraft.getMinecraft().player) != null)
        {
            for (EntityPlayer player : GameHelper.getInstance().localWorld.playerEntities)
            {
                if (player instanceof AbstractClientPlayer)
                {
                    AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
                    UUID clientPlayerUUID = GameHelper.getUUID(clientPlayer);
                    PlayerData clientPlayerData = GameHelper.getInstance().getPlayerData(clientPlayerUUID);
                    PlayerRole clientPlayerRole = clientPlayerData.getPlayerRole();
                    BlockPos clientPlayerPos = clientPlayer.getPosition();

                    if (clientPlayerRole == PlayerRole.NONE)
                    {
                        clientPlayerData = new PlayerData(clientPlayer.getName(), PlayerRole.UNKNOWN, false,
                                clientPlayerPos);
                        clientPlayerRole = clientPlayerData.getPlayerRole();
                        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
                    }

                    if (!clientPlayerData.getLastPos().equals(clientPlayerPos))
                    {
                        clientPlayerPos = clientPlayer.getPosition();
                        clientPlayerData.setLastPos(clientPlayerPos);
                        GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
                    }

                    if (clientPlayerRole == PlayerRole.UNKNOWN || clientPlayerRole == PlayerRole.INNOCENT
                            || (clientPlayerRole == PlayerRole.MURDERER && !clientPlayerData.hasBow()))
                    {
                        UUID murdererUUID = GameHelper.getInstance().getMurdererUUID();

                        ItemStack itemStack = GameHelper.getHeldItemStack(clientPlayer);
                        Item item = itemStack.getItem();

                        if (clientPlayerRole == PlayerRole.UNKNOWN && murdererUUID == null && item == Items.IRON_SWORD
                                && itemStack.getDisplayName()
                                        .equals(GameString.KNIFE_NAME.getGameText().getStrippedFormattedText())
                                && GameHelper.getLore(itemStack).equals(GameHelper.getInstance().knifeLore))
                        {
                            MinecraftForge.EVENT_BUS
                                    .post(new NewMurdererDetectedEvent(clientPlayerUUID, clientPlayerData));
                        }
                        else if (item == Items.BOW && itemStack.getDisplayName()
                                .equals(GameString.BOW_NAME.getGameText().getStrippedFormattedText()))
                        {
                            if ((clientPlayerRole == PlayerRole.UNKNOWN || clientPlayerRole == PlayerRole.INNOCENT)
                                    && GameHelper.getInstance().getDetectiveUUID() != clientPlayerUUID
                                    && GameHelper.getLore(itemStack).equals(GameHelper.getInstance().detBowLore))
                            {
                                MinecraftForge.EVENT_BUS
                                        .post(new NewDetectiveDetectedEvent(clientPlayerUUID, clientPlayerData));
                            }
                            else if (GameHelper.getLore(itemStack).equals(GameHelper.getInstance().innoBowLore))
                            {
                                if ((clientPlayerRole == PlayerRole.UNKNOWN || clientPlayerRole == PlayerRole.INNOCENT)
                                        && !clientPlayerData.hasBow())
                                {
                                    MinecraftForge.EVENT_BUS
                                            .post(new NonMurdererAcquiredBowEvent(clientPlayerUUID, clientPlayerData));
                                }
                                else if (clientPlayerRole == PlayerRole.MURDERER)
                                {
                                    MinecraftForge.EVENT_BUS
                                            .post(new MurdererAcquiredBowEvent(clientPlayerUUID, clientPlayerData));
                                }
                            }
                        }

                        if (clientPlayerRole == PlayerRole.UNKNOWN && murdererUUID != null)
                        {
                            clientPlayerData.setPlayerRole(PlayerRole.INNOCENT);
                            GameHelper.getInstance().setPlayerData(clientPlayerUUID, clientPlayerData);
                        }
                    }
                }
            }

            for (Entry<UUID, PlayerData> entry : GameHelper.getInstance().getTrackedPlayerData().entrySet())
            {
                UUID playerUUID = entry.getKey();
                PlayerData playerData = entry.getValue();

                if (playerData.getPlayerRole() != PlayerRole.DEAD)
                {
                    EntityPlayer foundPlayer = GameHelper.getInstance().localWorld.getPlayerEntityByUUID(playerUUID);

                    BlockPos playerPos = playerData.getLastPos();
                    int playerX = playerPos.getX();
                    int playerY = playerPos.getY();
                    int playerZ = playerPos.getZ();

                    if (foundPlayer == null)
                    {
                        if (GameHelper.getInstance().localPlayer.isInRangeToRender3d(playerX, playerY, playerZ))
                        {
                            switch (playerData.getPlayerRole())
                            {
                            case UNKNOWN:
                            case INNOCENT:
                                MinecraftForge.EVENT_BUS.post(new NonMurdererKilledEvent(playerUUID, playerData));
                                break;
                            case DETECTIVE:
                                MinecraftForge.EVENT_BUS.post(new DetectiveKilledEvent(playerUUID, playerData));
                                break;
                            case MURDERER:
                                MinecraftForge.EVENT_BUS.post(new MurdererKilledEvent(playerUUID, playerData));
                                break;
                            default:
                                break;
                            }
                        }
                    }
                    else if (foundPlayer.isInvisible())
                    {
                        boolean hasInvisibilityPotionEffect = false;
                        for (PotionEffect potionEffect : foundPlayer.getActivePotionEffects())
                        {
                            if (!hasInvisibilityPotionEffect && potionEffect.getPotion() == MobEffects.INVISIBILITY
                                    && potionEffect.getDuration() > GameHelper.MAX_INVISIBILITY_TICKS)
                            {
                                hasInvisibilityPotionEffect = true;
                                switch (playerData.getPlayerRole())
                                {
                                case UNKNOWN:
                                case INNOCENT:
                                    MinecraftForge.EVENT_BUS.post(new NonMurdererKilledEvent(playerUUID, playerData));
                                    break;
                                case DETECTIVE:
                                    MinecraftForge.EVENT_BUS.post(new DetectiveKilledEvent(playerUUID, playerData));
                                    break;
                                case MURDERER:
                                    MinecraftForge.EVENT_BUS.post(new MurdererKilledEvent(playerUUID, playerData));
                                    break;
                                default:
                                    break;
                                }

                                break;
                            }
                        }

                        if (!hasInvisibilityPotionEffect)
                        {
                            switch (playerData.getPlayerRole())
                            {
                            case UNKNOWN:
                            case INNOCENT:
                                MinecraftForge.EVENT_BUS.post(new NonMurdererKilledEvent(playerUUID, playerData));
                                break;
                            case DETECTIVE:
                                MinecraftForge.EVENT_BUS.post(new DetectiveKilledEvent(playerUUID, playerData));
                                break;
                            case MURDERER:
                                MinecraftForge.EVENT_BUS.post(new MurdererKilledEvent(playerUUID, playerData));
                                break;
                            default:
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
