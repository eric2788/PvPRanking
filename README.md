# PvPRanking

使用 Normalization 作為 排位分段的 插件。

有問題請善用 issue
## 一般資訊

### 版本: 
1.12.2 ~ 1.13.2(1.12.2 尚未測試)

[!] Java11 版本

插件版本: 請到 [這裏](/src/plugin.yml) 查看

### 可選掛接
PlaceHolderAPI:

    %pvprank_rank% - 返回段位
    %pvprank_score% - 返回積分
    %pvprank_n-score% - 返回標準分
    %pvprank_plays% - 返回遊玩次數


### 指令:
  - /pvprank <info | reset> <玩家> - 查看/重設排位資料
 - /pvpdata <info | reset> <玩家> - 查看/重設玩家資料

### 權限
 - pvprank.info - 查看排位資料
 - pvprank.reset - 重設排位資料
 - pvprank.help - 查看幫助
 - pvpdata.info - 查看玩家資料
 - pvpdata.reset - 重設玩家資料
 - pvprank.admin - 以上所有

## 一般運作
透過獲取所有玩家數據內的積分，并進行標準差和平均計算，然後透過玩家與平均分的差距獲取排位數據。
排位段位，更新時間及主導儲存等等均可自定義設置。

設置方面，所有 yml 內均有解釋


## 下載
[文件目錄下有提供](/PvPRanking.jar)


## 使用

扔進去插件資料夾即可。但在這裏建議伺服器需要平均達到一定的人數，否則計算可能出現異常。

目前一般升段降段都會有通知，但插件師可以取消事件并採用新的通知方式。

至於聊天格式顯示插件，如果你是服主，請尋找有支援PlaceholderAPI的聊天格式插件并用其變數；若果你是製作聊天格式插件的插件師，則可使用本插件的 API 提取數據。

## API 

[查看JavaDocs文件](https://free-mc-plugins.github.io/PvPRanking)

[查看JavaDocs文件API部分](https://free-mc-plugins.github.io/PvPRanking/com/ericlam/mc/ranking/api/package-summary.html)

[查看JavaDocs文件事件部分](https://free-mc-plugins.github.io/PvPRanking/com/ericlam/mc/ranking/bukkit/event/package-summary.html)

### 基本掛接
- 透過 implements PlayerData 掛接自定義的儲存數據
- 透過 extends DataHandler 掛接自定義的儲存數據的各種方法(包括獲取/儲存/刪除)
- 透過 使用 PvPRankingAPI 獲得 所需數據
- 目前任何數值修改後一律需要使用 PvPRankingAPI.update(UUID uuid) 作為強制更新段位數據而作即時更新。


### 維基
如果仍然不懂如何掛接可參考[圖文教學](https://github.com/free-mc-plugins/PvPRanking/wiki/%E6%9C%89%E9%97%9C%E8%A8%BB%E5%86%8A%E6%8E%92%E4%BD%8D%E7%B3%BB%E7%B5%B1%E7%9A%84-API)。
