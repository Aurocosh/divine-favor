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
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ItemTalisman(val name: String, texturePath: String, val spirit: ModSpirit, val favorCost: Int) : ModItem(name, texturePath) {

    private val propertyList = ArrayList<TalismanProperty<out Any>>()
    private val propertyMap = HashMap<String, TalismanProperty<out Any>>()

    val spiritId: Int
        get() = spirit.id

    init {
        setMaxStackSize(1)
    }

    val properties: List<TalismanProperty<out Any>>
        get() = propertyList

    fun getProperty(name: String) = propertyMap[name]

    private fun addProperty(property: TalismanProperty<out Any>) {
        if (propertyMap.containsKey(property.name)) {
            DivineFavor.logger.error("Talisman property conflict in talisman $name. Conflicting property name ${property.name}")
        } else {
            propertyList.add(property)
            propertyMap[property.name] = property
        }
    }

    protected fun registerIntProperty(name: String, prefix: String, suffix: String, defaultValue: Int, minValue: Int, maxValue: Int): TalismanPropertyInt {
        val property = TalismanPropertyInt(name, prefix, suffix, defaultValue, minValue, maxValue)
        addProperty(property)
        return property
    }

    protected fun registerBoolProperty(name: String, prefix: String, defaultValue: Boolean): TalismanPropertyBool {
        val property = TalismanPropertyBool(name, prefix, "tooltip.$name", defaultValue)
        addProperty(property)
        return property
    }

    abstract fun validateCastType(context: TalismanContext): Boolean

    fun cast(context: TalismanContext): Boolean {
        if (!context.valid)
            return false
        if (!validateCastType(context))
            return false
        if (!validate(context))
            return false
        if (isConsumeCharge(context) && !claimCost(context.player))
            return false
        if (context.world.isRemote)
            performActionClient(context)
        else
            performActionServer(context)
        return true
    }

    fun claimCost(player: EntityLivingBase): Boolean {
        if (favorCost == 0)
            return true
        if (player !is EntityPlayer)
            return false

        val spiritData = player.divinePlayerData.spiritData
        if (!spiritData.consumeFavor(spirit.id, favorCost))
            return false
        if (player.world.isRemote)
            return true

        MessageSyncFavor(spirit, spiritData).sendTo(player)
        return true
    }

    @SideOnly(Side.CLIENT)
    fun getUseInfo(player: EntityPlayer): String {
        val spiritData = player.divinePlayerData.spiritData
        val favorValue = spiritData.getFavor(spirit.id)
        val infinite = spiritData.isFavorInfinite(spirit.id) || favorCost == 0

        val useCount = if (infinite) -1 else favorValue / favorCost
        val description: String
        if (useCount < 0)
            description = I18n.format("tooltip.divinefavor:talisman.infinite_use")
        else if (useCount == 0)
            description = I18n.format("tooltip.divinefavor:talisman.unusable")
        else
            description = I18n.format("tooltip.divinefavor:talisman.cost", favorCost, useCount)
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
        val favorCost = talisman.getUseInfo(player)
        tooltip.add(favorCost)

        val spirit = talisman.spirit
        val name = I18n.format(spirit.nameTranslationKey)
        val message = I18n.format("tooltip.divinefavor:talisman.spirit", name)
        tooltip.add(message)
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
}