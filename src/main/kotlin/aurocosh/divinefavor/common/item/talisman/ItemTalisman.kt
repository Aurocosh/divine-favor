package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.stack_properties.IPropertyAccessor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ItemTalisman(val name: String, texturePath: String, val spirit: ModSpirit, protected val favorCost: Int) : ModItem(name, texturePath), IBlockCatcher {
    protected val propertyHandler: TalismanPropertyHandler = TalismanPropertyHandler("talisman $name properties")
    val properties: IPropertyAccessor = propertyHandler

    val spiritId: Int
        get() = spirit.id

    init {
        setMaxStackSize(1)
    }

    open fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost
    }

    abstract fun validateCastType(context: TalismanContext): Boolean

    fun cast(context: TalismanContext): Boolean {
        if (!validateCastType(context))
            return false
        if(!preprocess(context))
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

        properties.getPropertyTooltip(stack).forEach { tooltip.add(it) }
    }

    protected open fun preprocess(context: TalismanContext): Boolean = context.valid
    protected open fun validate(context: TalismanContext): Boolean = true
    protected open fun isConsumeCharge(context: TalismanContext): Boolean = true

    open fun raycastBlock(): Boolean = false
    open fun raycastTarget(): Boolean = false

    protected open fun performActionServer(context: TalismanContext) {}
    protected open fun performActionClient(context: TalismanContext) {}

    @SideOnly(Side.CLIENT)
    open fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
    }

    override fun canCatch(): Boolean  = false
}