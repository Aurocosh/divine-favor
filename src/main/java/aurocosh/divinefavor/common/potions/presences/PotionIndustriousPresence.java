package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.industrious.IndustriousPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlessings;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class PotionIndustriousPresence extends ModPotion {
    public static final int TICK_RATE = UtilTick.secondsToTicks(1);
    private static final LoopedCounter TICK_COUNTER = new LoopedCounter(TICK_RATE);

    private static final List<Block> acceptedBlocks = new ArrayList<>();

    static {
        acceptedBlocks.add(Blocks.DIAMOND_ORE);
        acceptedBlocks.add(Blocks.EMERALD_ORE);
        acceptedBlocks.add(Blocks.GOLD_ORE);
        acceptedBlocks.add(Blocks.IRON_ORE);
        acceptedBlocks.add(Blocks.LAPIS_ORE);
        acceptedBlocks.add(Blocks.LIT_REDSTONE_ORE);
        acceptedBlocks.add(Blocks.QUARTZ_ORE);
        acceptedBlocks.add(Blocks.REDSTONE_ORE);
    }

    public PotionIndustriousPresence() {
        super("industrious_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        IndustriousPresenceData auraData = PlayerData.get(player).getIndustriousPresenceData();
        auraData.reset();
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (!TICK_COUNTER.isFinished())
            return;
        BlockPos position = livingBase.getPosition();
        if (!livingBase.world.canSeeSky(new BlockPos(position.getX(), position.getY() + (double) livingBase.getEyeHeight(), position.getZ())))
            return;
        livingBase.removePotionEffect(ModBlessings.industrious_presence);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.isPotionActive(ModBlessings.industrious_presence))
            return;
        IBlockState state = event.getState();
        if (!acceptedBlocks.contains(state.getBlock()))
            return;
        IndustriousPresenceData auraData = PlayerData.get(player).getIndustriousPresenceData();
        if (auraData.count()) {
            player.removePotionEffect(ModBlessings.industrious_presence);
            player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_romol));
        }
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            TICK_COUNTER.tick();
    }
}
