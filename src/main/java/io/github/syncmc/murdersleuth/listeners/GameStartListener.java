package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import io.github.syncmc.murdersleuth.utils.GameText;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameStartListener
{
    public final MurderSleuthUtils murderSleuthUtils;
    public GameStartListener(MurderSleuthUtils murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @SubscribeEvent
    public void onServerChat(ClientChatReceivedEvent event)
    {
        ITextComponent component = event.getMessage();
        if (component.getFormattedText().equals(GameText.GAME_START.getFormattedText()))
        {
            component.appendSibling(new TextComponentString("\n" + murderSleuthUtils.clearWeaponHolders()));
        }
    }
}
