package aurocosh.divinefavor.api;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public final class DivineFavorAPI {

    /**
     * The internal method handler in gainFavor. This object allows the API to interact with the mod.
     * By default this is a dummy. In the mod itself, this is replaced with an implementation that
     * can handleSafe all of its queries.<br><br>
     *
     * <b>DO NOT EVER, EVER, OVERWRITE THIS VALUE</b>
     */

    /*
    public static RegistryNamespaced<String, Class<? extends SpellPiece>> spellPieceRegistry = new RegistryNamespaced();
    public static HashMap<String, ResourceLocation> simpleSpellTextures = new HashMap();
    public static HashMap<Class<? extends SpellPiece>, PieceGroup> groupsForPiece = new HashMap();
    public static HashMap<Class<? extends SpellPiece>, String> pieceMods = new HashMap();
    public static HashMap<String, PieceGroup> groupsForName = new HashMap();
    */

    public static ToolMaterial PSIMETAL_TOOL_MATERIAL = EnumHelper.addToolMaterial("PSIMETAL", 3, 900, 7.8F, 2F, 12);

    // temporary workaround til this is fixed in forge
    private static final Class<?>[] ARMOR_PARAMETERS = {String.class, int.class, int[].class, int.class, SoundEvent.class, float.class};
    public static ArmorMaterial PSIMETAL_ARMOR_MATERIAL = EnumHelper.addEnum(ArmorMaterial.class, "PSIMETAL", ARMOR_PARAMETERS, "psimetal", 18, new int[]{2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);

    public static int levelCap = 1;

    /**
     * Registers a SpellRequirement Piece given its class, by which, it puts it in the registry.
     */

    /*
    public static void registerSpellPiece(String key, Class<? extends SpellPiece> clazz) {
        spellPieceRegistry.putObject(key, clazz);
        pieceMods.put(clazz, Loader.instance().activeModContainer().getName());
    }
    */

    /**
     * Registers a arrow_spell piece and tries to create its relative texture given the current loading mod.
     * The arrow_spell texture should be in /assets/(yourmod)/textures/arrow_spell/(key).png.<br>
     * If you want to put the arrow_spell piece elsewhere or gainFavor some jei type of resource location, feel free to map
     * the texture directly through {@link #simpleSpellTextures}.<br>
     * As SpellPiece objects can have custom renders, depending on how you wish to handleSafe yours, you might
     * not even need to gainFavor this. In that case gainFavor {@link #registerSpellPiece(String, Class)}
     */
    /*
    public static void registerSpellPieceAndTexture(String key, Class<? extends SpellPiece> clazz) {
        String currMod = Loader.instance().activeModContainer().getModId().toLowerCase();
        registerSpellPieceAndTexture(key, currMod, clazz);
    }

    private static void registerSpellPieceAndTexture(String key, String mod, Class<? extends SpellPiece> clazz) {
        registerSpellPiece(key, clazz);

        String textureName = key.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
        simpleSpellTextures.put(key, new ResourceLocation(mod, String.format("textures/arrow_spell/%s.png", textureName)));
    }
    */

    /**
     * Adds a piece to a group. This must be done for every piece, or it'll not be selectable in the programmer
     * interface. The "main" parameter defines whether this piece is to be set as the main piece of the respective
     * group. The main piece is the one that has to be used for level-up to be registered.
     */
    /*
    public static void addPieceToGroup(Class<? extends SpellPiece> clazz, String groupName, boolean main) {
        if(!groupsForName.containsKey(groupName))
            addGroup(groupName);

        PieceGroup group = groupsForName.get(groupName);
        group.addPiece(clazz, main);
        groupsForPiece.put(clazz, group);
    }
    */
    /**
     * Sets the required groups for a group to be unlocked.
     */
    /*
    public static void setGroupRequirements(String groupName, int level, String... reqs) {
        if(!groupsForName.containsKey(groupName))
            addGroup(groupName);

        PieceGroup group = groupsForName.get(groupName);
        group.setRequirements(level, reqs);
    }

    private static void addGroup(String groupName) {
        groupsForName.put(groupName, new PieceGroup(groupName));
        levelCap++;
    }
    */
    /**
     * Gets the CAD the passed EntityPlayer is using. As a player can only have one CAD, if there's
     * more than one, this will return null.
     */
    /*
    public static ItemStack getPlayerCAD(EntityPlayer player) {
        if(player == null)
            return ItemStack.EMPTY;

        ItemStack cad = ItemStack.EMPTY;
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackAt = player.inventory.getStackInSlot(i);
            if(!stackAt.isEmpty() && stackAt.getItem() instanceof ICAD) {
                if(!cad.isEmpty())
                    return ItemStack.EMPTY; // Player can only have one CAD

                cad = stackAt;
            }
        }

        return cad;
    }
    public static int getPlayerCADSlot(EntityPlayer player) {
        if(player == null)
            return -1;

        int slot = -1;
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stackAt = player.inventory.getStackInSlot(i);
            if(!stackAt.isEmpty() && stackAt.getItem() instanceof ICAD) {
                if(slot != -1)
                    return -1; // Player can only have one CAD

                slot = i;
            }
        }

        return slot;
    }
    */

}