package aurocosh.divinefavor.common.item.talismans.spell.base

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

class TalismanContext {
    val player: EntityPlayer
    val target: EntityLivingBase?
    val world: World
    val pos: BlockPos
    val posVec: Vec3d
    val hand: EnumHand
    val facing: EnumFacing
    val castType: CastType
    val options: EnumSet<SpellOptions>

    constructor(player: EntityPlayer) {
        this.player = player
        this.world = player.world
        this.pos = BlockPos.ORIGIN
        this.posVec = Vec3d.ZERO
        this.hand = EnumHand.MAIN_HAND
        options = EnumSet.noneOf(SpellOptions::class.java)
        facing = EnumFacing.UP
        castType = CastType.Undefined
        target = null
    }

    constructor(player: EntityPlayer, world: World, pos: BlockPos, posVec: Vec3d, hand: EnumHand, facing: EnumFacing, target: EntityLivingBase?, type: CastType, options: EnumSet<SpellOptions>) {
        this.player = player
        this.world = world
        this.pos = pos
        this.posVec = posVec
        this.hand = hand
        this.facing = facing
        this.target = target
        this.castType = type
        this.options = options
    }
}
