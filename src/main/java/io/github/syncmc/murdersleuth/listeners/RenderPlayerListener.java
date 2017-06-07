package io.github.syncmc.murdersleuth.listeners;

import java.util.UUID;

import io.github.syncmc.murdersleuth.listeners.KeyInputListener.PlayerView;
import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayerListener
{
    public final MurderSleuthUtils murderSleuthUtils;
    public RenderPlayerListener(MurderSleuthUtils murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event)
    {
        EntityPlayer player = event.getEntityPlayer();
        UUID uuid = MurderSleuthUtils.getPlayerUUID(player);
        
        if (murderSleuthUtils.playerView == PlayerView.MURDERER)
        {
            if (murderSleuthUtils.murdUUID != uuid)
            {
                event.setCanceled(true);
            }
        }
        else if (murderSleuthUtils.playerView != PlayerView.ALL)
        {
            if (player.isPlayerSleeping())
            {
                event.setCanceled(true);
            }
            else if (murderSleuthUtils.playerView == PlayerView.DETECTIVE)
            {
                if (murderSleuthUtils.detUUID != uuid)
                {
                    event.setCanceled(true);
                }
            }
            else if (murderSleuthUtils.playerView == PlayerView.HOLDING_BOWS)
            {
                if (!murderSleuthUtils.innosWithBows.containsKey(uuid) && murderSleuthUtils.detUUID != uuid)
                {
                    event.setCanceled(true);
                }
            }
            else if (murderSleuthUtils.playerView == PlayerView.INNOCENTS_HOLDING_BOWS)
            {
                if (!murderSleuthUtils.innosWithBows.containsKey(uuid) || murderSleuthUtils.murdUUID == uuid)
                {
                    event.setCanceled(true);
                }
            }
            else if (murderSleuthUtils.playerView == PlayerView.INNOCENTS)
            {
                if (murderSleuthUtils.murdUUID == uuid || murderSleuthUtils.detUUID == uuid)
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}
