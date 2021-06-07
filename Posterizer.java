/**
 * DC-posterizer
 *  
 * Author: Zane Deck (GreyEminence)
 * Date: 4/19/2021
 * 
 * Desc: This program takes an input image located in the file DC-Posterizer located off the desktop and
 *       creates a new image that pixel-by-pixel represents the clostest color avaliable in the designated 
 *       palette.
 * 
 */
import java.io.File;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.FileReader;

class RBGNode{
    int R;
    int B;
    int G;
    public int getB() {
        return B;
    }
    public int getR(){
        return R;
    }
    public int getG(){
        return G;
    }
    public void setRGB(int Rn, int Gn, int Bn){
        this.R = Rn;
        this.B = Bn;
        this.G = Gn;
    }
}
class Palette{
    //palette object
    //RBGNode palette[];
    //palette = new RBGNode[16];
}

class Posterizer{
    public static void main(String[] args){
        //load image
        BufferedImage image = null;
        String filename = "./input.PNG";  //change 'input' to whatever your png filename is (keep .PNG)
                                          // or just rename your image =/
        try{
            image = ImageIO.read(new File(filename));
            System.out.println("Reading complete.");
        }catch(IOException e){
            System.out.println("Error1: "+e);
        }
        Scanner sc = new Scanner(System.in); 

        //get palette selection from user
        String pal;
        File f;
        while(true){
            System.out.println("please enter desired palette name: ");
            pal = sc.nextLine();
            f = new File(pal + ".txt");
            if(f.isFile()) {
                //exists proceed to read it
                break;
            }
            else if(pal.equalsIgnoreCase("exit")){
                System.exit(0);
            }
            else{
                System.out.println("invalid file, try again, or type 'exit' to exit");
            }
        }// retry until good selection
        sc.close();

        //open file
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f));
        
            //read palettes from file
            int lines = Integer.parseInt(reader.readLine());
            RBGNode palette[]; 
            palette = new RBGNode[lines];
            for (int i = 0; i < lines; i++){
                //read the three rgb values as ints
                String[] colors = reader.readLine().split("\\s+");
                RBGNode inst = new RBGNode();
                inst.setRGB(Integer.parseInt(colors[0]),
                            Integer.parseInt(colors[1]),
                            Integer.parseInt(colors[2]));
                palette[i] = inst;
                }
            reader.close();


        // changes every pixel of the input image to the clostest match in the palette
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int  pixel   = image.getRGB(x, y); 
        
                  // Unpacks the 32 bits into the individual R,G,B 
                  // channel values which are 8 bits (i.e. 0..255)
                int  red   = (pixel & 0x00ff0000) >> 16;
                int  green = (pixel & 0x0000ff00) >> 8;
                int  blue  =  pixel & 0x000000ff;
        
                  // Pick clostsest match
                int score = 0;
                int min = 10000; // above max value 3 * 255 so it will always pick from the palette
                // initialization for safety
                int nr = red;
                int nb = blue;
                int ng = green;
                for (int i = 0; i < lines; i++){
                    int dr = Math.abs(red - palette[i].getR());
                    int db = Math.abs(blue - palette[i].getB());
                    int dg = Math.abs(green - palette[i].getG());
                    score = dr + db + dg;
                    if (score < min){
                        min = score;
                        nr = palette[i].getR();
                        nb = palette[i].getB();
                        ng = palette[i].getG();
                    }
                }
        
                  // Pack the red,green and blue channels back into a 
                  // 32 bit int pixel 
                pixel = (nb & 0x000000ff) | ((ng<<8)& 0x0000ff00) | ((nr<<16)&0x00ff0000);
        
                image.setRGB(x, y, pixel);
            }
        }
    }
    catch(Exception e){
        System.out.println("Buffered Reader: " + e);
        System.exit(0);
    }
        //save image
    try{
        File fo = new File("Output.png");  //output file path
        ImageIO.write(image, "png", fo);
        System.out.println("Writing complete.");
    }catch(IOException e){
        System.out.println("Error: "+e);
      }
    }
}