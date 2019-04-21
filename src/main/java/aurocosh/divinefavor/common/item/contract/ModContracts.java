package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.config.common.ConfigItem;

public final class ModContracts {
    public static ItemFavorContract capacity_minor;
    public static ItemFavorContract capacity_major;
    public static ItemFavorContract regen_minor;
    public static ItemFavorContract regen_major;
    public static ItemFavorContract inform;
    public static ItemFavorContract creative;

    public static void preInit() {
        capacity_minor = new ItemFavorContract("capacity_minor", "capacity_minor", ConfigItem.contractCapacityMinor);
        capacity_major = new ItemFavorContract("capacity_major", "capacity_major", ConfigItem.contractCapacityMajor);
        regen_minor = new ItemFavorContract("regen_minor", "regen_minor", ConfigItem.contractRegenMinor);
        regen_major = new ItemFavorContract("regen_major", "regen_major", ConfigItem.contractRegenMajor);
        inform = new ItemFavorContract("inform", "inform", ConfigItem.contractInform);
        creative = new ItemFavorContract("creative", "creative", ConfigItem.contractCreative);
    }
}