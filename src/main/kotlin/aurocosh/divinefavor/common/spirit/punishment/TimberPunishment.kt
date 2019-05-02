package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TimberPunishment : SpiritPunishment() {

    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        player.addPotionEffect(ModEffect(ModCurses.roots, ROOTS_DURATION).setIsCurse())
        player.addPotionEffect(ModEffect(MobEffects.BLINDNESS, BLINDNESS_DURATION).setIsCurse())
    }

    companion object {
        var ROOTS_DURATION = UtilTick.minutesToTicks(5f)
        var BLINDNESS_DURATION = UtilTick.secondsToTicks(30f)
    }
}
