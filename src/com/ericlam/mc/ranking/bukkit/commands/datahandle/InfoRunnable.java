package com.ericlam.mc.ranking.bukkit.commands.datahandle;

import com.ericlam.mc.rankcal.RankDataManager;

public class InfoRunnable extends CommandRunnable {

    @Override
    public void run() {
        if (player == null) {
            sender.sendMessage("§c找不到此玩家。");
            return;
        }

        String[] tosend = RankDataManager.getInstance().getDataHandler().showPlayerData(player);

        if (tosend == null || tosend.length == 0) {
            sender.sendMessage("§c已掛接本插件接口的某插件師并沒有允許使用這個指令來顯示數據。");
            return;
        }
        sender.sendMessage(tosend);
    }
}
