package net.garethpw.blamegareth.bukkit.command;

import static net.garethpw.blamegareth.common.command.BaseBlameCommand.*;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

abstract class BukkitBaseBlameCommand extends BukkitBaseCommand {

  private final String participle;

  protected BukkitBaseBlameCommand(final String name, final String permission, final String participle) {
    super(name, permission);
    this.participle = participle;
  }

  protected abstract int increment();

  @Override
  public final void execute(final CommandSender sender, final String[] args) {
    if (BukkitBlameGarethPlugin.getRateLimiter().canExecute(sender)) {
      final TextComponent message = new TextComponent(sender.getName());
      message.setColor(ChatColor.RED);

      final int number = increment();

      final TextComponent extra = new TextComponent(String.format(
        " has %s Gareth for the %d%s time!",
        participle, number, indicator(number)
      ));
      extra.setColor(ChatColor.GOLD);

      message.addExtra(extra);

      ProxyServer.getInstance().broadcast(message);
    } else {
      final TextComponent message = new TextComponent("You're doing that too quickly!");
      message.setColor(ChatColor.RED);

      sender.sendMessage(message);
    }
  }

}
