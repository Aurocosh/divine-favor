package aurocosh.divinefavor.common.item.bone_dagger

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigItem
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.soul_shards.ItemSoulShard
import aurocosh.divinefavor.common.item.soul_shards.ModSoulShards
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.EntityEnderman
import net.minecraft.entity.monster.EntityEndermite
import net.minecraft.entity.monster.EntityShulker
import net.minecraft.entity.monster.EntityWitherSkeleton
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.passive.EntityWaterMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import java.util.*

class ItemBoneDaggerAwakened : ModItem("bone_dagger_awakened", "bone_dagger_awakened", ConstMainTabOrder.DAGGERS) {
    init {
        setMaxStackSize(1)
        containerItem = this
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        if (world.isRemote || !player.isSneaking)
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack)
        makeSoulShard(player, player)
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack)
    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity?): Boolean {
        if (player.world.isRemote)
            return false
        if (entity !is EntityLivingBase)
            return false

        val compound = stack.compound
        val chance = compound.getFloat(TAG_SOUL_STEAL_CHANCE)
        if (UtilRandom.rollDiceFloat(chance)) {
            entity.addPotionEffect(ModEffect(ModCurses.soul_theft, ConfigItem.awakenedBoneDagger.soulTheftDuration).setIsCurse())
            val theftData = entity.divineLivingData.soulTheftData
            theftData.addThief(player)
            makeSoulShard(entity, player)

            compound.setFloat(TAG_SOUL_STEAL_CHANCE, 0f)
        } else
            compound.setFloat(TAG_SOUL_STEAL_CHANCE, chance + ConfigItem.awakenedBoneDagger.soulSteelingSpeed)
        return false
    }

    private fun makeSoulShard(victim: EntityLivingBase, user: EntityPlayer) {
        val item: ItemSoulShard
        when {
            victim is EntityPlayer -> item = ModSoulShards.shard_will
            victim is EntityWitherSkeleton || victim is EntityWither -> item = ModSoulShards.shard_wither
            victim is EntityEnderman || victim is EntityEndermite || victim is EntityShulker || victim is EntityDragon -> item = ModSoulShards.shard_end
            victim is EntityVillager -> item = ModSoulShards.shard_mind
            victim.isEntityUndead -> item = ModSoulShards.shard_undeath
            hellClasses.contains(victim::class.java) -> item = ModSoulShards.shard_nether
            victim is EntityWaterMob -> item = ModSoulShards.shard_water
            victim is EntityAnimal -> item = ModSoulShards.shard_peace
            else -> item = ModSoulShards.shard_wild
        }

        val stack = ItemStack(item)
//        ItemSoulShard.setOwner(stack, victim)
        user.attackEntityFrom(ModDamageSources.divineDamage, 0.5f)
        user.addItemStackToInventory(stack)
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    companion object {
        private const val TAG_SOUL_STEAL_CHANCE = "SOUL_STEAL_CHANCE"

        private val witherClasses = HashSet<Class<out EntityLiving>>()
        private val hellClasses = HashSet<Class<out EntityLiving>>()

        init {
            witherClasses.add(EntityWither::class.java)
            witherClasses.add(EntityWitherSkeleton::class.java)

            val hell = ResourceLocation("hell")
            for (creatureType in EnumCreatureType.values())
                for (spawnListEntry in Biome.REGISTRY.getObject(hell)!!.getSpawnableList(creatureType))
                    hellClasses.add(spawnListEntry.entityClass)
        }
    }
}
