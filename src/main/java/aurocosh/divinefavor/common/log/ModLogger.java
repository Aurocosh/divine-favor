/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package aurocosh.divinefavor.common.log;

import aurocosh.divinefavor.common.config.IHasConfig;
import aurocosh.divinefavor.common.lib.LibMisc;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ModLogger implements IHasConfig {

  private Logger logger;
  private boolean sendInfo = true;//info are things we WANT to stay on release. by default
  private boolean sendLogs = false;//in config. only used for dev or live debugging
  private boolean runUnitTests;

  public ModLogger(Logger l) {
    logger = l;
  }

  /**
   * info defaults to TRUE in config file use this for logs you want to run in release
   * 
   * @param string
   */
  public void info(String string) {
    if (sendInfo)
      logger.info(string);
  }

  /**
   * Defaults to FALSE in config file use for dev debugging, and then leave some in place for release that will safely not spam out unless turned on
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

  /**
   * always check this before running a unit test
   * 
   * @return
   */
  public boolean runUnitTests() {
    return this.runUnitTests;
  }

  /**
   * logs only if runUnitTests() is true
   * 
   * @param string
   */
  public void logTestResult(String string) {
    if (runUnitTests())
      logger.info("[UnitTest]" + string);
  }

  @Override
  public void syncConfig(Configuration config) {
    String category = LibMisc.logging;
    sendInfo = config.getBoolean("Information", category, true, "Log basic game startup information such as ore dictionary registration");
    runUnitTests = config.getBoolean("UnitTests", category, false, "Run unit tests on startup and log the result.  Still experimental and not widely used");
    sendLogs = config.getBoolean("Debug", category, false, "Log debug related information.  This can be very spammy, only used for debugging problems or new features, so just leave it off normally.");
  }
}
