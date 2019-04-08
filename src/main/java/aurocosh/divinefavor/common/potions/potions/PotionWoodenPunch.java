package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionWoodenPunch extends ModPotionToggleLimited {

    public PotionWoodenPunch() {
        super("wooden_punch", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!player.isPotionActive(ModPotions.wooden_punch))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("axe", state))
            return;

        SpiritData spiritData = PlayerData.get(player).getSpiritData();
        ItemSpellTalisman talisman = ModPotions.wooden_punch.getTalisman();
        ModSpirit spirit = talisman.getSpirit();
        if (!spiritData.consumeFavor(spirit.getId(), talisman.getFavorCost()))
            return;
        new MessageSyncFavor(spirit, spiritData).sendTo(player);

        ItemStack stack = player.getHeldItemMainhand();
        UtilBlock.removeBlock(player, world, stack, pos, true, false, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
