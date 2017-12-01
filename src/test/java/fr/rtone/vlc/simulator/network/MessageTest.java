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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Message Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>juil. 4, 2017</pre>
 */
public class MessageTest {

    Message<Integer> message;

    @Before
    public void before() throws Exception {
        //Add some random persons
        message = new Message<>();
        message.addAll(Arrays.asList(1, 2, 3, 3, 3, 6));
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUnique()
     */
    @Test
    public void testGetUnique() throws Exception {
        Message<Integer> expected = new Message<>();
        expected.addAll(Arrays.asList(1, 2, 3, 6));
        Message<Integer> actual = message.getUnique();
        assertEquals(expected, actual);
    }

    /**
     * Method: countUnique()
     */
    @Test
    public void testCountUnique() throws Exception {
        int expected = 4;
        int actual = message.countUnique();
        assertEquals(expected, actual);
    }

    /**
     * Method: isCompleted(int expected)
     */
    @Test
    public void testIsCompleted() throws Exception {
        boolean expected = false;
        boolean actual = message.isCompleted(10);
        assertEquals(expected, actual);
        expected = true;
        actual = message.isCompleted(4);
        assertEquals(expected, actual);
    }

} 
