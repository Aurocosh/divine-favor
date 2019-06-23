package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import aurocosh.divinefavor.common.util.UtilTick
import aurocosh.divinefavor.common.util.UtilWorld
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.entity.monster.EntityWitch
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.EnumSkyBlock
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionRottenMight : ModPotionToggle("rotten_might", 0x7FB8A4) {
    init {
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "eb589436-7fe1-4005-b583-a296c8c27678", ConfigSpell.rottenMight.speedModifier, 2)
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "6b1bc9e9-1397-4e29-8996-010eda197e1b", ConfigSpell.rottenMight.damageModifier, 1)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val world = livingBase.world
        if (world.isRemote)
            return
        if (!TICK_COUNTER.isFinished)
            return

        val pos = livingBase.position
        if (world.isDaytime && world.canSeeSky(pos))
            livingBase.setFire(ConfigSpell.rottenMight.burnTime)

        val lightLevel = UtilWorld.getLightLevel(world, pos)
        if (lightLevel > ConfigSpell.rottenMight.tolerableLightLevel)
            livingBase.addPotionEffect(PotionEffect(MobEffects.BLINDNESS, ConfigSpell.rottenMight.blindnessDuration))
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val TICK_RATE = UtilTick.secondsToTicks(1f)
        private val TICK_COUNTER = LoopedCounter(TICK_RATE)

        @SubscribeEvent
        fun handleInfection(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityLivingBase ?: return
            if (!entity.isPotionActive(ModPotions.rotten_might)) return
            if (!UtilRandom.rollDiceFloat(ConfigSpell.rottenMight.infectionChance)) return
            event.entityLiving.addPotionEffect(ModEffectToggle(ModPotions.rotten_might,1))
        }

        @SubscribeEvent
        fun handleWeakness(event: LivingDamageEvent) {
            val livingBase = event.entityLiving
            if (!livingBase.isPotionActive(ModPotions.rotten_might))
                return

            val source = event.source
            if (source.isFireDamage)
                event.amount = event.amount + ConfigSpell.rottenMight.fireDamage

            val entity = source.trueSource
            if (entity is EntityPlayer) {
                val stack = entity.heldItemMainhand
                if (!stack.isEmpty) {
                    val baneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack)
                    event.amount = event.amount + baneLevel * ConfigSpell.rottenMight.smiteDamage
                }
            }
        }

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                TICK_COUNTER.tick()
        }
    }
}
