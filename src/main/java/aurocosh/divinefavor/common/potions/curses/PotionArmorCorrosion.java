package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.corrosion.ArmorCorrosionData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionArmorCorrosion extends ModPotion {
    public PotionArmorCorrosion() {
        super("armor_corrosion", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer)) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion);
            return;
        }

        List<Integer> slots = new ArrayList<>();
        slots.add(0);
        slots.add(1);
        slots.add(2);
        slots.add(3);

        EntityPlayer player = (EntityPlayer) livingBase;
        ArmorCorrosionData corrosionData = PlayerData.get(player).getArmorCorrosionData();
        corrosionData.removeAllCorrosion();

        int slotsToCorrode = UtilRandom.nextInt(ConfigArrow.armorCorrosion.minSlotsToCorrode, ConfigArrow.armorCorrosion.maxSlotsToCorrode);
        for (int i = 0; i < slotsToCorrode; i++) {
            int index = UtilRandom.getRandomIndex(slots);
            corrosionData.addCorrosionToArmorSlot(slots.get(index));
            slots.remove(index);
        }
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ArmorCorrosionData corrosionData = PlayerData.get(player).getArmorCorrosionData();
        corrosionData.removeAllCorrosion();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (!(livingBase instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) livingBase;
        ArmorCorrosionData corrosionData = PlayerData.get(player).getArmorCorrosionData();
        if (corrosionData.nothingToCorrode()) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion);
            return;
        }

        if (corrosionData.isCorrosionNeeded()) {
            List<Integer> slots = new ArrayList<>(corrosionData.getCorrodedArmorSlots());
            for (Integer slot : slots) {
                ItemStack stack = player.inventory.armorItemInSlot(slot);
                if (stack.isEmpty())
                    corrosionData.removeCorrosionFromArmorSlot(slot);
                else
                    stack.damageItem(ConfigArrow.armorCorrosion.corrosionDamage, player);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
