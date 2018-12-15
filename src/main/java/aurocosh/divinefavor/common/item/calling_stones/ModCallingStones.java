package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModCallingStones {
    public static ItemCallingStone allfire_calling_stone;
    public static ItemCallingStone timber_calling_stone;
    public static ItemCallingStone romol_calling_stone;
    public static ItemCallingStone squarefury_calling_stone;

    public static void preInit() {
        allfire_calling_stone = ModRegistries.items.register(new ItemCallingStone("allfire", ModSpirits.allfire, ModMultiBlocks.allfire_altar));
        timber_calling_stone = ModRegistries.items.register(new ItemCallingStone("timber", ModSpirits.timber, ModMultiBlocks.timber_altar));
        romol_calling_stone = ModRegistries.items.register(new ItemCallingStone("romol", ModSpirits.romol, ModMultiBlocks.romol_altar));
        squarefury_calling_stone = ModRegistries.items.register(new ItemCallingStone("squarefury", ModSpirits.squarefury, ModMultiBlocks.squarefury_altar));
    }
}