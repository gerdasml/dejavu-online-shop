package lt.dejavu.auth.security.service;

import lt.dejavu.auth.model.Endpoint;
import lt.dejavu.auth.model.User;

import java.util.List;

public interface AccessibilityService {
    List<Endpoint> getAccessibleEndpoints(User user);
}
