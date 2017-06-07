package io.github.syncmc.murdersleuth;

import io.github.syncmc.murdersleuth.handlers.KeybindHandler;
import io.github.syncmc.murdersleuth.listeners.GameStartListener;
import io.github.syncmc.murdersleuth.listeners.HoldWeaponListener;
import io.github.syncmc.murdersleuth.listeners.KeyInputListener;
import io.github.syncmc.murdersleuth.listeners.RenderPlayerListener;
import io.github.syncmc.murdersleuth.rendering.GoldRenderFactory;
import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MurderSleuth.MODID, useMetadata = true, clientSideOnly = true)
public class MurderSleuth
{
    public static final String MODID = "murdersleuth";
    public final MurderSleuthUtils murderSleuthUtils = new MurderSleuthUtils();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        KeybindHandler.init();
        RenderingRegistry.registerEntityRenderingHandler(EntityItem.class, new GoldRenderFactory(murderSleuthUtils));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new GameStartListener(murderSleuthUtils));
        MinecraftForge.EVENT_BUS.register(new HoldWeaponListener(murderSleuthUtils));
        MinecraftForge.EVENT_BUS.register(new KeyInputListener(murderSleuthUtils));
        MinecraftForge.EVENT_BUS.register(new RenderPlayerListener(murderSleuthUtils));
    }
}
