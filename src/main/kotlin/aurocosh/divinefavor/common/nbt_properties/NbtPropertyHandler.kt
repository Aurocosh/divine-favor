package aurocosh.divinefavor.common.nbt_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.nbt_properties.interfaces.INbtPropertyAccessor
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class NbtPropertyHandler(private val parentName: String = "") : INbtPropertyAccessor {
    private val propertyList = ArrayList<NbtProperty<out Any>>()
    private val propertyMap = HashMap<String, NbtProperty<out Any>>()

    override val list get() = propertyList
    override operator fun get(name: String) = propertyMap[name]

    override fun exist(name: String) = propertyMap.containsKey(name)
    override fun exist(property: NbtProperty<out Any>): Boolean = propertyMap.containsKey(property.name)

    fun <T : Any, K : NbtProperty<T>> registerProperty(property: K): K {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Nbt property conflict in $parentName. Conflicting property name ${property.name}")
        } else {
            propertyMap[property.name] = property
            propertyList.add(property)
        }
        return property
    }
}