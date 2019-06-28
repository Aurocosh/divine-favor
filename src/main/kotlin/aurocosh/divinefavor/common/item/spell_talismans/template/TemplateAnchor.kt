package aurocosh.divinefavor.common.item.spell_talismans.template

import aurocosh.divinefavor.common.lib.EnumIndexer
import aurocosh.divinefavor.common.lib.IIndexedEnum

enum class TemplateAnchor()  {
    NorthEast,
    SouthEast,
    SouthWest,
    NorthWest,
    Center;

    companion object : IIndexedEnum<TemplateAnchor> {
        override val indexer: EnumIndexer<TemplateAnchor> = EnumIndexer(values())
    }
}