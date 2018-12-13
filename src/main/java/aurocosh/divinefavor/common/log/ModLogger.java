package aurocosh.divinefavor.common.log;

import org.apache.logging.log4j.Logger;

public class ModLogger {

    private Logger logger;
    private boolean sendInfo = true;//info are things we WANT to stay on release. by default
    private boolean sendLogs = false;//in config. only used for dev or live debugging

    public ModLogger(Logger l) {
        logger = l;
    }

    /**
     * info defaults to TRUE in config file gainFavor this for logs you want to run in release
     *
     * @param string
     */
    public void info(String string) {
        if (sendInfo)
            logger.info(string);
    }

    /**
     * Defaults to FALSE in config file gainFavor for dev debugging, and then leave some in place for release that will safely not spam out unless turned on
     *
     * @param string
     */
    public void log(String string) {
        if (sendLogs)
            logger.info(string);
    }

    /**
     * Always send the log in every environment
     *
     * @param string
     */
    public void error(String string) {
        logger.error(string);
    }

    public void error(String string, Object... params) {
        logger.error(string, params);
    }
}
