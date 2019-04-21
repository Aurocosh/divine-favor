package aurocosh.divinefavor.common.muliblock.instance;

import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.BlockPos;

public class MultiBlockInstanceAltar extends MultiBlockInstance {
    public final ModSpirit spirit;

    public MultiBlockInstanceAltar(ModMultiBlock multiBlock, MultiBlockConfiguration configuration, BlockPos controllerPosition, ModSpirit spirit) {
        super(multiBlock, configuration, controllerPosition);
        this.spirit = spirit;
    }
}
