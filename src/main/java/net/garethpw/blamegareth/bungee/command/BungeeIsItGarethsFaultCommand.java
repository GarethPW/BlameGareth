package net.garethpw.blamegareth.bungee.command;

import net.garethpw.blamegareth.bungee.BungeeBlameGarethPlugin;
import net.garethpw.blamegareth.bungee.BungeeBlameGarethPlugin.Stats;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public final class BungeeIsItGarethsFaultCommand extends BungeeBaseCommand {

  public BungeeIsItGarethsFaultCommand() {
    super("isitgarethsfault", "blamegareth.check", "iigf", "checkgareth");
  }

  private static String pluralise(final int number, final String singular, final String plural) {
    return number == 1 ? singular : plural;
  }

  @Override
  public void execute(final CommandSender sender, final String[] args) {
    final Stats stats = BungeeBlameGarethPlugin.getInstance().getStats();

    final TextComponent message = new TextComponent(String.format(
      "Blamed: %d time%s\n",
      stats.blameCount, pluralise(stats.blameCount, "", "s")
    ));

    message.addExtra(String.format(
      "Forgiven: %d time%s\n",
      stats.forgiveCount, pluralise(stats.forgiveCount, "", "s")
    ));

    message.addExtra("Verdict: ");
    message.setColor(ChatColor.GOLD);

    final TextComponent verdictBool = new TextComponent();

    if (stats.blameCount > stats.forgiveCount) {
      verdictBool.setText("Yes");
      verdictBool.setColor(ChatColor.GREEN);
    } else {
      verdictBool.setText("No");
      verdictBool.setColor(ChatColor.RED);
    }

    message.addExtra(verdictBool);

    sender.sendMessage(message);
  }

}
