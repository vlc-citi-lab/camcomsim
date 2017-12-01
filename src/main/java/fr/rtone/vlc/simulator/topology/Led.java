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

public class Led {

    private int txFreqHz;
    private Rll rll;

    private Led() {
    }

    public Led(int txFreqHz, Rll rll) {
        this.txFreqHz = txFreqHz;
        this.rll = rll;
    }

    public Rll getRll() {
        return rll;
    }

    public void setRll(Rll rll) {
        this.rll = rll;
    }

    public int getTxFreqHz() {
        return txFreqHz;
    }

    public void setTxFreqHz(int txFreqHz) {
        this.txFreqHz = txFreqHz;
    }

    public enum Rll {
        RLL_MANCHESTER("RLL_MANCHESTER", 2f), RLL_4B6B("RLL_4B6B", 1.5f);

        private final String name;
        private final float codeLen;

        Rll(String s, float codeLen) {
            this.name = s;
            this.codeLen = codeLen;
        }

        public Rll fromString(String value) {
            switch (value) {
                case "RLL_4B6B":
                    return RLL_4B6B;
                case "RLL_MANCHESTER":
                    return RLL_MANCHESTER;
                default:
                    return RLL_MANCHESTER;
            }
        }

        public float getCodeLen() {
            return this.codeLen;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

}
