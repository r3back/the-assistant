package com.qualityplus.enchanting.api.config;

import com.qualityplus.assistant.config.ConfigReloader;

public interface ConfigFiles<C, R, I, M, CMD> extends ConfigReloader {
    C config();
    R recipes();
    I inventories();
    M messages();
    CMD commands();
}
