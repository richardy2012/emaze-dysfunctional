package net.emaze.dysfunctional.dispatching.adapting;

import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.BinaryDelegate;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;

/**
 * Adapts a delegate to a binary delegate. Adapting is performed by ignoring the
 * first parameter passed to the adapted delegate.
 *
 * @param <R> the adapter result type
 * @param <T1> the adapter first parameter type
 * @param <T2> the adapter second parameter type
 * @author rferranti
 */
public class IgnoreFirst<R, T1, T2> implements BinaryDelegate<R, T1, T2> {

    private final Delegate<R, T2> adapted;

    public IgnoreFirst(Delegate<R, T2> adaptee) {
        dbc.precondition(adaptee != null, "cannot ignore first parameter of a null delegate");
        this.adapted = adaptee;
    }

    @Override
    public R perform(T1 first, T2 second) {
        return adapted.perform(second);
    }
}