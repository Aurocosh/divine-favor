package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SpellTalismanInfernalTouch extends ItemSpellTalisman {
    private static final Map<Block, Block> blocks = new HashMap<>();
    private static final Map<Material, Block> materials = new HashMap<>();

    static {
        for (Map.Entry<String, String> conversion : ConfigSpells.infernalTouch.blockConversions.entrySet()) {
            String blockName = conversion.getKey();
            Block block = Block.getBlockFromName(blockName);
            if (block == null) {
                DivineFavor.logger.error("Block does not exist: " + blockName);
                continue;
            }
            String resultName = conversion.getValue();
            Block result = Block.getBlockFromName(resultName);
            if (result == null) {
                DivineFavor.logger.error("Block does not exist: " + resultName);
                continue;
            }
            blocks.put(block, result);
        }

        for (Map.Entry<String, String> conversion : ConfigSpells.infernalTouch.materialConversions.entrySet()) {
            String materialName = conversion.getKey();
            Material material = UtilMaterial.getMaterial(materialName);
            if (material == null) {
                DivineFavor.logger.error("Material does not exist: " + materialName);
                continue;
            }
            String resultName = conversion.getValue();
            Block result = Block.getBlockFromName(resultName);
            if (result == null) {
                DivineFavor.logger.error("Block does not exist: " + resultName);
                continue;
            }
            materials.put(material, result);
        }
    }

    public SpellTalismanInfernalTouch(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        IBlockState state = context.world.getBlockState(context.pos);
        Block block = state.getBlock();

        Block result = blocks.get(block);
        if (result == null) {
            Material material = state.getMaterial();
            result = materials.get(material);
        }

        if (result != null) {
            IBlockState newState = result.getDefaultState();
            context.world.setBlockState(context.pos, newState);
        }
    }
}
