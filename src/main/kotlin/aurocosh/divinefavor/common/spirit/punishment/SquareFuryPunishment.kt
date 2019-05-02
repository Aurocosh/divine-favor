package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SquareFuryPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        player.attackEntityFrom(ModDamageSources.divineDamage, ConfigPunishments.squarefury.damage.toFloat())
    }
}
