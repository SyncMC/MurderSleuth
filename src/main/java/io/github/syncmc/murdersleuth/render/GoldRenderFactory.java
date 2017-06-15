package io.github.syncmc.murdersleuth.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GoldRenderFactory implements IRenderFactory<EntityItem>
{
    @Override
    public Render<? super EntityItem> createRenderFor(final RenderManager manager)
    {
        return new RenderGold(manager, Minecraft.getMinecraft().getRenderItem());
    }
}
