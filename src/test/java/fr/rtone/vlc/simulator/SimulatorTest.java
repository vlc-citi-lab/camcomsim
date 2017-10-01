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

import fr.rtone.vlc.simulator.scenario.RsScenario;
import fr.rtone.vlc.simulator.scenario.Scenario;
import fr.rtone.vlc.simulator.utils.SimulationParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by alexisduque on 10/07/2017.
 */
public class SimulatorTest {

    SimulationParameters simuParams;
    Scenario rsScenario;

    @Before
    public void before() throws Exception {
        simuParams = new SimulationParameters();
        simuParams.setChanDistanceCm(0);
        simuParams.setPhysduHeaderLen(4);
        simuParams.setPhysduPayloadLen(24);
        simuParams.setLedTxFreqHz(8000);
        simuParams.setSimuGenerationSizeByte(500);
        simuParams.setCamIFGRatio(0.2f);
        simuParams.setVerbose(1);
        simuParams.setSimuNbIteration(1);
        rsScenario = new RsScenario(simuParams);
    }

    @After
    public void after() throws Exception {
    }


    @Test
    @Ignore
    public void testRsScenario() throws Exception {
        rsScenario.runSimulation();
    }


}
