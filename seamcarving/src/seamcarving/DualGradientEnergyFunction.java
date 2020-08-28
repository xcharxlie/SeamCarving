package seamcarving;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;


public class DualGradientEnergyFunction implements EnergyFunction {
    @Override
    public double apply(Picture picture, int x, int y) {
        int width = picture.width();
        int height = picture.height();
        if (x<0||y<0) {
            throw new IndexOutOfBoundsException();
        }
        if (width<3||height<3||x>=width||y>=height) {
            throw new IndexOutOfBoundsException();
        }

        Color c5 = picture.get(x, y);
        int r5 = c5.getRed();
        int b5 = c5.getBlue();
        int g5 = c5.getGreen();
        int fx = 0;
        int fy = 0;
        if (x==0) {
            Color temp = picture.get(x+2, y);
            int r = temp.getRed();
            int b = temp.getBlue();
            int g = temp.getGreen();
            Color c1 = picture.get(x+1, y);
            int r1 = c1.getRed();
            int b1 = c1.getBlue();
            int g1 = c1.getGreen();
            //fx = -3*picture.getRGB(x, y)+4*picture.getRGB(x+1, y)-picture.getRGB(x+2, y);
            int rd = -3*r5+4*r1-r;
            int bd = -3*b5+4*b1-b;
            int gd = -3*g5+4*g1-g;
            fx = rd*rd+bd*bd+gd*gd;
        } else if (x==width-1) {
            Color temp = picture.get(x-2, y);
            int r = temp.getRed();
            int b = temp.getBlue();
            int g = temp.getGreen();
            Color c2 = picture.get(x-1, y);
            int r2 = c2.getRed();
            int b2 = c2.getBlue();
            int g2  = c2.getGreen();
            //fx = -3*picture.getRGB(x, y)+4*picture.getRGB(x-1, y)-picture.getRGB(x-2, y);
            int rd = -3*r5+4*r2-r;
            int bd = -3*b5+4*b2-b;
            int gd = -3*g5+4*g2-g;
            fx = rd*rd+bd*bd+gd*gd;
        }
        if (y==0) {
            Color temp = picture.get(x, y+2);
            int r = temp.getRed();
            int b = temp.getBlue();
            int g = temp.getGreen();
            //fy = -3*picture.getRGB(x, y)+4*picture.getRGB(x, y+1)-picture.getRGB(x, y+2);
            Color c3 = picture.get(x, y+1);
            int r3 = c3.getRed();
            int b3 = c3.getBlue();
            int g3 = c3.getGreen();
            int rd = -3*r5+4*r3-r;
            int bd = -3*b5+4*b3-b;
            int gd = -3*g5+4*g3-g;
            fy = rd*rd+bd*bd+gd*gd;
        } else if (y==height-1) {
            Color c4 = picture.get(x, y-1);
            int r4 = c4.getRed();
            int b4 = c4.getBlue();
            int g4  = c4.getGreen();
            Color temp = picture.get(x, y-2);
            int r = temp.getRed();
            int b = temp.getBlue();
            int g = temp.getGreen();
            //fy = -3*picture.getRGB(x, y)+4*picture.getRGB(x, y-1)-picture.getRGB(x, y-2);
            int rd = -3*r5+4*r4-r;
            int bd = -3*b5+4*b4-b;
            int gd = -3*g5+4*g4-g;
            fy = rd*rd+bd*bd+gd*gd;
        }

        if (fx==0 && x!=0 && x!=width-1) {
            Color c1 = picture.get(x+1, y);
            Color c2 = picture.get(x-1, y);
            int r1 = c1.getRed();
            int b1 = c1.getBlue();
            int g1 = c1.getGreen();
            int r2 = c2.getRed();
            int b2 = c2.getBlue();
            int g2  = c2.getGreen();
            fx = (r1-r2)*(r1-r2)+(b1-b2)*(b1-b2)+(g1-g2)*(g1-g2);
        }
        if (fy==0&&y!=0&y!=height-1) {
            Color c4 = picture.get(x, y-1);
            int r4 = c4.getRed();
            int b4 = c4.getBlue();
            int g4  = c4.getGreen();
            Color c3 = picture.get(x, y+1);
            int r3 = c3.getRed();
            int b3 = c3.getBlue();
            int g3 = c3.getGreen();
            fy = (r3-r4)*(r3-r4)+(b3-b4)*(b3-b4)+(g3-g4)*(g3-g4);
        }

        return (double) Math.sqrt(fx+fy);
    }
}
