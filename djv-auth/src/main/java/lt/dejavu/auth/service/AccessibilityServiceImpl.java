package lt.dejavu.auth.service;

import lt.dejavu.auth.configuration.properties.AuthProperties;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.token.Endpoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class AccessibilityServiceImpl implements AccessibilityService {
    private final static String ID_PLACEHOLDER = "{id}";
    private Map<UserType, Function<Integer, List<Endpoint>>> endpointProvider;

    public AccessibilityServiceImpl(AuthProperties authProperties) {
        endpointProvider = buildEndpointProvider(authProperties);
    }

    @Override
    public List<Endpoint> getAccessibleEndpoints(User user) {
        return endpointProvider.get(user.getType()).apply(user.getId());
    }

    private Map<UserType, Function<Integer, List<Endpoint>>> buildEndpointProvider(AuthProperties authProperties) {
        return authProperties.getRights()
                             .entrySet()
                             .stream()
                             .collect(
                                     toMap(
                                             Map.Entry::getKey,
                                             entry -> addUserId(entry.getValue())
                                          )
                                     );
    }

    private Function<Integer, List<Endpoint>> addUserId(List<Endpoint> rights) {
        return id -> rights.stream()
                           .map(e -> addUserId(e, id))
                           .collect(toList());
    }

    private Endpoint addUserId(Endpoint e, int id) {
        Endpoint ee = new Endpoint();
        ee.setMethod(e.getMethod());
        ee.setPath(e.getPath().replace(ID_PLACEHOLDER, String.valueOf(id)));
        return ee;
    }
}
