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

import fr.rtone.vlc.simulator.network.Message;
import fr.rtone.vlc.simulator.network.Packet;
import fr.rtone.vlc.simulator.network.Physdu;
import fr.rtone.vlc.simulator.network.PhysduPayload;
import org.apache.commons.lang3.RandomUtils;

public class GenerationBuilder {
    public static Message<Physdu> buildGeneration(int nbElem, int headerLen, int payloadLen) {
        Message<Physdu> message = new Message<>();
        for (int i = 0; i < nbElem; i++) {
            byte j = (byte) i;
            Physdu physdu = buildRandomPhysdu(headerLen, payloadLen);
            message.add(physdu);
        }
        return message;
    }

    public static Physdu buildRandomPhysdu(int headerLenBits, int payloadLenBits) {
        int headerLenByte = (headerLenBits % 8 == 0) ? Math.floorDiv(headerLenBits, 8) : Math.floorDiv(headerLenBits,
                8) + 1;
        int payloadLenByte = (payloadLenBits % 8 == 0) ? Math.floorDiv(payloadLenBits, 8) : Math.floorDiv
                (payloadLenBits, 8) + 1;
        byte[] header = RandomUtils.nextBytes(headerLenByte);
        byte[] payload = RandomUtils.nextBytes(payloadLenByte);
        PhysduPayload physduPayload = new Packet(header, headerLenBits, payload, payloadLenBits);
        Physdu physdu = new Physdu(physduPayload, physduPayload.getPhysduPayloadMaxLengthBits());
        return physdu;
    }
}
