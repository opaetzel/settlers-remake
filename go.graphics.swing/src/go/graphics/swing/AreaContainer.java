package go.graphics.swing;

import go.graphics.RedrawListener;
import go.graphics.area.Area;
import go.graphics.event.GOEvent;
import go.graphics.event.GOEventHandlerProvider;
import go.graphics.swing.event.swingInterpreter.GOSwingEventConverter;
import go.graphics.swing.opengl.JOGLDrawContext;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JPanel;

/**
 * This class lets you embed areas into swing components.
 * 
 * @author michael
 */
public class AreaContainer extends JPanel implements RedrawListener, GOEventHandlerProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8204496712425576430L;
	private final Area area;

	private Component canvas;
	private JOGLDrawContext context;

	/**
	 * creates a new area conaainer
	 * 
	 * @param area
	 *            The area to display
	 */
	public AreaContainer(Area area) {
		this(area, false);
	}

	/**
	 * creates a new area conaainer
	 * 
	 * @param area
	 *            The area to display
	 */
	public AreaContainer(Area area, boolean forceLightweight) {
		this.area = area;
		this.setLayout(new BorderLayout());

		GLProfile profile = GLProfile.getDefault();
		GLCapabilities cap = new GLCapabilities(profile);
		cap.setStencilBits(1);

		GLEventListener glEventListener = new GLEventListener() {

			@Override
			public void reshape(GLAutoDrawable gl, int x, int y, int width, int height) {
				resizeArea(gl.getGL().getGL2(), x, y, width, height);
			}

			@Override
			public void init(GLAutoDrawable arg0) {
				arg0.getGL().setSwapInterval(0);
			}

			@Override
			public void dispose(GLAutoDrawable arg0) {
			}

			@Override
			public void display(GLAutoDrawable glDrawable) {
				draw(glDrawable.getGL().getGL2());
			}
		};

		if (forceLightweight) {
			GLJPanel panel = new GLJPanel(cap);
			panel.addGLEventListener(glEventListener);
			canvas = panel;
		} else {
			GLCanvas glCanvas = new GLCanvas(cap);
			glCanvas.addGLEventListener(glEventListener);
			canvas = glCanvas;
		}

		new GOSwingEventConverter(canvas, this);

		area.addRedrawListener(this);
		this.add(canvas);
	}

	/**
	 * Resizes the area.
	 * 
	 * @param gl2
	 *            The GL object
	 * @param x
	 *            unused
	 * @param y
	 *            unused
	 * @param width
	 *            The width
	 * @param height
	 *            The hieght
	 */
	protected void resizeArea(GL2 gl2, int x, int y, int width, int height) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		// coordinate system origin at lower left with width and height same as
		// the window
		GLU glu = new GLU();
		glu.gluOrtho2D(0.0f, width, 0.0f, height);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		gl2.glViewport(0, 0, width, height);

		area.setWidth(width);
		area.setHeight(height);
	}

	/**
	 * Draws the content area on the OpenGl object.
	 * 
	 * @param gl2
	 *            Where to draw on.
	 */
	protected void draw(GL2 gl2) {
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

		gl2.glLoadIdentity();

		if (context == null || context.getGl2() != gl2) {
			context = new JOGLDrawContext(gl2);
		}
		context.startFrame();
		area.drawArea(context);
	}

	@Override
	public void requestRedraw() {
		canvas.repaint();
	}

	@Override
	public void handleEvent(GOEvent event) {
		area.handleEvent(event);
	}
}
