package es.brouse.auctionhouse.loader.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Pageable<T> {
    private final Map<Integer, List<T>> pages;
    private final int pageSize;
    @Getter
    private int currentPage;

    public Pageable(int pageSize) {
        this.pageSize = pageSize;
        this.currentPage = 1;
        this.pages = Maps.newHashMap();
    }

    public Pageable() {
        this(-1);
    }

    /**
     * Move the 'currentPage' index to the previous page.
     * @throws IndexOutOfBoundsException If the 'currentPage' is the last page
     * @return If the page was moved successfully
     */
    public boolean nextPage() {
        if (!pages.containsKey(currentPage + 1)) return false;

        currentPage++;
        return true;
    }

    /**
     * Move the 'currentPage' index to the next page.
     * @return If the page was moved successfully
     */
    public boolean previousPage()  {
        if (currentPage == 1) return false;
        currentPage--;
        return true;
    }

    /**
     * Add a new Page to the Pageable.
     * If the {@param elements} is higher than 'maxAmount'
     * the page won't be created.
     * @param elements Elements to add
     * @return If the page was added
     */
    @SafeVarargs
    public final boolean addPage(T... elements) {
        if (pageSize == -1 || elements.length <= pageSize) {
            pages.put(pages.size() + 1, Arrays.asList(elements));
            return true;
        }
        return false;
    }

    /**
     * Remove the page on the specific index.
     * If the page is being used, it will cancel
     * the remove action.
     * @param index index to remove
     * @return If the page was removed
     */
    public boolean removePage(int index) {
        if (currentPage != index) {
            pages.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Get the items on the given page. If the page doesn't
     * exist, it will return null
     * @param page page to get the items
     * @return the items on the given page
     */
    public List<T> getPage(int page) {
        return pages.getOrDefault(page, Lists.newArrayList());
    }
}
