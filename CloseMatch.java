import java.io.File;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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

class CloseMatch{
    public static void main(String[] args){
        //load image
        BufferedImage image = null;        
        String filename = "Desktop/input.PNG";  //change 'input.PNG' to whatever your png filename is (keep .PNG)
        try{
            image = ImageIO.read(new File(filename));
            System.out.println("Reading complete.");
        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        //set palette:
       
        // set num to the number of colors in the palette
        RBGNode palette[]; 
        int num = 8;

        palette = new RBGNode[num];

         // replace the (number, number, number) with the RGB value of the desired color
         // if you need more or less colors, create more or less of these three lines as needed,
         //                                     if creating more remember to:
         //                                         1. increase the number folowing 'inst' for each new entry (in all 3 lines)
         //                                         2. increase the number in the brackets folowing 'palette'
         //                                         3. double check the RGB values are in the order Red Green Blue
        RBGNode inst1 = new RBGNode();
        inst1.setRGB(146,154,168);
        palette[0] = inst1;

        RBGNode inst2 = new RBGNode();
        inst2.setRGB(84,108,125);
        palette[1] = inst2;

        RBGNode inst3 = new RBGNode();
        inst3.setRGB(40,70,96);
        palette[2] = inst3;

        RBGNode inst4 = new RBGNode();
        inst4.setRGB(35,46,63);
        palette[3] = inst4;

        RBGNode inst5 = new RBGNode();
        inst5.setRGB(15,19,40);
        palette[4] = inst5;

        RBGNode inst6 = new RBGNode();
        inst6.setRGB(137,104,104);
        palette[5] = inst6;
        
        RBGNode inst7 = new RBGNode();
        inst7.setRGB(99,61,61);
        palette[6] = inst7;

        RBGNode inst8 = new RBGNode();
        inst8.setRGB(68,31,41);
        palette[7] = inst8;

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
                for (int i = 0; i < num; i++){
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
        //save image
    try{
        File f = new File("Desktop/Output.png");  //output file path
        ImageIO.write(image, "png", f);
        System.out.println("Writing complete.");
    }catch(IOException e){
        System.out.println("Error: "+e);
      }
    }
}
