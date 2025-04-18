package com.car.manager.core.service;

import com.car.manager.core.Util;
import com.car.manager.core.domain.PhotoAvatarEntity;
import com.car.manager.core.exception.InvalidFileFormatException;
import com.car.manager.core.storage.FileStorage;
import com.car.manager.core.storage.FileStorageException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AvatarService {
    private static final List<String> VALID_EXTENSIONS = List.of(".png", ".jpg", ".jpeg");
    private FileStorage storage;

    public AvatarService(FileStorage storage){
        this.storage = storage;
    }

    public void validateExtension(String extension) throws InvalidPropertiesFormatException {
        if(Objects.isNull(extension) || !VALID_EXTENSIONS.contains(extension)){
            throw new InvalidFileFormatException("Only JPEG or PNG images are allowed");
        }
    }

    public void uploadAvatar(PhotoAvatarEntity entity, InputStream inputStream, String folder, String extension) throws FileStorageException {
        Optional<URL> photoPathOptional = entity.getPhotoPathOptional();
        if(photoPathOptional.isPresent()){
            storage.delete(photoPathOptional.get().getFile());
        }
        entity.setPhotoPath(this.storage.upload(inputStream, folder, Util.generateAvatarPath(folder, entity.getId(), extension)));
    }
}
