package com.qualityplus.assistant.base.factory;

import com.qualityplus.assistant.api.util.IPlaceholder;
import com.qualityplus.assistant.config.ConfigDatabase;
import com.qualityplus.assistant.util.StringUtils;
import com.qualityplus.assistant.util.placeholder.Placeholder;
import com.qualityplus.assistant.util.placeholder.PlaceholderBuilder;

import java.util.List;
import java.util.Optional;

public interface UriGetter {

    default String getUri(ConfigDatabase configDatabase){

        List<IPlaceholder> placeholderList = PlaceholderBuilder.create(
                new Placeholder("user", Optional.ofNullable(configDatabase.userName).orElse("")),
                new Placeholder("host", Optional.ofNullable(configDatabase.host).orElse("")),
                new Placeholder("port", Optional.ofNullable(String.valueOf(configDatabase.port)).orElse("")),
                new Placeholder("password", Optional.ofNullable(configDatabase.passWord).orElse("")),
                new Placeholder("database", Optional.ofNullable(configDatabase.database).orElse(""))
        ).get();

        return StringUtils.processMulti(configDatabase.type.getUri(), placeholderList);
    }
}
