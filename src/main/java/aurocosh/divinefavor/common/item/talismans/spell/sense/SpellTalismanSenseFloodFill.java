package aurocosh.divinefavor.common.item.talismans.spell.sense;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightFloodFill;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanSenseFloodFill extends ItemSpellTalisman {
    protected final int floodLimit;
    protected final int searchLimit;
    protected final float minShift;
    protected final float maxShift;
    protected final Color3f color3f;
    protected final SenseBlockPredicate predicate;

    public SpellTalismanSenseFloodFill(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock, SenseBlockPredicate predicate, int floodLimit, int searchLimit) {
        super(name, "sense/", spirit, senseBlock.favorCost, options);
        this.color3f = color3f;
        this.predicate = predicate;
        minShift = senseBlock.minShift;
        maxShift = senseBlock.maxShift;
        this.floodLimit = floodLimit;
        this.searchLimit = searchLimit;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();

        World world = player.world;
        new MessageParticlesHighlightFloodFill(5, world.provider.getDimension(), context.pos, minShift, maxShift, color3f, predicate, "", floodLimit, searchLimit, context.facing.getOpposite()).sendToAllAround(world, playerPosition, ConfigGeneral.particleRadius);
    }
}
