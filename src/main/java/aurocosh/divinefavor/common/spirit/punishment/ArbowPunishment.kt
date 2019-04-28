package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ArbowPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        player.attackEntityFrom(ModDamageSources.divineDamage, ConfigPunishments.arbow.damage.toFloat())
        player.addPotionEffect(ModEffect(ModCurses.yummy_smell, ConfigPunishments.arbow.curseDuration.random()))
        player.addPotionEffect(ModEffect(ModCurses.cripple, ConfigPunishments.arbow.curseDuration.random()))
    }
}
