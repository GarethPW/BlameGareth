package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.command.BaseBlameCommand;

public class ForgiveGarethCommand extends BaseBlameCommand {

  public ForgiveGarethCommand() {
    super("forgivegareth", "blamegareth.blame", "forgiven");
  }

  protected int increment() { return plugin.incrementForgives(); }

}
