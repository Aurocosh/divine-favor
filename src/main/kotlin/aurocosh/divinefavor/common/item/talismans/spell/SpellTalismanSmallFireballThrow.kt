package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import java.util.*

class SpellTalismanSmallFireballThrow(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        throwSmallFireball(context.world, context.player)
    }

    private fun throwSmallFireball(worldIn: World, playerIn: EntityPlayer): Boolean {
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (UtilRandom.random.nextFloat() * 0.4f + 0.8f))
        if (playerIn.world.isRemote)
            return true

        val looking = playerIn.lookVec

        val fireball = EntitySmallFireball(worldIn, playerIn, 0.0, 0.0, 0.0)
        fireball.posY += playerIn.eyeHeight.toDouble()
        fireball.motionX = looking.x
        fireball.motionY = looking.y
        fireball.motionZ = looking.z
        fireball.accelerationX = fireball.motionX * 0.1
        fireball.accelerationY = fireball.motionY * 0.1
        fireball.accelerationZ = fireball.motionZ * 0.1

        worldIn.spawnEntity(fireball)
        return true
    }
}
