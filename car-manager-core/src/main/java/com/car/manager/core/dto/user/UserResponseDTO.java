package com.car.manager.core.dto.user;

import java.net.URL;

public class UserResponseDTO extends UserDTO{

    private URL photoPath;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public URL getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(URL photoPath) {
        this.photoPath = photoPath;
    }
}
