package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.ranking.bukkit.commands.RankCommandExecutor;
import com.ericlam.mc.ranking.main.PvPRanking;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Don't ask me why I use Runnable, I just wanna try different way to implement this shit
 * 哈？ 為啥我用 Runnable ? 我只是想試試新方法玩玩而已ww
 */
public class PvPDataCommandExecutor implements CommandExecutor, TabCompleter {

    private PvPRanking plugin;
    private List<String> subcommands = new ArrayList<>();

    public PvPDataCommandExecutor(PvPRanking plugin) {
        this.plugin = plugin;
        subcommands.add("info");
        subcommands.add("reset");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ComponentBuilder author = new ComponentBuilder("§7插件作者: §m§bEric Lam").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/eric2788")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§b打開 Github 連結")));
        ComponentBuilder source = new ComponentBuilder("§d>> 插件原始碼").event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/eric2788/PvPRanking")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§b打開 Github 連結")));
        if (strings.length < 2) {
            commandSender.sendMessage("§ePvPRanking §7- §e版本 §a" + PvPRanking.getPlugin().getDescription().getVersion());
            commandSender.spigot().sendMessage(author.create());
            commandSender.spigot().sendMessage(source.create());
            if (!commandSender.hasPermission("pvprank.help")) return true;
            commandSender.sendMessage("§e幫助:");
            commandSender.sendMessage("§a/pvpdata info <player> §7- 查看玩家的排位");
            commandSender.sendMessage("§a/pvpdata reset <player> §7- 重設玩家的排位");
            return true;
        }

        var method = strings[0].toLowerCase();
        var player = RankCommandExecutor.getOfflinePlayer(strings[1]);

        if (!subcommands.contains(method)) {
            commandSender.sendMessage("§c找不到此指令。");
            return false;
        }

        if (!commandSender.hasPermission("pvpdata." + method)) {
            commandSender.sendMessage("§c你沒有權限。");
            return false;
        }
        switch (method) {
            case "info":
                new InfoRunnable().setSender(commandSender).setPlayer(player).runTaskAsynchronously(plugin);
                return true;
            case "reset":
                new ResetRunnable().setSender(commandSender).setPlayer(player).runTaskAsynchronously(plugin);
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return strings.length == 1 ? subcommands : null;
    }
}
