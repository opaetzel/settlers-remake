package jsettlers.common.mapobject;

public enum EMapObjectType {
	TREE_GROWING,
	TREE_ADULT,
	TREE_DEAD,
	CORN_GROWING,
	CORN_ADULT,
	CORN_DEAD,
	WAVES,
	STONE,
	/**
	 * This is the arrow object type.
	 * <p>
	 * Map objects with type arrow must implement {@link IArrowMapObject}
	 */
	ARROW,
	/**
	 * A Ghost (disappearing settler).
	 */
	GHOST,
	/**
	 * A working area mark. A progress of 0 means the outer ring, 1 means the inner ring. Currently, there are 4 rings.
	 */
	WORKAREA_MARK,
	/**
	 * The coal-mark for mountains. 0 means few coal, 1 is a lot.
	 */
	FOUND_COAL,
	FOUND_IRON,
	FOUND_GOLD,
	FOUND_GEMSTONE,
	FOUND_BRIMSTONE,
	FOUND_NOTHING,
	/**
	 * The sign that marks the center of a building site.
	 */
	BUILDINGSITE_SIGN,
	/**
	 * Building site borders.
	 */
	BUILDINGSITE_POST,

	/**
	 * in front of a door
	 */
	FLAG_DOOR,

	/**
	 * on top of the roof
	 */
	FLAG_ROOF,

	/**
	 * the rest of a stone that can not be cut any more.
	 */
	CUT_OFF_STONE,

	/**
	 * Type of objects used to view the user where a building can be build.
	 * <p />
	 * The value of the construction mark is given by {@link IMapObject} .getStateProgress().<br>
	 * The value ranges from 0 to 1 where 0 is best and 1 is worst.<br>
	 * If there is no marking at a position, the building can not be constructed there.
	 */
	CONSTRUCTION_MARK,

	/**
	 * Type to represent material stacks.
	 * <p />
	 * {@link IMapObject}s of this type must implement {@link IStackMapObject}.
	 */
	STACK_OBJECT,

	/**
	 * Type to represent a Building
	 * <p />
	 * {@link IMapObject}s of this type must implement {@link IBuildingMapObject}.
	 */
	BUILDING,

	/**
	 * Simple smoke
	 */
	SMOKE,

	/**
	 * A pig. Progress is ignored.
	 */
	PIG,

	/**
	 * cloud of smoke when a building get's torn down.
	 */
	BUILDING_DECONSTRUCTION_SMOKE,
	WINE,
	PLANT_DECORATION,
	DESERT_DECORATION,

	/**
	 * Animated fish in the water.
	 */
	FISH_DECORATION,

	/**
	 * Doesn't need to be drawn. <br>
	 * Must implement {@link IAttackableTowerMapObject}.
	 */
	ATTACKABLE_TOWER,

	/**
	 * doesn't need to be drawn.
	 */
	INFORMABLE_MAP_OBJECT;

	public static final EMapObjectType[] values = EMapObjectType.values();
	public final byte ordinal;

	private EMapObjectType() {
		this.ordinal = (byte) super.ordinal();
	}
}
