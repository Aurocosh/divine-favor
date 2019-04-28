package aurocosh.divinefavor.common.item.contract

import aurocosh.divinefavor.common.config.common.ConfigItem

object ModContracts {
    lateinit var capacity_minor: ItemFavorContract
    lateinit var capacity_major: ItemFavorContract
    lateinit var regen_minor: ItemFavorContract
    lateinit var regen_major: ItemFavorContract
    lateinit var inform: ItemFavorContract
    lateinit var creative: ItemFavorContract

    fun preInit() {
        capacity_minor = ItemFavorContract("capacity_minor", "capacity_minor", ConfigItem.contractCapacityMinor)
        capacity_major = ItemFavorContract("capacity_major", "capacity_major", ConfigItem.contractCapacityMajor)
        regen_minor = ItemFavorContract("regen_minor", "regen_minor", ConfigItem.contractRegenMinor)
        regen_major = ItemFavorContract("regen_major", "regen_major", ConfigItem.contractRegenMajor)
        inform = ItemFavorContract("inform", "inform", ConfigItem.contractInform)
        creative = ItemFavorContract("creative", "creative", ConfigItem.contractCreative)
    }
}