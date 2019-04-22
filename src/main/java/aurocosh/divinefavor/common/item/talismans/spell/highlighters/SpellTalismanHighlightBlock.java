package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.talismans.spell.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightBlock;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanHighlightBlock extends ItemSpellTalisman {
    private final int radius;
    private final float minShift;
    private final float maxShift;
    private final Color3f color3f;

    public SpellTalismanHighlightBlock(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock) {
        super(name, spirit, senseBlock.favorCost, options);
        this.color3f = color3f;
        radius = senseBlock.radius;
        minShift = senseBlock.minShift;
        maxShift = senseBlock.maxShift;
    }

    @Override
    protected boolean validate(TalismanContext context) {
        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = context.player.getHeldItem(otherHand);
        if (heldItem.isEmpty())
            return false;
        Item item = heldItem.getItem();
        return item instanceof ItemBlock;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();

        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = player.getHeldItem(otherHand);
        ItemBlock itemBlock = (ItemBlock) heldItem.getItem();
        String blockName = itemBlock.getBlock().getRegistryName().toString();

        new MessageParticlesHighlightBlock(radius, 5, player.world.provider.getDimension(), playerPosition, minShift, maxShift, color3f, blockName)
                .sendToAllAround(world, playerPosition, ConfigGeneral.particleRadius);
    }
}
