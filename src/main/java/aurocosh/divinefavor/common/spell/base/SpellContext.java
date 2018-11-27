package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.activation.DataHandler;

public class SpellContext {
    public EntityPlayer playerIn;
    public World worldIn;
    public BlockPos pos;
    public EnumHand hand;
    public EnumFacing facing;

    public SpellContext(EntityPlayer playerIn) {
        this.playerIn = playerIn;
        this.worldIn = playerIn.world;
        this.pos = null;
        this.hand = EnumHand.MAIN_HAND;
    }

    public SpellContext(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, PlayerData data){
        this.playerIn = playerIn;
        this.worldIn = worldIn;
        this.pos = pos;
        this.hand = hand;
        this.facing = facing;
    }
}
