package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.corrosion.IArmorCorrosionStatusHandler;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static aurocosh.divinefavor.common.custom_data.living.corrosion.ArmorCorrosionGillsDataHandler.CAPABILITY_ARMOR_CORROSION;

@Mod.EventBusSubscriber
public class PotionArmorCorrosion extends ModPotion {
    public final int DAMAGE_RATE = 1;

    public final int MIN_SLOTS_TO_CORRODE = 1;
    public final int MAX_SLOTS_TO_CORRODE = 3;

    public PotionArmorCorrosion() {
        super("armor_corrosion", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);

        List<Integer> slots = new ArrayList<>();
        slots.add(0);
        slots.add(1);
        slots.add(2);
        slots.add(3);

        IArmorCorrosionStatusHandler handler = livingBase.getCapability(CAPABILITY_ARMOR_CORROSION, null);
        assert handler != null;
        handler.removeAllCorrosion();

        int slotsToCorrode = UtilRandom.nextInt(MIN_SLOTS_TO_CORRODE, MAX_SLOTS_TO_CORRODE);
        for (int i = 0; i < slotsToCorrode; i++) {
            int index = UtilRandom.getRandomIndex(slots);
            handler.addCorrosionToArmorSlot(slots.get(index));
            slots.remove(index);
        }
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);

        IArmorCorrosionStatusHandler handler = livingBase.getCapability(CAPABILITY_ARMOR_CORROSION, null);
        assert handler != null;
        handler.removeAllCorrosion();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;

        if (!(livingBase instanceof EntityPlayer)) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion);
            return;
        }

        EntityPlayer player = (EntityPlayer) livingBase;
        IArmorCorrosionStatusHandler handler = player.getCapability(CAPABILITY_ARMOR_CORROSION, null);
        assert handler != null;

        if (handler.nothingToCorrode()) {
            livingBase.removePotionEffect(ModCurses.armor_corrosion);
            return;
        }

        if (handler.isCorrosionNeeded()) {
            List<Integer> slots = new ArrayList<>(handler.getCorrodedArmorSlots());
            for (Integer slot : slots) {
                ItemStack stack = player.inventory.armorItemInSlot(slot);
                if (stack.isEmpty())
                    handler.removeCorrosionFromArmorSlot(slot);
                else
                    stack.damageItem(DAMAGE_RATE, player);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
