package com.car.manager.core.domain;

import java.net.URL;
import java.util.Optional;

public interface PhotoAvatarEntity {
    Long getId();
    Optional<URL> getPhotoPathOptional();
    void setPhotoPath(URL photoPath);
}
