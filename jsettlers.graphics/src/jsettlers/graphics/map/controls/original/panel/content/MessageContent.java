package jsettlers.graphics.map.controls.original.panel.content;

import go.graphics.text.EFontSize;
import jsettlers.common.buildings.EBuildingType;
import jsettlers.common.map.IGraphicsGrid;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.graphics.action.Action;
import jsettlers.graphics.utils.UIPanel;

public class MessageContent implements IContentProvider {

	private final UIPanel panel;

	public MessageContent(String message, String okMessage, Action okAction,
			String abortMessage, Action abortAction) {
		panel = new UIPanel();

		panel.addChild(new Label(message, EFontSize.NORMAL), .1f, .5f, .9f, .9f);

		if (abortMessage != null && abortAction != null) {
			UILabeledButton okButton = new UILabeledButton(abortMessage, abortAction);
			panel.addChild(okButton, .1f, .1f, .5f, .2f);
		}
		if (okMessage != null && okAction != null) {
			UILabeledButton okButton = new UILabeledButton(okMessage, okAction);
			panel.addChild(okButton, .5f, .1f, .9f, .2f);
		}
	}

	@Override
	public UIPanel getPanel() {
		return panel;
	}

	@Override
	public ESecondaryTabType getTabs() {
		return null;
	}

	@Override
	public void displayBuildingBuild(EBuildingType type) {
	}

	@Override
	public void showMapPosition(ShortPoint2D pos, IGraphicsGrid grid) {
	}

}
