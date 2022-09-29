package es.brouse.auctionhouse.loader.entities;

import es.brouse.auctionhouse.loader.serializer.Entity;
import es.brouse.auctionhouse.loader.serializer.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "AuctionHouseItems")
public class AuctionHouseItem {
    @Serializable(name = "owner", identifier = true)
    @Getter
    private String owner;

    @Serializable(name = "material")
    @Getter private String material;

    @Serializable(name = "price")
    @Getter private Integer price;
}

