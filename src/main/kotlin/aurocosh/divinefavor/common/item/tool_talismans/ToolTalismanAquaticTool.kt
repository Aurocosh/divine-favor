package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanAquaticTool(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        if (event.entityPlayer.isInWater)
            event.newSpeed += ConfigTool.aquaticTool.miningSpeed
    }
}
