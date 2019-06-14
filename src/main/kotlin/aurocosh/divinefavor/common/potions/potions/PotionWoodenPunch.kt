package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PotionWoodenPunch : ModPotionToggleLimited("wooden_punch", 0x7FB8A4) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }
}

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object WoodenPunch {
    @SubscribeEvent
    fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
        val world = event.world
        if (world.isRemote)
            return

        val player = event.entityPlayer
        if (!player.isPotionActive(ModPotions.wooden_punch))
            return

        val talisman = ModPotions.wooden_punch.talisman
        val stack = UtilPlayer.getItemInHand(player) { it === talisman }
        if (stack.isEmpty)
            return

        val pos = event.pos
        val state = world.getBlockState(pos)
        val block = state.block
        if (!block.isToolEffective("axe", state))
            return

        val cost = talisman.getFavorCost(stack)
        val spiritData = player.divinePlayerData.spiritData
        val spirit = talisman.spirit
        if (!spiritData.consumeFavor(spirit.id, cost))
            return

        MessageSyncFavor(spirit, spiritData).sendTo(player)
        UtilBlock.removeBlock(player, world, stack, pos, true, false, true)
    }
}