package it.demib.stabletoolkitback.utility;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
public class LogMessageUtility {

  public static String assembleLogMessage(String className, String methodName, String logMessage) {
    return String.format("%s::%s - %s", className, methodName, logMessage);
  }
}
