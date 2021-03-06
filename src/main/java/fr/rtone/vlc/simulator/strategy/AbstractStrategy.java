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
package fr.rtone.vlc.simulator.strategy;

import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Physdu;

public interface AbstractStrategy {
    Physdu nextPhysdu();

    Message<Physdu> getNetworkData();

    Message<Physdu> updateInitData(Message<Physdu> initData);

    Message<Physdu> init(Message<Physdu> payloadToSend);

    enum Strategy {
        PRLC_STRATEGY("PRLC_STRATEGY"), REPEAT_PACKET_STRATEGY("REPEAT_PACKET_STRATEGY"), REPEAT_SEQUENCE_STRAEGY
                ("REPEAT_SEQUENCE_STRAEGY");

        private final String name;

        Strategy(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }
}
