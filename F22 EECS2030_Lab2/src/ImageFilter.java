/**
 * @author Andriy Pavlovych
 * The class is meant to illustrate a couple of image-processing algorithms:
 * Simple edge detection and image blending
 */
public class ImageFilter{
	//TODO prevent object creation, as this is a utility class
	private void imageFilter() {
		
	}
	/**
	 * Method implements simple edge detection
	 * @param imageData imageData array of image pixels
	 * @param width image width
	 */
	public static void edgeDetection(int[] imageData, int width) {
		//TODO your task is to replace this stub code with the proper implementation of the method 
		//The code below merely demonstrates how to extract RGB pixel values from the image and how to write them them back
//		for (int i=0; i<imageData.length; i++){
//			int red, green, blue;
//			red 	= (imageData[i] & 0x00FF0000)>>16; //try 0.0 * (imageData[i] & 0x00FF0000)>>16 here
//			green 	= (imageData[i] & 0x0000FF00)>>8;
//			blue 	= (imageData[i] & 0x000000FF);
//
//		//one way to store the result back
//		imageData[i] = red<<16 | green <<8 | blue;
//		}
		
		// First, let's define a kernel for edge detection:
		// The following kernel will do the job 
		int[][]edge_detection_kernel = {{-1, 0, 1},{-2, 0, 2},{-1, 0, 1}};
		
		
		// No lets store the height of the image in the variable height
			// This will allow us to loop through the rows of the image 
		int height = imageData.length/width;
		
		// To store the "filter" applied to a new array
		int[] newImageData = new int[imageData.length];
		
		// Initializing / setting rows and columns of the image to use in the while loop
		int rowsOfImage = 0, columnsOfImage = 0;
		
		// Initializing red green blue variables so we can use them later on in the while loops( to store the actual r g b values)
		int red, green, blue;
		// Now we will use a while loop to loop through the rows of the image to apply the convolution 
		while(rowsOfImage < height) {
			
			// reset the column so it does not store the old r g b values
			columnsOfImage = 0;
			
			// In this while loop, we are simply looping through the columns 
			while(columnsOfImage < width) {
				
				//reset r, g, b for every pixel(in the column) we loop through
				red = 0;
				green = 0;
				blue = 0;
				
				// Now we loop through every element of the edge detection kernel in order to apply it to every pixel
					// Doing so by looping through every row and column of the kernel
				for(int i = 0; i < edge_detection_kernel.length; i++) {
					for(int j = 0; j < edge_detection_kernel[i].length; j++) {
						
						
						// ImageRowIndex to eventually calculate the image pixel address
						int imageRowIndex = rowsOfImage + i - edge_detection_kernel.length/2;
						
						// ImageRowColumn to eventually calculate the image pixel address
						int imageColumnIndex = columnsOfImage + j - edge_detection_kernel.length/2;
						
						// Since we need to check if in case a pixel coordinate is off screen
							// 	we need to set them to an appropriate index. similar to imageColumnIndex
						if( imageRowIndex < 0) {imageRowIndex = 0;} 
						else if( imageRowIndex >= imageData.length/width) {imageRowIndex = imageData.length/width - 1;}
						
						
						// Since we need to check if in case a pixel coordinate is off screen
						// we need to set them to an appropriate index
						if( imageColumnIndex < 0) {imageColumnIndex = 0;}
						else if( imageColumnIndex >= width) {imageColumnIndex = width - 1;}
					
						
						// Now we can calculate the image pixel address using the formula provided within the lab
						int image1DIndex = imageRowIndex * width + imageColumnIndex;
						
						
						//Now all we need to do is set the r g b values in order to update the r g b values for the resulting image
						red =  red + (edge_detection_kernel[i][j] * ((imageData[image1DIndex] & 0x00FF0000)>>16));
						green = green + (edge_detection_kernel[i][j] * ((imageData[image1DIndex] & 0x0000FF00)>>8));
						blue = blue + (edge_detection_kernel[i][j] * (imageData[image1DIndex] & 0x000000FF));
						
					}
					
				}
				
				// Setting boundaries for r g b values within the range 0 <= r g b <= 255
					// So, if an r g b value is less than 0, just set it to 0 otherwise if it is greater than 255 then set it to 255
				if(red < 0) {red = 0;}
				else if(red > 255) {red = 255;}
				if(green < 0) {green = 0;}
				else if(green > 255) {green = 255;}
				if(blue < 0) {blue = 0;}
				else if(blue > 255) {blue = 255;}
				
				newImageData[rowsOfImage * width + columnsOfImage] = red << 16 | green << 8 | blue;
				
				columnsOfImage++;
			}
			
			rowsOfImage++;
		}
		
		System.arraycopy(newImageData, 0, imageData, 0, imageData.length);
	}

		

	/**
	 * Method merges the contents of the two images of identical size. The result is saved into the first image.
	 * @param imageData1 imageData array of image pixels of the first image
	 * @param imageData2 imageData array of image pixels of the second image
	 * @param width image width
	 */
	public static void mergeImages(int[] imageData1, int[] imageData2, int width) {
		//TODO throw exceptions as appropriate
			// if imageData1 and imageData2 don't have the same length	
		if(imageData2.length != imageData1.length) {
			throw new IllegalArgumentException("Images are different sizes");
		}
		
		// General thought process
			// First we need to initialize red, green, and blue variables so we can store r,g,b values for both of the provided images
			// Than we simply add both the extracted r,g,b values and average them
			// then we store them back in imageData1 indices
			
		
		for (int i=0; i<imageData1.length; i++){
			int red, green, blue, red2, green2, blue2;
			
			red 	= (imageData1[i] & 0x00FF0000)>>16; 
			green 	= (imageData1[i] & 0x0000FF00)>>8;
			blue 	= (imageData1[i] & 0x000000FF);
			
			red2 	= (imageData2[i] & 0x00FF0000)>>16; 
			green2 	= (imageData2[i] & 0x0000FF00)>>8;
			blue2 	= (imageData2[i] & 0x000000FF);
			
			
			// Now we can simply
			imageData1[i] = ((red + red2) / 2)<<16 | ((green  + green2 ) / 2)<<8 | (blue + blue2)/ 2;

		}
		
	}
}


















