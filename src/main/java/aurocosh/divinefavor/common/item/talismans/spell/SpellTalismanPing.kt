package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.entity.EntityPing
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.SoundEvents
import net.minecraft.util.SoundCategory
import java.util.*

class SpellTalismanPing(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val pos = context.posVec

        val ropeNode = EntityPing(context.world)
        ropeNode.setLocationAndAngles(pos.x, pos.y, pos.z, 0f, 0f)
        context.world.spawnEntity(ropeNode)
        context.world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.BLOCK_NOTE_BELL, SoundCategory.PLAYERS, 1f, 1.5f)
    }
}
