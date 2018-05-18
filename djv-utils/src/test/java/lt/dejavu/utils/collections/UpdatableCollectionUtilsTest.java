package lt.dejavu.utils.collections;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UpdatableCollectionUtilsTest {

    @Test
    public void testCollectionsEmpty() {
        test(new ArrayList<UpdatableClass>(), new ArrayList<UpdatableClass>());
    }

    @Test
    public void testNoCommon() {
        List<UpdatableClass> oldC = new ArrayList<>();
        oldC.add(new UpdatableClass(1,"A"));
        List<UpdatableClass> newC = new ArrayList<>();
        newC.add(new UpdatableClass(2,"B"));

        test(oldC, newC);
    }

    @Test
    public void testRemoved() {
        List<UpdatableClass> oldC = new ArrayList<>();
        oldC.add(new UpdatableClass(1,"A"));
        oldC.add(new UpdatableClass(2,"B"));
        List<UpdatableClass> newC = new ArrayList<>();
        newC.add(new UpdatableClass(2,"B"));

        test(oldC, newC);
    }

    @Test
    public void testAdded() {
        List<UpdatableClass> oldC = new ArrayList<>();
        oldC.add(new UpdatableClass(1,"A"));
        List<UpdatableClass> newC = new ArrayList<>();
        newC.add(new UpdatableClass(1,"A"));
        newC.add(new UpdatableClass(2,"B"));
        test(oldC, newC);
    }

    @Test
    public void testUpdated() {
        List<UpdatableClass> oldC = new ArrayList<>();
        oldC.add(new UpdatableClass(1,"A", true));
        oldC.add(new UpdatableClass(2, "B", true));
        List<UpdatableClass> newC = new ArrayList<>();
        newC.add(new UpdatableClass(1,"C"));
        newC.add(new UpdatableClass(2,"B"));
        List<UpdatableClass> resultC = new ArrayList<>();
        resultC.add(new UpdatableClass(1,"C", true));
        resultC.add(new UpdatableClass(2,"B",  true));

        test(oldC, newC, resultC);
    }

    private <T extends Updatable<T>, K extends Collection<T>> void test(K oldCollection, K  newCollection) {
        Collection<T> result = UpdatableCollectionUtils.updateCollection(oldCollection, newCollection);
        Assert.assertEquals(newCollection, result);
    }

    private <T extends Updatable<T>, K extends Collection<T>> void test(K oldCollection, K newCollection, K result ) {
        Collection<T> updateResult = UpdatableCollectionUtils.updateCollection(oldCollection, newCollection);
        Assert.assertEquals(result, updateResult);
    }


    @Getter
    @EqualsAndHashCode
    @ToString
    private class UpdatableClass implements Updatable<UpdatableClass> {

        private long id;
        private String value;
        boolean existed;

        UpdatableClass(long a, String b) {
            this.id = a;
            this.value = b;
        }

        UpdatableClass(long a, String b, boolean existed) {
            this.id = a;
            this.value = b;
            this.existed = existed;
        }

        @Override
        public boolean canBeUpdated(UpdatableClass other) {
            return id ==  other.getId();
        }

        @Override
        public void update(UpdatableClass other) {
            value = other.getValue();
        }


    }
}
