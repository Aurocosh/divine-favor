package aurocosh.divinefavor.common.item.talismans.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ItemTalisman(val name: String, texturePath: String, val spirit: ModSpirit, val favorCost: Int) : ModItem(name, texturePath) {

    val spiritId: Int
        get() = spirit.id

    init {
        setMaxStackSize(1)
    }

    @SideOnly(Side.CLIENT)
    fun getUseInfo(player: EntityPlayer): String {
        val spiritData = PlayerData.get(player).spiritData
        val favorValue = spiritData.getFavor(spirit.id)

        val useCount = if (favorCost == 0) -1 else favorValue / favorCost
        val description: String
        if (useCount < 0)
            description = I18n.format("tooltip.divinefavor:talisman.infinite_use")
        else if (useCount == 0)
            description = I18n.format("tooltip.divinefavor:talisman.unusable")
        else
            description = I18n.format("tooltip.divinefavor:talisman.cost", favorCost, useCount)
        return description
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack?, world: World?, tooltip: MutableList<String>?, flag: ITooltipFlag?) {
        super.addInformation(stack, world, tooltip, flag)

        val player = DivineFavor.proxy.clientPlayer ?: return

        val talisman = stack!!.item as ItemTalisman
        val favorCost = talisman.getUseInfo(player)
        tooltip!!.add(favorCost)

        val spirit = talisman.spirit
        val name = I18n.format(spirit.nameTranslationKey)
        val message = I18n.format("tooltip.divinefavor:talisman.spirit", name)
        tooltip.add(message)
    }
}