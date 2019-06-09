package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilMob
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource

class BladeTalismanSnow(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: ItemStack, player: EntityPlayer, target: EntityLivingBase): Boolean {
        if (player.world.canSnowAt(player.position, false))
            return true
        return UtilMob.isMobHellish(target)
    }

    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), ConfigBlade.bladeOfSnow.damage)
    }
}
