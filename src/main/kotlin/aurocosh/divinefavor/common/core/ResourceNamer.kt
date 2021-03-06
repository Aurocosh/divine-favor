package aurocosh.divinefavor.common.core

import aurocosh.divinefavor.DivineFavor
import net.minecraft.util.ResourceLocation

object ResourceNamer {
    fun getFullName(name: String): ResourceLocation {
        return ResourceLocation(DivineFavor.MOD_ID, name)
    }

    fun getFullName(prefix: String, name: String): ResourceLocation {
        return ResourceLocation(DivineFavor.MOD_ID, "$prefix.$name")
    }

    fun getTypedNameString(type: String, prefix: String, name: String): String {
        return "$type.${DivineFavor.MOD_ID}:$prefix.$name"
    }

    fun getTypedNameString(type: String, name: String): String {
        return "$type.${DivineFavor.MOD_ID}:$name"
    }

    fun getNameString(prefix: String, name: String): String {
        return "${DivineFavor.MOD_ID}:$prefix.$name"
    }

    fun getNameString(name: String): String {
        return "${DivineFavor.MOD_ID}:$name"
    }

    fun getItemName(type: String, name: String): ResourceLocation {
        return ResourceLocation(DivineFavor.MOD_ID, "${type}_$name")
    }

    fun debugSection(name: String): () -> String {
        return { DivineFavor.MOD_ID + ":debug.$name" }
    }
}
