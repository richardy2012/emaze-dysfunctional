package net.emaze.dysfunctional.dispatching;

import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.dispatching.logic.Predicate;

/**
 *
 * @param <T>
 * @author rferranti
 */
public class DelegateToPredicate<T> implements Predicate<T> {

    private final Delegate<Boolean, T> adapted;

    public DelegateToPredicate(Delegate<Boolean, T> adapted) {
        dbc.precondition(adapted != null, "cannot adapt a null delegate");
        this.adapted = adapted;
    }

    @Override
    public boolean accept(T value) {
        return adapted.perform(value);
    }
}