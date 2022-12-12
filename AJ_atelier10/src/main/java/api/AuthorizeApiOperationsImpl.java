package api;

import jakarta.inject.Inject;
import utils.TimeProvider;

public class AuthorizeApiOperationsImpl implements AuthorizeApiOperations {

  @Inject
  private TimeProvider timeProvider ;

  @Override
  public boolean checkIfOperationIsAuthorized() {
    int currentHour = timeProvider.getCurrentHour();

    if (currentHour >= START_UNAUTHORIZED_TIME && currentHour < START_AUTHORIZED_TIME) {
      return false;
    }
    return true;
  }


}
