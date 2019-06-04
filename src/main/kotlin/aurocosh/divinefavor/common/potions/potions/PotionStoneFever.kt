package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.MobEffects
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionStoneFever : ModPotion("stone_fever", 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {

        @SubscribeEvent
        fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
            val world = event.world
            if (world.isRemote)
                return

            if (!event.entityPlayer.isPotionActive(ModPotions.stone_fever))
                return

            val player = event.entityPlayer
            val stack = player.heldItemMainhand
            if (stack.isEmpty)
                return

            if (!stack.item.getToolClasses(stack).contains("pickaxe")) {
                punishPlayer(player)
                return
            }

            val block = world.getBlock(event.pos)
            if (block !== Blocks.STONE && block !== Blocks.COBBLESTONE) {
                punishPlayer(player)
                return
            }

            UtilBlock.removeBlock(player, world, stack, event.pos, true, true, true)
            stack.damageItem(1, player)
        }

        private fun punishPlayer(player: EntityPlayer) {
            player.removePotionEffect(ModPotions.stone_fever)

            player.addPotionEffect(ModEffect(MobEffects.SLOWNESS, ConfigSpells.stoneFever.slownessDuration).setIsCurse())
            player.addPotionEffect(ModEffect(MobEffects.BLINDNESS, ConfigSpells.stoneFever.blindnessDuration).setIsCurse())
            player.addPotionEffect(ModEffect(MobEffects.MINING_FATIGUE, ConfigSpells.stoneFever.fatigueDuration, ConfigSpells.stoneFever.fatigueLevel).setIsCurse())
        }
    }
}
