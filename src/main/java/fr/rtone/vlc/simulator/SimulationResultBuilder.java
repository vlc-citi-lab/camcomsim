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
import kotlin.Pair;

import java.util.ArrayList;

public class SimulationResultBuilder {
    private int nbTxGeneration;
    private int nbTxPhysdu;
    private int nbRxUniquePhysdu;
    private int nbRxPhysdu;
    private int nbInterFgError;
    private int nbIntraFError;
    private float duration100Us;
    private boolean isGenerationRx;
    private ArrayList<Pair<Integer, Integer>> pairNbTxNbRx;
    private ArrayList<Pair<Integer, Integer>> pairNbTxNbMiss;
    private float physduTxDurationMs;
    private SimulationParameters simuParams;
    private ArrayList<Integer> nbConsecutiveDrop;
    private Message<Physdu> rxPhysdu;

    public SimulationResultBuilder setSimuParams(SimulationParameters simuParams) {
        this.simuParams = simuParams;
        return this;
    }

    public SimulationResultBuilder setNbTxGeneration(int nbTxGeneration) {
        this.nbTxGeneration = nbTxGeneration;
        return this;
    }

    public SimulationResultBuilder setNbTxPhysdu(int nbTxPhysdu) {
        this.nbTxPhysdu = nbTxPhysdu;
        return this;
    }

    public SimulationResultBuilder setNbRxUniquePhysdu(int nbRxUniquePhysdu) {
        this.nbRxUniquePhysdu = nbRxUniquePhysdu;
        return this;
    }

    public SimulationResultBuilder setNbRxPhysdu(int nbRxPhysdu) {
        this.nbRxPhysdu = nbRxPhysdu;
        return this;
    }

    public SimulationResultBuilder setNbInterFgError(int nbInterFgError) {
        this.nbInterFgError = nbInterFgError;
        return this;
    }

    public SimulationResultBuilder setNbIntraFError(int nbIntraFError) {
        this.nbIntraFError = nbIntraFError;
        return this;
    }

    public SimulationResultBuilder setDuration100Us(float duration100Us) {
        this.duration100Us = duration100Us;
        return this;
    }

    public SimulationResultBuilder setIsGenerationRx(boolean isGenerationRx) {
        this.isGenerationRx = isGenerationRx;
        return this;
    }

    public SimulationResultBuilder setPairNbTxNbRx(ArrayList<Pair<Integer, Integer>> pairNbTxNbRx) {
        this.pairNbTxNbRx = pairNbTxNbRx;
        return this;
    }

    public SimulationResultBuilder setPairNbTxNbMiss(ArrayList<Pair<Integer, Integer>> pairNbTxNbMiss) {
        this.pairNbTxNbMiss = pairNbTxNbMiss;
        return this;
    }

    public SimulationResult createSimulationResult() {
        SimulationResult result = new SimulationResult(nbTxGeneration, nbTxPhysdu, nbRxUniquePhysdu, nbRxPhysdu,
                nbInterFgError, nbIntraFError, duration100Us, isGenerationRx, pairNbTxNbRx, pairNbTxNbMiss,
                physduTxDurationMs, simuParams, nbConsecutiveDrop, rxPhysdu);
        result.computeGoodput();
        result.computeThrougput();
        return result;
    }

    public SimulationResultBuilder setPhysduTxDurationMs(float physduTxDurationMs) {
        this.physduTxDurationMs = physduTxDurationMs;
        return this;
    }

    public SimulationResultBuilder setNbConsecutiveDrop(ArrayList<Integer> nbConsecutiveDrop) {
        this.nbConsecutiveDrop = nbConsecutiveDrop;
        return this;
    }

    public SimulationResultBuilder setRxPhysu(Message<Physdu> physduRx) {
        this.rxPhysdu = physduRx;
        return this;
    }
}