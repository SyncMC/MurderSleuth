package io.github.syncmc.murdersleuth.listeners;

import java.util.UUID;

import io.github.syncmc.murdersleuth.enums.PlayerRole;
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.PlayerData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PreRenderPlayerListener
{
    @SubscribeEvent
    public void onPreRenderPlayer(final RenderPlayerEvent.Pre event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player instanceof AbstractClientPlayer)
        {
            AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
            UUID playerUUID = GameHelper.getUUID(clientPlayer);
            PlayerData playerData = GameHelper.getInstance().getPlayerData(playerUUID);
            PlayerRole playerRole = playerData.getPlayerRole();

            switch (GameHelper.getInstance().playerView)
            {
            case ALL:
                break;
            case ALIVE:
                if (clientPlayer.isPlayerSleeping())
                {
                    event.setCanceled(true);
                }

                break;
            case NON_MURDERERS:
                if (playerRole == PlayerRole.MURDERER)
                {
                    event.setCanceled(true);
                }

                break;
            case HOLDING_BOWS:
                if (!playerData.hasBow())
                {
                    event.setCanceled(true);
                }

                break;
            case NON_MURDERERS_HOLDING_BOWS:
                if (playerRole == PlayerRole.MURDERER || !playerData.hasBow())
                {
                    event.setCanceled(true);
                }

                break;
            case DETECTIVE:
                if (playerRole != PlayerRole.DETECTIVE)
                {
                    event.setCanceled(true);
                }

                break;
            case MURDERER:
                if (playerRole != PlayerRole.MURDERER)
                {
                    event.setCanceled(true);
                }

                break;
            default:
                break;
            }
        }
    }
}
