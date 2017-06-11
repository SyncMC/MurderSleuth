package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.handlers.KeybindHandler;
import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputListener
{
    private final GameHelper gameHelper;
    public KeyInputListener(GameHelper gameHelper)
    {
        this.gameHelper = gameHelper;
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeybindHandler.showPlayerData.isPressed())
        {
            gameHelper.chatHelper.addMurdererMessage();
            gameHelper.chatHelper.addDetectiveMessage();
            gameHelper.chatHelper.addNonMurdererWithBowMessages();
        }
        
        if (KeybindHandler.clearPlayerData.isPressed())
        {
            gameHelper.clearTrackedPlayerData();
            gameHelper.chatHelper.addGameText(GameString.PLAYER_DATA_CLEARED.getGameText());
        }
        
        if (KeybindHandler.rotatePlayerView.isPressed())
        {
            gameHelper.rotatePlayerView();
        }
        
        if (KeybindHandler.toggleGoldView.isPressed())
        {
            gameHelper.toggleGoldView();
        }
    }
}
