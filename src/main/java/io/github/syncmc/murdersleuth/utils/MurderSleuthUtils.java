package io.github.syncmc.murdersleuth.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.syncmc.murdersleuth.listeners.KeyInputListener.GoldView;
import io.github.syncmc.murdersleuth.listeners.KeyInputListener.PlayerView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextComponentString;

public class MurderSleuthUtils
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public EntityPlayerSP player;
    public WorldClient world;
    
    public final NBTTagList knifeLore = new NBTTagList();
    public final NBTTagList detBowLore = new NBTTagList();
    public final NBTTagList innoBowLore = new NBTTagList();
    
    public UUID murdUUID;
    public UUID detUUID;
    
    public EntityPlayer murderer;
    public EntityPlayer detective;
    
    public Map<UUID, EntityPlayer> innosWithBows = new HashMap<UUID, EntityPlayer>();
    
    public boolean clearing;
    
    public PlayerView playerView = PlayerView.ALL;
    public GoldView goldView = GoldView.ALL;
    
    public MurderSleuthUtils()
    {
        knifeLore.appendTag(new NBTTagString(GameText.KNIFE_LORE_ONE.getStrippedFormattedText()));
        knifeLore.appendTag(new NBTTagString(GameText.KNIFE_LORE_TWO.getStrippedFormattedText()));

        detBowLore.appendTag(new NBTTagString(GameText.DETECTIVE_BOW_LORE_ONE.getStrippedFormattedText()));
        detBowLore.appendTag(new NBTTagString(GameText.DETECTIVE_BOW_LORE_TWO.getStrippedFormattedText()));

        innoBowLore.appendTag(new NBTTagString(GameText.INNOCENT_BOW_LORE_ONE.getStrippedFormattedText()));
        innoBowLore.appendTag(new NBTTagString(GameText.INNOCENT_BOW_LORE_TWO.getStrippedFormattedText()));
    }
    
    public static UUID getPlayerUUID(EntityPlayer player)
    {
        return EntityPlayer.getUUID(player.getGameProfile());
    }
    
    public boolean varsNotNull()
    {
        return player != null && world != null;
    }
    
    public void assignVars()
    {
        player = mc.player;
        world = mc.world;
    }
    
    public NBTTagList getLore(ItemStack itemStack)
    {
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt != null) {
            NBTTagCompound display = nbt.getCompoundTag("display");
            if (display != null) {
                NBTTagList lore = display.getTagList("Lore", 8);
                if (lore != null)
                {
                    return lore;
                }
            }
        }
        
        return new NBTTagList();
    }
    
    public void addMsg(String msg)
    {
        if (player != null)
        {
            player.sendMessage(new TextComponentString(msg));
        }
    }
    
    public void addMurdMsg()
    {
        addMsg(murderer == null ? GameText.NO_MURDERER.getFormattedText() : GameText.createITextComponent("RED + BOLD, GRAY, RED, GRAY", murderer.getName() + " ", "is the ", "murderer", "!").getFormattedText());
    }
    
    public void addMurdBowMsg()
    {
        addMsg(GameText.createITextComponent("GRAY, RED, GRAY, RED + BOLD, GRAY", "The ", "murderer", ", ", murderer.getName(), ", has a bow, too!").getFormattedText());
    }
    
    public void addDetMsg()
    {
        addMsg(detective == null ? GameText.NO_DETECTIVE.getFormattedText() : GameText.createITextComponent("AQUA + BOLD, GRAY, AQUA, GRAY", detective.getName() + " ", "is the ", "detective", "!").getFormattedText());
    }
    
    public void addInnoBowMsg(UUID innoUUID)
    {
        addMsg(GameText.createITextComponent("GREEN + BOLD, GRAY, GREEN, GRAY", innosWithBows.get(innoUUID).getName() + " ", "is an ", "innocent ", "player with a bow!").getFormattedText());
    }
    
    public void addInnoBowMsgs()
    {
        if (innosWithBows.isEmpty())
        {
            addMsg(GameText.NO_INNOCENTS_WITH_BOWS.getFormattedText());
        }
        
        for (UUID uuid : innosWithBows.keySet())
        {
            if (murdUUID == uuid)
            {
                addMurdBowMsg();
            }
            else
            {
                addInnoBowMsg(uuid);
            }
        }
    }
    
    public String clearWeaponHolders()
    {
        clearing = true;
        
        murdUUID = null;
        detUUID = null;
        
        murderer = null;
        detective = null;
        
        innosWithBows.clear();
        
        clearing = false;
        return GameText.WEAPON_HOLDERS_CLEARED.getFormattedText();
    }
    
    public void rotatePlayerView()
    {
        playerView = playerView.next();
        switch (playerView)
        {
        case ALL:
            addMsg(GameText.PLAYER_VIEW_ALL.getFormattedText());
            break;
        case ALIVE:
            addMsg(GameText.PLAYER_VIEW_ALIVE.getFormattedText());
            break;
        case INNOCENTS:
            addMsg(GameText.PLAYER_VIEW_INNOCENTS.getFormattedText());
            break;
        case HOLDING_BOWS:
            addMsg(GameText.PLAYER_VIEW_HOLDING_BOWS.getFormattedText());
            break;
        case INNOCENTS_HOLDING_BOWS:
            addMsg(GameText.PLAYER_VIEW_INNOCENTS_HOLDING_BOWS.getFormattedText());
            break;
        case DETECTIVE:
            addMsg(GameText.PLAYER_VIEW_DETECTIVE.getFormattedText());
            break;
        case MURDERER:
            addMsg(GameText.PLAYER_VIEW_MURDERER.getFormattedText());
            break;
        }
    }
    
    public void toggleGoldView()
    {
        goldView = goldView.next();
        switch (goldView)
        {
        case ALL:
            addMsg(GameText.GOLD_VIEW_ALL.getFormattedText());
            break;
        case NONE:
            addMsg(GameText.GOLD_VIEW_NONE.getFormattedText());
            break;
        }
    }
}
