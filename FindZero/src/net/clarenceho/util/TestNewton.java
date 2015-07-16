package net.clarenceho.util;

import static org.junit.Assert.*;

import org.junit.Test;
import java.math.BigDecimal;

public class TestNewton {

    @Test
    public void test1() {

        FindZero fz = new FindZero();
        assertTrue(closeEnough(fz.newtonMethod(
                // fx = (x^3) / 5 - (x^2) + x
                (x) -> {
                    return x.multiply(x).multiply(x).divide(new BigDecimal("5.0")).subtract(x.multiply(x)).add(x);
                } ,
                // dfx = (3 * x^2) / 5 - 2 * x + 1
                (x) -> {
                    return new BigDecimal("3.0").multiply(x).multiply(x).divide(new BigDecimal("5.0"))
                            .subtract(new BigDecimal("2.0").multiply(x)).add(new BigDecimal("1.0"));
                } , new BigDecimal("-0.001")), new BigDecimal("0.0")));
    }

    @Test
    public void test2() {

        FindZero fz = new FindZero();
        assertTrue(closeEnough(fz.newtonMethod(
                // fx = (x^3) / 5 - (x^2) + x
                (x) -> {
                    return x.multiply(x).multiply(x).divide(new BigDecimal("5.0")).subtract(x.multiply(x)).add(x);
                } ,
                // dfx = (3 * x^2) / 5 - 2 * x + 1
                (x) -> {
                    return new BigDecimal("3.0").multiply(x).multiply(x).divide(new BigDecimal("5.0"))
                            .subtract(new BigDecimal("2.0").multiply(x)).add(new BigDecimal("1.0"));
                } , new BigDecimal("1.0")), new BigDecimal("1.38196")));
    }

    @Test
    public void test3() {

        FindZero fz = new FindZero();
        assertTrue(closeEnough(fz.newtonMethod(
                // fx = x^2 - 2 * x - 4
                (x) -> {
                    return x.multiply(x).subtract(new BigDecimal("2.0").multiply(x)).subtract(new BigDecimal("4.0"));
                } ,
                // dfx = 2 * x - 2
                (x) -> {
                    return new BigDecimal("2.0").multiply(x).subtract(new BigDecimal("2.0"));
                } , new BigDecimal("100")), new BigDecimal("3.23607")));
    }

    private boolean closeEnough(BigDecimal x, BigDecimal y) {
        return Math.abs(x.subtract(y).doubleValue()) < 0.00001;
    }
}
