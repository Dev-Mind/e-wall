package fr.emse.numericwall.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.beust.jcommander.internal.Lists;

/**
 * A rectangle is define by his origin, a width and a height
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
public class Rectangle {
    private Point origin;
    private int width;
    private int height;

    public static Rectangle create(Point origin, int width, int height) {
        Rectangle rectangle = new Rectangle();
        rectangle.origin = origin;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }

    public Point getOrigin() {
        return origin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(width, rectangle.width) &&
                Objects.equals(height, rectangle.height) &&
                Objects.equals(origin, rectangle.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, width, height);
    }
}
