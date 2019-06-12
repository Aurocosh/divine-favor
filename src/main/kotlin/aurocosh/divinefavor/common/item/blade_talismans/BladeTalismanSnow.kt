package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.attackEntityNoTimer
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilMob
import net.minecraft.util.DamageSource

class BladeTalismanSnow(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        if(context.target == null)
            return false
        if (context.world.canSnowAt(context.pos, false))
            return true
        return UtilMob.isMobHellish(context.target)
    }

    override fun performActionServer(context: TalismanContext) {
        context.target?.attackEntityNoTimer(DamageSource.causePlayerDamage(context.player), ConfigBlade.bladeOfSnow.damage)
    }
}
