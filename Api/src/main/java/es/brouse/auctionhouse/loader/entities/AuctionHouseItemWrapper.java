package es.brouse.auctionhouse.loader.entities;

import es.brouse.auctionhouse.loader.serialize.EntityWrapper;
import es.brouse.auctionhouse.loader.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.loader.serialize.mapper.EntityReader;

public class AuctionHouseItemWrapper implements EntityWrapper<AuctionHouseItem> {
    @Override
    public AuctionHouseItem mapEntity(EntityReader reader) {
        return AuctionHouseItem.builder()
                .owner(reader.getString("owner"))
                .last_better(reader.getString("last_better"))
                .material(reader.getString("material"))
                .price(reader.getInteger("price"))
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
