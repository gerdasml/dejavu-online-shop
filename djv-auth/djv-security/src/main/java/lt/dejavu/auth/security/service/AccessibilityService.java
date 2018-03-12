package lt.dejavu.auth.security.service;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.security.model.Endpoint;

import java.util.List;

public interface AccessibilityService {
    List<Endpoint> getAccessibleEndpoints(User user);
}
