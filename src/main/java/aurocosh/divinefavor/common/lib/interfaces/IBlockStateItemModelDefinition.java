package aurocosh.divinefavor.common.lib.interfaces;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public interface IBlockStateItemModelDefinition {
    /**
     * A maps from item meta values to blockstate variants
     * @return
     */
    @SideOnly(Side.CLIENT)
    Map<Integer, String> getVariants();
}