package org.open2jam.render.entities;

import org.open2jam.parsers.Event;
import org.open2jam.render.SpriteList;

/** a NoteEntity is a animated entity which moves down.
**/
public class NoteEntity extends AnimatedEntity implements TimeEntity
{
    private SampleEntity sampleEntity;

    private Event.Channel channel = Event.Channel.NONE;

    State state = State.NOT_JUDGED;

    private double hitTime = 0;

    double time_to_hit;
    
    public boolean force_miss = false;
    public boolean force_miss_trigger = false;
    
    public boolean combo_trigger = false;
    public boolean combo_multiplier = false;

    public enum State {
        NOT_JUDGED,
        LN_HEAD_JUDGE,
        JUDGE, 
        TO_KILL,
        LN_HOLD,
        FORCE_MISS
    }

    public NoteEntity(SpriteList sl, Event.Channel ch, double x, double y)
    {
        super(sl, x, y);
        this.channel = ch;
    }

    NoteEntity(NoteEntity org) {
        super(org);
        this.channel = org.channel;
        this.sampleEntity = org.sampleEntity;
        this.state = org.state;
    }

    public void setSampleEntity(SampleEntity sampleEntity) {
        this.sampleEntity = sampleEntity;
    }

    public SampleEntity getSampleEntity() {
        return sampleEntity;
    }
    
    /**
     * Triggers the note's keysound. Must be from user event.
     */
    public void keysound() {
        if (sampleEntity != null) sampleEntity.keysound();
    }
    
    /**
     * Stops the note's keysound.
     */
    public void missed() {
        if (sampleEntity != null) sampleEntity.missed();
    }

    public void setHitTime(double hit) { this.hitTime = hit; }
    public double getHitTime() { return hitTime; }

    public void setState(State value) { state = value; }
    public State getState() { return state; }

    public double getTimeToJudge() {
        return time_to_hit;
    }
    
    public void updateHit(double now)
    {
        setHitTime(testTimeHit(now));
    }

    public double testTimeHit(double now)
    {
        return getTimeToJudge() - now;
    }

    @Override
    public void setPos(double x, double y)
    {
        this.x = x;
        this.y = y - height;
    }

    @Override
    public void setTime(double time){
        this.time_to_hit = time;
    }
    @Override
    public double getTime() {
        return time_to_hit;
    }
    
    @Override
    public void judgment() {}

    @Override
    public NoteEntity copy(){
        return new NoteEntity(this);
    }

    public double getStartY(){
        return y + height;
    }


    public Event.Channel getChannel() {
        return channel;
    }

    public String getChannelName() {
        switch(this.channel) {
		case NOTE_1: return "NOTE_1";
		case NOTE_2: return "NOTE_2";
		case NOTE_3: return "NOTE_3";
		case NOTE_4: return "NOTE_4";
		case NOTE_5: return "NOTE_5";
		case NOTE_6: return "NOTE_6";
		case NOTE_7: return "NOTE_7";
		default: return "NONE";
	    }
        }
}