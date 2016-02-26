package net.emaze.dysfunctional.order;

import net.emaze.dysfunctional.options.Maybe;
import org.junit.Test;
import org.junit.Assert;

public class PeriodicSequencingPolicyTest {

    @Test
    public void nextInBounds() {
        final PeriodicSequencingPolicy<Integer> psp = new PeriodicSequencingPolicy<Integer>(new NextIntegerSequencingPolicy(), 0, 1);
        Assert.assertEquals(Maybe.just(Integer.valueOf(1)), psp.next(0));
    }

    @Test
    public void nextUpperBound() {
        final PeriodicSequencingPolicy<Integer> psp = new PeriodicSequencingPolicy<Integer>(new NextIntegerSequencingPolicy(), 0, 1);
        Assert.assertEquals(Maybe.just(Integer.valueOf(0)), psp.next(1));
    }
}