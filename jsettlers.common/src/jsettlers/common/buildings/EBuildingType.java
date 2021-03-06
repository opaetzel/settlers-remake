package jsettlers.common.buildings;

import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import jsettlers.common.buildings.jobs.IBuildingJob;
import jsettlers.common.buildings.loader.BuildingFile;
import jsettlers.common.images.EImageLinkType;
import jsettlers.common.images.ImageLink;
import jsettlers.common.images.OriginalImageLink;
import jsettlers.common.landscape.ELandscapeType;
import jsettlers.common.movable.EMovableType;
import jsettlers.common.position.RelativePoint;

/**
 * This interface defines the main building type.
 * 
 * @author michael
 */
public enum EBuildingType {
	STONECUTTER(3),
	FORESTER(7),
	LUMBERJACK(0),
	SAWMILL(1),

	COALMINE(6),
	IRONMINE(4),
	GOLDMINE(5),
	GOLDMELT(8),
	IRONMELT(9),
	TOOLSMITH(10),
	WEAPONSMITH(11),

	FARM(21),
	PIG_FARM(20),
	/**
	 * Needs to implement {@link IBuilding.Mill}
	 */
	MILL(14), // 13 => rotating
	WATERWORKS(19),
	SLAUGHTERHOUSE(13),
	BAKER(23),
	FISHER(22),
	WINEGROWER(33),
	CHARCOAL_BURNER(30),
	DONKEY_FARM(35),

	SMALL_LIVINGHOUSE(24),
	MEDIUM_LIVINGHOUSE(25),
	BIG_LIVINGHOUSE(26),

	LOOKOUT_TOWER(2),
	TOWER(17),
	BIG_TOWER(18),
	CASTLE(16),
	HOSPITAL(29),
	BARRACK(34),

	DOCKYARD(45),
	HARBUR(44),
	STOCK(-1),
	// 27: katapult

	TEMPLE(31),
	BIG_TEMPLE(32),

	// /**
	// * Test building for own image files.
	// */
	// LAGERHAUS(0)
	;

	public static final EBuildingType[] values = EBuildingType.values();
	public static final int NUMBER_OF_BUILDINGS = values.length;

	public final int ordinal;

	private final IBuildingJob startJob;

	private final EMovableType workerType;

	private final RelativePoint doorTile;

	private final RelativePoint[] blockedTiles;

	private final int imageIndex;

	private final short workradius;

	private final RelativeStack[] stacks;

	private final RelativePoint workcenter;

	private final RelativePoint flag;

	private final RelativeBricklayer[] bricklayers;

	private final byte numberOfConstructionMaterials;

	private final ImageLink guiImage;

	private final ImageLink[] images;

	private final ImageLink[] buildImages;

	private final RelativePoint[] protectedTiles;

	private final RelativePoint[] buildmarks;

	private final ELandscapeType[] groundtypes;

	private final short viewdistance;

	private final OccupyerPlace[] occupyerPlaces;

	private final BuildingAreaBitSet buildingAreaBitSet;

	EBuildingType(int imageIndex) {
		this.ordinal = ordinal();

		this.imageIndex = imageIndex;
		BuildingFile file = new BuildingFile(this.toString());
		startJob = file.getStartJob();
		workerType = file.getWorkerType();
		doorTile = file.getDoor();
		blockedTiles = file.getBlockedTiles();
		protectedTiles = file.getProtectedTiles();
		stacks = file.getStacks();
		workradius = file.getWorkradius();
		workcenter = file.getWorkcenter();
		flag = file.getFlag();
		bricklayers = file.getBricklayers();
		occupyerPlaces = file.getOccupyerPlaces();
		guiImage = file.getGuiImage();
		ImageLink[] tempimages = file.getImages();
		if (tempimages.length == 0) {
			// TODO: this can be removed if all images are converted
			System.out.println("WARNING: Building " + this.toString() + " does not have an image definition.");
			images = new ImageLink[] { new OriginalImageLink(EImageLinkType.SETTLER, 13, imageIndex, 0) };
			buildImages = new ImageLink[] { new OriginalImageLink(EImageLinkType.SETTLER, 13, imageIndex, 1) };
		} else {
			images = tempimages;
			buildImages = file.getBuildImages();
		}

		buildmarks = file.getBuildmarks();
		groundtypes = file.getGroundtypes();
		viewdistance = file.getViewdistance();

		this.numberOfConstructionMaterials = calculateNumberOfConstructionMaterials();

		this.buildingAreaBitSet = new BuildingAreaBitSet(this.getProtectedTiles());
	}

	private final byte calculateNumberOfConstructionMaterials() {
		byte sum = 0;
		for (RelativeStack curr : stacks) {
			sum += curr.requiredForBuild();
		}
		return sum;
	}

	public final IBuildingJob getStartJob() {
		return startJob;
	}

	public final EMovableType getWorkerType() {
		return workerType;
	}

	public final RelativePoint getDoorTile() {
		return doorTile;
	}

	public final RelativePoint[] getBlockedTiles() {
		return blockedTiles;
	}

	public final RelativeStack[] getRequestStacks() {
		return stacks;
	}

	@Deprecated
	public final int getImageIndex() {
		return imageIndex;
	}

	/**
	 * Gets the images needed to display this building
	 * 
	 * @return The images
	 */
	public final ImageLink[] getImages() {
		return images;
	}

	/**
	 * Gets the gui image
	 * 
	 * @return The image. It may be <code>null</code>
	 */
	public final ImageLink getGuiImage() {
		return guiImage;
	}

	/**
	 * Gets the working radius of the building. If it is 0, the building does not support a working radius.
	 * 
	 * @return
	 */
	public final short getWorkradius() {
		return workradius;
	}

	public final RelativePoint getWorkcenter() {
		return workcenter;
	}

	public final RelativePoint getFlag() {
		return flag;
	}

	public final RelativeBricklayer[] getBricklayers() {
		return bricklayers;
	}

	public final byte getNumberOfConstructionMaterials() {
		return numberOfConstructionMaterials;
	}

	/**
	 * Gets the tiles that are protected by this building. On thse tiles, no other buildings may be build.
	 * 
	 * @return The tiles as array.
	 */
	public final RelativePoint[] getProtectedTiles() {
		return protectedTiles;
	}

	public final RelativePoint[] getBuildmarks() {
		return buildmarks;
	}

	public final ImageLink[] getBuildImages() {
		return buildImages;
	}

	public final ELandscapeType[] getGroundtypes() {
		return groundtypes;
	}

	public final short getViewDistance() {
		return viewdistance;
	}

	public final OccupyerPlace[] getOccupyerPlaces() {
		return occupyerPlaces;
	}

	public final IBuildingJob getJobByName(String jobname) {
		HashSet<String> visited = new HashSet<String>();

		ConcurrentLinkedQueue<IBuildingJob> queue = new ConcurrentLinkedQueue<IBuildingJob>();
		queue.add(startJob);

		while (!queue.isEmpty()) {
			IBuildingJob job = queue.poll();
			if (visited.contains(job.getName())) {
				continue;
			}
			if (job.getName().equals(jobname)) {
				return job;
			}
			visited.add(job.getName());

			queue.add(job.getNextFailJob());
			queue.add(job.getNextSucessJob());
		}
		throw new IllegalArgumentException("This building has no job with name " + jobname);
	}

	public final BuildingAreaBitSet getBuildingAreaBitSet() {
		return buildingAreaBitSet;
	}

}
