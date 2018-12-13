package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.constants.items.ConstCallingStoneNames;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModCallingStones {
    public static ModItem allfire_calling_stone;
    public static ModItem timber_calling_stone;
    public static ModItem romol_calling_stone;

    public static void preInit() {
        allfire_calling_stone = ModRegistries.items.register(new ItemCallingStone(ConstCallingStoneNames.ALLFIRE, ModSpirits.allfire, ModMultiBlocks.allfire_altar));
        timber_calling_stone = ModRegistries.items.register(new ItemCallingStone(ConstCallingStoneNames.TIMBER, ModSpirits.timber, ModMultiBlocks.timber_altar));
        romol_calling_stone = ModRegistries.items.register(new ItemCallingStone(ConstCallingStoneNames.ROMOL, ModSpirits.romol, ModMultiBlocks.romol_altar));
    }
}