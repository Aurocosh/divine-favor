package aurocosh.divinefavor.common.core

import aurocosh.divinefavor.common.constants.ConstMisc
import net.minecraft.util.ResourceLocation

object ResourceNamer {
    fun getFullName(name: String): ResourceLocation {
        return ResourceLocation(ConstMisc.MOD_ID, name)
    }

    fun getFullName(prefix: String, name: String): ResourceLocation {
        return ResourceLocation(ConstMisc.MOD_ID, "$prefix.$name")
    }

    fun debugSection(name: String): () -> String {
        return { ConstMisc.MOD_ID + ":debug.$name" }
    }
}
