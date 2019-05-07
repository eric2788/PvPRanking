package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.ranking.bukkit.commands.RankCommandExecutor;
import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Don't ask me why I use Runnable, I just wanna try different way to implement this shit
 * 哈？ 為啥我用 Runnable ? 我只是想試試新方法玩玩而已ww
 */
public class PvPDataCommandExecutor implements CommandExecutor, TabCompleter {

    private PvPRanking plugin;
    private Map<String, CommandRunnable> commandmap = new LinkedHashMap<>();

    public PvPDataCommandExecutor(PvPRanking plugin) {
        this.plugin = plugin;
        commandmap.put("info", new InfoRunnable());
        commandmap.put("reset", new ResetRunnable());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage("§e幫助:");
            commandSender.sendMessage("§a/pvpdata info <player> §7- 查看玩家的排位");
            commandSender.sendMessage("§a/pvpdata reset <player> §7- 重設玩家的排位");
            return true;
        }

        var method = strings[0].toLowerCase();
        var player = RankCommandExecutor.getOfflinePlayer(strings[1]);

        if (!commandmap.containsKey(method)) {
            commandSender.sendMessage("§c找不到此指令。");
            return false;
        }

        if (!commandSender.hasPermission("pvpdata." + method)) {
            commandSender.sendMessage("§c你沒有權限。");
            return false;
        }
        CommandRunnable runnable = commandmap.get(method).setSender(commandSender).setPlayer(player);
        if (!runnable.isCancelled()) runnable.cancel();
        runnable.runTaskAsynchronously(plugin);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return strings.length == 1 ? new ArrayList<>(commandmap.keySet()) : null;
    }
}
