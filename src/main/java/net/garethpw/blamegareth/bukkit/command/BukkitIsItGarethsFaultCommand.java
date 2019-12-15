package net.garethpw.blamegareth.bukkit.command;

import static net.garethpw.blamegareth.common.command.IsItGarethsFaultCommand.*;

import net.garethpw.blamegareth.bukkit.BukkitBlameGarethPlugin;
import net.garethpw.blamegareth.common.Stats;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class BukkitIsItGarethsFaultCommand implements CommandExecutor {

  @Override
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    final Stats stats = BukkitBlameGarethPlugin.getInstance().getStats();

    final String blamed = String.format(
      "Blamed: %d time%s\n",
      stats.blameCount, pluralise(stats.blameCount, "", "s")
    );

    final String forgiven = String.format(
      "Forgiven: %d time%s\n",
      stats.forgiveCount, pluralise(stats.forgiveCount, "", "s")
    );

    String verdict = String.format(
      "Verdict: ",
      stats.blameCount > stats.forgiveCount ?
        ChatColor.GREEN + "Yes" : ChatColor.RED + "No"
    );

    if (stats.blameCount > stats.forgiveCount) {
      verdict += ChatColor.GREEN + "Yes";
    } else {
      verdict += ChatColor.RED + "No";
    }

    sender.sendMessage(ChatColor.GOLD + blamed + forgiven + verdict);

    return true;
  }

}
