package aurocosh.divinefavor.common.state_mappers.common;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomStateMappedBlock {
    @SideOnly(Side.CLIENT)
    IStateMapper getCustomStateMapper();
}
