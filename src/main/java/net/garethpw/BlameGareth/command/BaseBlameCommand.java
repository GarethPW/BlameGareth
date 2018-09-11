package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.BlameGarethPlugin;
import net.garethpw.BlameGareth.RateLimiter;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

abstract class BaseBlameCommand extends BaseCommand {

  protected static ProxyServer proxy = ProxyServer.getInstance();
  protected static RateLimiter rateLimiter = BlameGarethPlugin.getRateLimiter();

  protected String participle;

  public BaseBlameCommand(String name, String permission, String actionParticiple) {
    super(name, permission);
    participle = actionParticiple;
  }

  private static String indicator(int number) {
    int mod100 = number % 100;

    if (11 <= mod100 && mod100 <= 19) {
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

  protected abstract int increment();

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (rateLimiter.canExecute(sender)) {
      TextComponent message = new TextComponent(sender.getName());
      message.setColor(ChatColor.RED);

      int number = increment();

      TextComponent extra = new TextComponent(String.format(
        " has %s Gareth for the %d%s time!",
        participle, number, indicator(number)
      ));
      extra.setColor(ChatColor.GOLD);

      message.addExtra(extra);

      proxy.broadcast(message);
    } else {
      TextComponent message = new TextComponent("You're doing that too quickly!");
      message.setColor(ChatColor.RED);

      sender.sendMessage(message);
    }
  }

}
