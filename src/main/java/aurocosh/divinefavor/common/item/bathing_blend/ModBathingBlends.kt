package aurocosh.divinefavor.common.item.bathing_blend

import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects

object ModBathingBlends {
    private var charcoal: ItemBathingBlend? = null
    private var ender_pearl: ItemBathingBlend? = null
    private var feathers: ItemBathingBlend? = null
    private var fleshy: ItemBathingBlend? = null
    private var flint: ItemBathingBlend? = null
    private var lapis: ItemBathingBlend? = null
    private var redstone: ItemBathingBlend? = null
    private var snow: ItemBathingBlend? = null
    private var wood: ItemBathingBlend? = null

    fun preInit() {
        charcoal = ItemBathingBlendModPotion("charcoal", ModBlendEffects.charred_aura, ConfigItem.blendCharcoal)
        ender_pearl = ItemBathingBlendModPotion("ender_pearl", ModBlendEffects.distorted_aura, ConfigItem.blendEnderPearl)
        feathers = ItemBathingBlendModPotion("feathers", ModBlendEffects.hunters_aura, ConfigItem.blendFeathers)
        fleshy = ItemBathingBlendModPotion("fleshy", ModBlendEffects.visceral_aura, ConfigItem.blendFleshy)
        flint = ItemBathingBlendModPotion("flint", ModBlendEffects.mineral_aura, ConfigItem.blendFlint)
        lapis = ItemBathingBlendModPotion("lapis", ModBlendEffects.calling_aura, ConfigItem.blendLapis)
        redstone = ItemBathingBlendModPotion("redstone", ModBlendEffects.energetic_aura, ConfigItem.blendRedstone)
        snow = ItemBathingBlendModPotion("snow", ModBlendEffects.frosty_aura, ConfigItem.blendSnow)
        wood = ItemBathingBlendModPotion("wood", ModBlendEffects.arboreal_aura, ConfigItem.blendWood)
    }
}