package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.world.World

class ItemStoneball : ModItem("stone_ball", "stone_ball", ConstMainTabOrder.OTHER_ITEMS) {
    init {
        this.maxStackSize = 16
        creativeTab = DivineFavor.TAB_MAIN
    }

    /**
     * Called when the equipped item is right clicked.
     */
    override fun onItemRightClick(worldIn: World?, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val itemStack = playerIn.getHeldItem(handIn)

        if (!playerIn.capabilities.isCreativeMode)
            itemStack.shrink(1)

        worldIn!!.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (Item.itemRand.nextFloat() * 0.4f + 0.8f))

        if (!worldIn.isRemote) {
            val stoneball = EntityStoneball(worldIn, playerIn)
            stoneball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 1.0f, 1.0f)
            worldIn.spawnEntity(stoneball)
        }

        playerIn.addStat(StatList.getObjectUseStats(this)!!)
        return ActionResult(EnumActionResult.SUCCESS, itemStack)
    }
}