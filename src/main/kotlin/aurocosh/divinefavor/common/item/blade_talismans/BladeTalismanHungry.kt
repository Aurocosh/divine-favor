package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory

class BladeTalismanHungry(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: TalismanContext) {
        val target = context.target as? EntityPlayer ?: return
        val player = context.player

        val foodToSteal = Math.min(target.foodStats.foodLevel, ConfigBlade.hungryBlade.foodStolen)
        val saturationToSteal = Math.min(target.foodStats.saturationLevel, ConfigBlade.hungryBlade.saturationStolen)

        target.foodStats.addStats(-foodToSteal, -saturationToSteal)
        player.foodStats.addStats(foodToSteal, saturationToSteal)
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, player.world.rand.nextFloat() * 0.1f + 0.9f)
    }
}
