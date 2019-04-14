package aurocosh.divinefavor.common.state_mappers;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

import java.util.Collections;
import java.util.Map;

public class InvisibleStateMapper implements IStateMapper {
    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
        return Collections.emptyMap();
    }
}
