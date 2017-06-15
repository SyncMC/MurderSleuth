package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.enums.GameString;
import io.github.syncmc.murdersleuth.util.ChatHelper;
import io.github.syncmc.murdersleuth.util.GameHelper;
import io.github.syncmc.murdersleuth.util.KeybindHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyInputListener
{
    @SubscribeEvent
    public void onKeyInput(final KeyInputEvent event)
    {
        if (KeybindHandler.showPlayerData.isPressed())
        {
            ChatHelper.getInstance().addMurdererMessage();
            ChatHelper.getInstance().addDetectiveMessage();
            ChatHelper.getInstance().addNonMurdererWithBowMessages();
        }

        if (KeybindHandler.clearPlayerData.isPressed())
        {
            GameHelper.getInstance().clearTrackedPlayerData();
            ChatHelper.getInstance().addGameText(GameString.PLAYER_DATA_CLEARED.getGameText());
        }

        if (KeybindHandler.rotatePlayerView.isPressed())
        {
            GameHelper.getInstance().rotatePlayerView();
        }

        if (KeybindHandler.toggleGoldView.isPressed())
        {
            GameHelper.getInstance().toggleGoldView();
        }
    }
}
