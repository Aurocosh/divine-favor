package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.init.Blocks
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraftforge.items.CapabilityItemHandler
import java.util.*

class SpellTalismanCombustion(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun performActionServer(context: TalismanContext) {
        val pos = context.pos
        if (context.world.getBlock(pos) !== Blocks.CHEST)
            return

        val entity = context.world.getTileEntity(pos)
        val stackHandler = entity!!.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, context.facing)

        var stacksToSmelt = ConfigSpells.combustion.maxStacksToSmelt
        val slotCount = stackHandler!!.slots
        var i = 0
        while (i < slotCount && stacksToSmelt > 0) {
            val stack = stackHandler.getStackInSlot(i)
            val result = FurnaceRecipes.instance().getSmeltingResult(stack)

            if (result.isEmpty) {
                i++
                continue
            }
            if (!UtilRandom.rollDiceFloat(ConfigSpells.combustion.smeltingChance)) {
                i++
                continue
            }

            val smelted = result.copy()
            smelted.count = stack.count

            stackHandler.extractItem(i, stack.count, false)
            stackHandler.insertItem(i, smelted, false)
            stacksToSmelt--
            i++
        }

        context.world.newExplosion(context.player, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), ConfigSpells.combustion.explosionPower, ConfigSpells.combustion.causeFire, ConfigSpells.combustion.damageTerraing)
    }
}
