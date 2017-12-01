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

public class Camera {

    private float frameIntervalMs = 0;
    private float igfRatio = 0;
    private double pDecodeError = 0;

    private Camera() {
    }

    public Camera(float frameIntervalMs, float igfRatio, double pDecodeError) {
        this.frameIntervalMs = frameIntervalMs;
        this.pDecodeError = pDecodeError;
        this.igfRatio = igfRatio;
    }

    public float getOnDurationMs() {
        return frameIntervalMs * getOnRatio();
    }

    public float getOffDurationMs() {
        return frameIntervalMs * igfRatio;
    }

    public float getOnRatio() {
        return igfRatio >= 1 ? 0 : 1 - igfRatio;
    }

    public float getFrameInterval() {
        return frameIntervalMs;
    }

    public float getIgfRatio() {
        return igfRatio;
    }

    public double getpDecodeError() {
        return pDecodeError;
    }
}
