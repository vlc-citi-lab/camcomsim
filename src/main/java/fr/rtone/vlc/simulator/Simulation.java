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
package fr.rtone.vlc.simulator;

import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Physdu;
import fr.rtone.vlc.simulator.strategy.AbstractStrategy;
import fr.rtone.vlc.simulator.topology.Camera;
import fr.rtone.vlc.simulator.topology.Channel;
import fr.rtone.vlc.simulator.topology.Led;
import fr.rtone.vlc.simulator.utils.SimulationParameters;
import fr.rtone.vlc.simulator.utils.SimulationResult;
import fr.rtone.vlc.simulator.utils.SimulationResultBuilder;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class Simulation implements Callable<SimulationResult> {
    private int nbIteration;
    private SimulationParameters simuParams;
    private Message<Physdu> dataTobroadcast = new Message<>();
    private ArrayList<Led> leds = new ArrayList<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    private ArrayList<Camera> cameras = new ArrayList<>();
    private AbstractStrategy strategy;

    public Simulation(ArrayList<Led> leds, ArrayList<Camera> cameras, Message<Physdu> datas, ArrayList<Channel>
            channels, AbstractStrategy strategy, SimulationParameters simulationParams) {
        this.leds = leds;
        this.cameras = cameras;
        this.dataTobroadcast = datas;
        this.channels = channels;
        this.simuParams = simulationParams;
        this.strategy = strategy;
    }

    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(ArrayList<Camera> cameras) {
        this.cameras = cameras;
    }

    public ArrayList<Led> getLeds() {
        return leds;
    }

    public void setLeds(ArrayList<Led> leds) {
        this.leds = leds;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<Led> addLed(Led led) {
        this.leds.add(led);
        return this.leds;
    }

    public ArrayList<Camera> addCamera(Camera camera) {
        this.cameras.add(camera);
        return this.cameras;
    }

    public int getNbIteration() {
        return nbIteration;
    }

    public void setNbIteration(int nbIteration) {
        this.nbIteration = nbIteration;
    }

    public SimulationParameters getSimuParams() {
        return simuParams;
    }

    public void setSimuParams(SimulationParameters simuParams) {
        this.simuParams = simuParams;
    }

    public Message<Physdu> getDataTobroadcast() {
        return dataTobroadcast;
    }

    public void setDataTobroadcast(Message<Physdu> dataTobroadcast) {
        this.dataTobroadcast = dataTobroadcast;
    }

    public int getChannelNumber() {
        return this.channels.size();
    }

    private Boolean shouldStop(Boolean rxSuccess, float events) {
        int maxEvents = simuParams.getMaxEvent();
        Boolean stopOnSucess = simuParams.getStopWhenCompleted();
        if (rxSuccess && stopOnSucess)
            return true;
        if (events > maxEvents && maxEvents > 0)
            return true;
        if (maxEvents == 0 && !stopOnSucess) {
            System.err.println("Invalid stop condition. Will never stop.");
            return true;
        }
        return false;
    }

    public SimulationResult run() {

        Random random = new Random(System.currentTimeMillis());
        boolean isGenerationRx = false;
        float duration100Us = 0;
        int interFrameDropped = 0;
        int intraFrameDropped = 0;
        int nbTx = 0;
        float physduDuration100Us = 0;
        int countConsecutiveDrop = 0;
        ArrayList<Pair<Integer, Integer>> nbRxPerNbTx = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> nbDroppedPerNbTx = new ArrayList<>();
        ArrayList<Integer> nbConsecutiveDrop = new ArrayList<>();

        Message<Physdu> physduRx = new Message<>();
        Led led = leds.get(0);
        Channel channel = channels.get(0);
        int MAX_DURATION_100US = 500000;
        while (!shouldStop(isGenerationRx, duration100Us)) {
            nbTx++;
            double error = random.nextDouble();
            Physdu physdu = strategy.nextPhysdu();
            physduDuration100Us = (int) physdu.getDuration100Us(led.getTxFreqHz(), led.getRll());
            double channelError = channel.getNextErrorProbability(physduDuration100Us);
            if (channelError >= error) {
                if (channelError == 1) {
                    interFrameDropped++;
                } else {
                    intraFrameDropped++;
                }
                countConsecutiveDrop++;
            } else {
                if (simuParams.getVerbose() > 3) {
                    System.out.println(physdu.toString());
                }
                physduRx.add(physdu);
                // if last physdu was lost add the number off consecutive drop
                // to the list and reset the counter
                if (countConsecutiveDrop > 0) {
                    nbConsecutiveDrop.add(countConsecutiveDrop);
                    countConsecutiveDrop = 0;
                }
            }
            nbRxPerNbTx.add(new Pair<>(physduRx.size(), nbTx));
            nbDroppedPerNbTx.add(new Pair<>(interFrameDropped + interFrameDropped, nbTx));
            duration100Us += physduDuration100Us;
            if (physduRx.isCompleted(simuParams.getSimuGenerationSizeByte())) {
                if (simuParams.getVerbose() > 3) {
                    System.out.println("All the packets received ! ");
                }
                isGenerationRx = true;
                break;
            }
        }
        SimulationResultBuilder resultBuilder = new SimulationResultBuilder();
        SimulationResult result = resultBuilder.setDuration100Us(duration100Us)
                .setIsGenerationRx(isGenerationRx)
                .setNbInterFgError(interFrameDropped)
                .setNbIntraFError(intraFrameDropped)
                .setNbRxPhysdu(physduRx.size())
                .setNbTxPhysdu(nbTx)
                .setRxPhysu(physduRx)
                .setPhysduTxDurationMs(physduDuration100Us / 10)
                .setNbRxUniquePhysdu(physduRx.countUnique())
                .setPairNbTxNbMiss(nbDroppedPerNbTx)
                .setSimuParams(simuParams).setNbConsecutiveDrop(nbConsecutiveDrop)
                .setPairNbTxNbRx(nbRxPerNbTx)
                .createSimulationResult();
        if (simuParams.getVerbose() > 1) {
            System.out.println(result.toString());
        }
        return result;
    }

    @Override
    public SimulationResult call() throws Exception {
        SimulationResult result = run();
        return result;
    }
}
