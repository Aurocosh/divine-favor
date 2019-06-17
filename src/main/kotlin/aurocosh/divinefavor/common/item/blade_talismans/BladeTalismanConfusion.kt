package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

class BladeTalismanConfusion(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        return context.target !is EntityPlayer
    }

    override fun performActionServer(context: TalismanContext) {
        val target = context.target
        if(target is EntityPlayer)
            return
        if(target !is EntityLiving)
            return

        val boundingBox = UtilCoordinates.getBoundingBox(target.position, ConfigBlade.confusion.radius.toDouble())
        val entities = target.world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox) { livingBase ->
            livingBase != target
        }

        if (entities.isNotEmpty())
            target.attackTarget = entities.random()
    }
}
