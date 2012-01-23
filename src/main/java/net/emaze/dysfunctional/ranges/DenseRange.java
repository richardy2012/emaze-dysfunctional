package net.emaze.dysfunctional.ranges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.equality.EqualsBuilder;
import net.emaze.dysfunctional.hashing.HashCodeBuilder;
import net.emaze.dysfunctional.order.Order;
import net.emaze.dysfunctional.order.SequencingPolicy;

/**
 *
 * @param <T>
 * @author rferranti
 */
public class DenseRange<T> implements Range<T> {

    private final SequencingPolicy<T> sequencer;
    private final Comparator<T> comparator;
    private final T lower;
    private final T upper;

    public DenseRange(SequencingPolicy<T> sequencer, Comparator<T> comparator, T lower, T upper) {
        dbc.precondition(sequencer != null, "trying to create a DenseRange<T> with a null SequencingPolicy<T>");
        dbc.precondition(comparator != null, "trying to create a DenseRange<T> with a null Comparator<T>");
        dbc.precondition(lower != null, "trying to create a DenseRange<T> with null lower bound");
        dbc.precondition(upper != null, "trying to create a DenseRange<T> with null upper bound");
        dbc.precondition(Order.of(comparator, lower, upper) != Order.GT, "trying to create a DenseRange<T> a lower bound greater than upper bound");
        this.sequencer = sequencer;
        this.comparator = comparator;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public boolean contains(T element) {
        dbc.precondition(element != null, "checking if null is contained in DenseRange<T>");
        return Order.of(comparator, element, lower).isGte() && Order.of(comparator, element, upper).isLte();
    }

    @Override
    public T lower() {
        return lower;
    }

    @Override
    public T upper() {
        return upper;
    }

    @Override
    public int compareTo(Range<T> other) {
        dbc.precondition(other != null, "comparing (compareTo) a DenseRange<T> with null");
        return new RangeComparator<T>().compare(this, other);
    }

    @Override
    public Iterator<T> iterator() {
        return new RangeIterator<T>(sequencer, comparator, lower, upper);
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof DenseRange == false) {
            return false;
        }
        final DenseRange<T> other = (DenseRange<T>) rhs;
        return new EqualsBuilder().append(this.sequencer, other.sequencer).
                append(this.comparator, other.comparator).
                append(this.lower, other.lower).
                append(this.upper, other.upper).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sequencer).
                append(comparator).
                append(lower).
                append(upper).
                toHashCode();
    }

    @Override
    public String toString() {
        return String.format("%s-%s", lower, upper);
    }

    /**
     * Apples to apples (dense to dense) : yields false if this.lower >
     * other.upper or other.lower > this.upper Apples to oranges: (dense to
     * nonDense) yields nonDense.overlaps(dense)
     *
     * @param other
     * @return
     */
    @Override
    public boolean overlaps(Range<T> other) {
        dbc.precondition(other != null, "checking for overlaps between a DenseRange<T> and null");
        if (other instanceof DenseRange == false) {
            return other.overlaps(this);
        }
        if (Order.of(comparator, this.lower(), other.upper()) == Order.GT || Order.of(comparator, other.lower(), this.upper()) == Order.GT) {
            return false;
        }
        return true;
    }

    @Override
    public List<DenseRange<T>> densified() {
        final List<DenseRange<T>> out = new ArrayList<DenseRange<T>>();
        out.add(this);
        return out;
    }
}
