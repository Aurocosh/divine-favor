package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.energetic.EnergeticAuraData;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionEnergeticAura extends ModPotion {
    public static final int CHECK_RATE = UtilTick.secondsToTicks(1);
    private static final LoopedCounter COUNTER = new LoopedCounter(CHECK_RATE);

    public PotionEnergeticAura() {
        super("energetic_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        EnergeticAuraData auraData = PlayerData.get(player).getEnergeticAuraData();
        auraData.reset();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (!ModSpirits.redwind.isActive())
            return;
        if (!COUNTER.isFinished())
            return;
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        EnergeticAuraData auraData = PlayerData.get(player).getEnergeticAuraData();
        if (!player.isSprinting() || !auraData.tryLuck())
            return;
        player.removePotionEffect(ModBlendEffects.energetic_aura);
        player.addPotionEffect(new ModEffect(ModBlessings.energetic_presence, UtilTick.minutesToTicks(2)));
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
