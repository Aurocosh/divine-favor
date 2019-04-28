package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

import net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END

class RedwindPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        RedwindPunishmentTask(player).start()
    }

    class RedwindPunishmentTask(private val player: EntityPlayer) : ServerSideTask(player.world) {

        private var dragCount: Int = 0
        private var dragTime: Int = 0

        init {
            dragCount = ConfigPunishments.redwind.dragCount.random()
            dragTime = 0
        }

        @SubscribeEvent
        fun tickEvent(event: TickEvent.ServerTickEvent) {
            if (event.phase == END)
                return

            if (dragTime-- > 0)
                return
            dragTime = ConfigPunishments.redwind.dragDuration.random()
            player.addPotionEffect(ModEffect(ModCurses.red_fury, dragTime).setIsCurse())

            if (dragCount-- <= 0)
                finish()
        }

        @SubscribeEvent
        fun onPlayerLogout(event: PlayerEvent.PlayerLoggedOutEvent) {
            if (event.player === player)
                finish()
        }
    }
}
