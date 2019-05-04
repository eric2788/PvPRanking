# PvPRanking

使用 Normalization 作為 排位分段的 插件。

有問題請善用 issue
## 一般資訊

### 版本: 1.13.2

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
 自行看 plugin.yml

## 一般運作
透過獲取所有玩家數據內的積分，并進行標準差和平均計算，然後透過玩家與平均分的差距獲取排位數據。
排位段位，更新時間及主導儲存等等均可自定義設置。

設置方面，所有 yml 內均有解釋


## 下載
[文件目錄下有提供](/PvPRanking.jar)

## API 

[查看JavaDocs文件](https://eric2788.github.io/PvPRanking)

[查看JavaDocs文件API部分](https://eric2788.github.io/PvPRanking/com/ericlam/mc/ranking/api/package-summary.html)

[查看JavaDocs文件事件部分](https://eric2788.github.io/PvPRanking/com/ericlam/mc/ranking/bukkit/event/package-summary.html)

### 基本掛接
- 透過 implements PlayerData 掛接自定義的儲存數據
- 透過 extends DataHandler 掛接自定義的儲存數據的各種方法(包括獲取/儲存/刪除)
- 透過 使用 PvPRankingAPI 獲得 所需數據
