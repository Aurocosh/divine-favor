package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContextGenerator
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class PotionCrushingPalm : ModPotionToggleLimited("crushing_palm", 0x7FB8A4) {
    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }
}

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object CrushingPalm {
    @SubscribeEvent
    @JvmStatic
    fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
        val world = event.world
        if (world.isRemote)
            return

        val player = event.entityPlayer
        if (!player.isPotionActive(ModPotions.crushing_palm))
            return

        val talisman = ModPotions.crushing_palm.talisman
        val context = CastContextGenerator.player(player)
        if (!talisman.claimCost(context))
            return

        val pos = event.pos
        val state = world.getBlockState(pos)
        val block = state.block
        if (block.isToolEffective("pickaxe", state))
            UtilBlock.removeBlock(player, world, ItemStack.EMPTY, pos, true, false, true)
    }
}