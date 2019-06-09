package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource

class BladeTalismanRainSword(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: ItemStack, player: EntityPlayer, target: EntityLivingBase): Boolean {
        if (player.world.isRaining)
            return true
        if (target is EntityEnderman)
            return true
        return false
    }

    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        target.attackEntityFrom(DamageSource.DROWN, ConfigBlade.rainSword.damage)
    }
}