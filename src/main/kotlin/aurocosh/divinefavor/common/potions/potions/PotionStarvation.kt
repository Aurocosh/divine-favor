package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionStarvation : ModPotion("starvation", 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        fun onPlayerLeftClickBlock(event: PlayerInteractEvent.RightClickBlock) {
            val world = event.world
            if (world.isRemote)
                return

            val player = event.entityPlayer
            if (!player.isPotionActive(ModPotions.starvation))
                return
            if (!player.heldItemMainhand.isEmpty)
                return
            if (!player.heldItemOffhand.isEmpty)
                return

            val pos = event.pos
            val state = world.getBlockState(pos)
            val material = state.material
            if (material !== Material.GRASS && material !== Material.LEAVES)
                return
            if (!UtilBlock.canBreakBlock(player, world, pos, false))
                return
            val stateNew = if (material === Material.GRASS)
                    Blocks.DIRT.defaultState
                else
                    Blocks.AIR.defaultState
            world.setBlockState(pos, stateNew)

            player.foodStats.addStats(ConfigSpell.starvation.foodPerGrass, ConfigSpell.starvation.saturationPerGrass)
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f)
        }
    }
}
