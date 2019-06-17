package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer

class BladeTalismanHeavy(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        return context.target !is EntityPlayer
    }

    override fun performActionServer(context: TalismanContext) {
        val target = context.target as? EntityLiving ?: return
        UtilEntity.addVelocity(target, context.player.lookVec, ConfigBlade.heavyBlade.knockback)
    }
}
