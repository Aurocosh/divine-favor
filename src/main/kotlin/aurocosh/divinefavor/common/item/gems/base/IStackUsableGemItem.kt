package aurocosh.divinefavor.common.item.gems.base

import aurocosh.divinefavor.common.item.base.ICastable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.interfaces.IStackPropertyContainer

interface IStackUsableGemItem : ICastable, IStackPropertyContainer {
    val consumeOnUse: Boolean
    val favorCost: Int
    val spirit: ModSpirit
}