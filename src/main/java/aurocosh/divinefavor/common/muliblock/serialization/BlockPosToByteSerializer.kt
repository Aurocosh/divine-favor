package aurocosh.divinefavor.common.muliblock.serialization

import com.google.gson.*
import net.minecraft.util.math.BlockPos

import java.lang.reflect.Type

class BlockPosToByteSerializer : JsonDeserializer<BlockPos>, JsonSerializer<BlockPos> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BlockPos {
        val jsonObject = json.asJsonArray

        val x = jsonObject.get(0).asByte
        val y = jsonObject.get(1).asByte
        val z = jsonObject.get(2).asByte
        return BlockPos(x.toInt(), y.toInt(), z.toInt())
    }

    override fun serialize(src: BlockPos, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonArray()
        json.add(src.x.toByte())
        json.add(src.y.toByte())
        json.add(src.z.toByte())
        return json
    }
}

