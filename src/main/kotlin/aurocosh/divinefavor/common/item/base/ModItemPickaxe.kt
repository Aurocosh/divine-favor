package aurocosh.divinefavor.common.item.base

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.lib.interfaces.IOrdered
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ModItemPickaxe(name: String, override val texturePath: String, override val orderIndex: Int = 0, material: ToolMaterial) : ItemPickaxe(material), IModelHolder, IDescriptionProvider, IOrdered {

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
        ModRegistries.pickaxes.register(this)
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (!this.isInCreativeTab(tab))
            return
        items.add(ItemStack(this, 1))
    }
}
