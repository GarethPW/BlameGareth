package net.garethpw.BlameGareth.command;

import net.garethpw.BlameGareth.BlameGarethPlugin.Stats;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class IsItGarethsFaultCommand extends BaseCommand {

  public IsItGarethsFaultCommand() {
    super("isitgarethsfault", "blamegareth.check", "iigf", "checkgareth");
  }

  private String pluralise(int number, String singular, String plural) {
    return number == 1 ? singular : plural;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    Stats stats = plugin.getStats();

    TextComponent message = new TextComponent(String.format(
      "Blamed: %d time%s\n",
      stats.blameCount, pluralise(stats.blameCount, "", "s")
    ));

    message.addExtra(String.format(
      "Forgiven: %d time%s\n",
      stats.forgiveCount, pluralise(stats.forgiveCount, "", "s")
    ));

    message.addExtra("Verdict: ");
    message.setColor(ChatColor.GOLD);

    TextComponent verdictBool = new TextComponent();

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
