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

        //Limit the last 8 slots on the inventory
        slotRestrictive.setRestrict(true);
        slotRestrictive.addRange(size - 8, size - 1);

        //Create the buttons and add them the function to load the currentPage
        setPrevButton(45, AuctionHouse.getSettings().getPreviousButton(), event -> {
            if (previousPage()) {
                loadPage(getCurrentPage());
                refreshPage(event.getWhoClicked());
            }
            event.setCancelled(true);
        });

        setNextButton(53, AuctionHouse.getSettings().getNextButton(), event -> {
            if (nextPage()) {
                loadPage(getCurrentPage());
                refreshPage(event.getWhoClicked());
            }
            event.setCancelled(true);
        });

        //Load the first page
        loadPage(1);
    }

    @Override
    public String getTitle() {
        return Translator.getString("messages.sectiongui.title",
                AuctionHouse.getSettings().getLang(), section, getCurrentPage());
    }

    private void loadPage(int page) {
        //Before asking the database for the items let's check if they have been stored
        GUIButton[] buttons = getPage(page).getEntries().values().toArray(new GUIButton[0]);

        if (buttons.length == 0) {
            //We subtract one to the page and slotRestrictive to the size
            int size = super.size - slotRestrictive.getSlots().size();

            //If pageable is empty let's ask the DB
            Serializer<AuctionHouseItem> serializer = EntitySerializer.getSerializer(AuctionHouseItem.builder().build());

            //If entities is null it will set Lists#newArrayList() (def. value) if not it will fill the content
            buttons = serializer.getEntities((page - 1) * size, page * size).stream()
                    .map(item -> SectionButtons.sectionButton(item, event -> {
                        event.getWhoClicked().openInventory(new BetGUI(event.getCurrentItem()).getInventory());
                        event.setCancelled(true);
                    })).toArray(GUIButton[]::new);

            //Add items to the inventory buttons and load the next page if possible
            addPage(page, buttons);

            if (getPage(getCurrentPage()).getEntries().size() == size && getCurrentPage() + 1 != page) {
                loadPage(page + 1);
            }
        }else {
            //Add items to the inventory buttons and load the next page if possible
            addPage(page, buttons);
        }
    }
}
