package es.brouse.auctionhouse.entities;

import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;
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

    public static class Wrapper implements EntityWrapper<AuctionHouseItem> {
        @Override
        public AuctionHouseItem mapEntity(EntityReader reader) {
            return AuctionHouseItem.builder()
                    .identifier(reader.getString("_id"))
                    .owner(reader.getString("owner"))
                    .last_better(reader.getString("last_better"))
                    .material(reader.getString("material"))
                    .price(reader.getDouble("price"))
                    .build();
        }

        @Override
        public EntityMapper<AuctionHouseItem> getMapper(AuctionHouseItem entity) {
            return new EntityMapper<>(entity)
                    .set("owner", entity.getOwner())
                    .set("last_better", entity.getLast_better())
                    .set("material", entity.getMaterial())
                    .set("price", entity.getPrice());
        }
    }
}
