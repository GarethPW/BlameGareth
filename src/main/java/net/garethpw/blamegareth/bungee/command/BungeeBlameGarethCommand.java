package net.garethpw.blamegareth.bungee.command;

import net.garethpw.blamegareth.bungee.BungeeBlameGarethPlugin;

public final class BungeeBlameGarethCommand extends BungeeBaseBlameCommand {

  public BungeeBlameGarethCommand() {
    super("blamegareth", "blamegareth.blame", "blamed");
  }

  @Override
  protected int increment() {
    return BungeeBlameGarethPlugin.getInstance().incrementBlames();
  }

}
