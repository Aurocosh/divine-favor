package aurocosh.divinefavor.common.muliblock;

import net.minecraft.world.World;

public interface IMultiblockController {
    World getWorld();
    ModMultiBlockInstance getMultiblockInstance();

    void multiblockDamaged();
    void tryToFormMultiBlock();
}
