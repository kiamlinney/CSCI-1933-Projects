// Written by Liam Kinney, kinne351

// FractalDrawer class draws a fractal of a shape indicated by user input
import java.awt.Color;
import java.util.Scanner;
import java.util.Random;

public class FractalDrawer {
    private double totalArea; //member variable for tracking the total area 
    private Random random = new Random(); 
    //new color object, generates a random color using the random class
    private float r = random.nextFloat();
    private float g = random.nextFloat();
    private float b = random.nextFloat();
    private Color randomColor = new Color(r, g, b);

    public FractalDrawer() {} //default contructor 
    
    //drawFractal creates a new Canvas object
    //and determines which shapes to draw a fractal by calling appropriate helper function
    //drawFractal returns the area of the fractal
    public double drawFractal(String type){
        //ensuring that the color object only generates bright rainbow pastel colors by changing the hue, saturation, and brightness of the color 
        randomColor = Color.getHSBColor(random.nextFloat(), 0.9f, 1.0f);
        switch(type){
            case "circle" -> {
                Canvas circDrawing = new Canvas(800,800);
                drawCircleFractal(200, 400, 400, randomColor.darker().darker().darker().darker().darker().darker().darker(), circDrawing, 7);
                //makes the initial circle 7 shades darker, because after each iteration, it is made one times brighter
            }
            case "triangle" -> {
                Canvas triDrawing = new Canvas(800,800);
                int width = 400;
                double height = (width*Math.sqrt(3))/2; //in order to make an exactly equilateral triangle
                drawTriangleFractal(width, -height, (800-width)/2, 800-(height), randomColor.darker().darker().darker().darker().darker().darker().darker(), triDrawing,7);
            }

            case "rectangle" -> {
                Canvas recDrawing = new Canvas(800,800);
                drawRectangleFractal(200, 200, 300, 300, randomColor.darker().darker().darker().darker().darker().darker().darker(), recDrawing, 7);
            }
        }
        return totalArea; 
    }
    
    // drawCircleFractal draws a circle fractal using recursive techniques
    public void drawCircleFractal(double radius, double x, double y, Color c, Canvas can, int level){
        Circle myCircle = new Circle(radius, x, y);
        
        myCircle.setColor(c);
        can.drawShape(myCircle);

        if(level > 0){
             drawCircleFractal(radius*0.5, x+radius, y, c.brighter(), can, level-1);
             drawCircleFractal(radius*0.5, x-radius, y, c.brighter(), can, level-1);
             drawCircleFractal(radius*0.5, x, y+radius, c.brighter(), can, level-1);
             drawCircleFractal(radius*0.5, x, y-radius, c.brighter(), can, level-1);
            //generates four new circles on the ends of the original circle, and then on the ends of the new circles until the level reaches 0
            
             totalArea += myCircle.calculateArea();
        }
    }

    // drawTriangleFractal draws a triangle fractal using recursive techniques
    public void drawTriangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level){
        Triangle myTriangle = new Triangle(width, height, x, y);

        myTriangle.setColor(c);
        can.drawShape(myTriangle);

        if(level > 0){
            drawTriangleFractal(width*0.5, (height)*0.5, x-width*0.25, y-height*0.5, c.brighter(), can, level-1);
            drawTriangleFractal(width*0.5, (height)*0.5, x+width*0.25, y+height*0.5, c.brighter(), can, level-1);
            drawTriangleFractal(width*0.5, (height)*0.5, x+width*0.75, y-height*0.5, c.brighter(), can, level-1);
            //creates the sierpinski triangle, generates three new triangles on each side of the original upside down triangle until level reaches 0

            totalArea += myTriangle.calculateArea();
        }
    }

    // drawRectangleFractal draws a rectangle fractal using recursive techniques
    public void drawRectangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level){
        Rectangle myRectangle = new Rectangle(width, height, x, y);
        
        myRectangle.setColor(c);
        can.drawShape(myRectangle);

        if(level > 0){
            drawRectangleFractal(width*0.6, height*0.6, x-width*0.6, y-height*0.6, c.brighter(), can, level-1);
            drawRectangleFractal(width*0.6, height*0.6, x+width, y+height, c.brighter(), can, level-1);
            drawRectangleFractal(width*0.6, height*0.6, x+width, y-height*0.6, c.brighter(), can, level-1);
            drawRectangleFractal(width*0.6, height*0.6, x-width*0.6, y+height, c.brighter(), can, level-1);
            //creates four new rectangles on the four corners of the original, and for the next ones and so on until level reaches 0

            totalArea += myRectangle.calculateArea();
        }
    }
    
    //main should ask user for shape input, and then draw the corresponding fractal.
    //should print area of fractal
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("\033[0;1m====================================== Fractal Generator ======================================\033[0m\n");
        System.out.println("This program will prompt for a shape to generate a fractal out of. Once input,");
        System.out.println("a fractal will be generated from that shape with a random color. To choose a");
        System.out.println("different shape, or the same shape in a different color, run the program again.\n");
        System.out.println("\033[3mNote: some fractals may not render all away, resize the window to completely load the fractal.");
        System.out.println("And sometimes, the fractal does not load at all, if that occurs, just run the program again.\033[0m\n");
        System.out.println("\033[0;1m===============================================================================================\033[0m\n");
        System.out.print("\033[0;1mEnter desired shape:\033[0m\n\033[0;1m1)\033[0m for circle. \033[0;1m2)\033[0m for triangle. \033[0;1m3)\033[0m for rectangle.\n: ");
        int choice = s.nextInt();
        
        switch(choice){
            case 1 -> {
                FractalDrawer fd = new FractalDrawer();
                System.out.printf("The total area of the generated circle fractal is %.2f.\n", fd.drawFractal("circle"));
            }   
                
            case 2 -> {
                FractalDrawer fd = new FractalDrawer();
                System.out.printf("The total area of the generated triangle fractal is %.2f.\n", Math.abs(fd.drawFractal("triangle")));
                //Used Math.abs to get the absolute value, becuase due to making the triangles upside down, the area was returned as negative.
            }
                
            case 3 -> {
                FractalDrawer fd = new FractalDrawer();
                System.out.printf("The total area of the generated rectangle fractal is %.2f.\n", fd.drawFractal("rectangle"));
            }
                
            default -> {
                System.out.println("Invalid input, please choose 1, 2, or 3.");
            }
        }   
        s.close();
    }
}