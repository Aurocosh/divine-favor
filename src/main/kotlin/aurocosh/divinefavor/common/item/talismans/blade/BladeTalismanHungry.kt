package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.SoundCategory

class BladeTalismanHungry(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        if (target !is EntityPlayer)
            return

        val foodToSteal = Math.min(target.foodStats.foodLevel, ConfigBlade.hungryBlade.foodStolen)
        val saturationToSteal = Math.min(target.foodStats.saturationLevel, ConfigBlade.hungryBlade.saturationStolen)

        target.foodStats.addStats(-foodToSteal, -saturationToSteal)
        player.foodStats.addStats(foodToSteal, saturationToSteal)
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, player.world.rand.nextFloat() * 0.1f + 0.9f)
    }
}
