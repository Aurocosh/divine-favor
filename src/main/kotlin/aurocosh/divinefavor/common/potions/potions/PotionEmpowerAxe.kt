package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.core.handlers.BlockClickTracker
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionEmpowerAxe : ModPotion("empower_axe", 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
            val world = event.world
            if (world.isRemote)
                return

            if (!event.entityPlayer.isPotionActive(ModPotions.empower_axe))
                return

            val player = event.entityPlayer
            val stack = player.heldItemMainhand
            if (stack.isEmpty)
                return

            if (!stack.item.getToolClasses(stack).contains("axe"))
                return

            val pos = event.pos
            val state = world.getBlockState(pos)
            val block = state.block
            if (!block.isToolEffective("axe", state))
                return

            if (BlockClickTracker.wasBlockLeftClicked(player, pos))
                return

            val doSomething = UtilRandom.rollDice(ConfigSpells.empowerAxe.instantBreakChance.toFloat())
            if (!doSomething)
                return

            UtilBlock.removeBlock(player, world, stack, pos, true, true, true)
            stack.damageItem(ConfigSpells.empowerAxe.toolDamage, player)
        }
    }
}
