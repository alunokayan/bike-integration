package br.edu.ifsp.spo.bike_integration.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileUtils {

    static final String[] VALID_IMAGE_EXTENSIONS = { "jpeg" };

    public static boolean isValidFileType(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().startsWith("image/")
                && isValidFileExtension(file.getOriginalFilename());
    }

    public static boolean isValidFileExtension(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        for (String validExtension : VALID_IMAGE_EXTENSIONS) {
            if (fileExtension.equals(validExtension)) {
                return true;
            }
        }
        return false;
    }

}
