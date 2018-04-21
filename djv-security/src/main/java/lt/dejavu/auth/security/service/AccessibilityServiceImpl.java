package lt.dejavu.auth.security.service;

import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.security.configuration.properties.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class AccessibilityServiceImpl implements AccessibilityService {
    private final static String ID_PLACEHOLDER = "{id}";
    private Map<UserType, Function<Long, List<Endpoint>>> endpointProvider;

    public AccessibilityServiceImpl(SecurityProperties securityProperties) {
        endpointProvider = buildEndpointProvider(securityProperties);
    }

    @Override
    public List<Endpoint> getAccessibleEndpoints(UserDto userDto) {
        return endpointProvider.get(userDto.getType()).apply(userDto.getId());
    }

    private Map<UserType, Function<Long, List<Endpoint>>> buildEndpointProvider(SecurityProperties securityProperties) {
        return securityProperties.getRights()
                                 .entrySet()
                                 .stream()
                                 .collect(
                                         toMap(
                                                 entry -> entry.getKey(),
                                                 entry -> addUserId(entry.getValue())
                                              )
                                         );
    }

    private Function<Long, List<Endpoint>> addUserId(List<Endpoint> rights) {
        return id -> rights.stream()
                           .map(e -> addUserId(e, id))
                           .collect(toList());
    }

    private Endpoint addUserId(Endpoint e, long id) {
        Endpoint ee = new Endpoint();
        ee.setMethod(e.getMethod());
        ee.setPath(e.getPath().replace(ID_PLACEHOLDER, String.valueOf(id)));
        return ee;
    }
}
