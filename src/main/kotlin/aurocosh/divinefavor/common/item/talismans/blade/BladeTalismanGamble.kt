package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.attackEntityNoTimer
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource

class BladeTalismanGamble(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: TalismanContext) {
        val target = context.target as? EntityLiving ?: return
        val player = context.player
        target.attackEntityNoTimer(DamageSource.MAGIC, ConfigBlade.gamble.damage)
        if (UtilRandom.rollDiceFloat(ConfigBlade.gamble.failProbability))
            player.attackEntityFrom(ModDamageSources.divineDamage, ConfigBlade.gamble.failDamage)
    }
}
