package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.item.ItemStack
import net.minecraftforge.event.world.BlockEvent
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.init.Enchantments
import net.minecraft.enchantment.EnchantmentHelper

class ToolTalismanMoltenTool(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        return true
    }

    override fun catchDrops(stack: ItemStack, toolStack: ItemStack, event: BlockEvent.HarvestDropsEvent) {
        val spellPick = toolStack.item as? ItemSpellPick ?: return
        if (!spellPick.canHarvestBlock(event.state))
            return

        val listIterator = event.drops.listIterator()
        for (drop in listIterator) {
            var smeltResult = FurnaceRecipes.instance().getSmeltingResult(drop)
            if (!smeltResult.isEmpty) {
                smeltResult = smeltResult.copy()
                smeltResult.count = drop.count
                val fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, toolStack)
                if (fortune > 0)
                    smeltResult.count = smeltResult.count * UtilRandom.nextInt(1, fortune + 1)
                listIterator.set(smeltResult)

                var xp = FurnaceRecipes.instance().getSmeltingExperience(smeltResult)
                if (xp < 1 && Math.random() < xp)
                    xp += 1f
                if (xp >= 1f)
                    event.state.block.dropXpOnBlockBreak(event.world, event.pos, xp.toInt())
            }
        }
    }
}
