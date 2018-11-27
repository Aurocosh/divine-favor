package aurocosh.divinefavor.common.item.base;

import aurocosh.divinefavor.common.constants.LibMisc;
import vazkii.arl.interf.IVariantHolder;

public interface  IDivineFavorItem extends IVariantHolder {
    @Override
    default String getModNamespace() {
        return LibMisc.MOD_ID;
    }
}
