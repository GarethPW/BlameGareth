package net.garethpw.blamegareth.bukkit.command;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;

public final class BukkitBlameGarethCommand extends BukkitBaseBlameCommand {

  public BukkitBlameGarethCommand() {
    super("blamed");
  }

  @Override
  protected int increment() {
    return BukkitBlameGarethPlugin.getInstance().incrementBlames();
  }

}
