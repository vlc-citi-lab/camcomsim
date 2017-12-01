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

/**
 *
 */
public class Packet extends PhysduPayload {

    public Packet(byte[] header, int headerLenBits, byte[] payload, int payloadLenBits) {
        super(header, headerLenBits, payload, payloadLenBits);
        int payloadLen = payloadLenBits;
        int headerLen = headerLenBits;
        this.header = header;
        this.payload = payload;
        this.headerMaxLengthBits = headerLen;
        this.payloadMaxLengthBits = payloadLen;
        this.physduPayloadType = PhysduPayloadType.PACKET;
    }

}
