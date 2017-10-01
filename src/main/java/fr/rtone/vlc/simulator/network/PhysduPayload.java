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

import fr.rtone.vlc.simulator.utils.DataPresenter;

public class PhysduPayload {
    protected int headerMaxLengthBits;
    protected int payloadMaxLengthBits;
    protected byte[] header;
    protected byte[] payload;
    PhysduPayloadType physduPayloadType;

    private PhysduPayload() {
    }
/*
    protected PhysduPayload(byte[] header, byte[] payload) {
        int payloadLen = payload != null ? payload.length * 8 : 0;
        int headerLen = header != null ? header.length * 8 : 0;
        new PhysduPayload(header, headerLen, payload, payloadLen);
    }*/

    protected PhysduPayload(byte[] header, int headerMaxLengthBits, byte[] paylaod, int payloadMaxLengthBits) {
        this.header = header;
        this.headerMaxLengthBits = headerMaxLengthBits;
        this.payload = paylaod;
        this.payloadMaxLengthBits = payloadMaxLengthBits;
    }

    public byte[] getHeader() {
        return header;
    }

    public String getHumanHeader() {
        return DataPresenter.bytesToHex(header);
    }

    public String getHumanPayload() {
        return DataPresenter.bytesToHex(payload);
    }

    public byte[] getPayload() {
        return payload;
    }

    public int getPhysduPayloadMaxLengthBits() {
        return (headerMaxLengthBits + payloadMaxLengthBits);
    }

    public int getPhysduPayloadLengthBits() {
        boolean isValid = isPhysduValid();
        if (!isValid) {
            return 0;
        }
        return (headerMaxLengthBits + payloadMaxLengthBits);
    }

    public int getPhysduPayloadPayloadLengthBits() {
        boolean isValid = isPhysduValid();
        if (!isValid) {
            return 0;
        }
        return (payloadMaxLengthBits);
    }

    public int getPhysduPayloadHeaderLengthBits() {
        boolean isValid = isPhysduValid();
        if (!isValid) {
            return 0;
        }
        return (headerMaxLengthBits);
    }

    public boolean isPhysduValid() {
        return payload != null;
    }

    public PhysduPayloadType getPhysduPayloadType() {
        return physduPayloadType;
    }

    @Override
    public String toString() {
        return "PACKET={ id:" + hashCode() + ", header: " + getHumanHeader() + ", type: " + physduPayloadType
                .toString() + ", payload: " + getHumanPayload() + " }";
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof PhysduPayload) {
            PhysduPayload ptr = (PhysduPayload) v;
            retVal = (ptr.getHeader() == this.header && ptr.getPayload() == this.payload && ptr.getPhysduPayloadType
                    () == this.physduPayloadType);
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.header.hashCode() + this.payload.hashCode() + this.physduPayloadType.hashCode());
        return hash;
    }

    public enum PhysduPayloadType {
        CHUNK("CHUNK"), PACKET("PACKET"), UNKNOW("UNKNOWN");

        private final String name;

        PhysduPayloadType(String s) {
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
