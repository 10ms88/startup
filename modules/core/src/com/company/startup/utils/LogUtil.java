/*
 * (c) Sergey Butenin 2017
 */

package com.company.startup.utils;/*
 * created by Sergey Eg 3/20/2018
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void printDebug(String msg) {
        log.debug("DEBUG: " + msg);
    }

    public static void printError(String msg) {
        log.error(msg);
    }

}
