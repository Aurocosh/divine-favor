package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.util.DamageSource

class BladeTalismanButcheringStrike(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        val target = context.target ?: return false
        if (target !is IAnimals)
            return false
        if (target is IMob)
            return false
        return true
    }

    override fun performActionServer(context: TalismanContext) {
        val target = context.target ?: return
        target.divineLivingData.extraLootingData.extraLooting += ConfigBlade.butcheringStrike.extraLooting
        target.attackEntityFrom(DamageSource.causePlayerDamage(context.player), ConfigBlade.butcheringStrike.extraDamage)
    }
}
