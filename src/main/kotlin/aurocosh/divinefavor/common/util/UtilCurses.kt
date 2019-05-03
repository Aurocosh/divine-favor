package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import net.minecraft.entity.EntityList
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

object UtilCurses {
    fun applyCurse(victim: EntityLivingBase, caster: EntityLivingBase, effect: ModEffect) {
        effect.setIsCurse()
        val resistance = getCurseResistance(victim)
        if (UtilRandom.rollDice(resistance.toFloat()))
            caster.addPotionEffect(effect)
        else
            victim.addPotionEffect(effect)
    }

    fun getCurseResistance(victim: EntityLivingBase): Float {
        var curseResistance: Float = ConfigCurses.baseCurseResistance

        val entityString = EntityList.getKey(victim)?.toString() ?: ""
        curseResistance += ConfigCurses.mobResistances[entityString] ?: 0.0.toFloat()

        curseResistance += victim.divineLivingData.curseData.curseCount * ConfigCurses.curseResistancePerCurse
        if (victim is EntityPlayer)
            curseResistance += ConfigCurses.playerCurseResistance
        return curseResistance
    }
}
