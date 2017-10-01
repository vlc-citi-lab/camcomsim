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
package fr.rtone.vlc.simulator.network;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PhysduPayload Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>juil. 2, 2017</pre>
 */
public class PhysduPayloadTest {

    PhysduPayload physduPayload;

    @Before
    public void before() throws Exception {
        byte[] header = RandomUtils.nextBytes(1);
        byte[] payload = RandomUtils.nextBytes(2);
        physduPayload = new Packet(header, 8, payload, 16);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getPhysduPayloadMaxLengthBits()
     */
    @Test
    public void testGetPhysduPayloadMaxLengthBits() throws Exception {
        int expected = 24;
        int actual = physduPayload.getPhysduPayloadMaxLengthBits();
        assertEquals(expected, actual);
    }

    /**
     * Method: getPhysduPayloadLengthBits()
     */
    @Test
    public void testGetPhysduPayloadPayloadLengthBits() throws Exception {
        int expected = 16;
        int actual = physduPayload.getPhysduPayloadPayloadLengthBits();
        assertEquals(expected, actual);
    }

    /**
     * Method: getPhysduPayloadLengthBits()
     */
    @Test
    public void testGetPhysduPayloadLengthBits() throws Exception {
        int expected = 24;
        int actual = physduPayload.getPhysduPayloadLengthBits();
        assertEquals(expected, actual);
    }

    /**
     * Method: getPhysduPayloadLengthBits()
     */
    @Test
    public void testGetPhysduHeaderLengthBits() throws Exception {
        int expected = 8;
        int actual = physduPayload.getPhysduPayloadHeaderLengthBits();
        assertEquals(expected, actual);
    }


}
