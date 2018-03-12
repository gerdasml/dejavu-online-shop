package lt.dejavu.auth.service;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.token.Endpoint;

import java.util.List;

public interface AccessibilityService {
    List<Endpoint> getAccessibleEndpoints(User user);
}
