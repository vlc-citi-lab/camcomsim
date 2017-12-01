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
package fr.rtone.vlc.simulator.utils;

import fr.rtone.vlc.simulator.Simulation;
import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Physdu;
import fr.rtone.vlc.simulator.strategy.AbstractStrategy;
import fr.rtone.vlc.simulator.topology.Camera;
import fr.rtone.vlc.simulator.topology.Channel;
import fr.rtone.vlc.simulator.topology.Led;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class SimulationBuilder {
    private ArrayList<Led> leds = new ArrayList<>();
    private ArrayList<Camera> cameras = new ArrayList<>();
    private Message<Physdu> datas = new Message<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    private AbstractStrategy strategy;
    private SimulationParameters simulationParams;

    public SimulationBuilder setLeds(ArrayList<Led> leds) {
        this.leds = leds;
        return this;
    }

    public SimulationBuilder addLed(Led led) {
        this.leds.add(led);
        return this;
    }

    public SimulationBuilder setSimulationParameters(SimulationParameters simulationParams) {
        this.simulationParams = simulationParams;
        return this;
    }

    public SimulationBuilder setStrategy(AbstractStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public SimulationBuilder setCameras(ArrayList<Camera> cameras) {
        this.cameras = cameras;
        return this;
    }

    public SimulationBuilder addCamera(Camera camera) {
        this.cameras.add(camera);
        return this;
    }

    public SimulationBuilder setDatas(Message<Physdu> datas) {
        this.datas = datas;
        return this;
    }

    public Simulation createSimulation() {
        return new Simulation(leds, cameras, datas, channels, strategy, simulationParams);
    }

    public Callable<SimulationResult> createCallableSimulation() {
        return new Simulation(leds, cameras, datas, channels, strategy, simulationParams);
    }

    public SimulationBuilder setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public SimulationBuilder addChannel(Channel channel) {
        this.channels.add(channel);
        return this;
    }

}