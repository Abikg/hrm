package com.makalu.hrm.utils;

import java.util.Base64;

public class ImageUtil {
    public String getImageData(byte[] data) {
        return Base64.getMimeEncoder().encodeToString(data);
    }
}
