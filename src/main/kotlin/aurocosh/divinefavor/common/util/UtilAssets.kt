package aurocosh.divinefavor.common.util

import com.google.gson.Gson
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.fml.common.ModContainer
import org.apache.commons.io.IOUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.util.*

object UtilAssets {
    fun getAssetPaths(mod: ModContainer, assetFolderPath: String, fileFormat: String): ArrayList<String> {
        val foundPaths = ArrayList<String>()
        val id = mod.modId
        val base = String.format("assets/%s/%s", id, assetFolderPath)

        CraftingHelper.findFiles(mod, base, { path -> Files.exists(path) },
                { _, file ->
                    if (file.toString().endsWith(fileFormat)) {
                        val fileStr = file.toString().replace("\\\\".toRegex(), "/")
                        val assetPath = fileStr.substring(fileStr.indexOf("/assets"))
                        foundPaths.add(assetPath)
                    }
                    true
                }, false, true)
        return foundPaths
    }

    fun <T> loadObjectsFromJsonAssets(clazz: Class<T>, mod: ModContainer, assetPaths: List<String>, gson: Gson): ArrayList<T> {
        val objectList = ArrayList<T>()
        val modObject = mod.mod ?: return objectList
        val modClass = modObject.javaClass
        for (path in assetPaths) {
            val stream = modClass.getResourceAsStream(path)
            val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))

            val element = gson.fromJson(reader, clazz)
            objectList.add(element)
        }
        return objectList
    }

    fun loadTextFile(mod: ModContainer, path: String): String {
        val modClass = mod.mod.javaClass
        val stream = modClass.getResourceAsStream(path) ?: return ""

        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }
}
