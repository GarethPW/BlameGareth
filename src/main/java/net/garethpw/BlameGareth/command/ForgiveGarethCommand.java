package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.BlameGarethPlugin;
import net.garethpw.BlameGareth.command.BaseBlameCommand;

public final class ForgiveGarethCommand extends BaseBlameCommand {

  public ForgiveGarethCommand() {
    super("forgivegareth", "blamegareth.blame", "forgiven");
  }

  @Override
  protected int increment() {
    return BlameGarethPlugin.getInstance().incrementForgives();
  }

}
