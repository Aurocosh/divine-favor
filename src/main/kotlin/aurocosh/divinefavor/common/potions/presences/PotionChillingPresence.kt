package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import aurocosh.divinefavor.common.util.UtilSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionChillingPresence : ModPotion("chilling_presence", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        val spawnRadius = ConfigPresence.chillingPresence.spawnRadius
        val wolfsToSpawn = UtilRandom.nextInt(ConfigPresence.chillingPresence.minWolfsToSpawn, ConfigPresence.chillingPresence.maxWolfsToSpawn)
        for (i in 0 until wolfsToSpawn) {
            val position = livingBase.position
            val randomPos = UtilCoordinates.getRandomNeighbour(position, spawnRadius, 0, spawnRadius)
            val world = livingBase.world
            val spawnPos = UtilCoordinates.findPlaceToStand(randomPos, world, spawnRadius) ?: return
            val entityDirewolf = EntityDirewolf(world)
            entityDirewolf.setLocationAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)
            world.spawnEntity(entityDirewolf)
        }
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase !is EntityPlayer)
            return

        UtilSpirit.convertMarksToInvites(livingBase, ModSpirits.blizrabi, ModCallingStones.calling_stone_blizrabi)
        MaterialPresence.onInviteGiven(livingBase)
    }

}
