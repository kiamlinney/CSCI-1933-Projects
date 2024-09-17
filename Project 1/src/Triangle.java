//Written by Liam Kinney, kinne351

import java.awt.Color;

public class Triangle {
    private double width;
    private double height;
    private double xPos;
    private double yPos;
    private Color color;

    public Triangle() {} //default constructor

    public Triangle(double width, double height, double xPos, double yPos) {
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public Triangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    //getter methods
    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public double getXPos() { return xPos; }

    public double getYPos() { return yPos; }

    public Color getColor() { return color; }

    //setter methods
    public void setWidth(double width) { this.width = width; }

    public void setHeight(double height) { this.height = height; }
    
    public void setColor(Color color) { this.color = color; }

    public void setPos(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double calculatePerimeter(){
        double ww = Math.pow(width, 2); //making a width squared variable
        double hh = Math.pow(height, 2); //making a height squared variable

        double perimeter = (width + Math.sqrt(ww + 4 * hh)); //calculating perimeter using the formula: width + sqrt(width^2 + 4 * height^2)

        return perimeter;
    }

    public double calculateArea(){ 
        return (0.5 * width * height); //calculating the area with the formula: 1/2 * width * height
    } 
}