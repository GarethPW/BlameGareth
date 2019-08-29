package net.garethpw.blamegareth.common.command;

public abstract class BaseBlameCommand {

  public static final String indicator(final int number) {
    final int mod100 = number % 100;

    if (11 <= mod100 && mod100 <= 13) {
      return "th";
    } else {
      switch (mod100 % 10) {
        case 1:  return "st";
        case 2:  return "nd";
        case 3:  return "rd";
        default: return "th";
      }
    }
  }

}
