package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanVolcanicGlassCutter(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun canHarvest(stack: ItemStack, event: PlayerEvent.HarvestCheck) {
        if (event.targetBlock.block == Blocks.OBSIDIAN)
            event.setCanHarvest(true)
    }

    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        if (event.state.block === Blocks.OBSIDIAN)
            event.newSpeed += ConfigTool.volcanicGlassCutter.miningSpeed
    }
}
