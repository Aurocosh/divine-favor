package aurocosh.divinefavor.common.state_mappers.common

import net.minecraft.client.renderer.color.BlockColors
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface ICustomColorHandlerBlock {
    @SideOnly(Side.CLIENT)
    fun initColorHandler(blockColors: BlockColors)
}
