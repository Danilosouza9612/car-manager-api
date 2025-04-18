package com.car.manager.core.dto.car;

import java.net.URL;

public class CarResponseDTO extends CarDTO{
    private long id;

    private URL photoPath;

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
