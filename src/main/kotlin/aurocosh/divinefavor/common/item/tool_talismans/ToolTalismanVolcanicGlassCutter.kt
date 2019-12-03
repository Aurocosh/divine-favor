package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanVolcanicGlassCutter(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun canHarvest(stack: ItemStack, state: IBlockState, toolCanHarvest: Boolean): Boolean {
        return toolCanHarvest || state.block == Blocks.OBSIDIAN
    }

    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        if (event.state.block === Blocks.OBSIDIAN)
            event.newSpeed += ConfigTool.volcanicGlassCutter.miningSpeed
    }

    override fun getHarvestLevel(stack: ItemStack, toolClass: String, player: EntityPlayer?, blockState: IBlockState?): Int {
        val state = blockState ?: return -1;
        if(state.block == Blocks.OBSIDIAN)
            return 5;
        return -1;
    }
}
