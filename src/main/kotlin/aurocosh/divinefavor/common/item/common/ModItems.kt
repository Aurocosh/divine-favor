package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.entity.rope.*
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.*
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDagger
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDaggerAwakened
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch
import aurocosh.divinefavor.common.item.rope.ItemRope
import aurocosh.divinefavor.common.item.storage_gem.ItemStorageGem
import aurocosh.divinefavor.common.item.talisman_container.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_container.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow

object ModItems {
    lateinit var banishing_wand: ModItem
    lateinit var bone_dagger: ModItem
    lateinit var bone_dagger_awakened: ModItem
    lateinit var bone_key: ModItem
    lateinit var contract_binder: ModItem
    lateinit var grimoire: ModItem
    lateinit var milky_apple: ModItem
    lateinit var mystic_architect_stick: ModItem
    lateinit var ritual_pouch: ModItem
    lateinit var spell_bow: ModItem
    lateinit var stoneball: ModItem

    lateinit var invite_gem: ModItem
    lateinit var invite_pebble: ModItem
    lateinit var storage_gem: ModItem
    lateinit var warp_gem: ModItem
    lateinit var warp_pebble: ModItem

    lateinit var rope_barrier: ModItem
    lateinit var rope_explosive: ModItem
    lateinit var rope_glowing: ModItem
    lateinit var rope_guide: ModItem
    lateinit var rope_inert: ModItem
    lateinit var rope_teleporting: ModItem

    lateinit var spell_blade_green : ModItem
    lateinit var spell_blade_red : ModItem

    fun preInit() {
        banishing_wand = ItemBanishingWand()
        bone_dagger = ItemBoneDagger()
        bone_dagger_awakened = ItemBoneDaggerAwakened()
        bone_key = ItemBoneKey()
        contract_binder = ItemContractBinder()
        grimoire = ItemGrimoire()
        invite_gem = ItemInviteMarker("invite_gem", true)
        invite_pebble = ItemInviteMarker("invite_pebble", false)
        milky_apple = ItemMilkyApple()
        mystic_architect_stick = ItemMysticArchitectStick()
        ritual_pouch = ItemRitualPouch()
        spell_bow = ItemSpellBow()
        stoneball = ItemStoneball()
        storage_gem = ItemStorageGem()
        warp_gem = ItemWarpMarker("warp_gem", true)
        warp_pebble = ItemWarpMarker("warp_pebble", false)

        rope_barrier = object : ItemRope<EntityRopeBarrierNode>("rope_barrier", "ropes/barrier", EntityRopeBarrierNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}
        rope_explosive = object : ItemRope<EntityRopeExplosiveNode>("rope_explosive", "ropes/explosive", EntityRopeExplosiveNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}
        rope_glowing = object : ItemRope<EntityRopeGlowingNode>("rope_glowing", "ropes/glowing", EntityRopeGlowingNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}
        rope_guide = object : ItemRope<EntityRopeGuideNode>("rope_guide", "ropes/guide", EntityRopeGuideNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}
        rope_inert = object : ItemRope<EntityRopeInertNode>("rope_inert", "ropes/inert", EntityRopeInertNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}
        rope_teleporting = object : ItemRope<EntityRopeTeleportingNode>("rope_teleporting", "ropes/teleporting", EntityRopeTeleportingNode::class.java, EntityRopeNodeBase.ROPE_LENGTH) {}

        spell_blade_green = ItemSpellBlade("spell_blade_green", "spell_blade_green/model", ConstMainTabOrder.DAGGERS)
        spell_blade_red = ItemSpellBlade("spell_blade_red", "spell_blade_red/model", ConstMainTabOrder.DAGGERS)
    }

    fun init() {
    }
}