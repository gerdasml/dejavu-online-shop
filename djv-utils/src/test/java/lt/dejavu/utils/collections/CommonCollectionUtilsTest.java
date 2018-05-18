package lt.dejavu.utils.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class CommonCollectionUtilsTest {

    @Test
    public void testCollectionsEmpty() {
        test(new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testNoCommon() {
        List<String> oldC = new ArrayList<>();
        oldC.add("A");
        List<String> newC = new ArrayList<>();
        newC.add("B");

        test(oldC, newC);
    }

    @Test
    public void testRemoved() {
        List<String> oldC = new ArrayList<>();
        oldC.add("A");
        oldC.add("B");
        List<String> newC = new ArrayList<>();
        newC.add("B");

        test(oldC, newC);
    }

    @Test
    public void testAdded() {
        List<String> oldC = new ArrayList<>();
        oldC.add("A");
        List<String> newC = new ArrayList<>();
        newC.add("A");
        newC.add("B");

        test(oldC, newC);
    }

    private <T, K extends Collection<T>> void test(K oldCollection, K  newCollection) {
        Collection<T> result = CommonCollectionUtils.updateCollection(oldCollection, newCollection);
        Assert.assertEquals(newCollection, result);
    }
}
