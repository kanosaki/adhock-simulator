package adsim.misc;

import adsim.Util;
import lombok.experimental.Value;

/**
 * 2D Vector class. - Immutable Object
 */
@Value
public class Vector {
    public static final double EPSILON = 1.0e-10;
    // Fields, getters etc. will be generated by lombok.
    double x;
    double y;

    public Vector add(Vector other) {
        return new Vector(this.getX() + other.getX(), this.getY()
                + other.getY());
    }

    public Vector sub(Vector other) {
        return new Vector(this.getX() - other.getX(), this.getY()
                - other.getY());
    }

    public double getNorm() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    public double getLength() {
        return getNorm();
    }

    public Vector lengthen(double len) {
        double norm = getNorm();
        if (norm == 0)
            throw new ArithmeticException(
                    "Zero vector cannot adjust length");
        return new Vector((getX() * len) / norm, (getY() * len) / norm);
    }

    public Vector scale(double scale) {
        return new Vector(getX() * scale, getY() * scale);
    }

    public Vector normalize() {
        double norm = getNorm();
        if (norm == 0)
            throw new ArithmeticException(
                    "Zero vector has no normalized vector");

        return new Vector(getX() / norm, getY() / norm);
    }

    public double distance(Vector other) {
        return Math.sqrt(disatnceSquare(other));
    }

    public double disatnceSquare(Vector other) {
        double deltaX = other.x - this.x;
        double deltaY = other.y - this.y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    /**
     * ベクトルを回転します
     * 
     * @param theta
     *            radian
     */
    public Vector rotate(double theta) {
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        return new Vector(x * cos - y * sin, x * sin + y * cos);
    }

    public static final Vector zero = new Vector(0, 0);
    public static final Vector unit = new Vector(1, 1);

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector other = (Vector) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Vector checkBound(double xMin, double xMax, double yMin, double yMax) {
        double newx = x, newy = y;
        if (x < xMin) {
            newx = xMin + Math.abs(xMin - x);
        }
        if (x > xMax) {
            newx = xMax - Math.abs(x - xMax);
        }
        if (y < yMin) {
            newy = yMin + Math.abs(yMin - y);
        }
        if (y > yMax) {
            newy = yMax - Math.abs(yMax - y);
        }

        if (newx == x && newy == y) {
            return this;
        } else {
            return new Vector(newx, newy);
        }
    }
}
