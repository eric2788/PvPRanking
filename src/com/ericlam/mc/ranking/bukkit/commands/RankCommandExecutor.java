package com.ericlam.mc.ranking.bukkit.commands;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.main.PvPRanking;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class RankCommandExecutor implements CommandExecutor, TabCompleter {
    private PvPRanking pl;

    public RankCommandExecutor(PvPRanking pl) {
        this.pl = pl;
    }

    public static OfflinePlayer getOfflinePlayer(String player) {
        for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
            if (off.getName().equals(player)) return off;
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ComponentBuilder author = new ComponentBuilder("§7Author: §m§bEric Lam").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/eric2788")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§b打開 Github 連結")));
        ComponentBuilder source = new ComponentBuilder("§d>> SourceCode").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/eric2788/PvPRanking")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§b打開 Github 連結")));
        if (strings.length < 2) {
            commandSender.sendMessage("§ePvPRanking §7- §ev §a" + PvPRanking.getPlugin().getDescription().getVersion());
            commandSender.spigot().sendMessage(author.create());
            commandSender.spigot().sendMessage(source.create());
            if (!commandSender.hasPermission("pvprank.help")) return true;
            commandSender.sendMessage("§e幫助:");
            commandSender.sendMessage("§a/pvprank info <player> §7- check player ranking");
            commandSender.sendMessage("§a/pvprank reset <player> §7- reset player ranking");
            return false;
        }

        var method = strings[0].toLowerCase();
        var off = getOfflinePlayer(strings[1]);

        if (off == null) {
            commandSender.sendMessage("§not found player.");
            return false;
        }

        if (!commandSender.hasPermission("pvprank." + method)) {
            commandSender.sendMessage("§no permission。");
            return false;
        }

        switch (method) {
            case "info":
                Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
                    RankData data = RankDataManager.getInstance().getRankData(off.getUniqueId());
                    var list = PvPRanking.getConfigManager().getConfig().getStringList("info-message");
                    list.forEach(line -> {
                        String replaced = line.replace("<name>", off.getName())
                                .replace("<rank>", data.getRank()).replace("<score>", data.getFinalScores() + "")
                                .replace("<n-score>", data.getnScores() + "").replace("<plays>", data.getPlays() + "");
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', replaced));
                    });
                });
                return true;
            case "reset":
                Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
                    var success = RankDataManager.getInstance().removeRankData(off.getUniqueId());
                    commandSender.sendMessage("§edelete " + (success ? "success" : "failed, maybe data empty"));
                });
                return true;
            default:
                commandSender.sendMessage("§cno this command");
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return strings.length == 1 ? List.of("info", "reset") : null;
    }
}
