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

val playerField: (CastContext) -> EntityPlayer = CastContext::player
val targetField: KProperty1<CastContext, EntityLivingBase?> = CastContext::target
val worldField: (CastContext) -> World = CastContext::world
val posField: (CastContext) -> BlockPos = CastContext::pos
val posVecField: KProperty1<CastContext, Vec3d> = CastContext::posVec
val handField: KProperty1<CastContext, EnumHand> = CastContext::hand
val facingField: KProperty1<CastContext, EnumFacing> = CastContext::facing
val castTypeField: KProperty1<CastContext, CastType> = CastContext::castType
val stackField: (CastContext) -> ItemStack = CastContext::stack
val containerStackField: (CastContext) -> ItemStack = CastContext::containerStack

