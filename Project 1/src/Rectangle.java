//Written by Liam Kinney, kinne351

import java.awt.Color;

public class Rectangle {
    private double width;
    private double height;
    private double xPos;
    private double yPos;
    private Color color;

    public Rectangle() {} //default constructor

    public Rectangle(double width, double height, double xPos, double yPos) { 
        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos; 
    }

    public Rectangle(double width, double height) { 
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
        return 2 * (width + height); 
    }

    public double calculateArea(){ 
        return (width * height); 
    }
}
