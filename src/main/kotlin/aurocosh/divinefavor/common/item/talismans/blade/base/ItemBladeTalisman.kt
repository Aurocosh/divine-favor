package aurocosh.divinefavor.common.item.talismans.blade.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.CastType
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

open class ItemBladeTalisman// Talisman functions
(name: String, texturePath: String, spirit: ModSpirit, favorCost: Int, private val requiresTarget: Boolean) : ItemTalisman("blade_talisman_$name", "blade_talismans/$texturePath$name", spirit, favorCost) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_BLADE_TALISMANS
    }

    private fun getTexturePath(path: Array<String>): String {
        return path.joinToString("/")
    }

    // Talisman functions
    constructor(name: String, spirit: ModSpirit, favorCost: Int, requiresTarget: Boolean) : this(name, "", spirit, favorCost, requiresTarget)

    fun cast(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
        if (attacker !is EntityPlayer)
            return false
        if (!validate(stack, attacker, target))
            return false
        if (isConsumeCharge(stack, attacker, target) && !claimCost(attacker))
            return false
        if (attacker.world.isRemote)
            performActionClient(stack, attacker, target)
        else
            performActionServer(stack, attacker, target)
        return true
    }

    // Talisman functions
    override fun hitEntity(stack: ItemStack, target: EntityLivingBase, attacker: EntityLivingBase): Boolean {
        if (stack.item is ItemBladeTalisman) {
            cast(stack, attacker, target)
        }
        return false
    }

    protected open fun validate(context: ItemStack, player: EntityPlayer, target: EntityLivingBase): Boolean {
        return true
    }

    protected open fun isConsumeCharge(context: ItemStack, player: EntityPlayer, target: EntityLivingBase): Boolean {
        return true
    }

    protected open fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {}

    protected open fun performActionClient(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {}
}