package aurocosh.divinefavor.common.spell.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellContext {
    public EntityPlayer player;
    public World world;
    public BlockPos pos;
    public EnumHand hand;
    public EnumFacing facing;
    public CastType castType;

    public SpellContext(EntityPlayer player) {
        this.player = player;
        this.world = player.world;
        this.pos = null;
        this.hand = EnumHand.MAIN_HAND;
        castType = CastType.UNSPECIFIED;
    }

    public SpellContext(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, CastType castType){
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.hand = hand;
        this.facing = facing;
        this.castType = castType;
    }
}
