package es.brouse.auctionhouse.gui;

import es.brouse.auctionhouse.AuctionHouse;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.entities.AuctionHouseItem;
import es.brouse.auctionhouse.loader.inventory.PagedGUI;
import es.brouse.auctionhouse.loader.serializer.SerializerManager;
import es.brouse.auctionhouse.loader.serializer.serializers.Serializer;
import es.brouse.auctionhouse.loader.translator.Translator;
import es.brouse.auctionhouse.loader.utils.builders.ItemBuilder;
import es.brouse.auctionhouse.section.AHSection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

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
        Serializer serializer = SerializerManager.getActiveSerializer(new AuctionHouseItem());
        YamlConfig config = new YamlConfig();

        //Map the object from the database to a AuctionHouseItem and then create each item
        ItemStack[] itemStacks = Arrays.stream(serializer.getEntities(from, to))
                .map(item -> ((AuctionHouseItem) item))
                .map(item -> ItemBuilder.of(Material.getMaterial(item.getMaterial()))
                        .displayName(Translator.getString("messages.sectiongui.item.name", config.getLang(), item.getMaterial()))
                        .lore(Translator.getStringList("messages.sectiongui.item.lore", config.getLang(), item.getOwner(), item.getPrice()))
                        .build())
                .toArray(ItemStack[]::new);
        addPage(itemStacks);
    }
}
