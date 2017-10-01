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

import fr.rtone.vlc.simulator.SimulationResult;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

public class SimulationStats {

    DescriptiveStatistics physduTxDurationMs = new DescriptiveStatistics();
    DescriptiveStatistics nbConsecutiveDrop = new DescriptiveStatistics();
    DescriptiveStatistics nbTxGeneration = new DescriptiveStatistics();
    DescriptiveStatistics nbTxPhysdu = new DescriptiveStatistics();
    DescriptiveStatistics nbRxUniquePhysdu = new DescriptiveStatistics();
    DescriptiveStatistics nbRxPhysdu = new DescriptiveStatistics();
    DescriptiveStatistics nbInterFgError = new DescriptiveStatistics();
    DescriptiveStatistics nbIntraFError = new DescriptiveStatistics();
    DescriptiveStatistics duration100Us = new DescriptiveStatistics();
    DescriptiveStatistics goodputBps = new DescriptiveStatistics();
    DescriptiveStatistics througputBps = new DescriptiveStatistics();

    private ArrayList<SimulationResult> simulationResults = new ArrayList<>();

    private SimulationStats() {
    }

    public SimulationStats(ArrayList<SimulationResult> results) {
        this.simulationResults = results;
        toDescriptiveStat();
    }

    private SimulationStats statsFromResultList(ArrayList<SimulationResult> resultList) {
        return this;
    }

    public ArrayList<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(ArrayList<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    public SimulationStats addSimulationResult(SimulationResult result) {
        this.simulationResults.add(result);
        return this;
    }

    private void toDescriptiveStat() {

        simulationResults.stream().map(SimulationResult::getNbConsecutiveDrop).collect(ArrayList::new, List::addAll,
                List::addAll).stream().forEach(n -> nbConsecutiveDrop.addValue(1.0 * (Integer) n));
        simulationResults.stream().map(SimulationResult::getNbTxGeneration).forEach(n -> nbTxGeneration.addValue(1.0
                * n));
        simulationResults.stream().map(SimulationResult::getNbRxUniquePhysdu).forEach(n -> nbRxUniquePhysdu.addValue
                (1.0 * n));
        simulationResults.stream().map(SimulationResult::getPhysduTxDurationMs).forEach(n -> physduTxDurationMs
                .addValue(1.0 * n));
        simulationResults.stream().map(SimulationResult::getGoodputBps).forEach(n -> goodputBps.addValue(1.0 * n));
        simulationResults.stream().map(SimulationResult::getNbRxPhysdu).forEach(n -> nbRxPhysdu.addValue(1.0 * n));
        simulationResults.stream().map(SimulationResult::getNbInterFgError).forEach(n -> nbInterFgError.addValue(1.0
                * n));
        simulationResults.stream().map(SimulationResult::getNbIntraFError).forEach(n -> nbIntraFError.addValue(1.0 *
                n));
        simulationResults.stream().map(SimulationResult::getThrougputBps).forEach((n -> througputBps.addValue(1.0 *
                n)));
        simulationResults.stream().map(SimulationResult::getDuration100Us).forEach((n -> duration100Us.addValue(1.0 *
                n)));
        simulationResults.stream().map(SimulationResult::getNbTxPhysdu).forEach((n -> nbTxPhysdu.addValue(1.0 * n)));
    }

    @Override
    public String toString() {
        return "SimulationStats{" + "physduTxDurationMs=" + physduTxDurationMs + ", nbConsecutiveDrop=" +
                nbConsecutiveDrop + ", nbTxGeneration=" + nbTxGeneration + ", nbTxPhysdu=" + nbTxPhysdu + ", " +
                "nbRxUniquePhysdu=" + nbRxUniquePhysdu + ", nbRxPhysdu=" + nbRxPhysdu + ", nbInterFgError=" +
                nbInterFgError + ", nbIntraFError=" + nbIntraFError + ", duration100Us=" + duration100Us + ", " +
                "goodputBps=" + goodputBps + ", througputBps=" + througputBps + '}';
    }

    public String toMeanString() {
        return "SimulationStats{" + "physduTxDurationMs=" + physduTxDurationMs.getMean() + ", nbConsecutiveDrop=" +
                nbConsecutiveDrop.getMean() + ", nbTxGeneration=" + nbTxGeneration.getMean() + ", nbTxPhysdu=" +
                nbTxPhysdu.getMean() + ", nbRxUniquePhysdu=" + nbRxUniquePhysdu.getMean() + ", nbRxPhysdu=" +
                nbRxPhysdu.getMean() + ", nbInterFgError=" + nbInterFgError.getMean() + ", nbIntraFError=" +
                nbIntraFError.getMean() + ", duration100Us=" + duration100Us.getMean() + ", goodputBps=" + goodputBps
                .getMean() + ", througputBps=" + througputBps.getMean() + '}';
    }
}
