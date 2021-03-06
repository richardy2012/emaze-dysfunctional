package net.emaze.dysfunctional.interceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import net.emaze.dysfunctional.dispatching.delegates.FirstParam;
import net.emaze.dysfunctional.Iterations;
import net.emaze.dysfunctional.testing.O;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class BinaryInterceptorChainTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingChainWithNullInnermostDelegateYieldsException() {
        final Iterator<BinaryInterceptor<O, O>> chain = Iterations.iterator();
        new BinaryInterceptorChain<O, O, O>(null, chain);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingChainWithNullChaingYieldsException() {
        final BiFunction<O, O, O> firstParam = new FirstParam<O, O>();
        new BinaryInterceptorChain<O, O, O>(firstParam, null);
    }

    @Test
    public void chainingIsDoneInCorrectOrder() {
        final List<Integer> bucket = new ArrayList<Integer>();
        final Iterator<BinaryInterceptor<O, O>> chain = Iterations.<BinaryInterceptor<O, O>>iterator(
                new BucketFillingInterceptor(3, bucket),
                new BucketFillingInterceptor(2, bucket),
                new BucketFillingInterceptor(1, bucket));
        final BinaryInterceptorChain<O, O, O> ic = new BinaryInterceptorChain<O, O, O>(new BucketFillingDelegate(4, bucket), chain);
        ic.apply(O.IGNORED, O.IGNORED);
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 3, 2, 1), bucket);
    }

    @Test
    public void whenAnInterceptorThrowsInBeforeCorrectAfterAreCalled() {
        final List<Integer> bucket = new ArrayList<Integer>();
        final Iterator<BinaryInterceptor<O, O>> chain = Iterations.<BinaryInterceptor<O, O>>iterator(
                new BucketFillingInterceptor(3, bucket),
                new ThrowingInterceptor(),
                new BucketFillingInterceptor(1, bucket));
        final BinaryInterceptorChain<O, O, O> ic = new BinaryInterceptorChain<O, O, O>(new BucketFillingDelegate(4, bucket), chain);
        try {
            ic.apply(O.IGNORED, O.IGNORED);
        } catch (Exception ex) {
        }
        Assert.assertEquals(Arrays.asList(1, 1), bucket);
    }

    public static class BucketFillingDelegate implements BiFunction<O, O, O> {

        private final int id;
        private final List<Integer> bucket;

        public BucketFillingDelegate(int id, List<Integer> bucket) {
            this.id = id;
            this.bucket = bucket;
        }

        @Override
        public O apply(O first, O second) {
            bucket.add(id);
            return null;
        }
    }

    public static class BucketFillingInterceptor implements BinaryInterceptor<O, O> {

        private final int id;
        private final List<Integer> bucket;

        public BucketFillingInterceptor(int id, List<Integer> bucket) {
            this.id = id;
            this.bucket = bucket;
        }

        @Override
        public void before(O first, O second) {
            bucket.add(id);
        }

        @Override
        public void after(O first, O second) {
            bucket.add(id);
        }
    }

    public class ThrowingInterceptor implements BinaryInterceptor<O, O> {

        @Override
        public void before(O first, O second) {
            throw new IllegalStateException();
        }

        @Override
        public void after(O first, O second) {
            throw new IllegalStateException();
        }
    }
}
