package net.emaze.dysfunctional.options;

import net.emaze.dysfunctional.contracts.dbc;
import net.emaze.dysfunctional.delegates.Predicate;

/**
 * Unary Predicate matching Maybe.nothing elements
 * @author rferranti
 */
public class IsNothing<T> implements Predicate<Maybe<T>> {

    @Override
    public boolean test(Maybe<T> element) {
        dbc.precondition(element != null, "testing IsNothing against null");
        return !element.hasValue();
    }

}
