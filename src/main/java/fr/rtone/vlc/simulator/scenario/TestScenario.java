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
package fr.rtone.vlc.simulator.scenario;

import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Physdu;
import fr.rtone.vlc.simulator.strategy.RepeatStrategy;
import fr.rtone.vlc.simulator.topology.Camera;
import fr.rtone.vlc.simulator.topology.Channel;
import fr.rtone.vlc.simulator.topology.Led;
import fr.rtone.vlc.simulator.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestScenario implements Scenario {

    private static final int NB_THREADS = 16;
    private ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
    private List<Future<SimulationResult>> resultList = new ArrayList<Future<SimulationResult>>();
    private SimulationParameters simulationParameters = new SimulationParameters();

    public TestScenario() {
        buildDefaultParams();
    }

    public TestScenario(SimulationParameters simulationParameters) {
        this.simulationParameters = simulationParameters;
    }

    private SimulationParameters buildDefaultParams() {
        SimulationParameters simuParams = new SimulationParameters();
        simuParams.setCamFrameIntervalMs(33.3f);
        simuParams.setCamIFGRatio(0.1f);
        simuParams.setLedTxFreqHz(8000);
        simuParams.setLedRll(Led.Rll.RLL_MANCHESTER);
        simuParams.setPhysduPayloadLen(19);
        simuParams.setSimuGenerationSizeByte(50);
        simuParams.setPhysduHeaderLen(5);
        simuParams.setSimuNbRepeatPhysdu(1);
        simuParams.setCamPDecodeError(0.01d);
        simuParams.setSaveResult(false);
        simuParams.setSimuNbIteration(300);
        this.simulationParameters = simuParams;
        return simuParams;
    }

    @Override
    public void runSimulation() {
        runSimulation(this.simulationParameters);
    }

    @Override
    public void runSimulation(SimulationParameters simuParams) {
        if (simuParams.getVerbose() > 0) {
            System.out.println("Simulation with Test Scenario");
        }
        ArrayList<SimulationResult> simuResuls = new ArrayList<>();
        Camera camera = new Camera(simuParams.getCamFrameIntervalMs(), simuParams.getCamIFGRatio(), simuParams
                .getCamPDecodeError());
        Led led = new Led(simuParams.getLedTxFreqHz(), simuParams.getLedRll());
        simuParams.setSimuStrategy(RepeatStrategy.Strategy.REPEAT_PACKET_STRATEGY);
        try {
            for (int i = 0; i < simuParams.getSimuNbIteration(); i++) {
                SimulationBuilder builder = new SimulationBuilder();
                Message<Physdu> datas = GenerationBuilder.buildGeneration(simuParams.getSimuGenerationSizeByte(),
                        simuParams.getPhysduHeaderLenBits(), simuParams.getPhysduPayloadLenBits());
                RepeatStrategy rp5 = new RepeatStrategy(simuParams.getSimuNbRepeatPhysdu(), datas);
                Channel channel = new Channel(simuParams.getChanDistanceCm(), camera);
                Callable<SimulationResult> simu = builder.addCamera(camera).addLed(led).addChannel(channel).setDatas
                        (datas).setSimulationParameters(simuParams).setStrategy(rp5).createCallableSimulation();
                Future<SimulationResult> submit = executor.submit(simu);
                resultList.add(submit);
            }
            for (Future<SimulationResult> result : resultList) {
                SimulationResult simuResult = result.get();
                simuResuls.add(simuResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                System.err.println("Waiting task to finish ...");

                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
                if (simuParams.isSaveResult()) {
                    JsonIO.toJsonFile(simuResuls, "rs_simu", simuParams.getSavePath(), true);
                }
                SimulationStats stats = new SimulationStats(simuResuls);
                if (simuParams.getVerbose() > 0) {
                    System.out.println(stats.toMeanString());
                }
                if (simuParams.getVerbose() > 1) {
                    System.out.println(simuParams.toString());
                }
            } else {
                if (simuParams.isSaveResult()) {
                    JsonIO.toJsonFile(simuResuls, "rs_simu", simuParams.getSavePath(), true);
                }
                SimulationStats stats = new SimulationStats(simuResuls);
                if (simuParams.getVerbose() > 0) {
                    System.out.println(stats.toMeanString());
                }
                if (simuParams.getVerbose() > 1) {
                    System.out.println(simuParams.toString());
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
