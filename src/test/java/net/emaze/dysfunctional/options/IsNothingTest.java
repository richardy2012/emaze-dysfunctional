package net.emaze.dysfunctional.options;

import net.emaze.dysfunctional.delegates.Predicate;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class IsNothingTest {
    
    @Test
    public void justIsNotNothing() {
        Predicate<Maybe<Integer>> p = new IsNothing<Integer>();
        Assert.assertFalse(p.test(Maybe.just(1)));
    }

    @Test
    public void nothingMatches() {
        Predicate<Maybe<Integer>> p = new IsNothing<Integer>();
        Assert.assertTrue(p.test(Maybe.<Integer>nothing()));
    }
}