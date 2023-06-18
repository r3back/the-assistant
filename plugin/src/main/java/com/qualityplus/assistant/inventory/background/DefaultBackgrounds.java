package com.qualityplus.assistant.inventory.background;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.qualityplus.assistant.inventory.Item;
import com.qualityplus.assistant.util.itemstack.ItemBuilder;

import java.util.Collections;

/**
 * Default Backgrounds
 *
 * TODO refactor this with a builder
 */
public interface DefaultBackgrounds {
    /**
     *
     * @return {@link Background}
     */
    public default Background getEmptyBackground() {
        return new Background(ImmutableMap.<Integer, Item>builder().build());
    }

    /**
     *
     * @return {@link Background}
     */
    public default Background getBackGroundFiller() {
        return Background.builder()
                .filler(ItemBuilder.of(
                        XMaterial.BLACK_STAINED_GLASS_PANE,
                        1,
                        " ",
                        Collections.emptyList()).build())
                .useFiller(true)
                .build();
    }

    /**
     *
     * @return {@link Background}
     */
    public default Background getBackgroundWithLastRowFilled() {
        final Item item = ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()).build();

        return new Background(ImmutableMap.<Integer, Item>builder()
                .put(45, item)
                .put(46, item)
                .put(47, item)
                .put(48, item)
                .put(50, item)
                .put(51, item)
                .put(52, item)
                .put(53, item)
                .build());
    }


    /**
     *
     * @return {@link Background}
     */
    public default Background getBackgroundWith4RowsDecoratedAround() {
        final Item item = ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()).build();

        return new Background(ImmutableMap.<Integer, Item>builder()
                .put(0, item)
                .put(1, item)
                .put(2, item)
                .put(3, item)
                .put(4, item)
                .put(5, item)
                .put(6, item)
                .put(7, item)
                .put(8, item)
                .put(9, item)
                .put(18, item)
                .put(27, item)
                .put(28, item)
                .put(29, item)
                .put(30, item)
                .put(32, item)
                .put(33, item)
                .put(34, item)
                .put(35, item)
                .put(17, item)
                .put(26, item)
                .build());
    }

    /**
     *
     * @return {@link Background}
     */
    public default Background getBackgroundWith6RowsDecoratedAround() {
        final Item item = ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()).build();

        return new Background(ImmutableMap.<Integer, Item>builder()
                .put(0, item)
                .put(1, item)
                .put(2, item)
                .put(3, item)
                .put(4, item)
                .put(5, item)
                .put(6, item)
                .put(7, item)
                .put(8, item)
                .put(9, item)
                .put(18, item)
                .put(27, item)
                .put(36, item)
                .put(45, item)
                .put(17, item)
                .put(26, item)
                .put(35, item)
                .put(44, item)
                .put(53, item)
                .put(46, item)
                .put(47, item)
                .put(48, item)
                .put(50, item)
                .put(51, item)
                .put(52, item)
                .build());
    }
}
