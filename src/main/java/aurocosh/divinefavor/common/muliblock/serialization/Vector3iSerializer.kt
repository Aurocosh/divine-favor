package aurocosh.divinefavor.common.muliblock.serialization

import com.google.gson.*
import net.minecraft.util.math.BlockPos

import java.lang.reflect.Type

class Vector3iSerializer : JsonDeserializer<BlockPos>, JsonSerializer<BlockPos> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BlockPos {
        val value = json.asLong
        return BlockPos.fromLong(value)
    }

    override fun serialize(src: BlockPos, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toLong())
    }
}

