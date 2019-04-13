package aurocosh.divinefavor.common.lib.interfaces;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public interface IMultipleItemModelDefinition {
    /**
     * A map from item meta values to different item models
     * @return
     */
    @SideOnly(Side.CLIENT)
    Map<Integer, ResourceLocation> getModels();
}