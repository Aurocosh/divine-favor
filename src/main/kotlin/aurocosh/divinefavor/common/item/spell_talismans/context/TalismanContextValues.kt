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
import kotlin.reflect.KProperty1

val playerField: (TalismanContext) -> EntityPlayer = TalismanContext::player
val targetField: KProperty1<TalismanContext, EntityLivingBase?> = TalismanContext::target
val worldField: (TalismanContext) -> World = TalismanContext::world
val posField: (TalismanContext) -> BlockPos = TalismanContext::pos
val posVecField: KProperty1<TalismanContext, Vec3d> = TalismanContext::posVec
val handField: KProperty1<TalismanContext, EnumHand> = TalismanContext::hand
val facingField: KProperty1<TalismanContext, EnumFacing> = TalismanContext::facing
val castTypeField: KProperty1<TalismanContext, CastType> = TalismanContext::castType
val stackField: (TalismanContext) -> ItemStack = TalismanContext::stack
val containerStackField: (TalismanContext) -> ItemStack = TalismanContext::containerStack

