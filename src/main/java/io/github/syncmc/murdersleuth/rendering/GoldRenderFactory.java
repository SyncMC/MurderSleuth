package io.github.syncmc.murdersleuth.rendering;

import io.github.syncmc.murdersleuth.utils.MurderSleuthUtils;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GoldRenderFactory implements IRenderFactory<EntityItem>
{
    public final MurderSleuthUtils murderSleuthUtils;
    public GoldRenderFactory(MurderSleuthUtils murderSleuthUtils)
    {
        this.murderSleuthUtils = murderSleuthUtils;
    }
    
    @Override
    public Render<? super EntityItem> createRenderFor(RenderManager manager)
    {
        return new RenderGold(manager, MurderSleuthUtils.mc.getRenderItem(), murderSleuthUtils);
    }
}
