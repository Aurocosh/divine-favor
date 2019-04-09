package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.tasks.base.ServerSideTask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END;

public class RedwindPunishment extends SpiritPunishment {
    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        new RedwindPunishmentTask(player).start();
    }

    public static class RedwindPunishmentTask extends ServerSideTask {
        private final EntityPlayer player;

        private int dragCount;
        private int dragTime;

        public RedwindPunishmentTask(EntityPlayer player) {
            super(player.world);

            this.player = player;
            dragCount = ConfigPunishments.redwind.dragCount.random();
            dragTime = 0;
        }

        @SubscribeEvent
        public void tickEvent(TickEvent.ServerTickEvent event) {
            if (event.phase == END)
                return;

            if (dragTime-- > 0)
                return;
            dragTime = ConfigPunishments.redwind.dragDuration.random();
            player.addPotionEffect(new ModEffect(ModCurses.red_fury, dragTime).setIsCurse());

            if (dragCount-- <= 0)
                finish();
        }

        @SubscribeEvent
        public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
            if (event.player == player)
                finish();
        }
    }
}
