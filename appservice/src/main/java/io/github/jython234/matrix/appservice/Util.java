package io.github.jython234.matrix.appservice;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

public class Util {
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * Copies a "resource" file from the JAR or resource folder to
     * the filesystem.
     * @param resource The resource file name.
     * @param copyLocation The location to copy the resource to on the
     *                     filesystem.
     * @throws IOException If there is an exception while attempting to
     *                     copy the file.
     */
    public static void copyResourceTo(String resource, File copyLocation) throws IOException {
        var in = resourceLoader.getResource(resource).getInputStream();
        FileUtils.copyInputStreamToFile(in, copyLocation);
    }
}
