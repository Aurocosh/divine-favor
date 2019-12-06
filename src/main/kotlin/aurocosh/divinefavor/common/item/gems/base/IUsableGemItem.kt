package aurocosh.divinefavor.common.item.gems.base

import aurocosh.divinefavor.common.spirit.base.ModSpirit

interface IUsableGemItem {
    val consumeOnUse: Boolean
    val favorCost: Int
    val spirit: ModSpirit
}