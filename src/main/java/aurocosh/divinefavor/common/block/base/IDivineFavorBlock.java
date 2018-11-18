package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.lib.LibMisc;
import vazkii.arl.interf.IModBlock;

public interface IDivineFavorBlock extends IModBlock {
    @Override
    default String getModNamespace() {
        return LibMisc.MOD_ID;
    }
}
