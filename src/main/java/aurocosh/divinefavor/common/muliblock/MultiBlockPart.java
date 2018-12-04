package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.block.Block;

public class MultiBlockPart {
    public final Vector3i position;
    public final Block block;

    public MultiBlockPart(Vector3i position, Block block) {
        this.position = position;
        this.block = block;
    }
}
