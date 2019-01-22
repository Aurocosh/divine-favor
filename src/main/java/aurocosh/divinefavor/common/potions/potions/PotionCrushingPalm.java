package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.TalismanUsesData;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.potions.common.ModPotions;
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
public class PotionCrushingPalm extends ModPotionToggleLimited {

    public PotionCrushingPalm() {
        super("crushing_palm", true, 0x7FB8A4);
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        if (!player.isPotionActive(ModPotions.crushing_palm))
            return;

        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!block.isToolEffective("pickaxe", state))
            return;

        TalismanUsesData usesData = PlayerData.get(player).getTalismanUsesData();
        ItemSpellTalisman talisman = ModPotions.crushing_palm.getTalisman();
        if (!usesData.consumeUse(talisman.getId()))
            return;

        new MessageSyncSpellUses(talisman.getId(), usesData).sendTo(player);

        ItemStack stack = player.getHeldItemMainhand();
        UtilBlock.removeBlockWithDrops(player, world, stack, pos, false, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }
}
