/**
 * Copyright 2017 Alexis DUQUE.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.rtone.vlc.simulator.topology;

import java.util.ArrayList;
import java.util.Random;

public class Channel {

    private static final double STD_SHIFT = 0.5;
    private static double CAMERA_FOCAL_DISTANCE = 35;
    private static double CAMERA_SENSOR_SIZE = 5.7;
    private static double[] ROI_EXP_RATIO = {1, 0.98452928286029235, 0.72436947075060389,
            0.52775098804825378, 0.42458059355383677, 0.31179044636046831, 0.25630101117437368, 0.16225149678257165,
            0.05831170015338602};
    private static double LED_SIZE = 10;
    private ArrayList<Double> channel = new ArrayList<>();
    private int distanceCm;
    private int channelDuration100Us;
    private int channelOnDuration100Us;
    private int currentIndex = 0;
    private Camera camera;
    private Random random = new Random(System.currentTimeMillis());

    public Channel(int distanceCm, Camera camera) {
        this.distanceCm = distanceCm;
        this.camera = camera;
        this.channelDuration100Us = Math.round(camera.getFrameInterval() * 10);
        this.currentIndex = random.nextInt(channelDuration100Us);
        buildChannel();
    }

    private Channel(float chanDistance, Camera camera) {
    }

    private ArrayList<Double> buildChannel() {
        channel = new ArrayList<>();
        for (int i = 0; i < channelDuration100Us; i++) {
            double roi = getRoiFromDistance(this.distanceCm * 10, CAMERA_FOCAL_DISTANCE, CAMERA_SENSOR_SIZE, LED_SIZE);
            double roiExp = getRoiFromExpDistance(this.distanceCm);
            double on = Math.ceil(camera.getOnDurationMs());
            this.channelOnDuration100Us = (int) (on * 10 * roiExp);
            if (i < channelOnDuration100Us) {
                channel.add(camera.getpDecodeError());
            } else {
                channel.add(1d);
            }
        }
        return channel;
    }

    public double getNextErrorProbability(float physduDuration100Us) {
        int physduDuration = Math.round(physduDuration100Us);
        currentIndex = (physduDuration + currentIndex + random.nextInt(10)) % channelDuration100Us;
        try {
            double channelErrorProb = channel.get((currentIndex));
            if (currentIndex + physduDuration >= channelOnDuration100Us) {
                return 1d;
            }
            return channelErrorProb;
        } catch (Exception e) {
            e.printStackTrace();
            currentIndex = 0;
        }
        /*
        List<Double> packetChannel = new ArrayList<>();
        if (currentIndex + physduDuration >= channel.size()) {
            packetChannel.addAll(this.channel.subList(currentIndex, channel.size() - 1));
            packetChannel.addAll(channel.subList(0, (currentIndex + physduDuration) % channel.size()));
        } else {
            packetChannel.addAll(channel.subList(currentIndex, currentIndex + physduDuration - 1));
        }
        if (packetChannel.contains(1d)) {
            return 1d;
        }*/
        //channelErrorProb = channelErrorProb == 1 ? 1 : random.nextGaussian() * STD_SHIFT + 1;
        //return channelErrorProb < 0 ? 0 : channelErrorProb;
        return channel.get((currentIndex));
    }

    public ArrayList<Double> getChannel() {
        return channel;
    }

    public double getRoiFromDistance(double distance, double focalDistance, double sensorSize, double ledSize) {
        if (distance <= 0) {
            return 1;
        }
        double objOnSensor = focalDistance * ledSize / distance;
        double ratio = objOnSensor / sensorSize;
        return Math.min(ratio, 1);
    }

    public double getRoiFromExpDistance(double distance) {
        int dis5 = (int) (distance / 5 + 0.5f);
        if (dis5 >= ROI_EXP_RATIO.length) {
            return 0;
        }
        return ROI_EXP_RATIO[dis5];
    }
}
