package aurocosh.divinefavor.common.item.base

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.talisman.TalismanPropertyHandler
import aurocosh.divinefavor.common.lib.interfaces.IOrdered
import aurocosh.divinefavor.common.registry.ModRegistries
import aurocosh.divinefavor.common.stack_properties.IPropertyAccessor
import aurocosh.divinefavor.common.stack_properties.IPropertyContainer
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ModItemTool constructor(name: String, override val texturePath: String, override val orderIndex: Int = 0, material: Item.ToolMaterial, effectiveBlocks: Set<Block>) : ItemTool(material, effectiveBlocks), IModelHolder, IDescriptionProvider, IOrdered {

    override val nameKey: String
        @SideOnly(Side.CLIENT)
        get() = "$translationKey.name"

    override val descriptionKey: String
        @SideOnly(Side.CLIENT)
        get() = "$translationKey.description"

    init {
        val fullName = ResourceNamer.getFullName(name)
        translationKey = fullName.toString()
        registryName = fullName
        ModRegistries.itemsTools.register(this)
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (!this.isInCreativeTab(tab))
            return
        items.add(ItemStack(this, 1))
    }
}
