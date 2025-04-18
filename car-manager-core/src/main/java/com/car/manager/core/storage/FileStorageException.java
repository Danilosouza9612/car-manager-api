package com.car.manager.core.storage;

import java.io.IOException;

public class FileStorageException extends IOException {
    public FileStorageException(){
        super("Error when uploading file");
    }
}
