package software.bernie.geckolib.json;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.util.JSONException;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.keyframe.JsonKeyFrameUtils;
import software.bernie.geckolib.animation.keyframe.VectorKeyFrameList;
import java.util.*;

/**
 * Helper for parsing the bedrock json animation format and finding certain elements
 */
public class JSONAnimationUtils
{
	/**
	 * Gets the "animations" object as a set of maps consisting of the name of the animation and the inner json of the animation.
	 *
	 * @param json The root json object
	 * @return The set of map entries where the string is the name of the animation and the JsonElement is the actual animation
	 */
	public static Set<Map.Entry<String, JsonElement>> getAnimations(JsonObject json)
	{
		return getObjectListAsArray(json.getAsJsonObject("animations"));
	}

	/**
	 * Gets the "bones" object from an animation json object.
	 *
	 * @param json The animation json
	 * @return The set of map entries where the string is the name of the group name in blockbench and the JsonElement is the object, which has all the position/rotation/scale keyframes
	 */
	public static Set<Map.Entry<String, JsonElement>> getBones(JsonObject json)
	{
		return getObjectListAsArray(json.getAsJsonObject("bones"));
	}

	/**
	 * Gets rotation key frames.
	 *
	 * @param json The "bones" json object
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the rotation keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getRotationKeyFrames(JsonObject json)
	{
		JsonObject rotationObject = json.getAsJsonObject("rotation");
		if(rotationObject.isJsonArray())
		{
			return ImmutableSet.of(new AbstractMap.SimpleEntry("0.001",
					rotationObject.getAsJsonArray()));
		}
		return getObjectListAsArray(rotationObject);
	}

	/**
	 * Gets position key frames.
	 * @param json The "bones" json object
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the position keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getPositionKeyFrames(JsonObject json)
	{
		JsonObject positionObject = json.getAsJsonObject("position");
		if(positionObject.isJsonArray())
		{
			return ImmutableSet.of(new AbstractMap.SimpleEntry("0.001",
					positionObject.getAsJsonArray()));
		}
		return getObjectListAsArray(positionObject);	}

	/**
	 * Gets scale key frames.
	 *
	 * @param json The "bones" json object
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the scale keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getScaleKeyFrames(JsonObject json)
	{
		JsonObject scaleObject = json.getAsJsonObject("scale");
		if(scaleObject.isJsonArray())
		{
			return ImmutableSet.of(new AbstractMap.SimpleEntry("0.001",
					scaleObject.getAsJsonArray()));
		}
		return getObjectListAsArray(scaleObject);	}

	/**
	 * Gets sound effect frames.
	 *
	 * @param json The animation json
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the sound effect keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getSoundEffectFrames(JsonObject json)
	{
		return getObjectListAsArray(json.getAsJsonObject("sound_effects"));
	}

	/**
	 * Gets particle effect frames.
	 *
	 * @param json The animation json
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the particle effect keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getParticleEffectFrames(JsonObject json)
	{
		return getObjectListAsArray(json.getAsJsonObject("particle_effects"));
	}

	/**
	 * Gets custom instruction key frames.
	 *
	 * @param json The animation json
	 * @return The set of map entries where the string is the keyframe time (not sure why the format stores the times as a string) and the JsonElement is the object, which has all the custom instruction keyframes.
	 */
	public static Set<Map.Entry<String, JsonElement>> getCustomInstructionKeyFrames(JsonObject json)
	{
		return getObjectListAsArray(json.getAsJsonObject("timeline"));
	}


	private static JsonElement getObjectByKey(Set<Map.Entry<String, JsonElement>> json, String key) throws JSONException
	{
		return json.stream().filter(x -> x.getKey().equals(key)).findFirst().orElseThrow(
				() -> new JSONException("Could not find key: " + key)).getValue();
	}


	/**
	 * Gets animation.
	 *
	 * @param animationFile the animation file
	 * @param animationName the animation name
	 * @return the animation
	 * @throws JSONException the json exception
	 */
	public static Map.Entry<String, JsonElement> getAnimation(JsonObject animationFile, String animationName) throws JSONException
	{
		return new AbstractMap.SimpleEntry(animationName, getObjectByKey(getAnimations(animationFile), animationName));
	}

	/**
	 * The animation format bedrock/blockbench uses is bizzare, and exports arrays of objects as plain parameters in a parent object, so this method undos it
	 *
	 * @param json The json to convert (pass in the parent object or the list of objects)
	 * @return The set of map entries where the string is the object key and the JsonElement is the actual object
	 */
	public static Set<Map.Entry<String, JsonElement>> getObjectListAsArray(JsonObject json)
	{
		return json.entrySet();
	}

	/**
	 * This is the central method that parses an animation and converts it to an Animation object with all the correct keyframe times and extra metadata.
	 *
	 * @param element The animation json
	 * @return The newly constructed Animation object
	 * @throws ClassCastException    Throws this exception if the JSON is formatted incorrectly
	 * @throws IllegalStateException Throws this exception if the JSON is formatted incorrectly
	 */
	public static Animation deserializeJsonToAnimation(Map.Entry<String, JsonElement> element) throws ClassCastException, IllegalStateException
	{
		Animation animation = new Animation();
		JsonObject animationJsonObject = element.getValue().getAsJsonObject();

		// Set some metadata about the animation
		animation.animationName = element.getKey();
		animation.animationLength = animationJsonObject.get("animation_length").getAsFloat();
		animation.boneAnimations = new ArrayList();

		// The list of all bones being used in this animation, where String is the name of the bone/group, and the JsonElement is the
		Set<Map.Entry<String, JsonElement>> bones = getBones(animationJsonObject);
		for (Map.Entry<String, JsonElement> bone : bones)
		{
			BoneAnimation boneAnimation = new BoneAnimation();
			boneAnimation.boneName = bone.getKey();

			JsonObject boneJsonObj = bone.getValue().getAsJsonObject();
			try
			{
				Set<Map.Entry<String, JsonElement>> scaleKeyFramesJson = getScaleKeyFrames(boneJsonObj);
				boneAnimation.scaleKeyFrames = JsonKeyFrameUtils.convertJsonToScaleKeyFrames(
						new ArrayList<>(scaleKeyFramesJson));
			}
			catch(Exception e)
			{
				// No scale key frames found
				boneAnimation.scaleKeyFrames = new VectorKeyFrameList<>();
			}

			try
			{
				Set<Map.Entry<String, JsonElement>> positionKeyFramesJson = getPositionKeyFrames(boneJsonObj);
				boneAnimation.positionKeyFrames = JsonKeyFrameUtils.convertJsonToPositionKeyFrames(
						new ArrayList<>(positionKeyFramesJson));
			}
			catch(Exception e)
			{
				// No position key frames found
				boneAnimation.positionKeyFrames = new VectorKeyFrameList<>();
			}

			try
			{
				Set<Map.Entry<String, JsonElement>> rotationKeyFramesJson = getRotationKeyFrames(boneJsonObj);
				boneAnimation.rotationKeyFrames = JsonKeyFrameUtils.convertJsonToRotationKeyFrames(
						new ArrayList<>(rotationKeyFramesJson));
			}
			catch(Exception e)
			{
				// No rotation key frames found
				boneAnimation.rotationKeyFrames = new VectorKeyFrameList<>();
			}

			animation.boneAnimations.add(boneAnimation);
		}
		return animation;
	}
}
