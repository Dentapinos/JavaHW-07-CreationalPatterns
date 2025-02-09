package org.example.track;

import org.example.checkpoints.Checkpoint;

import java.util.ArrayList;

public class BaseTrack implements Track {
    ArrayList<Checkpoint> track;

    public BaseTrack() {
        this.track = new ArrayList<>();
    }

    @Override
    public ArrayList<Checkpoint> getCheckpoints() {
       return track;
    }

    @Override
    public boolean isLastCheckpoint(Checkpoint checkpoint) {
        return track.getLast().getName().equals(checkpoint.getName());
    }

    @Override
    public void addCheckpoint(Checkpoint checkpoint) {
        track.add(checkpoint);
    }
}
