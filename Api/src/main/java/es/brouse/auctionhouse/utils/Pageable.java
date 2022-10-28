package es.brouse.auctionhouse.utils;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public interface Pageable<T> {

    /**
     * Move the 'currentPage' index to the previous page.
     * @throws IndexOutOfBoundsException If the 'currentPage' is the last page
     * @return If the page was moved successfully
     */
    boolean nextPage();

    /**
     * Move the 'currentPage' index to the next page.
     * @return If the page was moved successfully
     */
    boolean previousPage();

    /**
     * Create a new page with index {@param page} and set it's content to
     * {@param elements}. If there's already another page with the same index
     * it will be overwritten.
     * @param pageIndex index of the page
     * @param content Elements to add
     */
    void addPage(int pageIndex, T[] content);

    /**
     * Remove the page on the specific index.
     * If the page is being used, it will cancel
     * the remove action.
     * @param pageIndex index to remove
     * @return If the page was removed
     */
    boolean removePage(int pageIndex);

    /**
     * Get the items on the given page. If the page doesn't
     * exist, it will return null
     * @param pageIndex page to get the items
     * @return the items on the given page
     */
    Page<T> getPage(int pageIndex);

    class Page<T> {
        private final Map<Integer, T> entries = Maps.newHashMap();

        @SafeVarargs
        public Page(int size, T... element) {
            for (int i = 0; i < element.length; i++) {
                if (i <= size) {
                    entries.put(i, element[i]);
                }
            }
        }

        public Map<Integer, T> getEntries() {
            return new HashMap<>(entries);
        }
    }
}
