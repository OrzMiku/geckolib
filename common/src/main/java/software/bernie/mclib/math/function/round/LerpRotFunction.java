package software.bernie.mclib.math.function.round;

import software.bernie.geckolib.util.RenderUtils;
import software.bernie.mclib.math.MathValue;
import software.bernie.mclib.math.function.MathFunction;

/**
 * {@link MathFunction} value supplier
 *
 * <p>
 * <b>Contract:</b>
 * <br>
 * Returns the first value plus the difference between the first and second input values multiplied by the third input value, wrapping the end result as a degrees value
 */
public final class LerpRotFunction extends MathFunction {
    private final MathValue min;
    private final MathValue max;
    private final MathValue delta;

    public LerpRotFunction(String name, MathValue... values) {
        super(name);

        this.min = values[0];
        this.max = values[1];
        this.delta = values[2];
    }

    @Override
    public double compute() {
        return RenderUtils.lerpYaw(this.delta.get(), this.min.get(), this.max.get());
    }

    @Override
    public int getMinArgs() {
        return 3;
    }

    @Override
    public MathValue[] getArgs() {
        return new MathValue[] {this.min, this.max, this.delta};
    }
}
