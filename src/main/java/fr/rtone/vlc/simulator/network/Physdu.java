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

import fr.rtone.vlc.simulator.topology.Led.Rll;

/**
 *
 */
public class Physdu {
    int physduSF = 4;
    int physduMaxLenBits;
    PhysduPayload physduPayload;

    private Physdu() {
    }

    public Physdu(PhysduPayload payload, int maxLen) {
        this.physduPayload = payload;
        this.physduMaxLenBits = maxLen;
    }

    public boolean isPhysduValid() {
        return (physduPayload != null && physduMaxLenBits <= physduPayload.getPhysduPayloadLengthBits());
    }

    public int getPhysduMaxLenBits() {
        return physduMaxLenBits;
    }

    public double getDurationMs(int txFreq, Rll rll) {
        float nbBits = physduSF + rll.getCodeLen() * physduPayload.getPhysduPayloadLengthBits();
        return 1000 * (nbBits / txFreq);
    }

    public double getDuration100Us(int txFreq, Rll rll) {
        return getDurationMs(txFreq, rll) * 10;
    }

    public PhysduPayload getPhysduPayload() {
        return physduPayload;
    }

    @Override
    public String toString() {
        return "PACKET={ id:" + hashCode() + ", maxLen: " + physduMaxLenBits + ", payload: " + physduPayload.toString
                () + " }";
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Physdu) {
            Physdu ptr = (Physdu) v;
            retVal = (ptr.getPhysduMaxLenBits() == this.physduMaxLenBits && ptr.getPhysduPayload() == this
                    .physduPayload);
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 15 * hash + (this.physduMaxLenBits + this.physduPayload.hashCode());
        return hash;
    }
}
