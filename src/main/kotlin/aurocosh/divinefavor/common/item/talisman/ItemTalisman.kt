package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.item.talisman.properties.TalismanProperty
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ItemTalisman(val name: String, texturePath: String, val spirit: ModSpirit, protected val favorCost: Int) : ModItem(name, texturePath) {

    private val propertyList = ArrayList<TalismanProperty<out Any>>()
    private val propertyMap = HashMap<String, TalismanProperty<out Any>>()

    val spiritId: Int
        get() = spirit.id

    init {
        setMaxStackSize(1)
    }

    val properties: List<TalismanProperty<out Any>>
        get() = propertyList

    fun getProperty(index: Int) = propertyList[index]

    fun getProperty(name: String) = propertyMap[name]

    private fun addProperty(property: TalismanProperty<out Any>) {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Talisman property conflict in talisman $name. Conflicting property name ${property.name}")
        } else {
            propertyList.add(property)
            propertyMap[property.name] = property
        }
    }

    fun getSelectedPropertyIndex(stack: ItemStack): Int {
        if (propertyList.isEmpty())
            return -1;
        if (!stack.hasTagCompound())
            return 0
        return stack.compound.getInteger(TAG_PROPERTY_INDEX)
    }

    fun setSelectedPropertyIndex(stack: ItemStack, index: Int) {
        if (propertyList.isEmpty())
            return
        stack.compound.setInteger(TAG_PROPERTY_INDEX, index)
    }

    protected fun registerIntProperty(name: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = defaultValue): TalismanPropertyInt {
        val property = TalismanPropertyInt(name, defaultValue, minValue, maxValue)
        addProperty(property)
        return property
    }

    protected fun registerBoolProperty(name: String, defaultValue: Boolean): TalismanPropertyBool {
        val property = TalismanPropertyBool(name, defaultValue)
        addProperty(property)
        return property
    }

    open fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost
    }

    abstract fun validateCastType(context: TalismanContext): Boolean

    fun cast(context: TalismanContext): Boolean {
        if (!context.valid)
            return false
        if (!validateCastType(context))
            return false
        if (!validate(context))
            return false
        if (isConsumeCharge(context) && !claimCost(context.player, context.stack))
            return false
        if (context.world.isRemote)
            performActionClient(context)
        else
            performActionServer(context)
        return true
    }

    fun claimCost(player: EntityLivingBase, stack: ItemStack): Boolean {
        val trueCost = getFavorCost(stack)
        if (trueCost == 0)
            return true
        if (player !is EntityPlayer)
            return false

        val spiritData = player.divinePlayerData.spiritData
        if (!spiritData.consumeFavor(spirit.id, trueCost))
            return false
        if (player.world.isRemote)
            return true

        MessageSyncFavor(spirit, spiritData).sendTo(player)
        return true
    }

    @SideOnly(Side.CLIENT)
    fun getUseInfo(player: EntityPlayer, stack: ItemStack): String {
        val spiritData = player.divinePlayerData.spiritData
        val favorValue = spiritData.getFavor(spirit.id)
        val trueFavorCost = getFavorCost(stack)
        val infinite = spiritData.isFavorInfinite(spirit.id) || trueFavorCost == 0

        val useCount = if (infinite) -1 else favorValue / trueFavorCost
        val description: String
        if (useCount < 0)
            description = I18n.format("tooltip.divinefavor:talisman.infinite_use")
        else if (useCount == 0)
            description = I18n.format("tooltip.divinefavor:talisman.unusable")
        else
            description = I18n.format("tooltip.divinefavor:talisman.cost", trueFavorCost, useCount)
        return description
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        super.addInformation(stack, world, tooltip, flag)
        if (!DivineFavor.proxy.hasClientPlayer)
            return

        val player = DivineFavor.proxy.clientPlayer

        val talisman = stack.item as ItemTalisman
        val favorCost = talisman.getUseInfo(player, stack)
        tooltip.add(favorCost)

        val spirit = talisman.spirit
        val name = I18n.format(spirit.nameTranslationKey)
        val message = I18n.format("tooltip.divinefavor:talisman.spirit", name)
        tooltip.add(message)

        if (propertyList.isNotEmpty()) {
            tooltip.add("")
            tooltip.add(I18n.format("tooltip.divinefavor:property_list"))
            propertyList.map { it.toLocalString(stack) }.forEach { tooltip.add(it) }
        }
    }

    protected open fun validate(context: TalismanContext): Boolean {
        return true
    }

    protected open fun isConsumeCharge(context: TalismanContext): Boolean {
        return true
    }

    open fun raycastBlock(): Boolean {
        return false
    }

    open fun raycastTarget(): Boolean {
        return false
    }

    protected open fun performActionServer(context: TalismanContext) {}

    protected open fun performActionClient(context: TalismanContext) {}

    companion object {
        private const val TAG_PROPERTY_INDEX = "PropertyIndex"
    }
}