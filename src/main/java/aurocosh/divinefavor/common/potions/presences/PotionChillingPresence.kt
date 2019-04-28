package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionChillingPresence : ModPotion("chilling_presence", true, 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        val spawnRadius = ConfigPresence.chillingPresence.spawnRadius
        val wolfsToSpawn = UtilRandom.nextInt(ConfigPresence.chillingPresence.minWolfsToSpawn, ConfigPresence.chillingPresence.maxWolfsToSpawn)
        for (i in 0 until wolfsToSpawn) {
            val position = livingBase.position
            var spawnPos: BlockPos? = UtilCoordinates.getRandomNeighbour(position, spawnRadius, 0, spawnRadius)
            val world = livingBase.world
            spawnPos = UtilCoordinates.findPlaceToStand(spawnPos!!, world, spawnRadius)
            val entityDirewolf = EntityDirewolf(world)
            entityDirewolf.setLocationAndAngles(spawnPos!!.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)
            world.spawnEntity(entityDirewolf)
        }
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        val player = livingBase as EntityPlayer
        player.addItemStackToInventory(ItemStack(ModCallingStones.calling_stone_blizrabi))
    }
}
