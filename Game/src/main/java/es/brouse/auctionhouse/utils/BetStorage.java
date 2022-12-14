package es.brouse.auctionhouse.utils;

import com.google.common.collect.Maps;
import es.brouse.auctionhouse.entities.AuctionHouseItem;

import java.util.Map;

public class BetStorage {
    private static final Map<String, AuctionHouseItem> items = Maps.newHashMap();

    public boolean modify(String key, AuctionHouseItem item) {
        items.put(key, item);
        return true;
    }
}
