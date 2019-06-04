package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionEnergeticPresence : ModPotion("energetic_presence", 0x7FB8A4) {
    private val SPEED_MODIFIER = 0.35f

    init {
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "5c6cd11e-eb6c-43b2-9e56-34a386693e93", SPEED_MODIFIER.toDouble(), 2)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.energeticPresenceData.reset()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.isSprinting)
            tickLiquidWalk(livingBase, Blocks.WATER)
        if (livingBase !is EntityPlayer)
            return

        val presenceData = livingBase.divinePlayerData.energeticPresenceData
        if (livingBase.isInWater || !livingBase.world.getBlock(livingBase.position.down()).isWater())
            presenceData.reset()
        else if (presenceData.count() && !livingBase.world.isRemote) {
            livingBase.removePotionEffect(ModBlessings.energetic_presence)
            livingBase.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_redwind))
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
