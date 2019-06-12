package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import java.util.*

class SpellTalismanWindStep(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        addVelocity(context.player)
    }

    override fun performActionClient(context: TalismanContext) {
        addVelocity(context.player)
    }

    private fun addVelocity(player: EntityPlayer) {
        val lookVec = player.lookVec
        player.motionX += lookVec.x * ConfigSpell.windStep.addedVelocityXZ
        player.motionY += lookVec.y * ConfigSpell.windStep.addedVelocityY
        player.motionZ += lookVec.z * ConfigSpell.windStep.addedVelocityXZ
    }
}
