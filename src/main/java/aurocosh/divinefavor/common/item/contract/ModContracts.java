package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.config.common.ConfigItem;

public final class ModContracts {
    public static ItemContract capacity_minor;
    public static ItemContract capacity_major;
    public static ItemContract regen_minor;
    public static ItemContract regen_major;
    public static ItemContract inform;
    public static ItemContract creative;

    public static void preInit() {
        capacity_minor = new ItemContract("capacity_minor", "capacity_minor", 0, ConfigItem.contractCapacityMinor);
        capacity_major = new ItemContract("capacity_major", "capacity_major", 0, ConfigItem.contractCapacityMajor);
        regen_minor = new ItemContract("regen_minor", "regen_minor", 0, ConfigItem.contractRegenMinor);
        regen_major = new ItemContract("regen_major", "regen_major", 0, ConfigItem.contractRegenMajor);
        inform = new ItemContract("inform", "inform", 0, ConfigItem.contractInform);
        creative = new ItemContract("creative", "creative", 0, ConfigItem.contractCreative);
    }
}