package aurocosh.divinefavor.common.item.spell_talismans.sense

import aurocosh.divinefavor.common.config.entries.spell_talismans.generic.SenseConfig
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getOther
import aurocosh.divinefavor.common.lib.extensions.name
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemBlock
import java.util.*
import javax.vecmath.Color3f

class SpellTalismanSenseInSphereHeld(name: String, spirit: ModSpirit, options: EnumSet<SpellOptions>, color3f: Color3f, senseConfig: SenseConfig) : SpellTalismanSenseInSphere(name, spirit, options, color3f, senseConfig, SenseBlockPredicate.BLOCK) {

    override fun validate(context: TalismanContext): Boolean {
        val heldItem = context.player.getHeldItem(context.hand.getOther())
        if (heldItem.isEmpty)
            return false
        val item = heldItem.item
        return item is ItemBlock
    }

    override fun performActionServer(context: TalismanContext) {
        val player = context.player

        val heldItem = player.getHeldItem(context.hand.getOther())
        val itemBlock = heldItem.item as ItemBlock

        highlightBlocks(radius, player, player.position, minShift, maxShift, color3f, itemBlock.block.name)
    }
}
