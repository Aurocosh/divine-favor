package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilMob
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
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
        context.target?.attackEntityFrom(DamageSource.causePlayerDamage(context.player), ConfigBlade.bladeOfSnow.damage)
    }
}
