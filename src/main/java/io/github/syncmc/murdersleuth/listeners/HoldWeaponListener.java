package io.github.syncmc.murdersleuth.listeners;

import java.util.Map.Entry;
import java.util.UUID;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.enums.PlayerRole;
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.PlayerData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class HoldWeaponListener
{
    private final GameHelper gameHelper;
    public HoldWeaponListener(GameHelper gameHelper)
    {
        this.gameHelper = gameHelper;
    }
    
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {
        if (event.phase == Phase.END)
        {
            gameHelper.player = GameHelper.mc.player;
            if (gameHelper.player != null)
            {
                EntityPlayer player = event.player;
                if (player instanceof AbstractClientPlayer)
                {
                    AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
                    UUID playerUUID = GameHelper.getUUID(clientPlayer);
                    PlayerData playerData = gameHelper.getPlayerData(playerUUID);
                    PlayerRole playerRole = playerData.getPlayerRole();
                    
                    if (playerRole == PlayerRole.NONE)
                    {
                        PlayerData newPlayerData = new PlayerData(clientPlayer.getName(), clientPlayer.getLocationSkin(), PlayerRole.UNKNOWN, false);
                        playerData = newPlayerData;
                        playerRole = playerData.getPlayerRole();
                        gameHelper.setPlayerData(playerUUID, playerData);
                    }
                    
                    if (playerRole == PlayerRole.UNKNOWN || playerRole == PlayerRole.INNOCENT || (playerRole == PlayerRole.MURDERER && !playerData.hasBow()))
                    {
                        UUID murdererUUID = gameHelper.getMurdererUUID();
                        
                        ItemStack itemStack = GameHelper.getHeldItemStack(clientPlayer);
                        Item item = itemStack.getItem();
                        
                        if (playerRole == PlayerRole.UNKNOWN && murdererUUID == null && item == Items.IRON_SWORD && itemStack.getDisplayName().equals(GameString.KNIFE_NAME.getGameText().getStrippedFormattedText()) && GameHelper.getLore(itemStack).equals(gameHelper.knifeLore))
                        {
                            playerData.setPlayerRole(PlayerRole.MURDERER);
                            gameHelper.setPlayerData(playerUUID, playerData);
                            gameHelper.chatHelper.addMurdererMessage();
                        }
                        else if (item == Items.BOW && itemStack.getDisplayName().equals(GameString.BOW_NAME.getGameText().getStrippedFormattedText()))
                        {
                            if ((playerRole == PlayerRole.UNKNOWN || playerRole == PlayerRole.INNOCENT) && gameHelper.getDetectiveUUID() != playerUUID && GameHelper.getLore(itemStack).equals(gameHelper.detBowLore))
                            {
                                playerData.setPlayerRole(PlayerRole.DETECTIVE).setHasBow(true);
                                gameHelper.setPlayerData(playerUUID, playerData);
                                gameHelper.chatHelper.addDetectiveMessage();
                            }
                            else if (GameHelper.getLore(itemStack).equals(gameHelper.innoBowLore))
                            {
                                if ((playerRole == PlayerRole.UNKNOWN || playerRole == PlayerRole.INNOCENT) && !playerData.hasBow())
                                {
                                    playerData.setHasBow(true);
                                    gameHelper.setPlayerData(playerUUID, playerData);
                                    gameHelper.chatHelper.addNonMurdererWithBowMessage(playerUUID);
                                }
                                else if (playerRole == PlayerRole.MURDERER)
                                {
                                    playerData.setHasBow(true);
                                    gameHelper.setPlayerData(playerUUID, playerData);
                                    gameHelper.chatHelper.addMurdererWithBowMessage();
                                }
                            }
                        }
                        
                        if (playerRole == PlayerRole.UNKNOWN && murdererUUID != null)
                        {
                            playerData.setPlayerRole(PlayerRole.INNOCENT);
                            gameHelper.setPlayerData(playerUUID, playerData);
                        }
                    }
                    
                    for (Entry<UUID, PlayerData> entry : gameHelper.getTrackedPlayerData().entrySet())
                    {
                        UUID trackedPlayerUUID = entry.getKey();
                        EntityPlayer foundPlayer = gameHelper.player.getEntityWorld().getPlayerEntityByUUID(trackedPlayerUUID);
                        
                        if (foundPlayer == null)
                        {
                            PlayerData trackedPlayerData = entry.getValue();
                            trackedPlayerData.setPlayerRole(PlayerRole.DEAD);
                            gameHelper.setPlayerData(trackedPlayerUUID, trackedPlayerData);
                        }
                    }
                }
            }
        }
    }
}
