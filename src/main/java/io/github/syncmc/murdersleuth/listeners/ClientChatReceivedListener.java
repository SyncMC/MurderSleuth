package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientChatReceivedListener
{
    @SubscribeEvent
    public void onClientChatReceived(final ClientChatReceivedEvent event)
    {
        ITextComponent textComponent = event.getMessage();
        if (textComponent.getFormattedText().equals(GameString.GAME_START.getGameText().getFormattedText()))
        {
            GameHelper.getInstance().clearTrackedPlayerData();
            textComponent.appendSibling(
                    new TextComponentString("\n" + GameString.PLAYER_DATA_CLEARED.getGameText().getFormattedText()));
        }
    }
}
