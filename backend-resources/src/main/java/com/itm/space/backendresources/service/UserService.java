package com.itm.space.backendresources.service;

import com.itm.space.backendresources.api.request.*;
import com.itm.space.backendresources.api.response.*;
import java.util.*;

public interface UserService {

    void createUser(UserRequest userRequest);

    UserResponse getUserById(UUID id);
}