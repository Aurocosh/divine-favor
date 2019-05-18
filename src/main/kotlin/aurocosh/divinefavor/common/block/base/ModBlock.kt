package aurocosh.divinefavor.common.block.base

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.base.ModItemBlock
import aurocosh.divinefavor.common.lib.interfaces.IOrdered
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.block.Block
import net.minecraft.block.material.Material

open class ModBlock(name: String, material: Material, override val orderIndex: Int) : Block(material), IOrdered {

    protected open fun getItemBlock(): ModItemBlock? = ModItemBlock(this, orderIndex)

    init {
        val fullName = ResourceNamer.getFullName(name)
        translationKey = fullName.toString()
        registryName = fullName
        ModRegistries.blocks.register(this)

        val itemBlock = getItemBlock()
        if (itemBlock != null) {
            itemBlock.registryName = fullName
            ModRegistries.itemBlocks.register(itemBlock)
        }
    }
}
