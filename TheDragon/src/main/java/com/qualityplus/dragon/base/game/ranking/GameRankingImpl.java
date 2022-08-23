package com.qualityplus.dragon.base.game.ranking;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;
import com.qualityplus.dragon.TheDragon;
import com.qualityplus.dragon.api.TheDragonAPI;
import com.qualityplus.dragon.api.game.dragon.TheDragonEntity;
import com.qualityplus.dragon.api.game.ranking.GameRanking;
import com.qualityplus.dragon.api.service.UserDBService;
import com.qualityplus.dragon.base.configs.Messages;
import com.qualityplus.dragon.base.game.player.EventPlayer;
import com.qualityplus.dragon.persistance.data.UserData;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.platform.core.annotation.Component;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public final class GameRankingImpl implements GameRanking {
    private final Map<Integer, EventPlayer> eventPlayerMap = new HashMap<>();
    private @Inject UserDBService userDBService;
    private @Inject Messages messages;

    @Override
    public List<IPlaceholder> getPlaceholders(EventPlayer player) {
        return PlaceholderBuilder.create()
                .with(new Placeholder("dragon_player_rank", getByPlayer(player)),
                      new Placeholder("dragon_player_got_record", getRecordMessage(player)),
                      new Placeholder("dragon_player_damage", player.getDamage()),
                      new Placeholder("dragon_entity_xp", getDragonXp()),
                      new Placeholder("dragon_last_attacker", getLastAttacker()))
                .with(getCommonPlaceholders())
                .get();
    }


    @Override
    public Optional<EventPlayer> getByRank(int ranking) {
        return Optional.ofNullable(eventPlayerMap.getOrDefault(ranking, null));
    }

    @Override
    public void refreshRanking(){
        TheDragonAPI api = TheDragon.getApi();

        List<EventPlayer> eventPlayers = api.getUserService().getUsers();

        eventPlayers.sort((o1, o2) -> (int) (o2.getDamage() - o1.getDamage()));

        this.eventPlayerMap.clear();

        int ranking = 0;

        for(EventPlayer eventPlayer : eventPlayers){
            this.eventPlayerMap.put(ranking, eventPlayer);
            ranking++;
        }
    }

    private String getRecordMessage(EventPlayer player){
        Optional<UserData> dragonPlayer = userDBService.getDragonData(player.getUuid());

        double oldRecord = dragonPlayer.map(UserData::getRecord).orElse(0.0);

        return player.getDamage() > oldRecord ? messages.setupMessages.newRecordMessage : "";
    }

    private List<IPlaceholder> getCommonPlaceholders(){
        String notFound = messages.setupMessages.playerNotFound;

        return PlaceholderBuilder
                .create(IntStream.of(0,1,2)
                        .boxed()
                        .map(number -> new Placeholder(String.format("dragon_player_top_%d_name", number + 1), getByRank(number).map(EventPlayer::getName).orElse(notFound)))
                        .collect(Collectors.toList()))
                .with(IntStream.of(0,1,2)
                        .boxed()
                        .map(number -> new Placeholder(String.format("dragon_damage_top_%d_name", number + 1), getByRank(number).map(EventPlayer::getDamage).orElse(0D)))
                        .collect(Collectors.toList()))
                .get();
    }
    private String getLastAttacker(){
        return Optional.ofNullable(TheDragon.getApi().getUserService().getLast())
                .filter(Objects::nonNull)
                .map(uuid -> Bukkit.getOfflinePlayer(uuid).getName())
                .orElse("");
    }


    private double getDragonXp(){
        return Optional.ofNullable(TheDragon.getApi().getDragonService().getActiveDragon())
                .map(TheDragonEntity::getXp)
                .orElse(0D);
    }

    private Integer getByPlayer(EventPlayer eventPlayer) {
        return eventPlayerMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getName().equals(eventPlayer.getName()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);
    }

}
