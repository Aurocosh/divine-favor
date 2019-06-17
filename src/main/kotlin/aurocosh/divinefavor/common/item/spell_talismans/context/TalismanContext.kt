package aurocosh.divinefavor.common.item.spell_talismans.context

import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

data class ContextProperty<T>(val name: String, val defaultValue: T)
data class CommonContextValues(val player: EntityPlayer, val stack: ItemStack, val world: World)

class TalismanContext(
        val player: EntityPlayer, val target: EntityLivingBase?, val world: World,
        val pos: BlockPos, val posVec: Vec3d, val hand: EnumHand, val facing: EnumFacing,
        val castType: CastType, val stack: ItemStack,
        val raycastValid: Boolean, val stackValid: Boolean) {

    private val properties = HashMap<String, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(property: ContextProperty<T>): T {
        val value = (properties[property.name] ?: property.defaultValue)
        return value as T
    }

    fun <T : Any> set(property: ContextProperty<T>, value: T) {
        properties[property.name] = value
    }

    fun getCommon() = CommonContextValues(player, stack, world)
}
