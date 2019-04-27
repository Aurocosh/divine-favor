package aurocosh.divinefavor.common.item.talismans.spell.sense;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseConfig;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightInSphere;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public abstract class SpellTalismanSenseInSphere extends ItemSpellTalisman {
    protected final int radius;
    protected final float minShift;
    protected final float maxShift;
    protected final Color3f color3f;
    protected final SenseBlockPredicate predicate;

    public SpellTalismanSenseInSphere(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseConfig senseConfig, SenseBlockPredicate predicate) {
        super(name, "sense/", spirit, senseConfig.favorCost, options);
        this.color3f = color3f;
        radius = senseConfig.radius;
        minShift = senseConfig.minShift;
        maxShift = senseConfig.maxShift;
        this.predicate = predicate;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();
        highlightBlocks(radius, player, playerPosition, minShift, maxShift, color3f, predicate, "");
    }

    protected void highlightBlocks(int radius, EntityPlayer player, BlockPos position, float minShift, float maxShift, Color3f color3f, SenseBlockPredicate senseBlockPredicate, String blockName) {
        World world = player.world;
        new MessageParticlesHighlightInSphere(5, world.provider.getDimension(), position, minShift, maxShift, color3f, predicate, blockName, radius)
                .sendToAllAround(world, position, ConfigGeneral.particleRadius);
    }
}
