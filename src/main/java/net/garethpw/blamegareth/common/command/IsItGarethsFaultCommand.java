package net.garethpw.blamegareth.common.command;

public abstract class IsItGarethsFaultCommand {

  public static final String pluralise(final int number, final String singular, final String plural) {
    return number == 1 ? singular : plural;
  }

}
