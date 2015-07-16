package net.clarenceho.util;

import java.util.function.UnaryOperator;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Given equation y = f(x), find successively better approximations to the roots
 * (or zeroes) of a real-valued function (i.e. find x where y = 0)
 * <p>
 * Example of using the method: <br>
 * 
 * <pre>
 * {@code
 * 	FindZero fz = new FindZero();
 * 	assertTrue(closeEnough(fz.newtonMethod(
 * 			// fx = x^2 - 2 * x - 4
 * 			(x) -> {
 * 				return x.multiply(x).subtract(new BigDecimal("2.0").multiply(x)).subtract(new BigDecimal("4.0"));
 * 			} ,
 * 			// dfx = 2 * x - 2
 * 			(x) -> {
 * 				return new BigDecimal("2.0").multiply(x).subtract(new BigDecimal("2.0"));
 * 			} , new BigDecimal("100")), new BigDecimal("3.23607")));
 * }
 * </pre>
 * 
 * @author Clarence Ho (clarenceho at gmail dot com)
 *
 */

public class FindZero {

    private int count = 100;
    private BigDecimal margin = new BigDecimal("0.0000000001");
    private int scale = 20;

    /**
     * Use default parameters
     * <p>
     * count = 100<br>
     * margin = 0.0000000001<br>
     * scale = 20<br>
     */
    public FindZero() {

    }

    /**
     * Specify parameters for Newton's method
     * 
     * @param count
     *            maximum loop count
     * @param margin
     *            how close to zero we want to get to
     * @param scale
     *            precision when doing BigDecimal division
     */
    public FindZero(int count, BigDecimal margin, int scale) {
        this.count = count;
        this.margin = margin;
        this.scale = scale;
    }

    /**
     * Using Newton's method to solve the equation
     * 
     * @param fx
     *            implementation of function f(x)
     * @param dfx
     *            implementation of function f'(x)
     * @param init
     *            the initial guess of x
     * @return result found, or null if exhausted the loop count but the result
     *         is not within margin
     */
    public BigDecimal newtonMethod(UnaryOperator<BigDecimal> fx, UnaryOperator<BigDecimal> dfx, BigDecimal init) {

        BigDecimal x = init;
        for (int i = 0; i < count; i++) {
            // calculate value at this point
            BigDecimal y = fx.apply(x);
            if (y.abs().compareTo(margin) == -1) {
                break;
            }

            BigDecimal yPrime = dfx.apply(x);
            try {
                x = x.subtract(y.divide(yPrime, this.scale, RoundingMode.HALF_UP));
            } catch (ArithmeticException e) {
                return null;
            }
        }

        if (fx.apply(x).abs().compareTo(margin) != -1) {
            // no result found
            return null;
        }

        return x;
    }

}
