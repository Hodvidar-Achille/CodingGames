package com.hodvidar.utils.file;

import java.io.File;

public class Constance {

    public static final String RESOURCES_TEST = "src" + File.separator + "test" + File.separator + "resources";
    public static final String RESOURCES = RESOURCES_TEST;

    private Constance() {
        throw new IllegalStateException("Utility class");
    }
}
