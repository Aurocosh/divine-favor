package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.entries.talismans.spell.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightBlock;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public abstract class SpellTalismanHighlightBlockPredicated extends ItemSpellTalisman {
    protected final int radius;
    protected final float minShift;
    protected final float maxShift;
    protected final Color3f color3f;
    protected final HighlightPredicate predicate;

    public SpellTalismanHighlightBlockPredicated(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock, HighlightPredicate predicate) {
        super(name, "sense/", spirit, senseBlock.favorCost, options);
        this.color3f = color3f;
        radius = senseBlock.radius;
        minShift = senseBlock.minShift;
        maxShift = senseBlock.maxShift;
        this.predicate = predicate;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();
        highlightBlocks(radius, player, playerPosition, minShift, maxShift, color3f, predicate, "");
    }

    protected void highlightBlocks(int radius, EntityPlayer player, BlockPos position, float minShift, float maxShift, Color3f color3f, HighlightPredicate highlightPredicate, String blockName) {
        World world = player.world;
        new MessageParticlesHighlightBlock(radius, 5, world.provider.getDimension(), position, minShift, maxShift, color3f, predicate, blockName)
                .sendToAllAround(world, position, ConfigGeneral.particleRadius);
    }
}
