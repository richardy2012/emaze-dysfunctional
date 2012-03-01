package net.emaze.dysfunctional.consumers;

import java.util.Iterator;
import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.options.Maybe;
import net.emaze.dysfunctional.options.MaybeIterator;

/**
 * Searches for zero or one element, throws if more than one element is found.
 *
 * @param <E> the iterator element type
 * @author rferranti
 */
public class MaybeOneElement<E> implements Delegate<Maybe<E>, Iterator<E>> {

    @Override
    public Maybe<E> perform(Iterator<E> consumable) {
        dbc.precondition(consumable != null, "consuming a null iterator");
        final Maybe<E> maybeFound = new MaybeIterator<E>(consumable).next();
        dbc.state(!consumable.hasNext(), "found more than one element consuming the iterator");
        return maybeFound;
    }
}