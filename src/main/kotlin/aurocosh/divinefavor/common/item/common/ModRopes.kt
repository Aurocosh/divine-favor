package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.entity.rope.*
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.rope.ItemRope

object ModRopes {
    lateinit var rope_luminous: ModItem
    lateinit var rope_barrier: ModItem
    lateinit var rope_explosive: ModItem
    lateinit var rope_glowing: ModItem
    lateinit var rope_guide: ModItem
    lateinit var rope_inert: ModItem
    lateinit var rope_teleporting: ModItem

    // New fields

    fun preInit() {
        rope_luminous = object : ItemRope<EntityRopeLuminousNode>("rope_luminous", "ropes/luminous", EntityRopeLuminousNode::class.java) {}
        rope_barrier = object : ItemRope<EntityRopeBarrierNode>("rope_barrier", "ropes/barrier", EntityRopeBarrierNode::class.java) {}
        rope_explosive = object : ItemRope<EntityRopeExplosiveNode>("rope_explosive", "ropes/explosive", EntityRopeExplosiveNode::class.java) {}
        rope_glowing = object : ItemRope<EntityRopeGlowingNode>("rope_glowing", "ropes/glowing", EntityRopeGlowingNode::class.java) {}
        rope_guide = object : ItemRope<EntityRopeGuideNode>("rope_guide", "ropes/guide", EntityRopeGuideNode::class.java) {}
        rope_inert = object : ItemRope<EntityRopeInertNode>("rope_inert", "ropes/inert", EntityRopeInertNode::class.java) {}
        rope_teleporting = object : ItemRope<EntityRopeTeleportingNode>("rope_teleporting", "ropes/teleporting", EntityRopeTeleportingNode::class.java) {}

        // New instances
    }

    fun init() {
    }
}