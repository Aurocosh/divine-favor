package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource

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
