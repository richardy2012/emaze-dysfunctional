package net.emaze.dysfunctional.strings.lexcasts;

import net.emaze.dysfunctional.delegates.Delegate;
import net.emaze.dysfunctional.options.Maybe;

/**
 *
 * @author rferranti
 */
public class DoubleTryParser implements Delegate<Maybe<Double>, String> {

    @Override
    public Maybe<Double> perform(String parsee) {
        try {
            return Maybe.just(Double.parseDouble(parsee));
        } catch (NumberFormatException ex) {
            return Maybe.nothing();
        }
    }
}
