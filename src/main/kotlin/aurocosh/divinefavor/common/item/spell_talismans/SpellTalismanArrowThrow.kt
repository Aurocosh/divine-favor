package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import net.minecraftforge.event.ForgeEventFactory
import java.util.*

class SpellTalismanArrowThrow(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val stack = context.player.getHeldItem(context.hand)
        shootArrow(stack, context.world, context.player, 15)
    }

    private fun findAmmo(player: EntityPlayer): ItemStack {
        val stack = UtilPlayer.getItemInHand(player) { it is ItemArrow }
        if (!stack.isEmpty)
            return stack

        for (i in 0 until player.inventory.sizeInventory) {
            val itemStack = player.inventory.getStackInSlot(i)
            if (itemStack.item is ItemArrow)
                return itemStack
        }

        return ItemStack.EMPTY
    }

    private fun shootArrow(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase, charge: Int) {
        var chargeCounter = charge
        if (entityLiving !is EntityPlayer)
            return
        val flag = entityLiving.capabilities.isCreativeMode
        var itemStack = this.findAmmo(entityLiving)

        chargeCounter = ForgeEventFactory.onArrowLoose(stack, worldIn, entityLiving, chargeCounter, !itemStack.isEmpty || flag)
        if (chargeCounter < 0)
            return

        if (!itemStack.isEmpty || flag) {
            if (itemStack.isEmpty)
                itemStack = ItemStack(Items.ARROW)

            val f = getArrowVelocity(chargeCounter)

            if (f.toDouble() >= 0.1) {
                val flag1 = entityLiving.capabilities.isCreativeMode || itemStack.item is ItemArrow && (itemStack.item as ItemArrow).isInfinite(itemStack, stack, entityLiving)

                if (!worldIn.isRemote) {
                    val itemarrow = (if (itemStack.item is ItemArrow) itemStack.item else Items.ARROW) as ItemArrow
                    val entityarrow = itemarrow.createArrow(worldIn, itemStack, entityLiving)
                    entityarrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, f * 3.0f, 1.0f)

                    if (f == 1.0f)
                        entityarrow.isCritical = true

                    stack.damageItem(1, entityLiving)

                    if (flag1 || entityLiving.capabilities.isCreativeMode && (itemStack.item === Items.SPECTRAL_ARROW || itemStack.item === Items.TIPPED_ARROW))
                        entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY

                    worldIn.spawnEntity(entityarrow)
                }

                worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (UtilRandom.random.nextFloat() * 0.4f + 1.2f) + f * 0.5f)

                if (!flag1 && !entityLiving.capabilities.isCreativeMode) {
                    itemStack.shrink(1)

                    if (itemStack.isEmpty)
                        entityLiving.inventory.deleteStack(itemStack)
                }
            }
        }
    }

    companion object {
        fun getArrowVelocity(charge: Int): Float {
            var f = charge.toFloat() / 20.0f
            f = (f * f + f * 2.0f) / 3.0f
            return if (f < 1.0f) f else 1.0f
        }
    }
}
