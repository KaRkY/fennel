package org.fennel.api;

public class FennelException extends Exception {
  private static final long serialVersionUID = 5648927329043732514L;

  public FennelException() {
    super();
  }

  public FennelException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public FennelException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FennelException(final String message) {
    super(message);
  }

  public FennelException(final Throwable cause) {
    super(cause);
  }

}
