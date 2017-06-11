package io.github.syncmc.murdersleuth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.enums.GoldView;
import io.github.syncmc.murdersleuth.enums.PlayerRole;
import io.github.syncmc.murdersleuth.enums.PlayerView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class GameHelper
{
    public final ChatHelper chatHelper;
    
    public static final Minecraft mc = Minecraft.getMinecraft();
    public EntityPlayerSP player;
    
    public final NBTTagList knifeLore = new NBTTagList();
    public final NBTTagList detBowLore = new NBTTagList();
    public final NBTTagList innoBowLore = new NBTTagList();
    
    public final Map<UUID, PlayerData> trackedPlayerData = new ConcurrentHashMap<>();
    
    public PlayerView playerView = PlayerView.ALL;
    public GoldView goldView = GoldView.ALL;
    
    public GameHelper()
    {
        this.chatHelper = new ChatHelper(this);
        
        knifeLore.appendTag(new NBTTagString(GameString.KNIFE_LORE_ONE.getGameText().getStrippedFormattedText()));
        knifeLore.appendTag(new NBTTagString(GameString.KNIFE_LORE_TWO.getGameText().getStrippedFormattedText()));

        detBowLore.appendTag(new NBTTagString(GameString.DETECTIVE_BOW_LORE_ONE.getGameText().getStrippedFormattedText()));
        detBowLore.appendTag(new NBTTagString(GameString.DETECTIVE_BOW_LORE_TWO.getGameText().getStrippedFormattedText()));

        innoBowLore.appendTag(new NBTTagString(GameString.NON_DETECTIVE_BOW_LORE_ONE.getGameText().getStrippedFormattedText()));
        innoBowLore.appendTag(new NBTTagString(GameString.NON_DETECTIVE_BOW_LORE_TWO.getGameText().getStrippedFormattedText()));
    }
    
    @Nonnull
    public static UUID getUUID(EntityPlayer player)
    {
        return EntityPlayer.getUUID(player.getGameProfile());
    }
    
    @Nonnull
    public static ItemStack getHeldItemStack(EntityPlayer player)
    {
        return player.inventory.getCurrentItem();
    }
    
    @Nonnull
    public static NBTTagList getLore(ItemStack itemStack)
    {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt != null)
        {
            NBTTagCompound display = nbt.getCompoundTag("display");
            if (display != null)
            {
                NBTTagList lore = display.getTagList("Lore", 8);
                if (lore != null)
                {
                    return lore;
                }
            }
        }
        
        return new NBTTagList();
    }
    
    @Nonnull
    public Map<UUID, PlayerData> getTrackedPlayerData()
    {
        return this.trackedPlayerData;
    }
    
    @Nonnull
    public boolean isPlayerDataTracked(UUID playerUUID)
    {
        return this.trackedPlayerData.containsKey(playerUUID);
    }
    
    @Nonnull
    public PlayerData getPlayerData(UUID playerUUID)
    {
        return this.isPlayerDataTracked(playerUUID) ? this.trackedPlayerData.get(playerUUID) : new PlayerData();
    }
    
    public void setPlayerData(UUID playerUUID, PlayerData playerData)
    {
        this.trackedPlayerData.put(playerUUID, playerData);
    }
    
    public void clearTrackedPlayerData()
    {
        this.trackedPlayerData.clear();
    }
    
    @Nullable
    public UUID getMurdererUUID()
    {
        for (Map.Entry<UUID, PlayerData> entry : this.trackedPlayerData.entrySet())
        {
            UUID playerUUID = entry.getKey();
            PlayerData playerData = entry.getValue();
            PlayerRole playerRole = playerData.getPlayerRole();
            
            if (playerRole == PlayerRole.MURDERER)
            {
                return playerUUID;
            }
        }
        
        return null;
    }
    
    @Nullable
    public UUID getDetectiveUUID()
    {
        for (Map.Entry<UUID, PlayerData> entry : this.trackedPlayerData.entrySet())
        {
            UUID playerUUID = entry.getKey();
            PlayerData playerData = entry.getValue();
            PlayerRole playerRole = playerData.getPlayerRole();
            
            if (playerRole == PlayerRole.DETECTIVE)
            {
                return playerUUID;
            }
        }
        
        return null;
    }
    
    @Nonnull
    public List<UUID> getNonMurderersWithBowsUUIDList()
    {
        List<UUID> nonMurderersWithBowsUUIDList = new ArrayList<>();
        for (Map.Entry<UUID, PlayerData> entry : this.trackedPlayerData.entrySet())
        {
            UUID playerUUID = entry.getKey();
            PlayerData playerData = entry.getValue();
            PlayerRole playerRole = playerData.getPlayerRole();
            
            if ((playerRole == PlayerRole.UNKNOWN || playerRole == PlayerRole.INNOCENT) && playerData.hasBow())
            {
                nonMurderersWithBowsUUIDList.add(playerUUID);
            }
        }
        
        return nonMurderersWithBowsUUIDList;
    }
    
    public void rotatePlayerView()
    {
        this.playerView = this.playerView.next();
        switch (this.playerView)
        {
        case ALL:
            chatHelper.addGameText(GameString.PLAYER_VIEW_ALL.getGameText());
            break;
        case ALIVE:
            chatHelper.addGameText(GameString.PLAYER_VIEW_ALIVE.getGameText());
            break;
        case NON_MURDERERS:
            chatHelper.addGameText(GameString.PLAYER_VIEW_NON_MURDERERS.getGameText());
            break;
        case HOLDING_BOWS:
            chatHelper.addGameText(GameString.PLAYER_VIEW_HOLDING_BOWS.getGameText());
            break;
        case NON_MURDERERS_HOLDING_BOWS:
            chatHelper.addGameText(GameString.PLAYER_VIEW_NON_MURDERERS_HOLDING_BOWS.getGameText());
            break;
        case DETECTIVE:
            chatHelper.addGameText(GameString.PLAYER_VIEW_DETECTIVE.getGameText());
            break;
        case MURDERER:
            chatHelper.addGameText(GameString.PLAYER_VIEW_MURDERER.getGameText());
            break;
        }
    }
    
    public void toggleGoldView()
    {
        this.goldView = this.goldView.next();
        switch (this.goldView)
        {
        case ALL:
            chatHelper.addGameText(GameString.GOLD_VIEW_ALL.getGameText());
            break;
        case NONE:
            chatHelper.addGameText(GameString.GOLD_VIEW_NONE.getGameText());
            break;
        }
    }
}
