package lt.dejavu.utils.collections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.stream.Collectors.toList;

public class UpdatableCollectionUtils {

    // replaces oldCollection with newCollection objects according to equals method
    public static <T extends Updatable<T>, K extends Collection<T>> K updateCollection(K oldCollection, K newCollection) {
        updateExistingOrRemove(oldCollection, newCollection);
        addNew(oldCollection, newCollection);
        return oldCollection;
    }

    private static <T extends Updatable<T>, K extends Collection<T>> void updateExistingOrRemove(K oldCollection, K newCollection) {
        List<T> notExistingObjs = new LinkedList<>();
        oldCollection.forEach(oldObj -> {
            boolean updated = tryToUpdate(newCollection, oldObj);
            if (!updated) {
                notExistingObjs.add(oldObj);
            }
        });
        notExistingObjs.forEach(oldCollection::remove);
    }

    private static <T extends Updatable<T>, K extends Collection<T>> boolean tryToUpdate(K newCollection, T oldObj) {
        for (T newObj : newCollection) {
            if (oldObj.canBeUpdated(newObj)) {
                oldObj.update(newObj);
                return true;
            }
        }
        return  false;
    }

    private static <T extends Updatable<T>, K extends Collection<T>> void addNew(K oldCollection, K newCollection) {
        List<T> newObjs = newCollection.stream().filter(newObj -> oldCollection.stream().noneMatch(newObj::canBeUpdated)).collect(toList());
        oldCollection.addAll(newObjs);
    }

}
