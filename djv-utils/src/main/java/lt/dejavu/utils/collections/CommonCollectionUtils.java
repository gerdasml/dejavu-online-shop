package lt.dejavu.utils.collections;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CommonCollectionUtils {

    // replaces oldCollection with newCollection objects according to equals method
    public static <T, K extends Collection<T>> K updateCollection(K oldCollection, K  newCollection){
        removeNotExisting(oldCollection, newCollection);
        addNew(oldCollection, newCollection);
        return oldCollection;
    }

    private static <T, K extends Collection<T>> void removeNotExisting(K oldCollection, K newCollection) {
        List<T> notExistingObjs = oldCollection.stream().filter(x -> !newCollection.contains(x)).collect(toList());
        notExistingObjs.forEach(oldCollection::remove);
    }

    private static <T, K extends Collection<T>> void addNew(K oldCollection, K newCollection) {
        List<T> newObjs = newCollection.stream().filter(x -> !oldCollection.contains(x)).collect(toList());
        oldCollection.addAll(newObjs);
    }
}

