package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.frosty.FrostyAuraData;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class PotionFrostyAura extends ModPotion {
    public static final int CHECK_RATE = UtilTick.secondsToTicks(1);
    private static final LoopedCounter COUNTER = new LoopedCounter(CHECK_RATE);

    public PotionFrostyAura() {
        super("frosty_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        FrostyAuraData auraData = PlayerData.get(player).getFrostyAuraData();
        auraData.reset();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        FrostyAuraData auraData = PlayerData.get(player).getFrostyAuraData();
        if (auraData.count()) {
            player.removePotionEffect(ModBlendEffects.frosty_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.chilling_presence, UtilTick.minutesToTicks(2)));
            return;
        }
        if (!isConditionsMet(livingBase, player))
            auraData.reset();
    }

    private boolean isConditionsMet(EntityLivingBase livingBase, EntityPlayer player) {
        if (!COUNTER.isFinished())
            return true;
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : player.getArmorInventoryList())
            stacks.add(stack);
        for (ItemStack stack : player.getHeldEquipment())
            stacks.add(stack);
        boolean isAllEmpty = UtilList.isAll(stacks, ItemStack::isEmpty);
        if (!isAllEmpty)
            return false;

        if (!ModSpirits.blizrabi.isActive())
            return false;

        String biomeName = livingBase.world.getBiome(livingBase.getPosition()).getBiomeName();
        return biomeName.equals("Plains");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            COUNTER.tick();
    }
}
