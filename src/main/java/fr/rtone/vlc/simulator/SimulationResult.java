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

import com.google.gson.annotations.Expose;
import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Physdu;
import kotlin.Pair;

import java.util.ArrayList;

public class SimulationResult {
    private final Message<Physdu> rxPhysdu;
    @Expose
    private float physduTxDurationMs;
    @Expose
    private ArrayList<Integer> nbConsecutiveDrop;
    @Expose
    private int nbTxGeneration;
    @Expose
    private int nbTxPhysdu;
    @Expose
    private int nbRxUniquePhysdu;
    @Expose
    private int nbRxPhysdu;
    @Expose
    private int nbInterFgError;
    @Expose
    private int nbIntraFError;
    @Expose
    private float duration100Us;
    @Expose
    private boolean isGenerationRx;
    private ArrayList<Pair<Integer, Integer>> pairNbTxNbRx;
    private ArrayList<Pair<Integer, Integer>> pairNbTxNbMiss;
    @Expose
    private float goodputBps;
    @Expose
    private float througputBps;
    @Expose
    private SimulationParameters simuParams;

    public SimulationResult(int nbTxGeneration, int nbTxPhysdu, int nbRxUniquePhysdu, int nbRxPhysdu, int
            nbInterFgError, int nbIntraFError, float duration100Us, boolean isGenerationRx, ArrayList<Pair<Integer,
            Integer>> pairNbTxNbRx, ArrayList<Pair<Integer, Integer>> pairNbTxNbMiss, float physduTxDurationMs,
                            SimulationParameters simuParams, ArrayList<Integer> nbConsecutiveDrop, Message<Physdu>
                                    rxPhysdu) {
        this.nbTxGeneration = nbTxGeneration;
        this.nbTxPhysdu = nbTxPhysdu;
        this.nbRxUniquePhysdu = nbRxUniquePhysdu;
        this.nbRxPhysdu = nbRxPhysdu;
        this.nbInterFgError = nbInterFgError;
        this.nbIntraFError = nbIntraFError;
        this.duration100Us = duration100Us;
        this.isGenerationRx = isGenerationRx;
        this.pairNbTxNbRx = pairNbTxNbRx;
        this.pairNbTxNbMiss = pairNbTxNbMiss;
        this.simuParams = simuParams;
        this.physduTxDurationMs = physduTxDurationMs;
        this.nbConsecutiveDrop = nbConsecutiveDrop;
        this.rxPhysdu = rxPhysdu;
    }

    public Message<Physdu> getRxPhysdu() {
        return rxPhysdu;
    }

    public float getPhysduTxDurationMs() {
        return physduTxDurationMs;
    }

    public ArrayList<Integer> getNbConsecutiveDrop() {
        return nbConsecutiveDrop;
    }

    public int getNbTxGeneration() {
        return nbTxGeneration;
    }

    public int getNbTxPhysdu() {
        return nbTxPhysdu;
    }

    public int getNbRxUniquePhysdu() {
        return nbRxUniquePhysdu;
    }

    public int getNbRxPhysdu() {
        return nbRxPhysdu;
    }

    public int getNbInterFgError() {
        return nbInterFgError;
    }

    public int getNbIntraFError() {
        return nbIntraFError;
    }

    public float getDuration100Us() {
        return duration100Us;
    }

    public boolean isGenerationRx() {
        return isGenerationRx;
    }

    public ArrayList<Pair<Integer, Integer>> getPairNbTxNbRx() {
        return pairNbTxNbRx;
    }

    public ArrayList<Pair<Integer, Integer>> getPairNbTxNbMiss() {
        return pairNbTxNbMiss;
    }

    public float getGoodputBps() {
        return goodputBps;
    }

    public float getThrougputBps() {
        return througputBps;
    }

    public SimulationParameters getSimuParams() {
        return simuParams;
    }

    public float computeThrougput() {
        float nbBits = simuParams.getPhysduHeaderLenBits() + simuParams.getPhysduPayloadLenBits();
        float durationMs = duration100Us / 10;
        float throughputBps = (1000 * nbBits * nbRxPhysdu) / (durationMs);
        this.througputBps = throughputBps;
        return throughputBps;
    }

    public float computeGoodput() {
        float nbBitsGp = simuParams.getPhysduPayloadLenBits();
        float durationMs = duration100Us / 10;
        float goodputBps = (1000 * nbBitsGp * nbRxUniquePhysdu) / (durationMs);
        this.goodputBps = goodputBps;
        return goodputBps;
    }

    public int computeNbRxGeneration() {
        int generationLenByte = simuParams.getSimuGenerationSizeByte();
        int nbPacketN = generationLenByte / simuParams.getPhysduPayloadLenBits();
        int nbGenerationRx = 0;
        Message<Physdu> unique = new Message<>();
        for (Physdu physdu : rxPhysdu) {
            if (unique.countUnique() != nbPacketN) {
                unique.add(physdu);
            } else {
                unique = new Message<>();
                nbGenerationRx++;
            }
        }
        return nbGenerationRx;
    }

    private int getNbPacketsN() {
        return simuParams.getPhysduPayloadLenBits() / simuParams.getSimuGenerationSizeByte();
    }

    @Override
    public String toString() {
        return "SimulationResult{" + " physduTxDurationMs=" + physduTxDurationMs + ", nbConsecutiveDrop=" +
                nbConsecutiveDrop + ", nbTxGeneration=" + nbTxGeneration + ", nbTxPhysdu=" + nbTxPhysdu + ", " +
                "nbRxUniquePhysdu=" + nbRxUniquePhysdu + ", nbRxPhysdu=" + nbRxPhysdu + ", nbInterFgError=" +
                nbInterFgError + ", nbIntraFError=" + nbIntraFError + ", duration100Us=" + duration100Us + ", " +
                "isGenerationRx=" + isGenerationRx + ", goodputBps=" + goodputBps + ", througputBps=" + througputBps
                + '}';
    }
}
