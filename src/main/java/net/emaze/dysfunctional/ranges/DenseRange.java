package net.emaze.dysfunctional.ranges;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.equality.EqualsBuilder;
import net.emaze.dysfunctional.hashing.HashCodeBuilder;
import java.util.Optional;
import net.emaze.dysfunctional.order.Order;
import net.emaze.dysfunctional.order.SequencingPolicy;

/**
 *
 * @param <T>
 * @author rferranti
 */
public class DenseRange<T> implements Range<T> {

    private final SequencingPolicy<T> sequencer;
    private final Comparator<Optional<T>> comparator;
    private final T begin;
    private final Optional<T> end;

    public DenseRange(SequencingPolicy<T> sequencer, Comparator<Optional<T>> comparator, Endpoint left, T lower, Optional<T> upper, Endpoint right) {
        dbc.precondition(sequencer != null, "trying to create a DenseRange<T> with a null SequencingPolicy<T>");
        dbc.precondition(comparator != null, "trying to create a DenseRange<T> with a null Comparator<T>");
        dbc.precondition(lower != null, "trying to create a DenseRange<T> with null lower bound");
        dbc.precondition(upper != null, "trying to create a DenseRange<T> with null upper bound");
        dbc.precondition(Order.of(comparator, Optional.of(lower), upper) != Order.GT, "trying to create a DenseRange<T> a lower bound greater than upper bound");
        dbc.precondition(upper.isPresent() || right != Endpoint.Include, "cannot create a right inclusive range with right bound set as Nothing");
        this.sequencer = sequencer;
        this.comparator = comparator;
        this.begin = left == Endpoint.Include ? lower : sequencer.next(lower).get();
        this.end = upper.isPresent() && right == Endpoint.Include ? sequencer.next(upper.get()) : upper;
    }

    @Override
    public boolean contains(T element) {
        return Order.of(comparator, Optional.of(element), Optional.of(begin)).isGte() && Order.of(comparator, Optional.of(element), end).isLt();
    }

    @Override
    public T begin() {
        return begin;
    }

    @Override
    public Optional<T> end() {
        return end;
    }

    @Override
    public int compareTo(Range<T> other) {
        dbc.precondition(other != null, "comparing (compareTo) a DenseRange<T> with null");
        return new RangeComparator<T>(comparator).compare(this, other);
    }

    @Override
    public Iterator<T> iterator() {
        return new RangeIterator<T>(sequencer, comparator, begin, end);
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof DenseRange == false) {
            return false;
        }
        final DenseRange<T> other = (DenseRange<T>) rhs;
        final EqualsBuilder builder = new EqualsBuilder().append(this.sequencer, other.sequencer).
                append(this.comparator, other.comparator);
        if (!this.iterator().hasNext() && !other.iterator().hasNext()) {
            return builder.isEquals();
        }
        return builder.
                append(this.begin, other.begin).
                append(this.end, other.end).
                isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder().append(sequencer).
                append(comparator);
        if (!iterator().hasNext()) {
            return builder.toHashCode();
        }
        return builder.append(begin).
                append(end).
                toHashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s-%s)", begin, end.isPresent() ? end.get() : "...");
    }

    /**
     * Apples to apples (dense to dense) : yields false if this.lower >
     * other.upper or other.lower > this.upper Apples to oranges: (dense to
     * nonDense) yields nonDense.overlaps(dense)
     *
     * @param other
     * @return TODO
     */
    @Override
    public boolean overlaps(Range<T> other) {
        dbc.precondition(other != null, "checking for overlaps between a DenseRange<T> and null");
        if (other instanceof DenseRange == false) {
            return other.overlaps(this);
        }
        return !Order.of(comparator, Optional.of(this.begin()), other.end()).isGte() && !Order.of(comparator, Optional.of(other.begin()), this.end()).isGte();
    }

    @Override
    public List<DenseRange<T>> densified() {
        return Collections.singletonList(this);
    }
}
