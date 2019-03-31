package aurocosh.divinefavor.common.potions.blends;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.aura.distorted.DistortedAuraData;
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PotionDistortedAura extends ModPotion {
    public static final int CHECK_RATE = UtilTick.secondsToTicks(1);
    private static final LoopedCounter COUNTER = new LoopedCounter(CHECK_RATE);

    public PotionDistortedAura() {
        super("distorted_aura", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        DistortedAuraData auraData = PlayerData.get(player).getDistortedAuraData();
        auraData.reset();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (!(livingBase instanceof EntityPlayer))
            return;
        if (!COUNTER.isFinished())
            return;
        if (!ModSpirits.endererer.isActive())
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ItemStack headStack = player.inventory.armorInventory.get(3);
        if (!(headStack.getItem() instanceof ItemBlockEnderPumpkin))
            return;

        Vec3d pos = player.getPositionVector();
        int radius = ConfigAura.distortedAura.endermanRadius;
        AxisAlignedBB axis = new AxisAlignedBB(pos.x - radius, pos.y + 1, pos.z - radius, pos.x + radius, pos.y, pos.z + radius);
        List<EntityLivingBase> endermanList = player.world.getEntitiesWithinAABB(EntityEnderman.class, axis, (EntityLivingBase e) -> e != null && e.getAttackingEntity() != livingBase);
        if (endermanList.isEmpty())
            return;

        DistortedAuraData auraData = PlayerData.get(player).getDistortedAuraData();
        if (auraData.tryLuck()) {
            player.removePotionEffect(ModBlendEffects.distorted_aura);
            player.addPotionEffect(new ModEffect(ModBlessings.warping_presence, ConfigPresence.warpingPresence.duration));
        }
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
