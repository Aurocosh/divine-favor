package aurocosh.divinefavor.common.muliblock.instance;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

public class MultiBlockInstanceAltar extends MultiBlockInstance {
    public final ModSpirit spirit;

    public MultiBlockInstanceAltar(ModMultiBlock multiBlock, MultiBlockConfiguration configuration, Vector3i controllerPosition, ModSpirit spirit) {
        super(multiBlock, configuration, controllerPosition);
        this.spirit = spirit;
    }
}
