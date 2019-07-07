package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilSpirit
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionIndustriousPresence : ModPotion("industrious_presence", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.industriousPresenceData.reset()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (!TICK_COUNTER.isFinished)
            return
        val position = livingBase.position
        if (livingBase.world.canSeeSky(BlockPos(position.x.toDouble(), position.y + livingBase.eyeHeight.toDouble(), position.z.toDouble())))
            livingBase.removePotionEffect(ModBlessings.industrious_presence)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val TICK_RATE = UtilTick.secondsToTicks(1f)
        private val TICK_COUNTER = LoopedCounter(TICK_RATE)

        private val acceptedBlocks = ArrayList<Block>()

        init {
            acceptedBlocks.add(Blocks.DIAMOND_ORE)
            acceptedBlocks.add(Blocks.EMERALD_ORE)
            acceptedBlocks.add(Blocks.GOLD_ORE)
            acceptedBlocks.add(Blocks.IRON_ORE)
            acceptedBlocks.add(Blocks.LAPIS_ORE)
            acceptedBlocks.add(Blocks.LIT_REDSTONE_ORE)
            acceptedBlocks.add(Blocks.QUARTZ_ORE)
            acceptedBlocks.add(Blocks.REDSTONE_ORE)
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onBlockBroken(event: BlockEvent.BreakEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlessings.industrious_presence))
                return
            val state = event.state
            if (!acceptedBlocks.contains(state.block))
                return
            if (player.divinePlayerData.industriousPresenceData.count()) {
                player.removePotionEffect(ModBlessings.industrious_presence)
                UtilSpirit.convertMarksToInvites(player, ModSpirits.romol, ModCallingStones.calling_stone_romol)
                MaterialPresence.onInviteGiven(player)
            }
        }

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                TICK_COUNTER.tick()
        }
    }
}
