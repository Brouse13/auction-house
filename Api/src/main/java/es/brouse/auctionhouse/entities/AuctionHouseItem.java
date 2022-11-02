package es.brouse.auctionhouse.entities;

import es.brouse.auctionhouse.serialize.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class AuctionHouseItem implements Entity {
    @Getter private String identifier;

    @Getter private String owner;

    @Getter private String last_better;

    @Getter private String material;

    @Getter private Double price;

    @Override
    public String getName() {
        return "AuctionHouseItems";
    }
}
