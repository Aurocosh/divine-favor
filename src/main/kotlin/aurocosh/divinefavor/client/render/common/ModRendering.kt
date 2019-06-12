package aurocosh.divinefavor.client.render.common

import aurocosh.divinefavor.client.render.RenderPing
import aurocosh.divinefavor.client.render.entity.RenderDirewolf
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionHusk
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionSkeleton
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionStray
import aurocosh.divinefavor.client.render.entity.minion.RenderMinionZombie
import aurocosh.divinefavor.client.render.projectile.RenderIceArrow
import aurocosh.divinefavor.client.render.projectile.RenderSpellArrow
import aurocosh.divinefavor.client.render.rope.*
import aurocosh.divinefavor.common.entity.EntityPing
import aurocosh.divinefavor.common.entity.minions.*
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf
import aurocosh.divinefavor.common.entity.projectile.EntityIceArrow
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball
import aurocosh.divinefavor.common.entity.rope.*
import aurocosh.divinefavor.common.item.common.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.RenderCreeper
import net.minecraft.client.renderer.entity.RenderSnowball

object ModRendering {

    fun preInit() {}

    fun init() {
        val manager = Minecraft.getMinecraft().renderManager

        manager.entityRenderMap[EntitySpellArrow::class.java] = RenderSpellArrow(manager)
        manager.entityRenderMap[EntityIceArrow::class.java] = RenderIceArrow(manager)

        manager.entityRenderMap[EntityMinionZombie::class.java] = RenderMinionZombie(manager)
        manager.entityRenderMap[EntityMinionHusk::class.java] = RenderMinionHusk(manager)
        manager.entityRenderMap[EntityMinionSkeleton::class.java] = RenderMinionSkeleton(manager)
        manager.entityRenderMap[EntityMinionStray::class.java] = RenderMinionStray(manager)
        manager.entityRenderMap[EntityMinionCreeper::class.java] = RenderCreeper(manager)

        manager.entityRenderMap[EntityDirewolf::class.java] = RenderDirewolf(manager)

        manager.entityRenderMap[EntityRopeBarrierNode::class.java] = RenderRopeBarrierNode(manager)
        manager.entityRenderMap[EntityRopeExplosiveNode::class.java] = RenderRopeExplosiveNode(manager)
        manager.entityRenderMap[EntityRopeGlowingNode::class.java] = RenderRopeGlowingNode(manager)
        manager.entityRenderMap[EntityRopeGuideNode::class.java] = RenderRopeGuideNode(manager)
        manager.entityRenderMap[EntityRopeInertNode::class.java] = RenderRopeInertNode(manager)
        manager.entityRenderMap[EntityRopeLuminousNode::class.java] = RenderRopeLuminousNode(manager)
        manager.entityRenderMap[EntityRopeTeleportingNode::class.java] = RenderRopeTeleportingNode(manager)

        manager.entityRenderMap[EntityPing::class.java] = RenderPing(manager)

        val itemRenderer = Minecraft.getMinecraft().renderItem
        manager.entityRenderMap[EntityStoneball::class.java] = RenderSnowball<EntityStoneball>(manager, ModItems.stoneball, itemRenderer)
    }
}