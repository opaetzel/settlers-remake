package jsettlers.logic.movable.strategies;

import jsettlers.common.movable.EAction;
import jsettlers.common.movable.EDirection;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.map.newGrid.partition.manager.manageables.IManageableBricklayer;
import jsettlers.logic.map.newGrid.partition.manager.manageables.interfaces.IConstructableBuilding;
import jsettlers.logic.movable.Movable;
import jsettlers.logic.movable.MovableStrategy;

public class BricklayerStrategy extends MovableStrategy implements IManageableBricklayer {
	private static final long serialVersionUID = 7032795807942301297L;
	private static final float BRICKLAYER_ACTION_DURATION = 1f;

	private EBricklayerState state = EBricklayerState.JOBLESS;
	private IConstructableBuilding constructionSite;
	private ShortPoint2D bricklayerTargetPos;
	private EDirection lookDirection;

	public BricklayerStrategy(Movable movable) {
		super(movable);
		jobFinished();
	}

	@Override
	public boolean setBricklayerJob(IConstructableBuilding constructionSite, ShortPoint2D bricklayerTargetPos, EDirection direction) {
		if (state == EBricklayerState.JOBLESS) {
			this.constructionSite = constructionSite;
			this.bricklayerTargetPos = bricklayerTargetPos;
			this.lookDirection = direction;
			this.state = EBricklayerState.INIT_JOB;
			return true;
		} else {
			return false;
		}
	}

	private void jobFinished() {
		this.state = EBricklayerState.JOBLESS;
		this.bricklayerTargetPos = null;
		this.constructionSite = null;
		this.lookDirection = null;
		reportJobless();
	}

	private void reportJobless() {
		super.getStrategyGrid().addJobless(this);
	}

	@Override
	protected void action() {
		switch (state) {
		case JOBLESS:
			break;

		case INIT_JOB:
			if (constructionSite.isBricklayerRequestActive() && super.goToPos(bricklayerTargetPos)) {
				this.state = EBricklayerState.GOING_TO_POS;
			} else {
				jobFinished();
			}
			break;

		case GOING_TO_POS:
			super.lookInDirection(lookDirection);
			state = EBricklayerState.BUILDING;
		case BUILDING:
			tryToBuild();
			break;

		case DEAD_OBJECT:
			break;
		}
	}

	private void tryToBuild() {
		if (constructionSite.tryToTakeMaterial()) {
			super.playAction(EAction.ACTION1, BRICKLAYER_ACTION_DURATION);
		} else {
			jobFinished();
		}
	}

	@Override
	protected boolean checkPathStepPreconditions(ShortPoint2D pathTarget, int step) {
		if (constructionSite == null || constructionSite.isBricklayerRequestActive()) {
			return true;
		} else {
			jobFinished();
			return false;
		}
	}

	@Override
	protected void strategyKilledEvent(ShortPoint2D pathTarget) {
		if (state == EBricklayerState.JOBLESS) {
			super.getStrategyGrid().removeJobless(this);
		} else {
			abortJob();
		}

		state = EBricklayerState.DEAD_OBJECT;
	}

	@Override
	protected void pathAborted(ShortPoint2D pathTarget) {
		if (constructionSite != null) {
			abortJob();
			jobFinished(); // this job is done for us
		}
	}

	private void abortJob() {
		constructionSite.bricklayerRequestFailed(bricklayerTargetPos, lookDirection);
	}

	private static enum EBricklayerState {
		JOBLESS,
		INIT_JOB,
		GOING_TO_POS,
		BUILDING,

		DEAD_OBJECT
	}

}
