package aurocosh.divinefavor.client.render.common;

import aurocosh.divinefavor.client.render.entity.RenderDirewolf;
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionHusk;
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionSkeleton;
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionStray;
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionZombie;
import aurocosh.divinefavor.client.render.projectile.RenderSpellArrow;
import aurocosh.divinefavor.client.render.rope.RenderRopeExplosiveNode;
import aurocosh.divinefavor.client.render.rope.RenderRopeGlowingNode;
import aurocosh.divinefavor.client.render.rope.RenderRopeInertNode;
import aurocosh.divinefavor.client.render.rope.RenderRopeTeleportingNode;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball;
import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeGlowingNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeInertNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeTeleportingNode;
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
        manager.entityRenderMap.put(EntityDirewolf.class, new RenderDirewolf(manager));

        manager.entityRenderMap.put(EntityRopeExplosiveNode.class, new RenderRopeExplosiveNode(manager));
        manager.entityRenderMap.put(EntityRopeInertNode.class, new RenderRopeInertNode(manager));
        manager.entityRenderMap.put(EntityRopeGlowingNode.class, new RenderRopeGlowingNode(manager));
        manager.entityRenderMap.put(EntityRopeTeleportingNode.class, new RenderRopeTeleportingNode(manager));
    }
}