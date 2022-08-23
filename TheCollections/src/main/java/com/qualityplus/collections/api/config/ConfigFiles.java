package com.qualityplus.collections.api.config;

import com.qualityplus.assistant.config.ConfigReloader;

public interface ConfigFiles <T, I, C, M, CT, CA> extends ConfigReloader {
    T config();
    I inventories();
    M messages();
    C commands();
    CT collections();
    CA categories();
}
