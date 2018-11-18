package aurocosh.divinefavor.common.core.handlers;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.api.DivineFavorAPI;
import aurocosh.divinefavor.api.internal.IPlayerData;
import aurocosh.divinefavor.api.internal.Vector3;
import aurocosh.divinefavor.common.network.message.MessageDataSync;
import aurocosh.divinefavor.common.network.message.MessageDeductPsi;
import aurocosh.divinefavor.common.network.message.MessageLevelUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ObjectUtils;
import vazkii.arl.network.NetworkHandler;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.Map.Entry;

public class PlayerDataHandler {

    private static HashMap<Integer, PlayerData> playerData = new HashMap();
    //public static Set<SpellContext> delayedContexts = new HashSet();

    private static final String DATA_TAG = "PsiData";

    public static final DamageSource damageSourceOverload = new DamageSource("psi-overload").setDamageBypassesArmor().setMagicDamage();

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

        /*
        @SubscribeEvent
        public void onPlayerTick(LivingUpdateEvent event) {
            if(event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();

                ItemStack cadStack = PsiAPI.getPlayerCAD(player);
                if(!cadStack.isEmpty() && cadStack.getItem() instanceof ICAD)
                    ((ICAD) cadStack.getItem()).incrementTime(cadStack);

                PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.TICK));
                PlayerDataHandler.get(player).tick();
            }
        }

        @SubscribeEvent
        public void onEntityDamage(LivingHurtEvent event) {
            if(event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                PlayerDataHandler.get(player).damage(event.getAmount());

                EntityLivingBase attacker = null;
                if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase)
                    attacker = (EntityLivingBase) event.getSource().getTrueSource();

                PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.DAMAGE, event.getAmount(), attacker));
                if(event.getSource().isFireDamage())
                    PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.ON_FIRE));
            }
        }

        @SubscribeEvent
        public void onPlayerLogin(PlayerLoggedInEvent event) {
            if(event.player instanceof EntityPlayerMP) {
                MessageDataSync message = new MessageDataSync(get(event.player));
                NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) event.player);
            }
        }

        @SubscribeEvent
        public void onEntityJump(LivingJumpEvent event) {
            if(event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.JUMP));
            }
        }

        @SubscribeEvent
        public void onPsiArmorEvent(PsiArmorEvent event) {
            for(int i = 0; i < 4; i++) {
                ItemStack armor = event.getEntityPlayer().inventory.armorInventory.get(i);
                if(!armor.isEmpty() && armor.getItem() instanceof IPsiEventArmor) {
                    IPsiEventArmor handler = (IPsiEventArmor) armor.getItem();
                    handler.onEvent(armor, event);
                }
            }
        }

        @SubscribeEvent
        public void onChangeDimension(PlayerChangedDimensionEvent event) {
            get(event.player).eidosChangelog.clear();
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public void onRenderWorldLast(RenderWorldLastEvent event) {
            Minecraft mc = Minecraft.getMinecraft();
            Entity cameraEntity = mc.getRenderViewEntity();
            cameraEntity.getPosition();
            Frustum frustum = new Frustum();

            float partialTicks = event.getPartialTicks();
            double viewX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * partialTicks;
            double viewY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * partialTicks;
            double viewZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * partialTicks;
            frustum.setPosition(viewX, viewY, viewZ);

            for(EntityPlayer player : mc.world.playerEntities)
                PlayerDataHandler.get(player).render(player, partialTicks);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public void onFOVUpdate(FOVUpdateEvent event) {
            PlayerData data = get(Minecraft.getMinecraft().player);
            if(data.isAnchored) {
                float fov = event.getNewfov();
                if(data.eidosAnchorTime > 0)
                    fov *= Math.min(5, data.eidosAnchorTime - ClientTickHandler.partialTicks) / 5;
                else fov *= (10 - Math.min(10, data.postAnchorRecallTime + ClientTickHandler.partialTicks)) / 10;
                event.setNewfov(fov);
            }
        }
    */

    }
        public static class PlayerData implements IPlayerData {

            private static final String TAG_LEVEL = "level";
            private static final String TAG_AVAILABLE_PSI = "availablePsi";
            private static final String TAG_REGEN_CD = "regenCd";
            private static final String TAG_SPELL_GROUPS_UNLOCKED = "spellGroupsUnlocked";
            private static final String TAG_LAST_SPELL_GROUP = "lastSpellPoint";
            private static final String TAG_LEVEL_POINTS = "levelPoints";

            private static final String TAG_EIDOS_ANCHOR_X = "eidosAnchorX";
            private static final String TAG_EIDOS_ANCHOR_Y = "eidosAnchorY";
            private static final String TAG_EIDOS_ANCHOR_Z = "eidosAnchorZ";
            private static final String TAG_EIDOS_ANCHOR_PITCH = "eidosAnchorPitch";
            private static final String TAG_EIDOS_ANCHOR_YAW = "eidosAnchorYaw";
            private static final String TAG_EIDOS_ANCHOR_TIME = "eidosAnchorTime";

            public int level;
            public int availablePsi;
            public int lastAvailablePsi;
            public int regenCooldown;
            public String lastSpellGroup;
            public int levelPoints;
            public boolean loopcasting = false;
            public int loopcastTime = 0;
            public int loopcastAmount = 0;
            public int loopcastFadeTime = 0;

            // Eidos stuff
            public Stack<Vector3> eidosChangelog = new Stack();
            public Vector3 eidosAnchor = new Vector3(0, 0, 0);
            public double eidosAnchorPitch, eidosAnchorYaw;
            public boolean isAnchored;
            public boolean isReverting;
            public int eidosAnchorTime;
            public int postAnchorRecallTime;
            public int eidosReversionTime;
            public int lastDimension;

            // Exosuit Event Stuff
            boolean lowLight, underwater, lowHp;

            public boolean deductTick;

            public final List<String> spellGroupsUnlocked = new ArrayList();
            public final List<Deduction> deductions = new ArrayList();
            public WeakReference<EntityPlayer> playerWR;
            private final boolean client;

            public PlayerData(EntityPlayer player) {
                playerWR = new WeakReference(player);
                client = player.getEntityWorld().isRemote;

                load();
            }

            public void tick() {
                int dimension = playerWR.get().getEntityWorld().provider.getDimension();

                if(deductTick)
                    deductTick = false;
                else lastAvailablePsi = availablePsi;

                int max = getTotalPsi();
                if(availablePsi > max)
                    availablePsi = max;

                EntityPlayer player = playerWR.get();
                ItemStack cadStack = getCAD();
                if(regenCooldown == 0) {
                    boolean doRegen = true;
                    doRegen = false;
                    if(!cadStack.isEmpty()) {
                        //ICAD cad = (ICAD) cadStack.getItem();
                        //int maxPsi = cad.getStatValue(cadStack, EnumCADStat.OVERFLOW);
                        //int currPsi = cad.getStoredPsi(cadStack);
                        //if(currPsi < maxPsi) {
                        //    cad.regenPsi(cadStack, Math.max(1, getRegenPerTick() / 2));
                        //    doRegen = false;
                        //}
                    }

                    if(doRegen && availablePsi < max && regenCooldown == 0) {
                        availablePsi = Math.min(max, availablePsi + getRegenPerTick());
                        save();
                    }
                } else {
                    regenCooldown--;
                    save();
                }

                cadStack = getCAD();
                //ICAD icad = null;
                //Color color = new Color(ICADColorizer.DEFAULT_SPELL_COLOR);
/*
                if(!cadStack.isEmpty()) {
                    icad = (ICAD) cadStack.getItem();
                    color = Psi.proxy.getCADColor(cadStack);
                }
                float r = color.getRed() / 255F;
                float g = color.getGreen() / 255F;
                float b = color.getBlue() / 255F;
*/
/*
                loopcast: {
                    if(loopcasting) {
                        if(player == null || cadStack.isEmpty() || (player.getHeldItemMainhand() != cadStack && player.getHeldItemOffhand() != cadStack)) {
                            stopLoopcast();
                            break loopcast;
                        }

                        for(int i = 0; i < 5; i++) {
                            double x = player.posX + (Math.random() - 0.5) * 2.1 * player.width;
                            double y = player.posY - player.getYOffset();
                            double z = player.posZ + (Math.random() - 0.5) * 2.1 * player.width;
                            float grav = -0.15F - (float) Math.random() * 0.03F;
                            //Psi.proxy.sparkleFX(player.getEntityWorld(), x, y, z, r, g, b, grav, 0.25F, 15);
                        }

                        if(loopcastTime > 0 && loopcastTime % 5 == 0) {
                            ItemStack bullet = icad.getBulletInSocket(cadStack, icad.getSelectedSlot(cadStack));
                            if(bullet.isEmpty()) {
                                stopLoopcast();
                                break loopcast;
                            }

                            ISpellContainer spellContainer = (ISpellContainer) bullet.getItem();
                            if(spellContainer.containsSpell(bullet)) {
                                Spell spell = spellContainer.getSpell(bullet);
                                SpellContext context = new SpellContext().setPlayer(player).setSpell(spell).setLoopcastIndex(loopcastAmount + 1);
                                if(context.isValid()) {
                                    if(context.cspell.metadata.evaluateAgainst(cadStack)) {
                                        int cost = ItemCAD.getRealCost(cadStack, bullet, context.cspell.metadata.stats.get(EnumSpellStat.COST));
                                        if(cost > 0 || cost == -1) {
                                            if(cost != -1)
                                                deductPsi(cost, 3, true);

                                            if(!player.getEntityWorld().isRemote && loopcastTime % 10 == 0)
                                                player.getEntityWorld().playSound(null, player.posX, player.posX, player.posZ, PsiSoundHandler.loopcast, SoundCategory.PLAYERS, 0.5F, (float) (0.35 + Math.random() * 0.85));
                                        }

                                        context.cspell.safeExecute(context);
                                        loopcastAmount++;
                                    }
                                }
                            }
                        }

                        loopcastTime++;
                    } else if(loopcastFadeTime > 0)
                        loopcastFadeTime--;
                }


                if(player.isDead || dimension != lastDimension) {
                    eidosAnchorTime = 0;
                    eidosReversionTime = 0;
                    eidosChangelog.clear();
                    isAnchored = false;
                    isReverting = false;
                }

                if(eidosAnchorTime > 0) {
                    if(eidosAnchorTime == 1) {
                        if(player != null && player instanceof EntityPlayerMP) {
                            EntityPlayerMP pmp = (EntityPlayerMP) player;
                            pmp.connection.setPlayerLocation(eidosAnchor.x, eidosAnchor.y, eidosAnchor.z, (float) eidosAnchorYaw, (float) eidosAnchorPitch);

                            Entity riding = player.getRidingEntity();
                            while(riding != null) {
                                riding.setPosition(eidosAnchor.x, eidosAnchor.y, eidosAnchor.z);
                                riding = riding.getRidingEntity();
                            }
                        }
                        postAnchorRecallTime = 0;
                    }
                    eidosAnchorTime--;
                } else if(postAnchorRecallTime < 5) {
                    postAnchorRecallTime--;
                    isAnchored = false;
                }

                if(eidosReversionTime > 0) {
                    if(eidosChangelog.isEmpty()) {
                        eidosReversionTime = 0;
                        isReverting = false;
                    } else {
                        eidosChangelog.pop();
                        if(eidosChangelog.isEmpty()) {
                            eidosReversionTime = 0;
                            isReverting = false;
                        } else {
                            Vector3 vec = eidosChangelog.pop();
                            if(player != null) {
                                if(player instanceof EntityPlayerMP) {
                                    EntityPlayerMP pmp = (EntityPlayerMP) player;
                                    pmp.connection.setPlayerLocation(vec.x, vec.y, vec.z, 0, 0, ImmutableSet.of(EnumFlags.X_ROT, EnumFlags.Y_ROT));
                                } else {
                                    player.posX = vec.x;
                                    player.posY = vec.y;
                                    player.posZ = vec.z;
                                }

                                Entity riding = player.getRidingEntity();
                                while(riding != null) {
                                    riding.setPosition(vec.x, vec.y, vec.z);
                                    riding = riding.getRidingEntity();
                                }

                                for(int i = 0; i < 5; i++) {
                                    double spread = 0.6;

                                    double x = player.posX + (Math.random() - 0.5) * spread;
                                    double y = player.posY + (Math.random() - 0.5) * spread;
                                    double z = player.posZ + (Math.random() - 0.5) * spread;

                                    Psi.proxy.sparkleFX(player.getEntityWorld(), x, y, z, r, g, b, (float) x, (float) y, (float) z, 1.2F, 12);
                                }

                                player.motionX = 0;
                                player.motionY = 0;
                                player.motionZ = 0;
                                player.fallDistance = 0F;
                            }
                        }
                    }

                    eidosReversionTime--;
                    if(eidosReversionTime == 0 || player.isSneaking()) {
                        eidosChangelog.clear();
                        isReverting = false;
                    }
                } else {
                    if(eidosChangelog.size() >= 600)
                        eidosChangelog.remove(0);
                    eidosChangelog.push(Vector3.fromEntity(player));
                }

                BlockPos pos = player.getPosition();
                int skylight = (int) (player.getEntityWorld().getLightFor(EnumSkyBlock.SKY, pos) * player.getEntityWorld().provider.getSunBrightnessFactor(1F));
                int blocklight = player.getEntityWorld().getLightFor(EnumSkyBlock.BLOCK, pos);
                int light = Math.max(skylight, blocklight);

                boolean lowLight = light < 7;
                if(!this.lowLight && lowLight)
                    PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.LOW_LIGHT));
                this.lowLight = lowLight;

                boolean underwater = player.isInsideOfMaterial(Material.WATER);
                if(!this.underwater && underwater)
                    PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.UNDERWATER));
                this.underwater = underwater;

                boolean lowHp = player.getHealth() <= 6;
                if(!this.lowHp && lowHp)
                    PsiArmorEvent.post(new PsiArmorEvent(player, PsiArmorEvent.LOW_HP));
                this.lowHp = lowHp;

                List<Deduction> remove = new ArrayList();
                for(Deduction d : deductions) {
                    if(d.invalid)
                        remove.add(d);
                    else d.tick();
                }
                deductions.removeAll(remove);

                lastDimension = dimension;
*/
            }

            public void stopLoopcast() {
                if(loopcasting)
                    loopcastFadeTime = 5;
                loopcasting = false;
                loopcastTime = 0;
                loopcastAmount = 0;
            }

            public void damage(float amount) {
                int psi = (int) (getTotalPsi() * 0.02 * amount);
                if(psi > 0 && availablePsi > 0) {
                    psi = Math.min(psi, availablePsi);
                    deductPsi(psi, 20, true, true);
                }
            }

            public void skipToLevel(int level) {
                int currLevel = this.level;
                int points = level - currLevel;

                this.level = Math.max(currLevel, Math.min(DivineFavorAPI.levelCap, level));
                levelPoints = Math.max(0, Math.max(points, levelPoints));
                save();
            }

            public void levelUp() {
                EntityPlayer player = playerWR.get();
                if(player != null) {
                    level++;
                    levelPoints++;
                    lastSpellGroup = "";

                    if(player instanceof EntityPlayerMP) {
                        MessageLevelUp message = new MessageLevelUp(level);
                        MessageDataSync message2 = new MessageDataSync(this);

                        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
                        NetworkHandler.INSTANCE.sendTo(message2, (EntityPlayerMP) player);
                        if(level == 25)
                            player.sendMessage(new TextComponentTranslation("psimisc.softcapIndicator").setStyle(new Style().setColor(TextFormatting.AQUA)));
                    }
                }
            }

            public ItemStack getCAD() {
                return null;
                //return DivineFavorAPI.getPlayerCAD(playerWR.get());
            }

            public void deductPsi(int psi, int cd, boolean sync) {
                deductPsi(psi, cd, sync, false);
            }

            @Override
            public void deductPsi(int psi, int cd, boolean sync, boolean shatter) {
                int currentPsi = availablePsi;

                availablePsi -= psi;
                if(regenCooldown < cd)
                    regenCooldown = cd;

                if(availablePsi < 0) {
                    int overflow = -availablePsi;
                    availablePsi = 0;

                    /*
                    ItemStack cadStack = getCAD();
                    if(!cadStack.isEmpty()) {
                        ICAD cad = (ICAD) cadStack.getItem();
                        overflow = cad.consumePsi(cadStack, overflow);
                    }
                    */

                    if(!shatter && overflow > 0) {
                        float dmg = (float) overflow / (loopcasting ? 50 : 125);
                        if(!client) {
                            EntityPlayer player = playerWR.get();
                            if(player != null)
                                player.attackEntityFrom(damageSourceOverload, dmg);
                        }
                    }
                }

                if(sync && playerWR.get() instanceof EntityPlayerMP) {
                    MessageDeductPsi message = new MessageDeductPsi(currentPsi, availablePsi, regenCooldown, shatter);
                    NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) playerWR.get());
                }

                save();
            }

            public void addDeduction(int current, int deduct, boolean shatter) {
                if(deduct > current)
                    deduct = current;
                if(deduct < 0)
                    deduct = 0;

                if(deduct == 0)
                    return;

                deductions.add(new Deduction(current, deduct, 20, shatter));
            }

            @Override
            public int getLevel() {
                EntityPlayer player = playerWR.get();
                if(player != null && player.capabilities.isCreativeMode)
                    return DivineFavorAPI.levelCap;
                return level;
            }

            public int getLevelPoints() {
                return levelPoints;
            }

            @Override
            public int getAvailablePsi() {
                return availablePsi;
            }

            @Override
            public int getLastAvailablePsi() {
                return lastAvailablePsi;
            }

            @Override
            public int getTotalPsi() {
                return Math.min(5000, getLevel() * 200);
            }

            @Override
            public int getRegenPerTick() {
                return Math.min(25, getLevel());
            }

            @Override
            public int getRegenCooldown() {
                return regenCooldown;
            }

            @Override
            public boolean isPieceGroupUnlocked(String group) {
                EntityPlayer player = playerWR.get();
                if(player != null && player.capabilities.isCreativeMode)
                    return true;

                return spellGroupsUnlocked.contains(group);
            }

            @Override
            public void unlockPieceGroup(String group) {
                if(!isPieceGroupUnlocked(group)) {
                    spellGroupsUnlocked.add(group);
                    lastSpellGroup = group;
                    levelPoints--;
                    save();
                }
            }

            /*
            @Override
            public void markPieceExecuted(SpellPiece piece) {
               if(lastSpellGroup == null || lastSpellGroup.isEmpty() || levelPoints != 0)
                    return;

                PieceGroup group = PsiAPI.groupsForName.get(lastSpellGroup);
                if(group != null && group.mainPiece == piece.getClass())
                    levelUp();
            }
            */

            public void save() {
                if(!client) {
                    EntityPlayer player = playerWR.get();

                    if(player != null) {
                        NBTTagCompound cmp = getDataCompoundForPlayer(player);
                        writeToNBT(cmp);
                    }
                }
            }

            public void writeToNBT(NBTTagCompound cmp) {
                cmp.setInteger(TAG_LEVEL, level);
                cmp.setInteger(TAG_AVAILABLE_PSI, availablePsi);
                cmp.setInteger(TAG_REGEN_CD, regenCooldown);
                cmp.setInteger(TAG_LEVEL_POINTS, levelPoints);
                if(lastSpellGroup != null && !lastSpellGroup.isEmpty())
                    cmp.setString(TAG_LAST_SPELL_GROUP, lastSpellGroup);

                NBTTagList list = new NBTTagList();
                for(String s : spellGroupsUnlocked) {
                    if(s != null && !s.isEmpty())
                        list.appendTag(new NBTTagString(s));
                }
                cmp.setTag(TAG_SPELL_GROUPS_UNLOCKED, list);

                cmp.setDouble(TAG_EIDOS_ANCHOR_X, eidosAnchor.x);
                cmp.setDouble(TAG_EIDOS_ANCHOR_Y, eidosAnchor.y);
                cmp.setDouble(TAG_EIDOS_ANCHOR_Z, eidosAnchor.z);
                cmp.setDouble(TAG_EIDOS_ANCHOR_PITCH, eidosAnchorPitch);
                cmp.setDouble(TAG_EIDOS_ANCHOR_YAW, eidosAnchorYaw);
                cmp.setInteger(TAG_EIDOS_ANCHOR_TIME, eidosAnchorTime);
            }

            public void load() {
                if(!client) {
                    EntityPlayer player = playerWR.get();

                    if(player != null) {
                        NBTTagCompound cmp = getDataCompoundForPlayer(player);
                        readFromNBT(cmp);
                    }
                }
            }

            public void readFromNBT(NBTTagCompound cmp) {
                level = cmp.getInteger(TAG_LEVEL);
                availablePsi = cmp.getInteger(TAG_AVAILABLE_PSI);
                regenCooldown = cmp.getInteger(TAG_REGEN_CD);
                levelPoints = cmp.getInteger(TAG_LEVEL_POINTS);
                lastSpellGroup = cmp.getString(TAG_LAST_SPELL_GROUP);

                if(cmp.hasKey(TAG_SPELL_GROUPS_UNLOCKED)) {
                    spellGroupsUnlocked.clear();
                    NBTTagList list = cmp.getTagList(TAG_SPELL_GROUPS_UNLOCKED, 8); // 8 -> String
                    int count = list.tagCount();
                    for(int i = 0; i < count; i++)
                        spellGroupsUnlocked.add(list.getStringTagAt(i));
                }

                double x = cmp.getDouble(TAG_EIDOS_ANCHOR_X);
                double y = cmp.getDouble(TAG_EIDOS_ANCHOR_X);
                double z = cmp.getDouble(TAG_EIDOS_ANCHOR_X);
                eidosAnchor.set(x, y, z);
                eidosAnchorPitch = cmp.getDouble(TAG_EIDOS_ANCHOR_PITCH);
                eidosAnchorYaw = cmp.getDouble(TAG_EIDOS_ANCHOR_YAW);
                eidosAnchorTime = cmp.getInteger(TAG_EIDOS_ANCHOR_TIME);
            }

            @SideOnly(Side.CLIENT)
            public void render(EntityPlayer player, float partTicks) {
                RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
                double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partTicks - renderManager.viewerPosX;
                double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partTicks - renderManager.viewerPosY;
                double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partTicks - renderManager.viewerPosZ;

                float scale = 0.75F;
                if(loopcasting) {
                    float mul = Math.min(5F, loopcastTime + partTicks) / 5F;
                    scale *= mul;
                } else if(loopcastFadeTime > 0) {
                    float mul = Math.min(5F, loopcastFadeTime - partTicks) / 5F;
                    scale *= mul;
                } else return;

                /*
                int color = ICADColorizer.DEFAULT_SPELL_COLOR;

                ItemStack cad = PsiAPI.getPlayerCAD(playerWR.get());
                if(!cad.isEmpty() && cad.getItem() instanceof ICAD) {
                    ICAD icad = (ICAD) cad.getItem();
                    color = icad.getSpellColor(cad);
                }
                RenderSpellCircle.renderSpellCircle(ClientTickHandler.ticksInGame + partTicks, scale, x, y, z, color);
                */
            }

            public static class Deduction {

                public final int current;
                public final int deduct;
                public final int cd;
                public final boolean shatter;

                public int elapsed;

                public boolean invalid;

                public Deduction(int current, int deduct, int cd, boolean shatter) {
                    this.current = current;
                    this.deduct = deduct;
                    this.cd = cd;
                    this.shatter = shatter;
                }

                public void tick() {
                    elapsed++;

                    if(elapsed >= cd)
                        invalid = true;
                }

                public float getPercentile(float partTicks) {
                    return 1F - Math.min(1F, (elapsed + partTicks) / cd);
                }
            }

        }

        @SubscribeEvent
        public void onServerTick(TickEvent.ServerTickEvent event) {
            if(event.phase == TickEvent.Phase.END) {
                PlayerDataHandler.cleanup();

                /*List<SpellContext> delayedContextsCopy = new ArrayList(delayedContexts);
                for(SpellContext context : delayedContextsCopy) {
                    context.delay--;

                    if(context.delay <= 0) {
                        context.delay = 0; // Just in case it goes under 0
                        context.cspell.safeExecute(context);

                        if(context.delay == 0)
                            delayedContexts.remove(context);
                    }
                }*/
            }
        }
}