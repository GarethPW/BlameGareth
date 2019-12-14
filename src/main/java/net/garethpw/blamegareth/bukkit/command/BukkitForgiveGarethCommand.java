package net.garethpw.blamegareth.bukkit.command;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;

public final class BukkitForgiveGarethCommand extends BukkitBaseBlameCommand {

  public BukkitForgiveGarethCommand() {
    super("forgivegareth", "blamegareth.blame", "forgiven");
  }

  @Override
  protected int increment() {
    return BukkitBlameGarethPlugin.getInstance().incrementForgives();
  }

}
