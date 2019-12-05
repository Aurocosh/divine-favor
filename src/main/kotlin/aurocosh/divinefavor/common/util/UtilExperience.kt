package aurocosh.divinefavor.common.util

import net.minecraft.entity.player.EntityPlayer

/**
 * This class was adapted from code written by OpenMods contributors for the OpenModsLib mod: https://github.com/OpenMods/OpenModsLib
 * OpenModsLib is Open Source and distributed under MIT
 */

object UtilExperience {
    fun getPlayerXP(player: EntityPlayer): Int {
        return (getExperienceForLevel(player.experienceLevel) + player.experience * player.xpBarCap()).toInt()
    }

    fun removePlayerXP(player: EntityPlayer, amount: Int) = addPlayerXP(player, -amount)

    fun addPlayerXP(player: EntityPlayer, amount: Int) {
        if(player.capabilities.isCreativeMode)
            return
        val experience = getPlayerXP(player) + amount
        player.experienceTotal = experience
        player.experienceLevel = getLevelForExperience(experience)
        val expForLevel = getExperienceForLevel(player.experienceLevel)
        player.experience = (experience - expForLevel).toFloat() / player.xpBarCap().toFloat()
    }

    fun xpBarCap(level: Int): Int {
        if (level >= 30) return 112 + (level - 30) * 9
        return if (level >= 15) 37 + (level - 15) * 5
        else 7 + level * 2
    }

    private fun sum(n: Int, a0: Int, d: Int): Int {
        return n * (2 * a0 + (n - 1) * d) / 2
    }

    fun getExperienceForLevel(level: Int): Int {
        if (level == 0)
            return 0
        if (level <= 15)
            return sum(level, 7, 2)
        return if (level <= 30)
            315 + sum(level - 15, 37, 5)
        else
            1395 + sum(level - 30, 112, 9)
    }

    fun getLevelForExperience(targetXp: Int): Int {
        var targetXp = targetXp
        var level = 0
        while (true) {
            val xpToNextLevel = xpBarCap(level)
            if (targetXp < xpToNextLevel) return level
            level++
            targetXp -= xpToNextLevel
        }
    }
}
