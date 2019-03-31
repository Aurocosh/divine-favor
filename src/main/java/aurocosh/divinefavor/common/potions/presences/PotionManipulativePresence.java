package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.manipulative.ManipulativePresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionManipulativePresence extends ModPotion {
    public PotionManipulativePresence() {
        super("manipulative_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ManipulativePresenceData auraData = PlayerData.get(player).getManipulativePresenceData();
        auraData.reset();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityConstructing(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.isPotionActive(ModBlessings.manipulative_presence))
            return;
        if (event.getPlacedBlock().getBlock() != Blocks.PUMPKIN)
            return;
        World world = event.getWorld();
        Vector3i controller = new Vector3i(event.getPos());
        if (ModMultiBlocks.iron_golem.match(world, controller) == null)
            return;

        ManipulativePresenceData auraData = PlayerData.get(player).getManipulativePresenceData();
        if (auraData.tryLuck()) {
            player.removePotionEffect(ModBlessings.manipulative_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_loon));
        }
    }
}
