package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GameStartListener
{
    private final GameHelper gameHelper;
    public GameStartListener(GameHelper gameHelper)
    {
        this.gameHelper = gameHelper;
    }
    
    @SubscribeEvent
    public void onServerChat(ClientChatReceivedEvent event)
    {
        ITextComponent component = event.getMessage();
        if (component.getFormattedText().equals(GameString.GAME_START.getGameText().getFormattedText()))
        {
            gameHelper.clearTrackedPlayerData();
            component.appendSibling(new TextComponentString("\n" + GameString.PLAYER_DATA_CLEARED.getGameText().getFormattedText()));
        }
    }
}
