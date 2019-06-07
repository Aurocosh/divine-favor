package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncEvilEye
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import java.util.*

class SpellTalismanEvilEye(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun validate(context: TalismanContext): Boolean {
        return context.target != null
    }

    override fun performActionServer(context: TalismanContext) {
        val player = context.player

        val evilEyeData = player.divinePlayerData.evilEyeData
        if (player.isPotionActive(ModCurses.evil_eye))
            evilEyeData.increaseSeverity(ConfigSpells.evilEye.severityIncrease)
        else
            evilEyeData.severity = ConfigSpells.evilEye.startingSeverity

        val severity = evilEyeData.severity
        val target = context.target as EntityLivingBase
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), (ConfigSpells.evilEye.damagePerSeverity * severity).toFloat())
        target.addPotionEffect(PotionEffect(MobEffects.SLOWNESS, ConfigSpells.evilEye.slownessTime, ConfigSpells.evilEye.slownessLevel))
        player.addPotionEffect(ModEffect(ModCurses.evil_eye, ConfigSpells.evilEye.evilEyeTime).setIsCurse())

        MessageSyncEvilEye(severity).sendTo(player)
    }
}
