package lt.dejavu.auth.security.service;

import lt.dejavu.auth.dto.UserDto;
import lt.dejavu.auth.model.Endpoint;

import java.util.List;

public interface AccessibilityService {
    List<Endpoint> getAccessibleEndpoints(UserDto userDto);
}
