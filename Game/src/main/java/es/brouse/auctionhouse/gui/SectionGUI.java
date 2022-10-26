package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.entities.AHSection;
import es.brouse.auctionhouse.entities.AuctionHouseItem;
import es.brouse.auctionhouse.inventory.GUIButton;
import es.brouse.auctionhouse.inventory.PagedGUI;
import es.brouse.auctionhouse.serialize.EntitySerializer;
import es.brouse.auctionhouse.serialize.serializers.Serializer;
import es.brouse.auctionhouse.translator.Translator;

public class SectionGUI extends PagedGUI {
    private final AHSection section;
    public SectionGUI(AHSection section) {
        super(54, "messages.sectiongui.title");
        this.section = section;

        getSlotRestrictive().setRestrict(true);
        getSlotRestrictive().addRange(45, 53);

        setPrevButton(45, AuctionHouse.getSettings().getPreviousButton());
        setNextButton(53, AuctionHouse.getSettings().getNextButton());

        loadPages(0, getInventory().getSize() - 2);
    }

    @Override
    public String getTitle() {
        return Translator.getString("messages.sectiongui.title",
                AuctionHouse.getSettings().getLang(), section, getCurrentPage());
    }

    private void loadPages(int from, int to) {
        //Create a serializer to get entities from the database
        Serializer<AuctionHouseItem> serializer = EntitySerializer.getSerializer(AuctionHouseItem.builder().build());

        //Map each entry into a button of the inventory
        GUIButton[] guiButtons = serializer.getEntities(from, to).stream()
                .map(item -> SectionButtons.sectionButton(item, event -> {
                    event.getWhoClicked().openInventory(new BetGUI(event.getCurrentItem()).getInventory());
                    event.setCancelled(true);
                })).toArray(GUIButton[]::new);
        addPage(guiButtons);
    }
}
