package io.github.syncmc.murdersleuth.render;

import io.github.syncmc.murdersleuth.enums.GoldView;
import io.github.syncmc.murdersleuth.util.GameHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;

public class RenderGold extends RenderEntityItem
{
    public RenderGold(final RenderManager renderManagerIn, final RenderItem renderItemIn)
    {
        super(renderManagerIn, renderItemIn);
    }

    @Override
    public void doRender(final EntityItem entity, final double x, final double y, final double z, final float entityYaw,
            final float partialTicks)
    {
        if (GameHelper.getInstance().goldView == GoldView.ALL || entity.getEntityItem().getItem() != Items.GOLD_INGOT)
        {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
}
