package aurocosh.divinefavor.common.item.contract;

public final class ModContracts {
    public static ItemContract capacity_minor;
    public static ItemContract capacity_major;
    public static ItemContract regen_minor;
    public static ItemContract regen_major;
    public static ItemContract creative;

    public static void preInit() {
        capacity_minor = new ItemContract("capacity_minor", "capacity_minor", 0, 0, 0, 100);
        capacity_major = new ItemContract("capacity_major", "capacity_major", 0, 0, 0, 500);
        regen_minor = new ItemContract("regen_minor", "regen_minor", 0, 20, 0, 0);
        regen_major = new ItemContract("regen_major", "regen_major", 0, 200, 0, 0);
        creative = new ItemContract("creative", "creative", 0, 3000, 2000, 6000);
    }
}