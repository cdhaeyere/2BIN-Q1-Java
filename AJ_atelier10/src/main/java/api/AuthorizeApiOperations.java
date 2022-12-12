package api;

public interface AuthorizeApiOperations {

  final int START_UNAUTHORIZED_TIME = 0;
  final int START_AUTHORIZED_TIME = 6;

  final String AFTER_HOURS_MESSAGE = "At FREE WEB SERVICES we are doing our best to provide serious jokes. To protect your from addiction, we don’t provide any services at night. See you later in the day.";

  boolean checkIfOperationIsAuthorized();
}
