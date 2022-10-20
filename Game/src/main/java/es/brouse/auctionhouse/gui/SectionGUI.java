package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.entities.AHSection;
import es.brouse.auctionhouse.inventory.PagedGUI;
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
        //TODO Implement the Serializer#getEntiteies()
        /*
        Serializer serializer = EntitySerializer.getSerializer(new AuctionHouseItem());

        //Map the object from the database to a AuctionHouseItem and then create each item
        GUIButton[] itemStacks = Arrays.stream(serializer.getEntities(from, to))
                .map(item -> ((AuctionHouseItem) item))
                .map(item-> SectionButtons.sectionButton(item, event -> {
                    event.getWhoClicked().openInventory(new BetGUI(event.getCurrentItem()).getInventory());
                    event.setCancelled(true);
                })).toArray(GUIButton[]::new);
        addPage(itemStacks);
         */
    }
}
