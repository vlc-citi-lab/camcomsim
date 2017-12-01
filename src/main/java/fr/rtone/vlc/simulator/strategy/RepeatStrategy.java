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

public class RepeatStrategy implements AbstractStrategy {

    public static final Strategy STRATEGY = Strategy.REPEAT_PACKET_STRATEGY;
    public Message<Physdu> message;
    private int nbPhysdu;
    private Message<Physdu> networkData;
    private int nbRepeat;
    private int next;
    private int repeated;


    public RepeatStrategy(int nbRepeat, Message<Physdu> message) {
        this.nbRepeat = nbRepeat;
        this.networkData = message;
        this.nbPhysdu = message.size();
    }

    @Override
    public Physdu nextPhysdu() {
        if (next >= nbPhysdu) {
            next = 0;
        }
        repeated++;
        Physdu nextPhysdu = networkData.get(next);
        if (repeated >= nbRepeat) {
            next++;
            repeated = 0;
        }
        return nextPhysdu;
    }

    @Override
    public Message<Physdu> getNetworkData() {
        return null;
    }

    @Override
    public Message<Physdu> updateInitData(Message<Physdu> initData) {
        this.networkData = initData;
        this.nbPhysdu = initData.size();
        this.next = 0;
        return this.networkData;
    }

    @Override
    public Message<Physdu> init(Message<Physdu> payloadToSend) {
        return null;
    }
}
