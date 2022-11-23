package software.bernie.example.entity;

import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.factory.AnimationFactory;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Replacement {@link net.minecraft.world.entity.monster.Creeper} {@link GeoEntity} to showcase
 * replacing the model and animations of an existing entity
 * @see software.bernie.geckolib.renderer.GeoReplacedEntityRenderer
 * @see software.bernie.example.client.renderer.entity.ReplacedCreeperRenderer
 * @see software.bernie.example.client.model.entity.ReplacedCreeperModel
 */
public class ReplacedCreeperEntity implements GeoReplacedEntity {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

	/**
	 * Register the idle + walk animations for the entity.<br>
	 * In this situation we're going to use a generic controller that is already built for us
	 * @see DefaultAnimations
	 */
	@Override
	public void registerControllers(AnimatableManager<?> manager) {
		manager.addController(DefaultAnimations.genericWalkIdleController(this));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public EntityType<?> getReplacingEntityType() {
		return EntityType.CREEPER;
	}

	@Override
	public void createRenderer(Consumer<RenderProvider> consumer) {

	}

	@Override
	public Supplier<RenderProvider> getRenderProvider() {
		return null;
	}
}