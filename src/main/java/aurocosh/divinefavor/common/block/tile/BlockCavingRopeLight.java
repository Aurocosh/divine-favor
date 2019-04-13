package aurocosh.divinefavor.common.block.tile;

import aurocosh.divinefavor.common.block.base.ModBlockAir;
import aurocosh.divinefavor.common.item.base.ModItemBlock;

public class BlockCavingRopeLight extends ModBlockAir {
    public BlockCavingRopeLight(String name) {
        super(name);
        this.setTickRandomly(true);
        this.lightValue = 6;
    }

    @Override
    public ModItemBlock getItemBlock() {
        return null;
    }
}