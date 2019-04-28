package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionWoodenPunch : ModPotionToggleLimited("wooden_punch", true, 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
            val world = event.world
            if (world.isRemote)
                return

            val player = event.entityPlayer
            if (!player.isPotionActive(ModPotions.wooden_punch))
                return

            val pos = event.pos
            val state = world.getBlockState(pos)
            val block = state.block
            if (!block.isToolEffective("axe", state))
                return

            val spiritData = PlayerData.get(player).spiritData
            val talisman = ModPotions.wooden_punch.talisman
            val spirit = talisman.spirit
            if (!spiritData.consumeFavor(spirit.id, talisman.favorCost))
                return
            MessageSyncFavor(spirit, spiritData).sendTo(player)

            val stack = player.heldItemMainhand
            UtilBlock.removeBlock(player, world, stack, pos, true, false, true)
        }
    }
}
