package aurocosh.divinefavor.client.render.entity;

import aurocosh.divinefavor.client.render.RenderMinionZombie;
import aurocosh.divinefavor.client.render.RenderSpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball;
import aurocosh.divinefavor.common.entity.minions.MinionZombie;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ModRendering {

    public static void preInit() {

    }

    public static void init() {
        RenderManager manager = Minecraft.getMinecraft().getRenderManager();
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        manager.entityRenderMap.put(EntityStoneball.class, new RenderSnowball<EntityStoneball>(manager, ModItems.stoneball, itemRenderer));
        manager.entityRenderMap.put(EntitySpellArrow.class, new RenderSpellArrow(manager));
        manager.entityRenderMap.put(MinionZombie.class, new RenderMinionZombie(manager));
    }
}