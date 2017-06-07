package io.github.syncmc.murdersleuth.listeners;

import io.github.syncmc.murdersleuth.handlers.KeybindHandler;
import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputListener
{
    public final MurderSleuthUtils murderSleuthUtils;
    public KeyInputListener(MurderSleuthUtils murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    public enum PlayerView
    {
        ALL, ALIVE, INNOCENTS, HOLDING_BOWS, INNOCENTS_HOLDING_BOWS, DETECTIVE, MURDERER;
        private static final PlayerView[] VALUES = values();
        
        public PlayerView next()
        {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }
    }
    
    public enum GoldView
    {
        ALL, NONE;
        private static final GoldView[] VALUES = values();
        
        public GoldView next()
        {
            return VALUES[(this.ordinal() + 1) % VALUES.length];
        }
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeybindHandler.showWeaponHolders.isPressed())
        {
            murderSleuthUtils.addMurdMsg();
            murderSleuthUtils.addDetMsg();
            murderSleuthUtils.addInnoBowMsgs();
        }
        
        if (KeybindHandler.clearWeaponHolders.isPressed())
        {
            murderSleuthUtils.addMsg(murderSleuthUtils.clearWeaponHolders());
        }
        
        if (KeybindHandler.rotatePlayerView.isPressed())
        {
            murderSleuthUtils.rotatePlayerView();
        }
        
        if (KeybindHandler.toggleGoldView.isPressed())
        {
            murderSleuthUtils.toggleGoldView();
        }
    }
}
