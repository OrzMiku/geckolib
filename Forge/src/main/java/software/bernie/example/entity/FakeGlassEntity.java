package software.bernie.example.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.example.client.model.entity.FakeGlassModel;
import software.bernie.example.client.renderer.entity.FakeGlassRenderer;
import software.bernie.geckolib3.animatable.GeoEntity;
import software.bernie.geckolib3.core.animatable.GeoAnimatable;
import software.bernie.geckolib3.core.animation.AnimatableManager;
import software.bernie.geckolib3.core.animation.factory.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

/**
 * Example {@link GeoAnimatable} implementation of an entity that uses the texture-per-bone feature of
 * {@link software.bernie.geckolib3.renderer.ExtendedGeoEntityRenderer ExtendedGeoEntityRenderer}
 * @see FakeGlassModel
 * @see FakeGlassRenderer
 */
public class FakeGlassEntity extends PathfinderMob implements GeoEntity {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public FakeGlassEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	// We don't care about animations for this one
	@Override
	public void registerControllers(AnimatableManager<?> manager) {}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}