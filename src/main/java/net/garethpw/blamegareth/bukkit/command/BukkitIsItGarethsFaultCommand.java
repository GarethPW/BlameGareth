package net.garethpw.blamegareth.bukkit.command;

import static net.garethpw.blamegareth.common.command.IsItGarethsFaultCommand.*;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;
import net.garethpw.blamegareth.common.Stats;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public final class BukkitIsItGarethsFaultCommand extends BukkitBaseCommand {

  public BukkitIsItGarethsFaultCommand() {
    super("isitgarethsfault", "blamegareth.check", "iigf", "checkgareth");
  }

  @Override
  public void execute(final CommandSender sender, final String[] args) {
    final Stats stats = BukkitBlameGarethPlugin.getInstance().getStats();

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
