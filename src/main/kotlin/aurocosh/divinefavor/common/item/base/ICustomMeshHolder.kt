package aurocosh.divinefavor.common.item.base

import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface ICustomMeshHolder {
    @get:SideOnly(Side.CLIENT)
    val customMeshDefinition: ItemMeshDefinition
}
