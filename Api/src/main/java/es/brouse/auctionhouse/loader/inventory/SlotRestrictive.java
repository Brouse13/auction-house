package es.brouse.auctionhouse.loader.inventory;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class SlotRestrictive {
    @Getter @Setter private boolean restrict = false;
    private final Set<Integer> slots = Sets.newHashSet();

    /**
     * Get all the available slots
     * @return all the available slots
     */
    public Set<Integer> getSlots() {
        return ImmutableSet.copyOf(slots);
    }

    /**
     * Add a new slot to the available slots
     * @param slot slot to add
     */
    public void addSlot(int slot) {
        slots.add(slot);
    }

    /**
     * Add a new range of slots to the available slots
     * @param from first slot from range
     * @param to last slot from range
     */
    public void addRange(int from, int to) {
        for (int i = from; i <= to; i++)
            slots.add(i);
    }

    /**
     * Remove a slot from the available slots
     * @param slot slot to remove
     */
    public void removeSlot(int slot) {
        slots.remove(slot);
    }
}
