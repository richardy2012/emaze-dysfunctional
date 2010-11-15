package net.emaze.dysfunctional.ranges;

import org.junit.Assert;
import org.junit.Test;
import static net.emaze.dysfunctional.ranges.RangeMother.*;

/**
 *
 * @author rferranti
 */
public class RangeOpsTest {

    @Test
    public void canPerformDifferenceOnTwoDisjointRanges() {
        Range<Integer> lhs = r(0, 10);
        Range<Integer> rhs = r(11, 20);
        Assert.assertEquals(r(0, 10), RangeOps.difference(sequencer, comparator, lhs, rhs));
    }
    
    @Test
    public void canPerformDifferenceOnTwoOverlappingRanges() {
        Range<Integer> lhs = r(0, 10);
        Range<Integer> rhs = r(8, 20);
        Assert.assertEquals(r(0, 7), RangeOps.difference(sequencer, comparator, lhs, rhs));
    }
    
    @Test
    public void canPerformDifferenceOnTwoOverlappingRangesWithRhsLower() {
        Range<Integer> lhs = r(8, 20);
        Range<Integer> rhs = r(0, 10);
        Assert.assertEquals(r(11, 20), RangeOps.difference(sequencer, comparator, lhs, rhs));
    }
    
    @Test
    public void canPerformDifferenceOnTwoNestedRanges() {
        Range<Integer> lhs = r(0, 20);
        Range<Integer> rhs = r(4, 10);
        Assert.assertEquals(r(p(0, 3), p(11, 20)), RangeOps.difference(sequencer, comparator, lhs, rhs));
    }

}
