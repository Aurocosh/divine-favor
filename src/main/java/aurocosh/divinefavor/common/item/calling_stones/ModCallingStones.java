package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModCallingStones {
    public static ItemCallingStone calling_stone_allfire;
    public static ItemCallingStone calling_stone_arbow;
    public static ItemCallingStone calling_stone_blizrabi;
    public static ItemCallingStone calling_stone_redwind;
    public static ItemCallingStone calling_stone_romol;
    public static ItemCallingStone calling_stone_squarefury;
    public static ItemCallingStone calling_stone_timber;

    public static void preInit() {
        calling_stone_allfire = ModRegistries.items.register(new ItemCallingStone("allfire", ModSpirits.allfire, ModMultiBlocks.altar_allfire));
        calling_stone_arbow = ModRegistries.items.register(new ItemCallingStone("arbow", ModSpirits.arbow, ModMultiBlocks.altar_arbow));
        calling_stone_blizrabi = ModRegistries.items.register(new ItemCallingStone("blizrabi", ModSpirits.blizrabi, ModMultiBlocks.altar_blizrabi));
        calling_stone_redwind = ModRegistries.items.register(new ItemCallingStone("redwind", ModSpirits.redwind, ModMultiBlocks.altar_redwind));
        calling_stone_romol = ModRegistries.items.register(new ItemCallingStone("romol", ModSpirits.romol, ModMultiBlocks.altar_romol));
        calling_stone_squarefury = ModRegistries.items.register(new ItemCallingStone("squarefury", ModSpirits.squarefury, ModMultiBlocks.altar_squarefury));
        calling_stone_timber = ModRegistries.items.register(new ItemCallingStone("timber", ModSpirits.timber, ModMultiBlocks.altar_timber));
    }
}