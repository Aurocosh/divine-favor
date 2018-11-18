package aurocosh.divinefavor.api.spell;

import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellContext {
    public EntityPlayer playerIn;
    public World worldIn;
    public BlockPos pos;
    public EnumHand hand;
    public EnumFacing facing;
    public PlayerData data;

    public SpellContext(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, PlayerData data){
        this.playerIn = playerIn;
        this.worldIn = worldIn;
        this.pos = pos;
        this.hand = hand;
        this.facing = facing;
        this.data = data;
    }
}
