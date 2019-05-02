package aurocosh.divinefavor.common.item.base

import aurocosh.divinefavor.common.lib.interfaces.IOrdered
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ModItemBlock(block: Block, private val orderIndex: Int) : ItemBlock(block), IOrdered, IDescriptionProvider {

    override val nameKey: String
        @SideOnly(Side.CLIENT)
        get() = "$translationKey.name"

    override val descriptionKey: String
        @SideOnly(Side.CLIENT)
        get() = "$translationKey.description"

    override fun getOrderIndex(): Int {
        return orderIndex
    }
}
