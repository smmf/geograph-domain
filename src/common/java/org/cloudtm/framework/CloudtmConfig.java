package org.cloudtm.framework;

import pt.ist.fenixframework.Config;

/**
 * @file CloudtmConfig.java
 *
 * @brief Brief file description.
 *
 * Detailed file description.
 *
 * @author Fabio Cottefoglie <cottefoglie@algorithmica.it>
 * @date Jan 17, 2012
 */

/**
 * @class CloudtmConfig
 *
 * @brief CloudtmConfig brief description.
 *
 * CloudtmConfig detailed description.
 *
 * @note Type class notes here.
 * @todo Type todo here.
 */
public class CloudtmConfig {

  /*
   * Cloud-TM available frameworks
   */
  public enum Framework {
    FENIX,
    OGM,
    ISPN
  }

  protected Framework framework = null;

  public Framework getFramework() {
    return framework;
  }
}
