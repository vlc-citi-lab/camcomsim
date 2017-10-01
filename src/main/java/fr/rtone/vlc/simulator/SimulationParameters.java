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
package fr.rtone.vlc.simulator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import fr.rtone.vlc.simulator.strategy.AbstractStrategy;
import fr.rtone.vlc.simulator.topology.Led;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimulationParameters {

    /**
     * the number of bytes to send, the message size
     */
    @Expose
    private int simuGenerationSizeByte;
    @Expose
    private String simuScenario;
    @Expose
    private AbstractStrategy.Strategy simuStrategy;
    @Expose
    private int simuNbRepeatPhysdu;
    @Expose
    private int simuNbIteration;
    @Expose
    private float camFrameIntervalMs;
    @Expose
    private float camIFGRatio;
    @Expose
    private double camPDecodeError;
    @Expose
    private int chanDistanceCm;
    @Expose
    private int physduMaxLen;
    @Expose
    private int physduPayloadLen;
    @Expose
    private int physduHeaderLen;
    @Expose
    private int ledTxFreqHz;
    @Expose
    private Led.Rll ledRll;
    @Expose
    private boolean saveResult;
    @Expose
    private String savePath;
    @Expose
    private int verbose;


    public SimulationParameters() {
        this.saveResult = false;
        this.savePath = "";
        setCamFrameIntervalMs(33.3f);
        setCamIFGRatio(0.2f);
        setLedTxFreqHz(8000);
        setLedRll(Led.Rll.RLL_MANCHESTER);
        setSimuStrategy(AbstractStrategy.Strategy.REPEAT_PACKET_STRATEGY);
        setSimuNbIteration(1);
        setSimuGenerationSizeByte(50);
        setSimuNbRepeatPhysdu(1);
        setCamPDecodeError(0.001d);
        setChanDistanceCm(10);
        setPhysduHeaderLen(8);
        setPhysduPayloadLen(16);
        setVerbose(1);
        setSimuScenario("RsScenario");
    }

    public static SimulationParameters fromFile(String file) {
        Gson gson = new Gson();
        try {
            SimulationParameters simuParams = gson.fromJson(new FileReader(file), SimulationParameters.class);
            return simuParams;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSimuGenerationSizeByte() {
        return simuGenerationSizeByte;
    }

    public void setSimuGenerationSizeByte(int simuGenerationSizeByte) {
        this.simuGenerationSizeByte = simuGenerationSizeByte;
    }

    public AbstractStrategy.Strategy getSimuStrategy() {
        return simuStrategy;
    }

    public void setSimuStrategy(AbstractStrategy.Strategy simuStrategy) {
        this.simuStrategy = simuStrategy;
    }

    public int getSimuNbRepeatPhysdu() {
        return simuNbRepeatPhysdu;
    }

    public void setSimuNbRepeatPhysdu(int simuNbRepeatPhysdu) {
        this.simuNbRepeatPhysdu = simuNbRepeatPhysdu;
    }

    public int getSimuNbIteration() {
        return simuNbIteration;
    }

    public void setSimuNbIteration(int simuNbIteration) {
        this.simuNbIteration = simuNbIteration;
    }

    public float getCamFrameIntervalMs() {
        return camFrameIntervalMs;
    }

    public void setCamFrameIntervalMs(float camFrameIntervalMs) {
        this.camFrameIntervalMs = camFrameIntervalMs;
    }

    public float getCamIFGRatio() {
        return camIFGRatio;
    }

    public void setCamIFGRatio(float camIFGRatio) {
        this.camIFGRatio = camIFGRatio;
    }

    public double getCamPDecodeError() {
        return camPDecodeError;
    }

    public void setCamPDecodeError(double camPDecodeError) {
        this.camPDecodeError = camPDecodeError;
    }

    public int getChanDistanceCm() {
        return chanDistanceCm;
    }

    public void setChanDistanceCm(int chanDistanceCm) {
        this.chanDistanceCm = chanDistanceCm;
    }

    public int getPhysduMaxLen() {
        return physduMaxLen;
    }

    public void setPhysduMaxLen(int physduMaxLen) {
        this.physduMaxLen = physduMaxLen;
    }

    public int getPhysduPayloadLenBits() {
        return physduPayloadLen;
    }

    public void setPhysduPayloadLen(int physduPayloadLen) {
        this.physduPayloadLen = physduPayloadLen;
    }

    public int getPhysduHeaderLenBits() {
        return physduHeaderLen;
    }

    public void setPhysduHeaderLen(int physduHeaderLen) {
        this.physduHeaderLen = physduHeaderLen;
    }

    public SimulationParameters readFromJson(String jsonString) {
        Gson gson = new GsonBuilder().create();
        SimulationParameters params = gson.fromJson(jsonString, SimulationParameters.class);
        return params;
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String paramsString = gson.toJson(this);
        return paramsString;
    }

    public boolean toJsonFile(String path) {
        LocalDateTime date = LocalDateTime.now();
        String humanDate = date.format(DateTimeFormatter.ofPattern("ddMMyy-HHmmssAA"));
        String fixedPath = path;
        if (path == null || path.isEmpty()) {
            fixedPath = "./";
        }
        fixedPath = fixedPath + "/simu_params_" + humanDate + ".json";
        String content = this.toJsonString();
        try {
            Files.write(Paths.get(fixedPath), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getLedTxFreqHz() {
        return ledTxFreqHz;
    }

    public void setLedTxFreqHz(int ledTxFreqHz) {
        this.ledTxFreqHz = ledTxFreqHz;
    }

    public Led.Rll getLedRll() {
        return ledRll;
    }

    public void setLedRll(Led.Rll ledRll) {
        this.ledRll = ledRll;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof SimulationParameters) {
            SimulationParameters ptr = (SimulationParameters) v;
            retVal = (ptr.getSimuGenerationSizeByte() == this.simuGenerationSizeByte && ptr.getSimuStrategy() == this
                    .simuStrategy && ptr.getSimuNbRepeatPhysdu() == this.simuNbRepeatPhysdu && ptr.getSimuNbIteration
                    () == this.simuNbIteration && ptr.getCamFrameIntervalMs() == this.camFrameIntervalMs && ptr
                    .getCamIFGRatio() == this.camIFGRatio && ptr.getCamPDecodeError() == this.camPDecodeError && ptr
                    .getChanDistanceCm() == this.chanDistanceCm && ptr.getPhysduMaxLen() == this.physduMaxLen && ptr
                    .getPhysduPayloadLenBits() == this.physduPayloadLen && ptr.getPhysduHeaderLenBits() == this
                    .physduHeaderLen && ptr.getLedTxFreqHz() == this.ledTxFreqHz && ptr.getVerbose() == this.verbose
                    && ptr.getLedRll() == this.ledRll);
        }

        return retVal;
    }

    @Override
    public String toString() {
        return "SimulationParameters={ " + "simuGenerationSizeByte: " + simuGenerationSizeByte + ", simuStrategy: " +
                simuStrategy.toString() + ", simuNbRepeatPhysdu: " + simuNbRepeatPhysdu + ", simuNbIteration: " +
                simuNbIteration + ", camFrameIntervalMs: " + camFrameIntervalMs + ", camIFGRatio: " + camIFGRatio +
                ", camPDecodeError: " + camPDecodeError + ", chanDistanceCm: " + chanDistanceCm + ", physduMaxLen: "
                + physduMaxLen + ", physduPayloadLen: " + physduPayloadLen + ", physduHeaderLen: " + physduHeaderLen
                + ", ledTxFreqHz: " + ledTxFreqHz + ", ledRll: " + ledRll.toString() + ", filePath: " + savePath + "," +
                " saveResult: " + String.valueOf(saveResult) + ", verbose:" + verbose + " }";
    }

    public boolean isSaveResult() {
        return saveResult;
    }

    public void setSaveResult(boolean saveResult) {
        this.saveResult = saveResult;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getVerbose() {
        return verbose;
    }

    public void setVerbose(int verbose) {
        this.verbose = verbose;
    }

    public String getSimuScenario() {
        return simuScenario;
    }

    public void setSimuScenario(String simuScenario) {
        this.simuScenario = simuScenario;
    }
}
