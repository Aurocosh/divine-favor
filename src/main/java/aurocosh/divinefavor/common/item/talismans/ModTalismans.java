package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spell.common.ModSpells;

import java.util.List;

public final class ModTalismans {
    public static ItemTalisman arrowThrowTalisman;
    public static ItemTalisman bonemeal;
    public static ItemTalisman butchering_strike;
    public static ItemTalisman clock;
    public static ItemTalisman combustion;
    public static ItemTalisman consuming_fury;
    public static ItemTalisman crushing_palm;
    public static ItemTalisman crystalline_road;
    public static ItemTalisman distant_spark;
    public static ItemTalisman earthen_dive;
    public static ItemTalisman empower_axe;
    public static ItemTalisman empower_pickaxe;
    public static ItemTalisman fall_negation;
    public static ItemTalisman fell_tree;
    public static ItemTalisman focused_fury;
    public static ItemTalisman ground_flow;
    public static ItemTalisman grudge;
    public static ItemTalisman heat_wave;
    public static ItemTalisman ignition;
    public static ItemTalisman miners_focus;
    public static ItemTalisman obsidian_road;
    public static ItemTalisman searing_pulse;
    public static ItemTalisman small_fireball_throw;
    public static ItemTalisman snowball_throw;
    public static ItemTalisman stone_fever;
    public static ItemTalisman stoneball_throw;
    public static ItemTalisman surface_shift;
    public static ItemTalisman toadic_jump;
    public static ItemTalisman wild_sprint;
    public static ItemTalisman wind_step;
    public static ItemTalisman winter_breath;
    public static ItemTalisman wooden_punch;

    public static void preInit() {
        arrowThrowTalisman = ModRegistries.items.register(new TalismanBuilder("arrow_throw",20)
                .setSpell(ModSpells.arrow_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = ModRegistries.items.register(new TalismanBuilder("bonemeal",10)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        butchering_strike = ModRegistries.items.register(new TalismanBuilder("butchering_strike",15)
                .setSpell(ModSpells.butchering_strike)
                .castOnUse()
                .castOnRighClick()
                .create());
        combustion = ModRegistries.items.register(new TalismanBuilder("combustion",3)
                .setSpell(ModSpells.combustion)
                .castOnUse()
                .create());
        consuming_fury = ModRegistries.items.register(new TalismanBuilder("consuming_fury",2)
                .setSpell(ModSpells.consuming_fury)
                .castOnUse()
                .castOnRighClick()
                .create());
        crushing_palm = ModRegistries.items.register(new TalismanBuilder("crushing_palm",20)
                .setSpell(ModSpells.crushing_palm)
                .castOnUse()
                .castOnRighClick()
                .create());
        crystalline_road = ModRegistries.items.register(new TalismanBuilder("crystalline_road",5)
                .setSpell(ModSpells.crystalline_road)
                .castOnUse()
                .castOnRighClick()
                .create());
        distant_spark = ModRegistries.items.register(new TalismanBuilder("distant_spark",10)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .castOnRighClick()
                .create());
        earthen_dive = ModRegistries.items.register(new TalismanBuilder("earthen_dive",10)
                .setSpell(ModSpells.earthen_dive)
                .castOnUse()
                .create());
        empower_axe = ModRegistries.items.register(new TalismanBuilder("empower_axe",3)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        empower_pickaxe = ModRegistries.items.register(new TalismanBuilder("empower_pickaxe",3)
                .setSpell(ModSpells.empower_pickaxe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fall_negation = ModRegistries.items.register(new TalismanBuilder("fall_negation",5)
                .setSpell(ModSpells.fall_negation)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = ModRegistries.items.register(new TalismanBuilder("fell_tree",3)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        focused_fury = ModRegistries.items.register(new TalismanBuilder("focused_fury",2)
                .setSpell(ModSpells.focused_fury)
                .castOnUse()
                .castOnRighClick()
                .create());
        ground_flow = ModRegistries.items.register(new TalismanBuilder("ground_flow",6)
                .setSpell(ModSpells.ground_flow)
                .castOnUse()
                .castOnRighClick()
                .create());
        grudge = ModRegistries.items.register(new TalismanBuilder("grudge",2)
                .setSpell(ModSpells.grudge)
                .castOnUse()
                .castOnRighClick()
                .create());
        heat_wave = ModRegistries.items.register(new TalismanBuilder("heat_wave",10)
                .setSpell(ModSpells.heat_wave)
                .castOnUse()
                .castOnRighClick()
                .create());
        ignition = ModRegistries.items.register(new TalismanBuilder("ignition",30)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        miners_focus = ModRegistries.items.register(new TalismanBuilder("miners_focus",2)
                .setSpell(ModSpells.miners_focus)
                .castOnUse()
                .castOnRighClick()
                .create());
        obsidian_road = ModRegistries.items.register(new TalismanBuilder("obsidian_road",5)
                .setSpell(ModSpells.obsidian_road)
                .castOnUse()
                .castOnRighClick()
                .create());
        searing_pulse = ModRegistries.items.register(new TalismanBuilder("searing_pulse",6)
                .setSpell(ModSpells.searing_pulse)
                .castOnUse()
                .create());
        small_fireball_throw = ModRegistries.items.register(new TalismanBuilder("small_fireball_throw",10)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        snowball_throw = ModRegistries.items.register(new TalismanBuilder("snowball_throw",30)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stone_fever = ModRegistries.items.register(new TalismanBuilder("stone_fever",2)
                .setSpell(ModSpells.stone_fever)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = ModRegistries.items.register(new TalismanBuilder("stoneball_throw",30)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        surface_shift = ModRegistries.items.register(new TalismanBuilder("surface_shift",10)
                .setSpell(ModSpells.surface_shift)
                .castOnUse()
                .create());
        toadic_jump = ModRegistries.items.register(new TalismanBuilder("toadic_jump",15)
                .setSpell(ModSpells.toadic_jump)
                .castOnUse()
                .castOnRighClick()
                .create());
        wild_sprint = ModRegistries.items.register(new TalismanBuilder("wild_sprint",3)
                .setSpell(ModSpells.wild_sprint)
                .castOnUse()
                .castOnRighClick()
                .create());
        winter_breath = ModRegistries.items.register(new TalismanBuilder("winter_breath",10)
                .setSpell(ModSpells.winter_breath)
                .castOnUse()
                .castOnRighClick()
                .create());
        wind_step = ModRegistries.items.register(new TalismanBuilder("wind_step", 20)
                .setSpell(ModSpells.wind_step)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = ModRegistries.items.register(new TalismanBuilder("wooden_punch",30)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .setIsFree()
                .create());
        clock = ModRegistries.items.register(new TalismanBuilder("clock",300)
                .setSpell(ModSpells.tell_time)
                .castOnUse()
                .castOnRighClick()
                .create());

        List<ItemTalisman> talismanList = ModRegistries.items.getValues(ItemTalisman.class);
        ModMappers.talismans.register(talismanList);
    }

    public static void init() {
    }
}