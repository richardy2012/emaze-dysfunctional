package net.emaze.dysfunctional.convolutions;

import java.util.Iterator;
import net.emaze.dysfunctional.iterations.ArrayIterator;
import net.emaze.dysfunctional.options.Maybe;
import net.emaze.dysfunctional.tuples.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rferranti
 */
public class ZipLongestIteratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void creatingZipWithNullFormerIteratorYieldException() {
        new ZipLongestIterator<Integer, Integer>(null, new ArrayIterator<Integer>(new Integer[]{1}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingZipWithNullLatterIteratorYieldException() {
        new ZipLongestIterator<Integer, Integer>(new ArrayIterator<Integer>(new Integer[]{1}), null);
    }

    @Test
    public void iteratorHasNextWhenBothIteratorsHaveNext() {
        Iterator<Integer> former = new ArrayIterator<Integer>(new Integer[]{1});
        Iterator<Integer> latter = new ArrayIterator<Integer>(new Integer[]{2});
        ZipLongestIterator<Integer, Integer> zipli = new ZipLongestIterator<Integer, Integer>(former, latter);
        Assert.assertTrue(zipli.hasNext());
    }

    @Test
    public void firstNextContainsFirstElements() {
        Iterator<Integer> former = new ArrayIterator<Integer>(new Integer[]{1});
        Iterator<Integer> latter = new ArrayIterator<Integer>(new Integer[]{2});
        ZipLongestIterator<Integer, Integer> zipli = new ZipLongestIterator<Integer, Integer>(former, latter);
        Pair<Maybe<Integer>, Maybe<Integer>> got = zipli.next();
        Assert.assertEquals(Pair.of(Maybe.just(1), Maybe.just(2)), got);
    }

    @Test
    public void iteratorHasNextWhenOnlyFormerIteratorHasNext() {
        Iterator<Integer> former = new ArrayIterator<Integer>(new Integer[]{1});
        Iterator<Integer> latter = new ArrayIterator<Integer>(new Integer[]{});
        ZipLongestIterator<Integer, Integer> zipli = new ZipLongestIterator<Integer, Integer>(former, latter);
        Assert.assertTrue(zipli.hasNext());
    }

    @Test
    public void iteratorHasNextWhenOnlyLatterIteratorHasNext() {
        Iterator<Integer> former = new ArrayIterator<Integer>(new Integer[]{});
        Iterator<Integer> latter = new ArrayIterator<Integer>(new Integer[]{2});
        ZipLongestIterator<Integer, Integer> zipli = new ZipLongestIterator<Integer, Integer>(former, latter);
        Assert.assertTrue(zipli.hasNext());
    }

    @Test
    public void iteratorHasNoNextWhenBothIteratorHasNoNext() {
        Iterator<Integer> former = new ArrayIterator<Integer>(new Integer[]{});
        Iterator<Integer> latter = new ArrayIterator<Integer>(new Integer[]{});
        ZipLongestIterator<Integer, Integer> zipli = new ZipLongestIterator<Integer, Integer>(former, latter);
        Assert.assertFalse(zipli.hasNext());
    }
}
