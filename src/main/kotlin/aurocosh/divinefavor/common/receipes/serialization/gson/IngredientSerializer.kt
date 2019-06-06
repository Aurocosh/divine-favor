//package aurocosh.divinefavor.common.receipes.serialization.gson
//
//import aurocosh.divinefavor.common.muliblock.validators.AirStateValidator
//import aurocosh.divinefavor.common.muliblock.validators.BlockStateValidator
//import aurocosh.divinefavor.common.muliblock.validators.MultiBlockStateValidator
//import aurocosh.divinefavor.common.muliblock.validators.StateValidator
//import aurocosh.divinefavor.common.receipes.serialization.ingredients.RecipeIngredient
//import com.google.gson.*
//import net.minecraft.util.ResourceLocation
//import java.lang.reflect.Type
//import java.util.*
//
//class IngredientSerializer : JsonDeserializer<RecipeIngredient>, JsonSerializer<RecipeIngredient> {
//    @Throws(JsonParseException::class)
//    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RecipeIngredient? {
//        val jsonObject = json.asJsonObject
//        val type = jsonObject.get("type").asString
//        when (type) {
//            "" -> {
//                val blockName = jsonObject.get("name").asString
//                return BlockStateValidator(ResourceLocation(blockName))
//            }
//            "block" -> {
//                val blockName = jsonObject.get("name").asString
//                return BlockStateValidator(ResourceLocation(blockName))
//            }
//            "blocks" -> {
//                val names = ArrayList<ResourceLocation>()
//                val blocks = jsonObject.getAsJsonArray("names")
//                for (block in blocks)
//                    names.add(ResourceLocation(block.toString()))
//                return MultiBlockStateValidator(names)
//
//            }
//            "air" -> return AirStateValidator()
//            else -> return null
//        }
//    }
//
//    override fun serialize(src: RecipeIngredient, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
//        val json = JsonObject()
//        when (src) {
//            is BlockStateValidator -> {
//                json.addProperty("type", "block")
//                json.addProperty("name", src.name.toString())
//            }
//            is MultiBlockStateValidator -> {
//                json.addProperty("type", "blocks")
//                val blocks = JsonArray()
//                for (name in src.names)
//                    blocks.add(name.toString())
//                json.add("names", blocks)
//            }
//            is AirStateValidator -> json.addProperty("type", "air")
//        }
//        return json
//    }
//}
//
