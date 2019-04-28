package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.Mod
import java.util.*
import java.util.Arrays.asList

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionArmorCorrosion : ModPotion("armor_corrosion", true, 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion)
            return
        }

        val corrosionData = PlayerData.get(livingBase).armorCorrosionData
        corrosionData.removeAllCorrosion()

        val slots = asList(0,1,2,3)
        val slotsToCorrode = UtilRandom.nextInt(ConfigArrow.armorCorrosion.minSlotsToCorrode, ConfigArrow.armorCorrosion.maxSlotsToCorrode)
        for (i in 0 until slotsToCorrode) {
            val index = UtilRandom.getRandomIndex(slots)
            corrosionData.addCorrosionToArmorSlot(slots[index])
            slots.removeAt(index)
        }
    }

    override fun onPotionRemoved(livingBase: EntityLivingBase) {
        super.onPotionRemoved(livingBase)
        if (livingBase !is EntityPlayer)
            return
        val corrosionData = PlayerData.get(livingBase).armorCorrosionData
        corrosionData.removeAllCorrosion()
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase !is EntityPlayer)
            return

        val corrosionData = PlayerData.get(livingBase).armorCorrosionData
        if (corrosionData.nothingToCorrode()) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion)
            return
        }

        if (corrosionData.isCorrosionNeeded) {
            val slots = ArrayList(corrosionData.corrodedArmorSlots)
            for (slot in slots) {
                val stack = livingBase.inventory.armorItemInSlot(slot!!)
                if (stack.isEmpty)
                    corrosionData.removeCorrosionFromArmorSlot(slot)
                else
                    stack.damageItem(ConfigArrow.armorCorrosion.corrosionDamage, livingBase)
            }
        }
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }
}
