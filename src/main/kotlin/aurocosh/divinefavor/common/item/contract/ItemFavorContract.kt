package aurocosh.divinefavor.common.item.contract

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.entries.items.Contract
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemFavorContract(name: String, texturePath: String, val regen: Int, val min: Int, val max: Int, val isInformActivity: Boolean) : ItemContract(name, texturePath) {

    constructor(name: String, texturePath: String, contract: Contract) : this(name, texturePath, contract.regen, contract.minimum, contract.maximum, contract.informActivity) {}

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        if (regen != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.regen", regen))
        if (min != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.min", min))
        if (max != 0)
            tooltip.add(I18n.format("item.divinefavor:contract.max", max))
        if (isInformActivity)
            tooltip.add(I18n.format("item.divinefavor:contract.informActivity"))
    }
}
