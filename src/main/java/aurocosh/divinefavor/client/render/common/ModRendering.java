package aurocosh.divinefavor.client.render.common;

import aurocosh.divinefavor.client.render.entity.RenderMinionHusk;
import aurocosh.divinefavor.client.render.entity.RenderMinionSkeleton;
import aurocosh.divinefavor.client.render.entity.RenderMinionStray;
import aurocosh.divinefavor.client.render.entity.RenderMinionZombie;
import aurocosh.divinefavor.client.render.projectile.RenderSpellArrow;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderCreeper;
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
        manager.entityRenderMap.put(MinionHusk.class, new RenderMinionHusk(manager));
        manager.entityRenderMap.put(MinionSkeleton.class, new RenderMinionSkeleton(manager));
        manager.entityRenderMap.put(MinionStray.class, new RenderMinionStray(manager));
        manager.entityRenderMap.put(MinionCreeper.class, new RenderCreeper(manager));
    }
}