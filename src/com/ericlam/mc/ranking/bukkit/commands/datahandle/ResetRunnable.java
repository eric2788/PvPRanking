package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.rankcal.RankDataManager;

public class ResetRunnable extends CommandRunnable {

    @Override
    public void run() {
        if (player == null) {
            sender.sendMessage("§c找不到此玩家。");
            return;
        }

        var success = RankDataManager.getInstance().getDataHandler().removePlayerData(player);
        sender.sendMessage("§e刪除" + (success ? "成功。" : "失敗。"));
    }

}
