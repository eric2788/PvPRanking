# PvPRanking

Using Normalization as ranking calculation。

## Information

### Version: 
1.12.2 ~ 1.13.2 (1.12.2 / 1.14~ not tested)

[!] Java8

plugin version: go to [here](/src/plugin.yml)

### hooking
PlaceHolderAPI:

    %pvprank_rank% - get the rank
    %pvprank_score% - get the scores
    %pvprank_n-score% - get the n-scores
    %pvprank_plays% - get the played times


### commands:
  - /pvprank <info | reset> <player> - check/reset rank data
 - /pvpdata <info | reset> <player> - check/reset player data

### permissions
 - pvprank.info - check rank data
 - pvprank.reset - reset rank data
 - pvprank.help - help
 - pvpdata.info - check player data
 - pvpdata.reset - check player data
 - pvprank.admin - above all

## function
get all the player data and using normalization calculate the rank

all config in yml

In [Wiki](https://github.com/free-mc-plugins/PvPRanking/wiki), you can check the calculation result.


## download
[repo provided](/PvPRanking.jar)


## API 

[JavaDocs](https://free-mc-plugins.github.io/PvPRanking)

[JavaDocs API](https://free-mc-plugins.github.io/PvPRanking/com/ericlam/mc/ranking/api/package-summary.html)

[JavaDocs event](https://free-mc-plugins.github.io/PvPRanking/com/ericlam/mc/ranking/bukkit/event/package-summary.html)

### how to hook
- implementing PlayerData to create custom data
- extending DataHandler to customize
- using PvPRankingAPI to get data
- using PvPRankingAPI.update(UUID uuid) for updating data instantly.


### Wiki
If you still don't know how to hook, you can check this [tutorial](https://github.com/free-mc-plugins/PvPRanking/wiki/%5BEN%5D-Hooking-into-PvPRanking).
