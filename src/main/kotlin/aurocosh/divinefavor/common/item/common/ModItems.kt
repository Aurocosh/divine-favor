package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.ItemIceArrow
import aurocosh.divinefavor.common.item.ItemMilkyApple
import aurocosh.divinefavor.common.item.ItemStoneball
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.base.ModItemArrow
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDagger
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDaggerAwakened
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder
import aurocosh.divinefavor.common.item.gems.ItemInviteMarker
import aurocosh.divinefavor.common.item.gems.ItemMarkedGlass
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.gems.storage_gem.ItemStorageGem
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.tools.ItemBanishingWand
import aurocosh.divinefavor.common.item.tools.ItemBoneKey
import aurocosh.divinefavor.common.item.tools.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.item.Item
import java.util.*

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

    lateinit var spell_blade_green: ModItem
    lateinit var spell_blade_red: ModItem

    lateinit var spell_pick_blue: ModItem
    lateinit var spell_pick_orange: ModItem

    val markedGlasses: MutableList<ItemMarkedGlass> = ArrayList()

    // New fields
    lateinit var ice_arrow: ModItemArrow

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

        spell_blade_green = ItemSpellBlade("spell_blade_green", "spell_blade_green/model", ConstMainTabOrder.SWORDS, ConfigItem.spellBladeGreen, Item.ToolMaterial.IRON)
        spell_blade_red = ItemSpellBlade("spell_blade_red", "spell_blade_red/model", ConstMainTabOrder.SWORDS, ConfigItem.spellBladeRed, Item.ToolMaterial.IRON)

        spell_pick_blue = ItemSpellBlade("spell_pick_blue", "spell_pick_blue/model", ConstMainTabOrder.PICKS, ConfigItem.spellBladeGreen, Item.ToolMaterial.IRON)
        spell_pick_orange = ItemSpellBlade("spell_pick_orange", "spell_pick_orange/model", ConstMainTabOrder.PICKS, ConfigItem.spellBladeRed, Item.ToolMaterial.IRON)

        for (spirit in ModMappers.spirits.values)
            markedGlasses.add(ItemMarkedGlass(spirit, ConstGemTabOrder.MARKED_GLASS))

        // New instances
        ice_arrow = ItemIceArrow()
    }

    fun init() {
    }
}