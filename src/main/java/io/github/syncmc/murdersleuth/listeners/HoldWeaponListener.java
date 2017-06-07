package io.github.syncmc.murdersleuth.listeners;

import java.util.List;
import java.util.UUID;

import io.github.syncmc.murdersleuth.utils.GameText;
import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class HoldWeaponListener
{
    public final MurderSleuthUtils murderSleuthUtils;
    public HoldWeaponListener(MurderSleuthUtils murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        if (event.phase == Phase.END && !murderSleuthUtils.clearing)
        {
            murderSleuthUtils.assignVars();
            if (murderSleuthUtils.varsNotNull())
            {
                List<Entity> entities = murderSleuthUtils.world.getLoadedEntityList();
                for (Entity entity : entities)
                {
                    if (entity instanceof EntityPlayer)
                    {
                        EntityPlayer otherPlayer = (EntityPlayer) entity;
                        UUID uuid = MurderSleuthUtils.getPlayerUUID(otherPlayer);
                        
                        ItemStack itemStack = otherPlayer.inventory.getCurrentItem();
                        Item item = itemStack.getItem();
                        
                        if (murderSleuthUtils.murdUUID != uuid && item == Items.IRON_SWORD && itemStack.getDisplayName().equals(GameText.KNIFE_NAME.getStrippedFormattedText()) && murderSleuthUtils.getLore(itemStack).equals(murderSleuthUtils.knifeLore))
                        {
                            murderSleuthUtils.murderer = otherPlayer;
                            murderSleuthUtils.murdUUID = uuid;
                            murderSleuthUtils.addMurdMsg();
                        }
                        else if (item == Items.BOW && itemStack.getDisplayName().equals(GameText.BOW_NAME.getStrippedFormattedText()))
                        {
                            if (murderSleuthUtils.detUUID != uuid && murderSleuthUtils.getLore(itemStack).equals(murderSleuthUtils.detBowLore))
                            {
                                murderSleuthUtils.detective = otherPlayer;
                                murderSleuthUtils.detUUID = uuid;
                                murderSleuthUtils.addDetMsg();
                            }
                            else if (!murderSleuthUtils.innosWithBows.containsKey(uuid) && murderSleuthUtils.getLore(itemStack).equals(murderSleuthUtils.innoBowLore))
                            {
                                murderSleuthUtils.innosWithBows.put(uuid, otherPlayer);
                                if (murderSleuthUtils.murdUUID == uuid)
                                {
                                    murderSleuthUtils.addMurdBowMsg();
                                }
                                else
                                {
                                    murderSleuthUtils.addInnoBowMsg(uuid);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
