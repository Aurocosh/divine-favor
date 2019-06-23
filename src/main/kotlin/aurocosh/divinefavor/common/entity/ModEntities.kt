package aurocosh.divinefavor.common.entity

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.entity.minions.*
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf
import aurocosh.divinefavor.common.entity.projectile.*
import aurocosh.divinefavor.common.entity.rope.*
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {
    private var nextId = 0

    fun preInit() {
        registerModEntity(EntityStoneball::class.java, "stoneball", 256, 10, true)

        registerModEntity(EntityClimbingArrow::class.java, "climbing", 256, 1, true)
        registerModEntity(EntitySpellArrow::class.java, "spell_arrow", 256, 1, true)
        registerModEntity(EntitySpookyArrow::class.java, "spooky", 256, 1, true)
        registerModEntity(EntityIceArrow::class.java, "ice_arrow", 256, 1, true)

        registerModEntity(EntityMinionBlaze::class.java, "minion_blaze", 80, 3, false)
        registerModEntity(EntityMinionCaveSpider::class.java, "minion_cave_spider", 80, 3, false)
        registerModEntity(EntityMinionCreeper::class.java, "minion_creeper", 80, 3, false)
        registerModEntity(EntityMinionHusk::class.java, "minion_husk", 80, 3, false)
        registerModEntity(EntityMinionSkeleton::class.java, "minion_skeleton", 80, 3, false)
        registerModEntity(EntityMinionSpider::class.java, "minion_spider", 80, 3, false)
        registerModEntity(EntityMinionStray::class.java, "minion_stray", 80, 3, false)
        registerModEntity(EntityMinionZombie::class.java, "minion_zombie", 80, 3, false)

        registerModEntity(EntityDirewolf::class.java, "direwolf", 80, 3, false)

        registerModEntity(EntityRopeBarrierNode::class.java, "rope_barrier", 64, 1, true)
        registerModEntity(EntityRopeExplosiveNode::class.java, "rope_explosive", 64, 1, true)
        registerModEntity(EntityRopeGlowingNode::class.java, "rope_glowing", 64, 1, true)
        registerModEntity(EntityRopeGuideNode::class.java, "rope_guide", 64, 1, true)
        registerModEntity(EntityRopeInertNode::class.java, "rope_inert", 64, 1, false)
        registerModEntity(EntityRopeLuminousNode::class.java, "rope_luminous", 64, 1, true)
        registerModEntity(EntityRopeTeleportingNode::class.java, "rope_teleporting", 64, 1, true)

        registerModEntity(EntityPing::class.java, "ping", 64, 1, false)
    }

    private fun registerModEntity(entityClass: Class<out Entity>, entityName: String, trackingRange: Int, updateFrequency: Int, sendsVelocityUpdates: Boolean) {
        val name = ResourceNamer.getFullName("entity", entityName)
        EntityRegistry.registerModEntity(name, entityClass, entityName, nextId++, DivineFavor, trackingRange, updateFrequency, sendsVelocityUpdates)
    }
}