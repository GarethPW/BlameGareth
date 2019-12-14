package net.garethpw.blamegareth.bungee.command;

import net.garethpw.blamegareth.bungee.BungeeBlameGarethPlugin;

public final class BungeeForgiveGarethCommand extends BungeeBaseBlameCommand {

  public BungeeForgiveGarethCommand() {
    super("forgivegareth", "blamegareth.blame", "forgiven");
  }

  @Override
  protected int increment() {
    return BungeeBlameGarethPlugin.getInstance().incrementForgives();
  }

}
