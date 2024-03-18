package software.bernie.mclib.math.function.limit;

import software.bernie.mclib.math.MathValue;
import software.bernie.mclib.math.function.MathFunction;

/**
 * {@link MathFunction} value supplier
 *
 * <p>
 * <b>Contract:</b>
 * <br>
 * Returns the lesser of the two input values
 */
public final class MinFunction extends MathFunction {
    private final MathValue valueA;
    private final MathValue valueB;

    public MinFunction(String name, MathValue... values) {
        super(name);

        this.valueA = values[0];
        this.valueB = values[1];
    }

    @Override
    public double compute() {
        return Math.min(this.valueA.get(), this.valueB.get());
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public MathValue[] getArgs() {
        return new MathValue[] {this.valueA, this.valueB};
    }
}
