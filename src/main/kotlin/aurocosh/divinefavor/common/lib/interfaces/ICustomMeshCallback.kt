package aurocosh.divinefavor.common.lib.interfaces


import net.minecraft.client.renderer.ItemMeshDefinition
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface ICustomMeshCallback {

    /**
     * A callback to get a custom mesh definition
     * @return
     */
    @get:SideOnly(Side.CLIENT)
    val meshDefinition: ItemMeshDefinition
}