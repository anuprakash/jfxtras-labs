package jfxtras.labs.internal.scene.control.skin.gauge.linear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import jfxtras.css.CssMetaDataForSkinProperty;
import jfxtras.labs.scene.control.gauge.linear.CompleteSegment;
import jfxtras.labs.scene.control.gauge.linear.Segment;
import jfxtras.labs.scene.control.gauge.linear.SimpleMetroArcGauge;

import com.sun.javafx.css.converters.EnumConverter;

/**
 * 
 */
public class SimpleMetroArcGaugeSkin extends SkinBase<SimpleMetroArcGauge> {

	// ==================================================================================================================
	// CONSTRUCTOR
	
	/**
	 * 
	 */
	public SimpleMetroArcGaugeSkin(SimpleMetroArcGauge control) {
		super(control);
		construct();
	}

	/*
	 * 
	 */
	private void construct() {
		createNodes();
	}
	
	
	// ==================================================================================================================
	// StyleableProperties
	
    /**
     * animated
     */
    public final ObjectProperty<Animated> animatedProperty() { return animated; }
    private ObjectProperty<Animated> animated = new SimpleStyleableObjectProperty<Animated>(StyleableProperties.ANIMATED, this, "animated", StyleableProperties.ANIMATED.getInitialValue(null)) {
//    	{ // anonymous constructor
//			addListener( (invalidationEvent) -> {
//			});
//		}
    };
    public final void setAnimated(Animated value) { animatedProperty().set(value); }
    public final Animated getAnimated() { return animated.get(); }
    public final SimpleMetroArcGaugeSkin withAnimated(Animated value) { setAnimated(value); return this; }
    public enum Animated {YES, NO}

    // -------------------------
        
    private static class StyleableProperties 
    {
    	// TBEERNOT: the first value is animated, even if the CSS is set immediately on construction
        private static final CssMetaData<SimpleMetroArcGauge, Animated> ANIMATED = new CssMetaDataForSkinProperty<SimpleMetroArcGauge, SimpleMetroArcGaugeSkin, Animated>("-fxx-animated", new EnumConverter<Animated>(Animated.class), Animated.YES ) {
        	@Override 
        	protected ObjectProperty<Animated> getProperty(SimpleMetroArcGaugeSkin s) {
            	return s.animatedProperty();
            }
        };
        
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static  {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<CssMetaData<? extends Styleable, ?>>(SkinBase.getClassCssMetaData());
            styleables.add(ANIMATED);
            STYLEABLES = Collections.unmodifiableList(styleables);                
        }
    }
    
    /** 
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */    
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /**
     * This method should delegate to {@link Node#getClassCssMetaData()} so that
     * a Node's CssMetaData can be accessed without the need for reflection.
     * @return The CssMetaData associated with this node, which may include the
     * CssMetaData of its super classes.
     */
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
        
	// ==================================================================================================================
	// DRAW
	
	/**
	 * construct the nodes
	 */
	private void createNodes()
	{
		// dial
		dialPane = new Pane();
		dialPane.widthProperty().addListener( (observable) -> {
			drawDialPane();
		});
		dialPane.heightProperty().addListener( (observable) -> {
			drawDialPane();
		});
		
		// needle
		needlePane = new Pane();
		needlePane.widthProperty().addListener( (observable) -> {
			drawNeedlePane();
		});
		needlePane.heightProperty().addListener( (observable) -> {
			drawNeedlePane();
		});
		needleRotate = new Rotate(-10.0);
		getSkinnable().valueProperty().addListener( (observable) -> {
			rotateNeedle(true);
		});
		rotateNeedle(false);
		
		// we use a stack pane to control the layers
		StackPane lStackPane = new StackPane();
		lStackPane.getChildren().add(dialPane);
		lStackPane.getChildren().add(needlePane);
		getChildren().add(lStackPane);
		
		// style
		getSkinnable().getStyleClass().add(getClass().getSimpleName()); // always add self as style class, because CSS should relate to the skin not the control		
	}
	private Pane dialPane;
	private Pane needlePane;
	private Rotate needleRotate;

	/**
	 * 
	 */
	private void drawDialPane() {
		// TBEERNOT: can we optimize the drawing (e.g. when width & height have not changed, skip)
		// TBEERNOT: handle that not min <= value <= max

		// we always draw from scratch
		dialPane.getChildren().clear();
		
		// preparation
 		double width = dialPane.getWidth();
 		double height = dialPane.getHeight();
 		double controlMinValue = getSkinnable().getMinValue();
 		double controlMaxValue = getSkinnable().getMaxValue();
 		double controlValueRange = controlMaxValue - controlMinValue;
 		
 		// determine what segments to draw
 		List<Segment> segments = new ArrayList<Segment>(getSkinnable().segments());
 		if (segments.size() == 0) {
 			segments.add(completeSegment);
 		}
 		
 		// draw the segments
		Point2D center = new Point2D(width / 2.0, height * 0.6);
 		double radius = Math.min(center.getX(), center.getY());
 		int cnt = 0;
 		for (Segment segment : segments) {
 			
 			// create a path for this segment
 	 		double segmentMinValue = segment.getMinValue();
 	 		double segmentMaxValue = segment.getMaxValue();
 			double startAngle = (segmentMinValue - controlMinValue) / controlValueRange * FULL_ARC_IN_DEGREES; 
 			double endAngle = (segmentMaxValue - controlMinValue) / controlValueRange * FULL_ARC_IN_DEGREES; 
			Path segmentPath = createSegmentPath(center, radius, startAngle, endAngle);
// TBEERNOT: Replace with single arc node?			
// 			Arc segmentPath = new Arc();
// 			segmentPath.setCenterX(center.getX());
// 			segmentPath.setCenterY(center.getY());
// 			segmentPath.setRadiusX(radius);
// 			segmentPath.setRadiusY(radius);
// 			segmentPath.setStartAngle(200.0 - startAngle);
// 			segmentPath.setLength(endAngle - startAngle);
// 			segmentPath.setType(ArcType.ROUND);
			dialPane.getChildren().add(segmentPath);
			
			// setup CSS on the path
	        segmentPath.getStyleClass().addAll("segment", "segment" + cnt);
	        if (segment.getId() != null) {
	        	segmentPath.setId(segment.getId());
	        }
	        
 			cnt++;
 		}
	}
	static final private double FULL_ARC_IN_DEGREES = 270.0;
	final private CompleteSegment completeSegment = new CompleteSegment(getSkinnable());

	/**
	 * 
	 */
	private void rotateNeedle(boolean allowAnimation) {
 		double controlMinValue = getSkinnable().getMinValue();
 		double controlMaxValue = getSkinnable().getMaxValue();
 		double controlValueRange = controlMaxValue - controlMinValue;
 		double value = getSkinnable().getValue();
 		double angle = (value - controlMinValue) / controlValueRange * FULL_ARC_IN_DEGREES;
 		
 		// We cannot use node.setRotate(angle), because this rotates always around the center of the node and the needle's rotation center is not the same as the node's center.
 		// So we need to use the Rotate transformation, which allows to specify the center of rotation.
 		// This however also means that we cannot use RotateTransition, because that manipulates the rotate property of a node (and -as explain above- we couldn't use that).
 		// The only way to animate a Rotate transformation is to use a timeline and keyframes.
 		if (allowAnimation == false || Animated.NO.equals(getAnimated())) {
 	 		needleRotate.setAngle(angle);
 		}
 		else {
	        final KeyValue KEY_VALUE = new KeyValue(needleRotate.angleProperty(), angle, Interpolator.SPLINE(0.5, 0.4, 0.4, 1.0));
	        final KeyFrame KEY_FRAME = new KeyFrame(Duration.millis(1000), KEY_VALUE);
	        timeline.getKeyFrames().setAll(KEY_FRAME);
	        timeline.play();
 		}

	}
	Timeline timeline = new Timeline();

	private void drawNeedlePane() {
		// TBEERNOT: can we optimize the drawing (e.g. when width & height have not changed, skip)
		// TBEERNOT: handle that not min <= value <= max

 		// we always draw from scratch
		needlePane.getChildren().clear();
		
		// preparation
		double width = needlePane.getWidth();
 		double height = needlePane.getHeight();
		Point2D center = new Point2D(width / 2.0, height * 0.6);
 		double radius = Math.min(center.getX(), center.getY());
		double tipRadius = radius * 0.9;
		double needleRadius = radius * 0.5;
		
		// Java's math uses radians
		// 0 degrees is on the right side of the circle (3 o'clock), the gauge starts in the bottom left (about 7 o'clock), so add 90 + 45 degrees to offset to that. 
		double startAngleInRadians = Math.toRadians(0.0 - 20.0 + 135.0); 
		double tipAngleInRadians = Math.toRadians(0.0 + 135.0); 
		double endAngleInRadians = Math.toRadians(0.0 + 20.0 + 135.0);

		// calculate the two points of the segment
		Point2D startPoint = calculatePointOnCircle(center, needleRadius, startAngleInRadians);
		Point2D tipPoint = calculatePointOnCircle(center, tipRadius, tipAngleInRadians);
		Point2D endPoint = calculatePointOnCircle(center, needleRadius, endAngleInRadians);
		
		Path needle = new Path();
        needle.setFillRule(FillRule.EVEN_ODD);        
		needle.getStyleClass().add("needle");
		needle.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
        // begin of inner arc
        needle.getElements().add( new MoveTo(startPoint.getX(), startPoint.getY()) );
        
        // inner arc to the end point
        {
	        ArcTo arcTo = new ArcTo();
	        arcTo.setX(endPoint.getX());
	        arcTo.setY(endPoint.getY());
	        arcTo.setRadiusX(needleRadius);
	        arcTo.setRadiusY(needleRadius);
	        arcTo.setLargeArcFlag(true);
	        arcTo.setSweepFlag(false);
	        needle.getElements().add(arcTo);
        }
        
        needle.getElements().add(new LineTo(tipPoint.getX(), tipPoint.getY()));
        needle.getElements().add(new LineTo(startPoint.getX(), startPoint.getY()));
        
        needle.setStrokeWidth(needleRadius * 0.10);
        
        needleRotate.setPivotX(center.getX());
        needleRotate.setPivotY(center.getY());
        needle.getTransforms().setAll(needleRotate); 
        
        needlePane.getChildren().add(needle);
	}
	
	// ==================================================================================================================
	// SUPPORT
	
	/**
	 * 
	 * @param center
	 * @param radius
	 * @param startAngleInDegrees
	 * @param endAngleInDegrees
	 * @param cssClass
	 * @return
	 */
	private Path createSegmentPath(Point2D center, double radius, double startAngleInDegrees, double endAngleInDegrees) {
		
		// some additional info
		double angleInDegrees = endAngleInDegrees - startAngleInDegrees;
		
		// Java's math uses radians
		// 0 degrees is on the right side of the circle (3 o'clock), the gauge starts in the bottom left (about 7 o'clock), so add 90 + 45 degrees to offset to that. 
		double startAngleInRadians = Math.toRadians(startAngleInDegrees + 135.0); 
		double endAngleInRadians = Math.toRadians(endAngleInDegrees + 135.0);

		// calculate the four points of the segment
		Point2D arcStartPoint2D = calculatePointOnCircle(center, radius, startAngleInRadians);
		Point2D arcEndPoint2D = calculatePointOnCircle(center, radius, endAngleInRadians);
		
		// create a path to draw the segment with
        Path path = new Path();
        path.setFillRule(FillRule.EVEN_ODD);
        
        // begin of outer arc
        path.getElements().add( new MoveTo(arcStartPoint2D.getX(), arcStartPoint2D.getY()) );
      
        // arc to end of outer arc
        {
	        ArcTo arcTo = new ArcTo();
	        arcTo.setX(arcEndPoint2D.getX());
	        arcTo.setY(arcEndPoint2D.getY());
	        arcTo.setRadiusX(radius);
	        arcTo.setRadiusY(radius);
	        arcTo.setLargeArcFlag(angleInDegrees > 180.0);
	        arcTo.setSweepFlag(true);
	        path.getElements().add(arcTo);
        }

        // leg from end of arc to center
        path.getElements().add( new LineTo(center.getX(), center.getY()) );
      
        // leg from center to start of arc
        path.getElements().add( new LineTo(arcStartPoint2D.getX(), arcStartPoint2D.getY()) );
      
        // done
        return path;
    }
    
	/**
	 * http://www.mathopenref.com/coordparamcircle.html
	 * @param center
	 * @param radius
	 * @param angle
	 * @return
	 */
	static private Point2D calculatePointOnCircle(Point2D center, double radius, double angle) {
		double x = center.getX() + (radius * Math.cos(angle));
		double y = center.getY() + (radius * Math.sin(angle));
		return new Point2D(x, y);
	}
}
