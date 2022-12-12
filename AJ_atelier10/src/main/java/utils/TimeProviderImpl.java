package utils;

import java.time.LocalDateTime;

public class TimeProviderImpl implements TimeProvider {

  @Override
  public int getCurrentHour() {
    return LocalDateTime.now().getHour();
  }
}
