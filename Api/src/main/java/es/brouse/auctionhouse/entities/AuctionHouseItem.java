package es.brouse.auctionhouse.entities;

import es.brouse.auctionhouse.serialize.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionHouseItem implements Entity {
    @Getter private String owner;

    @Getter private String last_better;

    @Getter private String material;

    @Getter private Integer price;

    @Override
    public String getName() {
        return "AuctionHouseItems";
    }
}
