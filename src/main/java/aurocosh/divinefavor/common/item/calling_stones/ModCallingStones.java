package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.constants.items.ConstCallingStoneNames;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.registry.MetaItemContainer;
import aurocosh.divinefavor.common.registry.interfaces.IMetaContainer;
import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModCallingStones {
    private static MetaItemContainer<CallingStone> callingStones = new MetaItemContainer<>();

    public static CallingStone allfire_calling_stone;
    public static CallingStone timber_calling_stone;
    public static CallingStone romol_calling_stone;

    public static IMetaContainer<CallingStone> getMetaContainer() {
        return callingStones;
    }

    public static void preInit() {
        allfire_calling_stone = callingStones.register(new CallingStone(ConstCallingStoneNames.ALLFIRE, ModSpirits.allfire, ModMultiBlocks.allfire_altar));
        timber_calling_stone = callingStones.register(new CallingStone(ConstCallingStoneNames.TIMBER, ModSpirits.timber, ModMultiBlocks.timber_altar));
        romol_calling_stone = callingStones.register(new CallingStone(ConstCallingStoneNames.ROMOL, ModSpirits.romol, ModMultiBlocks.romol_altar));
    }
}