package es.brouse.auctionhouse.entities;

import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;

public class AuctionHouseItemWrapper implements EntityWrapper<AuctionHouseItem> {
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
