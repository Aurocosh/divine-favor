package aurocosh.divinefavor.common.item.base;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDescriptionProvider {
    @SideOnly(Side.CLIENT)
    String getNameKey();

    @SideOnly(Side.CLIENT)
    String getDescriptionKey();
}
