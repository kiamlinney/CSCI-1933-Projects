//Written by Liam Kinney, kinne351

import java.awt.Color;

public class Circle {
    private double radius;
    private double xPos; 
    private double yPos; 
    private Color color;

    public Circle() {} //default constructor

    public Circle(double radius, double xPos, double yPos) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    //getter methods
    public double getRadius() { return radius; }

    public double getXPos() { return xPos; }

    public double getYPos() { return yPos; }

    public Color getColor() { return color; }

    //setter methods
    public void setRadius(double radius) { this.radius = radius; }

    public void setColor(Color color) { this.color = color; }

    public void setPos(double xPos, double yPos) { 
        this.xPos = xPos; 
        this.yPos = yPos; 
    }

    //operators
    public double calculatePerimeter(){ 
        return (2 * Math.PI * radius); //returns the perimeter, or the circumference, which is 2πr
    } 

    public double calculateArea(){ 
        return (Math.PI * Math.pow(radius, 2));  //returns the area, which is πr^2
    }
}