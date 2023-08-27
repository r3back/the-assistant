package com.qualityplus.assistant.base.dependency;

import com.qualityplus.assistant.TheAssistantPlugin;
import lombok.experimental.UtilityClass;

/**
 * ProtocolLib Dependency Resolver
 *
 * TODO check if this class can be replaced with an addon instance
 */
@UtilityClass
public final class ProtocolLibDependency {
    private static final String PROTOCOL_LIB_PLUGIN_NAME = "ProtocolLib";
    private static final Boolean IS_PROTOCOL_LIB;

    static {
        IS_PROTOCOL_LIB = TheAssistantPlugin.getAPI()
                .getDependencyResolver()
                .isPlugin(PROTOCOL_LIB_PLUGIN_NAME);
    }

    /**
     * Retrieve is protocol lib is present
     *
     * @return true if protocol lib is installed
     */
    public Boolean isProtocolLib() {
        return IS_PROTOCOL_LIB;
    }
}
