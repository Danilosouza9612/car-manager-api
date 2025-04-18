package com.car.manager.core.storage;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface FileStorage {
    URL upload(InputStream inputStream, String folder, String identifier) throws FileStorageException;
    void delete(String folderIdentifier) throws FileStorageException;
}
