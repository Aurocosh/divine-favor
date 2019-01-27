package aurocosh.divinefavor.common.item.talismans.spell.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class TalismanContext {
    public final EntityPlayer player;
    public final EntityLivingBase target;
    public final World world;
    public final BlockPos pos;
    public final EnumHand hand;
    public final EnumFacing facing;
    public final CastType castType;
    public final EnumSet<SpellOptions> options;

    public TalismanContext(EntityPlayer player) {
        this.player = player;
        this.world = player.world;
        this.pos = null;
        this.hand = EnumHand.MAIN_HAND;
        options = EnumSet.noneOf(SpellOptions.class);
        facing = EnumFacing.UP;
        castType = CastType.Undefined;
        target = null;
    }

    public TalismanContext(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, EntityLivingBase target, CastType type, EnumSet<SpellOptions> options){
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.hand = hand;
        this.facing = facing;
        this.target = target;
        this.castType = type;
        this.options = options;
    }
}
