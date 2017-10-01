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

import fr.rtone.vlc.simulator.strategy.RepeatStrategy;
import fr.rtone.vlc.simulator.topology.Led;
import fr.rtone.vlc.simulator.utils.SimulationParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * SimulationParameters Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>juil. 4, 2017</pre>
 */
public class SimulationParametersTest {

    SimulationParameters simuParamsExpected;

    @Before
    public void before() throws Exception {
        simuParamsExpected = new SimulationParameters();
        simuParamsExpected.setCamFrameIntervalMs(33.3f);
        simuParamsExpected.setCamIFGRatio(0.2f);
        simuParamsExpected.setLedTxFreqHz(8000);
        simuParamsExpected.setLedRll(Led.Rll.RLL_MANCHESTER);
        simuParamsExpected.setSimuNbIteration(1);
        simuParamsExpected.setPhysduPayloadLen(16);
        simuParamsExpected.setSimuGenerationSizeByte(50);
        simuParamsExpected.setPhysduHeaderLen(8);
        simuParamsExpected.setChanDistanceCm(0);
        simuParamsExpected.setSimuNbRepeatPhysdu(1);
        simuParamsExpected.setCamPDecodeError(0.01d);
        simuParamsExpected.setSimuStrategy(RepeatStrategy.Strategy.REPEAT_PACKET_STRATEGY);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: fromFile(String file)
     */
    @Test
    public void testFromFile() throws Exception {
        SimulationParameters simuParams = SimulationParameters.fromFile("./simu_params_template.json");
        assertNotNull(simuParams);
        assertEquals(simuParamsExpected, simuParams);
    }


} 
