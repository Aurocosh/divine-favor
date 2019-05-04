package aurocosh.divinefavor.common.potions.base.potion

import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.registry.ModRegistries
import aurocosh.divinefavor.common.util.UtilGui
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.attributes.AbstractAttributeMap
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class ModPotion(name: String, protected var beneficial: Boolean, potionColor: Int) : Potion(false, potionColor) {
    var icon: ResourceLocation

    protected var isCurse: Boolean = false

    init {
        isCurse = false
        this.icon = ResourceLocation(ConstMisc.MOD_ID, "textures/potions/$name.png")

        //potion.setIcon(potion.getIcon());

        val fullName = ResourceNamer.getFullName(name)
        setPotionName("potion.$fullName.name")
        registryName = fullName
        ModRegistries.potions.register(this)
    }

    fun setIsCurse(curse: Boolean) {
        beneficial = false
        isCurse = curse
    }

    override fun affectEntity(source: Entity?, indirectSource: Entity?, entityLivingBaseIn: EntityLivingBase, amplifier: Int, health: Double) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health)
    }

    @SideOnly(Side.CLIENT)
    override fun isBeneficial(): Boolean {
        return this.beneficial
    }

    @SideOnly(Side.CLIENT)
    override fun renderInventoryEffect(x: Int, y: Int, effect: PotionEffect, mc: Minecraft) {
        UtilGui.drawTexture(mc, icon, x + 6, y + 6, 16, 16)
        renderCustomInvText(x, y, effect, mc)
    }

    @SideOnly(Side.CLIENT)
    open fun renderCustomInvText(x: Int, y: Int, effect: PotionEffect, mc: Minecraft) {
    }

    @SideOnly(Side.CLIENT)
    override fun renderHUDEffect(x: Int, y: Int, effect: PotionEffect, mc: Minecraft, alpha: Float) {
        UtilGui.drawTexture(mc, icon, x + 4, y + 4, 16, 16)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {}

    override fun getCurativeItems(): List<ItemStack> {
        val ret = java.util.ArrayList<ItemStack>()
        ret.add(ItemStack(Items.MILK_BUCKET))
        return ret
    }

    override fun applyAttributesModifiersToEntity(livingBase: EntityLivingBase, attributeMap: AbstractAttributeMap, amplifier: Int) {
        super.applyAttributesModifiersToEntity(livingBase, attributeMap, amplifier)
        onPotionAdded(livingBase)
    }

    override fun removeAttributesModifiersFromEntity(livingBase: EntityLivingBase, attributeMap: AbstractAttributeMap, amplifier: Int) {
        super.removeAttributesModifiersFromEntity(livingBase, attributeMap, amplifier)
        onPotionRemoved(livingBase)
    }

    protected open fun onPotionAdded(livingBase: EntityLivingBase) {
        if (isCurse) {
            val curse = livingBase.divineLivingData.curseData
            curse.addCurse()
        }
    }

    protected open fun onPotionRemoved(livingBase: EntityLivingBase) {
        if (isCurse) {
            val curse = livingBase.divineLivingData.curseData
            curse.removeCurse()
        }
    }
}
