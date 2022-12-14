package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.inventory.GUI;
import es.brouse.auctionhouse.inventory.SignGUI;
import es.brouse.auctionhouse.nbt.NBTHelper;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.translator.Translator;
import es.brouse.auctionhouse.utils.BetManager;
import es.brouse.auctionhouse.utils.Logger;
import es.brouse.auctionhouse.utils.builders.GUIButtonBuilder;
import es.brouse.auctionhouse.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BetGUI extends GUI {
    private final AuctionHouseItem item;
    private final BetManager betManager = new BetManager();

    public BetGUI(ItemStack itemStack) {
        super(9, "messages.betgui.title");

        item = getEntity(itemStack);
        setBackground(ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).build());

        setButton(0, GUIButtonBuilder.create().button(itemStack).build());
        setButton(2, BetButtons.bet1(event -> {
            bet(((Player) event.getWhoClicked()), item, 1d);
            event.getWhoClicked().closeInventory();
        }));
        setButton(3, BetButtons.bet2(event -> {
            bet(((Player) event.getWhoClicked()), item, 100d);
            event.getWhoClicked().closeInventory();
        }));
        setButton(4, BetButtons.bet3(event -> {
            bet(((Player) event.getWhoClicked()), item, 1_000d);
            event.getWhoClicked().closeInventory();
        }));

        //If ProtocolLib is enabled add the SignGUI
        if (SignGUI.isEnabled()) {
            setButton(5, BetButtons.betCustom(event -> new CustomBetGUI()
                    .open(((Player) event.getWhoClicked()), amount ->
                            bet(((Player) event.getWhoClicked()), item, amount))
            ));
        }

        setButton(8, BetButtons.closeInventory(event -> event.getWhoClicked().closeInventory()));
    }

    private AuctionHouseItem getEntity(ItemStack itemStack) {
        String bet_id = NBTHelper.getKey(itemStack, "bet_id");
        AuctionHouseItem item = AuctionHouseItem.builder().identifier(bet_id).build();
        Serializer<AuctionHouseItem> serializer = EntitySerializer.getSerializer(item);

        return serializer.getEntity();
    }

    private void bet(Player player, AuctionHouseItem item, double priceAdd) {
        //Try to execute the bet
        if (!betManager.canBet(player, item, priceAdd)) {
            Logger.player(player, "messages.player.bet.not_money");
            return;
        }

        //TODO make thant when a player bet it returns the initial money to the player and add a timer to the bet

        //Cancel betting for your own item
        if (item.getOwner().equals(player.getUniqueId().toString())) {
            Logger.player(player, Translator.getString("messages.player.bet.your_item"));
            return;
        }

        //Bet for the item
        if (betManager.bet(player, item, priceAdd)) {
            Logger.player(player, translate("messages.player.bet.success",
                    (item.getPrice() + priceAdd), item.getMaterial()));
        }else {
            Logger.player(player, translate("messages.player.bet.error"));
        }
    }

    private String translate(String key, Object... args) {
        return Translator.getString(key, args);
    }
}
