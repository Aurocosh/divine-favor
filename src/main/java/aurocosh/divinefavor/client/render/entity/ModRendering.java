package aurocosh.divinefavor.client.render.entity;

import aurocosh.divinefavor.common.entity.EntityStoneball;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ModRendering {

    public static void preInit() {

    }

    public static void init() {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        rm.entityRenderMap.put(EntityStoneball.class, new RenderSnowball<EntityStoneball>(rm, ModItems.stoneball, itemRenderer));
    }
}