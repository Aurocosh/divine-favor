package aurocosh.divinefavor.common.item.bone_dagger

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.util.UtilNbt
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

class ItemBoneDagger : ModItem("bone_dagger", "bone_dagger", ConstMainTabOrder.DAGGERS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onLeftClickEntity(stack: ItemStack?, player: EntityPlayer, entity: Entity?): Boolean {
        if (player.world.isRemote)
            return false
        if (entity !is EntityLivingBase)
            return false
        val nbt = UtilNbt.getNbt(stack!!)
        val chance = nbt.getFloat(TAG_AWAKENING_CHANCE)
        if (UtilRandom.rollDiceFloat(chance)) {
            stack.shrink(1)
            player.addItemStackToInventory(ItemStack(ModItems.bone_dagger_awakened, 1))
        }
        nbt.setFloat(TAG_AWAKENING_CHANCE, chance + ConfigItem.boneDagger.awakeningSpeed)
        return false
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    companion object {
        private val TAG_AWAKENING_CHANCE = "AWAKENING_CHANCE"
    }
}
