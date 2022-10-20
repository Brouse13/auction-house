package es.brouse.auctionhouse.flter;

public interface Filter<T> {
    /**
     * Check if the item is valid on this filter
     * @param item item to check
     * @return if the item is valid
     */
    boolean isValid(T item);
}
