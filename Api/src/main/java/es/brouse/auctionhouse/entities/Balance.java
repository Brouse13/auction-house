package es.brouse.auctionhouse.entities;

import es.brouse.auctionhouse.serialize.Entity;
import es.brouse.auctionhouse.serialize.EntityWrapper;
import es.brouse.auctionhouse.serialize.mapper.EntityMapper;
import es.brouse.auctionhouse.serialize.mapper.EntityReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class Balance implements Entity {
    @Getter private final String name = "Balance";
    @Getter private String identifier;
    @Getter private double balance;

    public Balance(UUID uuid, double balance) {
        this.identifier = uuid.toString();
        this.balance = balance;
    }

    public static class Wrapper implements EntityWrapper<Balance> {

        @Override
        public Balance mapEntity(EntityReader entityReader) {
            return Balance.builder()
                    .identifier(entityReader.getString("_id"))
                    .balance(entityReader.getDouble("balance"))
                    .build();
        }

        @Override
        public EntityMapper<Balance> getMapper(Balance entity) {
            return new EntityMapper<>(entity)
                    .set("balance", entity.balance);
        }
    }
}
