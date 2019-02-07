package aurocosh.divinefavor.common.potions.blessings;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.warping.WarpingPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionWarpingPresence extends ModPotion {

    public PotionWarpingPresence() {
        super("warping_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if(!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        WarpingPresenceData presenceData = PlayerData.get(player).getWarpingPresenceData();
        presenceData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityDamaged(EnderTeleportEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!player.isPotionActive(ModBlessings.warping_presence))
            return;
        WarpingPresenceData presenceData = PlayerData.get(player).getWarpingPresenceData();
        if(presenceData.tryLuck() && !player.world.isRemote){
            player.removePotionEffect(ModBlessings.warping_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_endererer));
        }
    }
}
