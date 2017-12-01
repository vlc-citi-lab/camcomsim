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

/**
 * A ChannelBuilder used to produced the correct Channel accoding to the Camera parameters and
 * the distance.
 */
public class ChannelBuilder {
    private int distanceCm;
    private Camera camera;

    /**
     * @param distanceCm the distance between the emitter and the receiver
     * @return ChannelBuilder
     */
    public ChannelBuilder setDistanceCm(int distanceCm) {
        this.distanceCm = distanceCm;
        return this;
    }

    /**
     * @param camera the camera used to build this channel
     * @return ChannelBuilder
     */
    public ChannelBuilder setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * @return Channel the channel created according to the distance and the
     * camera.
     */
    public Channel createChannel() {
        return new Channel(distanceCm, camera);
    }
}