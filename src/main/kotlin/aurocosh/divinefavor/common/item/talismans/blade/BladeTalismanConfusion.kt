package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.config.common.ConfigBlade
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class BladeTalismanConfusion(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun validate(context: ItemStack, player: EntityPlayer, target: EntityLivingBase): Boolean {
        return target !is EntityPlayer
    }

    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        if(target !is EntityLiving)
            return
        if(target is EntityPlayer)
            return

        val boundingBox = UtilCoordinates.getBoundingBox(target.position, ConfigBlade.confusion.radius.toDouble())
        val entities = target.world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox) { livingBase ->
            livingBase != target
        }

        if (entities.isNotEmpty())
            target.attackTarget = entities.random()
    }
}
