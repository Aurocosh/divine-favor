package aurocosh.divinefavor.common.constants;

public final class LibMisc {
    // Mod Constants
    public static final String MOD_ID = "divinefavor";
    public static final String MOD_NAME = "Divine Favor";
    public static final String BUILD = "GRADLE:BUILD";
    public static final String VERSION = "GRADLE:VERSION-" + BUILD;
    public static final String DEPENDENCIES = "required-before:autoreglib;required-after:patchouli;";
    public static final String PREFIX_MOD = MOD_ID + ":";
    public static final String PREFIX_CONF = MOD_ID + ".";
    public static final boolean BETA_TESTING = false;

    // Network Contants
    public static final String NETWORK_CHANNEL = MOD_ID;

    // Proxy Constants
    public static final String PROXY_COMMON = "aurocosh.divinefavor.common.core.proxy.CommonProxy";
    public static final String PROXY_CLIENT = "aurocosh.divinefavor.client.core.proxy.ClientProxy";
    public static final String GUI_FACTORY = "aurocosh.divinefavor.client.core.proxy.GuiFactory";

    public static final String logging = PREFIX_CONF + "logging";
    public static final String content = PREFIX_CONF + "content";
    public static final int SQ = 18;
}
