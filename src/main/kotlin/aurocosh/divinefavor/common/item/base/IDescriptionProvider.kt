package aurocosh.divinefavor.common.item.base

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface IDescriptionProvider {
    @get:SideOnly(Side.CLIENT)
    val nameKey: String

    @get:SideOnly(Side.CLIENT)
    val descriptionKey: String
}
