package org.simbrain.world.odorworld.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.simbrain.util.environment.SmellSource;
import org.simbrain.world.odorworld.OdorWorld;
import org.simbrain.world.odorworld.effectors.Effector;
import org.simbrain.world.odorworld.sensors.Sensor;

/**
 * Adapted and extended from From Developing Games in Java, by David Brackeen.
 */
public abstract class OdorWorldEntity {

    /**
     * Animation used to depict this object. If the animation has one frame this
     * is equivalent to just using a single image to represent it.
     */
    protected Animation anim;
    
    /** X Position. */
    protected float x;

    /** Y Position. */
    protected float y;

    /** X Velocity. */
    protected float dx = .1f;

    /** Y Velocity. */
    protected float dy = .1f;
    
    /** Sensors. */
    private List<Sensor> sensors= new ArrayList<Sensor>();
    
    /** Effectors. */
    private List<Effector> effectors = new ArrayList<Effector>();
    
    /** Smell Source (if any). */
    private SmellSource smellSource = null;
    
    /**
     * Updates this OdorWorldEntity's Animation and its position based on the velocity.
     */
    public abstract void update(long elapsedTime);
    
    /**
     * Called before update() if the creature collided with a tile horizontally.
     */
    public abstract void collideHorizontal();

    /**
     * Called before update() if the creature collided with a tile vertically.
     */
    public abstract void collideVertical();

    /** Back reference to parent world. */
    private OdorWorld world;

    /** Name of this entity. */
    private String name;

    /**
     * Construtor.
     *
     * @param world parent world.
     * @param anim default animation.
     */
    public OdorWorldEntity(final OdorWorld world, final Animation anim) {        
        this.anim = anim;
        this.world = world;
        anim.start();
    }

    /**
        Gets this OdorWorldEntity's current x position.
    */
    public float getX() {
        return x;
    }

    /**
        Gets this OdorWorldEntity's current y position.
    */
    public float getY() {
        return y;
    }

    /**
        Sets this OdorWorldEntity's current x position.
    */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets this OdorWorldEntity's current y position.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets this OdorWorldEntity's width, based on the size of the current
     * image.
     */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
     * Gets this OdorWorldEntity's height, based on the size of the current
     * image.
     */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
     * Gets the horizontal velocity of this OdorWorldEntity in pixels per
     * millisecond.
     */
    public float getVelocityX() {
        return dx;
    }

    /**
     * Gets the vertical velocity of this OdorWorldEntity in pixels per
     * millisecond.
     */
    public float getVelocityY() {
        return dy;
    }

    /**
     * Sets the horizontal velocity of this OdorWorldEntity in pixels per
     * millisecond.
     */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity of this OdorWorldEntity in pixels per
     * millisecond.
     */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }
    
    /**
     * Get the entity's name.
     *
     * @return entity's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the entity's name
     *
     * @param string name for entity.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets this OdorWorldEntity's current image.
     */
    public Image getImage() {
        return anim.getImage();
    }
    
    /**
     * Get bounds, based on current image.
     *
     * @return bounds of this entity.
     */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, getWidth(), getHeight());
    }
    
    /**
     * Add an effector.
     *
     * @param effector effetor to add
     */
    public void addEffector(final Effector effector) {
        effectors.add(effector);
        world.fireEffectorAdded(effector);
    }

    /**
     * Add a sensor.
     *
     * @param sensor sensor to add
     */
    public void addSensor(final Sensor sensor) {
        sensors.add(sensor);
        world.fireSensorAdded(sensor);
    }

    /**
     * Apply impact of all effectors.
     */
    public void applyEffectors() {
        for (Effector effector : effectors) {
            effector.activate();
        }
    }

    /**
     * Update all sensors.
     */
    public void readSensors() {
        for (Sensor sensor : sensors) {
            // Not sure yet...
        }
    }
}
