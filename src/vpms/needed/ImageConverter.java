/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.needed;


import java.io.File;
import java.nio.file.Files;


/**
 *
 * @author being
 */
public class ImageConverter {
    
    private byte[] imageBytes;
    

    public ImageConverter(File uploadedImage){
        try {
            String defaultPath = new Constants().defaultImagePath();
            this.imageBytes = getImageOrDefault(uploadedImage, defaultPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public byte[] returnByteArray(){
        return this.imageBytes;
    }
    
    public static byte[] convertImageToByteArray(File imageFile) throws Exception {
        return Files.readAllBytes(imageFile.toPath());
    }
        
    /**
     * Returns the byte array of the uploaded image, or the default image if null.
     * @param uploadedImage the uploaded image file (can be null)
     * @param defaultImagePath the path to the default image
     * @return byte array of the image
     * @throws IOException if file reading fails
    
     * @param uploadedImage
     * @param defaultImagePath
     * @return 
     * @throws java.lang.Exception */
    public static byte[] getImageOrDefault(File uploadedImage, String defaultImagePath) throws Exception {
        if (uploadedImage != null && uploadedImage.exists()) {
            return convertImageToByteArray(uploadedImage);
        } else {
            File defaultImage = new File(defaultImagePath);
            return convertImageToByteArray(defaultImage);
        }
    }

    
}
