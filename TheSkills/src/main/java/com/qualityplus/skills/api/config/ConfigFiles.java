package com.qualityplus.skills.api.config;

import com.qualityplus.assistant.config.ConfigReloader;

public interface ConfigFiles <T, I, C, M> extends ConfigReloader {
    T config();
    I inventories();
    M messages();
    C commands();
}
