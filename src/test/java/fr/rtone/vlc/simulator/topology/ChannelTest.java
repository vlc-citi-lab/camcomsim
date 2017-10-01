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

import fr.rtone.vlc.simulator.SimulationParameters;
import fr.rtone.vlc.simulator.scenario.RsScenario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Channel Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>août 24, 2017</pre>
 */
public class ChannelTest {

    private Channel channel;
    private RsScenario rsScenario;
    private SimulationParameters simuParams;

    @Before
    public void before() throws Exception {
        simuParams = new SimulationParameters();
        Camera cam = new Camera(33.3f, 0.28f, 0d);
        channel = new Channel(0, cam);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getNextErrorProbability(float physduDuration100Us)
     */
    @Test
    public void testGetNextErrorProbability() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getChannel()
     */
    @Test
    public void testGetChannel() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getRoiFromDistance(double distance, double focalDistance, double sensorSize, double ledSize)
     */
    @Test
    public void testGetRoiFromDistance() throws Exception {
        for (int i = 0; i < 10; i++) {
            //assertThat()
        }
    }


    /**
     * Method: buildChannel()
     */
    @Test
    public void testBuildChannel() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = Channel.getClass().getMethod("buildChannel"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    @Test
    public void testGetRoiFromExpDistance() {
        double dis5 = channel.getRoiFromExpDistance(0);
        assertEquals(dis5, 1, 0);
        dis5 = channel.getRoiFromExpDistance(10);
        assertEquals(dis5, 0.7243694707506039, 0);
    }

} 
