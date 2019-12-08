package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.*
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.base.ModItemArrow
import aurocosh.divinefavor.common.item.base.ModItemPickaxe
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDagger
import aurocosh.divinefavor.common.item.bone_dagger.ItemBoneDaggerAwakened
import aurocosh.divinefavor.common.item.compressed_item_drop.ItemCompressedItemsDrop
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder
import aurocosh.divinefavor.common.item.gem_pouch.ItemGemPouch
import aurocosh.divinefavor.common.item.gem_stabilizer.ItemGemStabilizer
import aurocosh.divinefavor.common.item.gems.ItemInviteMarker
import aurocosh.divinefavor.common.item.gems.ItemMarkedGlass
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.gems.storage.ItemStorageGem
import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_pouch.ItemMemoryPouch
import aurocosh.divinefavor.common.item.ritual_pouch.ItemRitualPouch
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.ItemGrimoire
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.ItemSpellBlade
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.ItemSpellBow
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.item.tools.ItemBanishingWand
import aurocosh.divinefavor.common.item.tools.ItemBoneKey
import aurocosh.divinefavor.common.item.tools.ItemEtherealBrush
import aurocosh.divinefavor.common.item.tools.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.item.Item
import java.util.*

object ModItems {
    lateinit var banishing_wand: ModItem
    lateinit var bone_dagger: ModItem
    lateinit var bone_dagger_awakened: ModItem
    lateinit var bone_key: ModItem
    lateinit var contract_binder: ModItem
    lateinit var etherealBrush: ModItem
    lateinit var memory_pouch: ModItem
    lateinit var milky_apple: ModItem
    lateinit var mystic_architect_stick: ModItem
    lateinit var ritual_pouch: ModItem
    lateinit var spell_bow: ModItem
    lateinit var stoneball: ModItem

    lateinit var invite_gem: ModItem
    lateinit var invite_pebble: ModItem
    lateinit var storage_pebble: ModItem

    lateinit var warp_gem: ModItem
    lateinit var warp_pebble: ModItem

    lateinit var stable_warp_gem: ModItem

    lateinit var grimoire_red: ModItem
    lateinit var grimoire_black: ModItem
    lateinit var grimoire_blue: ModItem
    lateinit var grimoire_green: ModItem
    lateinit var grimoire_yellow: ModItem
    lateinit var grimoire_cyan: ModItem
    lateinit var grimoire_purple: ModItem

    lateinit var spell_blade_green: ModItem
    lateinit var spell_blade_red: ModItem

    lateinit var spell_pick_blue: ModItemPickaxe
    lateinit var spell_pick_orange: ModItemPickaxe

    lateinit var goo_vial_small: ItemGooVial
    lateinit var goo_vial_medium: ItemGooVial
    lateinit var goo_vial_big: ItemGooVial


    val markedGlasses: MutableList<ItemMarkedGlass> = ArrayList()

    // New fields
    lateinit var compressed_items_drop: ModItem
    lateinit var stable_storage_pebble: ModItem
    lateinit var stable_warp_pebble: ModItem
    lateinit var stable_invite_pebble: ModItem
    lateinit var stable_invite_gem: ModItem
    lateinit var gem_pouch: ModItem
    lateinit var pebble_stabilizer: ModItem
    lateinit var gem_stabilizer: ModItem
    lateinit var experience_drop: ModItem
    lateinit var memory_drop: ItemMemoryDrop
    lateinit var ice_arrow: ModItemArrow

    fun preInit() {
        banishing_wand = ItemBanishingWand()
        bone_dagger = ItemBoneDagger()
        bone_dagger_awakened = ItemBoneDaggerAwakened()
        bone_key = ItemBoneKey()
        contract_binder = ItemContractBinder()
        etherealBrush = ItemEtherealBrush()
        memory_pouch = ItemMemoryPouch()
        milky_apple = ItemMilkyApple()
        mystic_architect_stick = ItemMysticArchitectStick()
        ritual_pouch = ItemRitualPouch()
        spell_bow = ItemSpellBow()
        stoneball = ItemStoneball()

        storage_pebble = ItemStorageGem("storage_pebble", true, 0, ModSpirits.endererer, 16)
        stable_storage_pebble = ItemStorageGem("stable_storage_pebble", false, ConfigSpell.remoteChest.favorCost, ModSpirits.endererer, 1)

        warp_gem = ItemWarpMarker("warp_gem", true, true, 0, ModSpirits.endererer, 64)
        warp_pebble = ItemWarpMarker("warp_pebble", false, true, 0, ModSpirits.endererer, 64)
        stable_warp_gem = ItemWarpMarker("stable_warp_gem", true, false, ConfigSpell.warpGem.favorCost, ModSpirits.endererer, 1)
        stable_warp_pebble = ItemWarpMarker("stable_warp_pebble", false, false, ConfigSpell.warpPebble.favorCost, ModSpirits.endererer, 1)

        invite_gem = ItemInviteMarker("invite_gem", true, true, 0, ModSpirits.endererer, 64)
        invite_pebble = ItemInviteMarker("invite_pebble", false, true, 0, ModSpirits.endererer, 64)
        stable_invite_gem = ItemInviteMarker("stable_invite_gem", true, false, ConfigSpell.inviteGem.favorCost, ModSpirits.endererer, 1)
        stable_invite_pebble = ItemInviteMarker("stable_invite_pebble", false, false, ConfigSpell.invitePebble.favorCost, ModSpirits.endererer, 1)

        grimoire_red = ItemGrimoire("red")
        grimoire_black = ItemGrimoire("black")
        grimoire_blue = ItemGrimoire("blue")
        grimoire_green = ItemGrimoire("green")
        grimoire_yellow = ItemGrimoire("yellow")
        grimoire_cyan = ItemGrimoire("cyan")
        grimoire_purple = ItemGrimoire("purple")

        spell_blade_green = ItemSpellBlade("spell_blade_green", "spell_blade_green/model", ConstMainTabOrder.SWORDS, ConfigItem.spellBladeGreen, Item.ToolMaterial.IRON)
        spell_blade_red = ItemSpellBlade("spell_blade_red", "spell_blade_red/model", ConstMainTabOrder.SWORDS, ConfigItem.spellBladeRed, Item.ToolMaterial.IRON)

        spell_pick_blue = ItemSpellPick("spell_pick_blue", "spell_pick_blue/model", ConstMainTabOrder.PICKS, ConfigItem.spellPickBlue, Item.ToolMaterial.IRON)
        spell_pick_orange = ItemSpellPick("spell_pick_orange", "spell_pick_orange/model", ConstMainTabOrder.PICKS, ConfigItem.spellPickOrange, Item.ToolMaterial.IRON)

        goo_vial_small = ItemGooVial("small", ConfigItem.smallGooVial.capacity)
        goo_vial_medium = ItemGooVial("medium", ConfigItem.mediumGooVial.capacity)
        goo_vial_big = ItemGooVial("big", ConfigItem.bigGooVial.capacity)

        for (spirit in ModMappers.spirits.values)
            markedGlasses.add(ItemMarkedGlass(spirit, ConstGemTabOrder.MARKED_GLASS))

        // New instances
        compressed_items_drop = ItemCompressedItemsDrop()
        gem_pouch = ItemGemPouch()
        pebble_stabilizer = ItemPebbleStabilizer()
        gem_stabilizer = ItemGemStabilizer()
        experience_drop = ItemExperienceDrop()
        memory_drop = ItemMemoryDrop()
        ice_arrow = ItemIceArrow()
    }

    fun init() {
    }
}