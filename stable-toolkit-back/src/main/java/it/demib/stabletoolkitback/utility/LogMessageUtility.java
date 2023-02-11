package it.demib.stabletoolkitback.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessageUtility {

  public static String assembleLogMessage(String className, String methodName, String logMessage) {
    return String.format("%s::%s - %s", className, methodName, logMessage);
  }
}
