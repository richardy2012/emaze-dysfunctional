package net.emaze.dysfunctional.strings.lexcasts;

import net.emaze.dysfunctional.dispatching.delegates.Delegate;
import net.emaze.dysfunctional.options.Maybe;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class ShortTryParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void excedingMaxRadixYieldsException() {
        new ShortTryParser(Character.MAX_RADIX + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lessThanMinRadixYieldsException() {
        new ShortTryParser(Character.MIN_RADIX - 1);
    }
    
    @Test(expected = ClassCastException.class)
    public void passingNonStringToErasureYieldsException() {
        Delegate d = new ShortTryParser(10);
        d.perform(new Object());
    }        

    @Test
    public void parsingNullStringYieldsNothing() {
        final Maybe<Short> got = new ShortTryParser(10).perform(null);
        Assert.assertFalse(got.hasValue());
    }

    @Test
    public void parsingInvalidStringYieldsNothing() {
        final Maybe<Short> got = new ShortTryParser(10).perform("A");
        Assert.assertFalse(got.hasValue());
    }

    @Test
    public void parsingValidStringYieldsJustValue() {
        final Maybe<Short> got = new ShortTryParser(10).perform("1");
        Assert.assertEquals(Maybe.just((short)1), got);
    }
}