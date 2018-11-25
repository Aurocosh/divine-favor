package aurocosh.divinefavor.common.core.handlers;

import aurocosh.divinefavor.common.network.message.MessageDataSync;
import aurocosh.divinefavor.common.network.message.MessageSyncSpellCharge;
import aurocosh.divinefavor.common.spell.base.SpellChargeType;
import aurocosh.divinefavor.common.util.UtilSerialize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import vazkii.arl.network.NetworkHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class PlayerDataHandler {


    private static HashMap<Integer, PlayerData> playerData = new HashMap();

    private static final String DATA_TAG = "DivineFavorData";

    //public static final DamageSource damageSourceOverload = new DamageSource("psi-overload").setDamageBypassesArmor().setMagicDamage();

    public static PlayerData get(EntityPlayer player) {
        int key = getKey(player);
        if(!playerData.containsKey(key))
            playerData.put(key, new PlayerData(player));

        PlayerData data = playerData.get(key);
        if(data != null && data.playerWR != null && data.playerWR.get() != player) {
            NBTTagCompound cmp = new NBTTagCompound();
            data.writeToNBT(cmp);
            playerData.remove(key);
            data = get(player);
            data.readFromNBT(cmp);
        }

        return data;
    }

    public static void cleanup() {
        List<Integer> removals = new ArrayList();
        Iterator<Entry<Integer, PlayerData>> it = playerData.entrySet().iterator();
        while(it.hasNext()) {
            Entry<Integer, PlayerData> item = it.next();
            PlayerData d = item.getValue();
            if(d != null && d.playerWR.get() == null)
                removals.add(item.getKey());
        }

        for(int i : removals)
            playerData.remove(i);
    }

    private static int getKey(EntityPlayer player) {
        return player.hashCode() << 1 + (player.getEntityWorld().isRemote ? 1 : 0);
    }

    public static NBTTagCompound getDataCompoundForPlayer(EntityPlayer player) {
        NBTTagCompound forgeData = player.getEntityData();
        if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

        NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if(!persistentData.hasKey(DATA_TAG))
            persistentData.setTag(DATA_TAG, new NBTTagCompound());

        return persistentData.getCompoundTag(DATA_TAG);
    }

    public static class EventHandler {
        @SubscribeEvent
        public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            if(event.player instanceof EntityPlayerMP) {
                MessageDataSync message = new MessageDataSync(get(event.player));
                NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) event.player);
            }
        }
    }

    public static class PlayerData {
        private static final String TAG_EMPOWER_AXE_CHARGES = "EmpowerAxeCharges";
        private static final String TAG_WOOD_BLOCKS_BROKEN = "WoodBlocksBroken";

        private static final String TAG_ALTAR_POSITIONS = "AltarPositions";
        private static final String TAG_LAST_CLICKED_POSITIONS = "LastClickedPositions";

        public int empowerAxeCharges;
        public int woodBlocksBroken;

        private ArrayList<BlockPos> altarPositions;
        private ArrayList<BlockPos> lastClickedPositions;

        public WeakReference<EntityPlayer> playerWR;
        private final boolean client;

        public PlayerData(EntityPlayer player) {
            playerWR = new WeakReference(player);
            client = player.world.isRemote;

            empowerAxeCharges = 0;
            woodBlocksBroken = 0;

            altarPositions = new ArrayList<>();
            lastClickedPositions = new ArrayList<>();

            load();
        }

        public void tick() {
//            EntityPlayer player = playerWR.get();
//            int dimension = player.getEntityWorld().provider.getDimension();
        }

        public int getSpellCharge(SpellChargeType dataType) {
            if (dataType == SpellChargeType.SPELL_EMPOWER_AXE)
                return empowerAxeCharges;
            return 0;
        }

        public void setSpellCharge(SpellChargeType dataType, int charge) {
            if (dataType == SpellChargeType.SPELL_EMPOWER_AXE)
                empowerAxeCharges = charge;
            save();
        }

        private int changeSpellCharge(SpellChargeType dataType, int charge) {
            int charges = 0;
            if (dataType == SpellChargeType.SPELL_EMPOWER_AXE){
                empowerAxeCharges += charge;
                charges = empowerAxeCharges;
            }
            save();
            return charges;
        }

        public boolean hasSpellCharge(SpellChargeType dataType, int charge) {
            return getSpellCharge(dataType) >= charge;
        }

        public boolean consumeSpellCharge(SpellChargeType dataType, int charge) {
            if(!hasSpellCharge(dataType,charge))
                return false;

            int charges = changeSpellCharge(dataType,-charge);

            if(playerWR.get() instanceof EntityPlayerMP) {
                MessageSyncSpellCharge message = new MessageSyncSpellCharge(dataType,charges);
                NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) playerWR.get());
            }
            return true;
        }

        public boolean provideSpellCharge(SpellChargeType dataType, int charge) {
            int charges = changeSpellCharge(dataType,charge);

            if(playerWR.get() instanceof EntityPlayerMP) {
                MessageSyncSpellCharge message = new MessageSyncSpellCharge(dataType,charges);
                NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) playerWR.get());
            }
            return true;
        }

        public void save() {
            if (client)
                return;

            EntityPlayer player = playerWR.get();
            if (player != null) {
                NBTTagCompound cmp = getDataCompoundForPlayer(player);
                writeToNBT(cmp);
            }
        }

        public void writeToNBT(NBTTagCompound cmp) {
            cmp.setInteger(TAG_EMPOWER_AXE_CHARGES, empowerAxeCharges);
            cmp.setInteger(TAG_WOOD_BLOCKS_BROKEN, woodBlocksBroken);

            int[] altarArray = UtilSerialize.SerializeBlockPosArray(altarPositions);
            cmp.setIntArray(TAG_ALTAR_POSITIONS, altarArray);

            int[] clickedArray = UtilSerialize.SerializeBlockPosArray(lastClickedPositions);
            cmp.setIntArray(TAG_LAST_CLICKED_POSITIONS, clickedArray);
        }

        public void load() {
            if (client)
                return;

            EntityPlayer player = playerWR.get();
            if (player != null) {
                NBTTagCompound cmp = getDataCompoundForPlayer(player);
                readFromNBT(cmp);
            }
        }

        public void readFromNBT(NBTTagCompound cmp) {
            empowerAxeCharges = cmp.getInteger(TAG_EMPOWER_AXE_CHARGES);
            woodBlocksBroken = cmp.getInteger(TAG_WOOD_BLOCKS_BROKEN);

            int[] altarArray = cmp.getIntArray(TAG_ALTAR_POSITIONS);
            altarPositions = UtilSerialize.DeserializeBlockPosArray(altarArray);

            int[] clickedArray = cmp.getIntArray(TAG_LAST_CLICKED_POSITIONS);
            lastClickedPositions = UtilSerialize.DeserializeBlockPosArray(clickedArray);
        }


    }
}