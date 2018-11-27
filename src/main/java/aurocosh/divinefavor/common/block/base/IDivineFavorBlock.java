package aurocosh.divinefavor.common.block.base;

import aurocosh.divinefavor.common.constants.LibMisc;
import vazkii.arl.interf.IModBlock;

public interface IDivineFavorBlock extends IModBlock {
    @Override
    default String getModNamespace() {
        return LibMisc.MOD_ID;
    }
}
