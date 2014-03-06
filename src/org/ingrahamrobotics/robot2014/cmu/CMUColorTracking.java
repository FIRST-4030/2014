/*
 * Copyright (C) 2014 Ingraham Robotics Team 4030
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ingrahamrobotics.robot2014.cmu;

import java.io.IOException;
import org.ingrahamrobotics.robot2014.cmu.api.CMUColorTrackingListener;
import org.ingrahamrobotics.robot2014.cmu.api.CMUCommandSet;
import org.ingrahamrobotics.util.LinkedList;

public class CMUColorTracking extends CMUCommandSet {

    private final LinkedList listeners = new LinkedList();
    private int timesNoObject = 0;
    private int[] averages = new int[8];
    private final LinkedList[] storedValues = new LinkedList[averages.length];
    private final int pastValuesToAverage;
    private boolean currentlyTracking;
    private TrackingParameters trackingParameters;

    public CMUColorTracking(final int pastValuesToAverage, TrackingParameters trackingParameters) {
        this.pastValuesToAverage = pastValuesToAverage;
        for (int i = 0; i < storedValues.length; i++) {
            storedValues[i] = new LinkedList();
        }
        this.trackingParameters = trackingParameters;
    }

    public void init(CMUCamConnection c) throws IOException {
        if (currentlyTracking) {
            return;
        }
        currentlyTracking = true;
        c.start();
        c.debug.log("[tracking] Adjusting settings");
        c.sendCommand("CT 1"); // set Color Tracking mode to YUV
        c.sendCommand("AG 0"); // turn off Auto Gain control
        c.sendCommand("AW 0"); // turn off Auto White balance
        c.sendCommand("ST " + trackingParameters.colorParameters);
        c.debug.log("[tracking] Starting tracking");
        c.sendCommand("TC");
    }

    public boolean runWith(final CMUCamConnection c) throws IOException {
        String response = c.readUntil("\r");
        try {
            update(response);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return currentlyTracking;
    }

    public void end(final CMUCamConnection c) throws IOException {
        c.write("\r");
    }

    public void stopTrackingNext() throws IOException {
        currentlyTracking = false;
    }

    private void update(String data) {
        if (data.startsWith("T")) {
            updateDataT(data);
        } else {
            throw new IllegalArgumentException("Unknown data packet format '" + data + "'.");
        }
        for (int i = 0; i < listeners.size(); i++) {
            CMUColorTrackingListener listener = (CMUColorTrackingListener) listeners.get(i);
            listener.onNewColorTrackingData(averages);
        }
    }

    private void updateDataT(String packet) {
        String[] split = CMUUtils.split(packet, " ");
        int[] newValues = new int[averages.length];
        if (split.length < newValues.length + 1) {
            throw new IllegalArgumentException("Invalid T packet '" + packet + "': incomplete number of values");
        }
        for (int i = 0; i < newValues.length; i++) {
            try {
                newValues[i] = Integer.parseInt(split[i + 1]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid T packet '" + packet + "': value '" + split[i + 1] + "' is not an integer.");
            }
        }
        updateAverages(newValues);
    }

    private void updateAverages(int[] newValues) {
        // This if statement is basically for not having averages with 0 values as well.
        if (newValues[0] == 0 || newValues[1] == 0) {
            timesNoObject++;
            if (timesNoObject > pastValuesToAverage) {
                averages = new int[averages.length]; // fill with 0s - default for no-object-found
            }
            return;
        } else if (timesNoObject != 0) {
            for (int i = 0; i < storedValues.length; i++) {
                storedValues[i].clear();
            }
            timesNoObject = 0;
        }
        int[] newAverages = new int[averages.length];
        for (int i = 0; i < storedValues.length; i++) {
            LinkedList queue = storedValues[i];
            queue.add(Integer.valueOf(newValues[i]));
            if (queue.size() > pastValuesToAverage) {
                queue.poll();
            }
            newAverages[i] = CMUUtils.average(queue);
        }
        averages = newAverages;
    }

    public void registerListener(CMUColorTrackingListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void unregisterListener(CMUColorTrackingListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void setTrackingParameters(TrackingParameters trackingParameters) {
        this.trackingParameters = trackingParameters;
    }
}
