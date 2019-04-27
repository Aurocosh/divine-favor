package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySnowball
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import java.util.*

class SpellTalismanSnowballThrow(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        throwSnowball(context.world, context.player)
    }

    fun throwSnowball(worldIn: World, playerIn: EntityPlayer): Boolean {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (UtilRandom.random.nextFloat() * 0.4f + 0.8f))
        if (worldIn.isRemote)
            return true
        val entitySnowball = EntitySnowball(worldIn, playerIn)
        entitySnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 1.5f, 1.0f)
        worldIn.spawnEntity(entitySnowball)
        return true
    }
}
