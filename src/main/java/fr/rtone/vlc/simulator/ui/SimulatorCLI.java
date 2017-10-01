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
package fr.rtone.vlc.simulator.ui;

import fr.rtone.vlc.simulator.scenario.Scenario;
import fr.rtone.vlc.simulator.strategy.AbstractStrategy;
import fr.rtone.vlc.simulator.topology.Led;
import fr.rtone.vlc.simulator.utils.SimulationParameters;
import org.apache.commons.cli.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.exit;

public class SimulatorCLI {

    private final static String PATH = "./";
    private static Options options = new Options();

    public static void main(final String[] args) throws ParseException {

        addOption(options);
        final CommandLineParser parser = new DefaultParser();
        final CommandLine firstLine = parser.parse(options, args, true);
        boolean helpMode = firstLine.hasOption("help");
        if (helpMode) {
            showHelp(options);
            exit(0);
        }

        try {
            final CommandLine line = parser.parse(options, args);
            SimulationParameters simuParams = parseOptions(line);
            try {
                Class scenarioClass = Class.forName("fr.rtone.vlc.simulator.scenario." + simuParams
                        .getSimuScenario());
                Scenario scenario = (Scenario) scenarioClass.getConstructor
                        (SimulationParameters.class).newInstance
                        (simuParams);
                scenario.runSimulation();
            } catch (ClassNotFoundException e) {
                System.out.println("Scenario implementation not found: " + e.getMessage());
                showHelp(options);
                exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            System.out.println("Unexpected exception:" + e.getMessage());
            showHelp(options);
            exit(1);
        }
    }

    private static void showHelp(Options options) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LED2Cam Simulator", options, true);
    }

    private static SimulationParameters parseOptions(CommandLine line) {
        SimulationParameters simuParams = new SimulationParameters();
        try {
            // parse the command line arguments
            simuParams.setChanDistanceCm(Integer.parseInt(line.getOptionValue("d", "-1")));
            simuParams.setSimuScenario(line.getOptionValue("sc", "TestScenario"));
            simuParams.setSimuStopCondition(line.getOptionValue("X", "50000X"));
            simuParams.setCamFrameIntervalMs(Float.parseFloat(line.getOptionValue("f", "33.3")));
            simuParams.setCamIFGRatio(Float.parseFloat(line.getOptionValue("dg", "0.2")));
            simuParams.setLedTxFreqHz(Integer.parseInt(line.getOptionValue("F", "8000")));
            simuParams.setLedRll(Led.Rll.valueOf(line.getOptionValue("rll", "RLL_MANCHESTER")));
            simuParams.setSimuNbIteration(Integer.parseInt(line.getOptionValue("i", "1")));
            simuParams.setPhysduPayloadLen(Integer.parseInt(line.getOptionValue("P", "16")));
            simuParams.setSimuGenerationSizeByte(Integer.parseInt(line.getOptionValue("G", "50")));
            simuParams.setPhysduHeaderLen(Integer.parseInt(line.getOptionValue("H", "8")));
            simuParams.setSimuNbRepeatPhysdu(Integer.parseInt(line.getOptionValue("r", "1")));
            simuParams.setVerbose(Integer.parseInt(line.getOptionValue("v", "1")));
            simuParams.setCamPDecodeError(Double.parseDouble(line.getOptionValue("e", "0.001")));
            simuParams.setSimuStrategy(AbstractStrategy.Strategy.valueOf(line.getOptionValue("S",
                    "REPEAT_PACKET_STRATEGY")));
            simuParams.setSaveResult(line.hasOption("s"));
            String stringPath = line.getOptionValue("file", PATH);
            try {
                Path path = Paths.get(stringPath);
                if (!Files.isDirectory(path) | !Files.isWritable(path)) {
                    System.err.println("Invalid path. Use default.");
                    stringPath = PATH;
                }
            } catch (Exception e) {
                System.out.println("Invalid path. Use default.");
                stringPath = PATH;
            }
            simuParams.setSavePath(stringPath);

            System.out.println(simuParams.toString());
        } catch (ClassCastException exp) {
            System.err.println("Invalid parameter" + exp.getMessage());
            exit(0);
        }
        return simuParams;
    }

    private static void addOption(Options options) {
        options.addOption("G", "generation", true, "The generation size in bytes.");
        options.addOption("S", "strategy", true, "The broadcast strategy. It can be RS, RP or RLC.");
        options.addOption("sc", "scenario", true, "The scenario implementation class name.");
        options.addOption("X", "stop", true, "The simulation stop condition.");
        options.addOption("r", "nb-repeat", true, "The number of PHYSDU repetition.");
        options.addOption("i", "nb-iteration", true, "The number of simulation run.");
        options.addOption("f", "frame-interval", true, "The frame period in ms.");
        options.addOption("dg", "ifg-ration", true, "The camera IFG ratio.");
        options.addOption("e", "perror", true, "The decoder PER.");
        options.addOption("d", "distance", true, "The distance between Tx and Rx.");
        //options.addOption("p", "physdu", true, "The PHYSDU max data (include payload+header) len.");
        options.addOption("P", "payload", true, "The PHYSDU data payload len.");
        options.addOption("H", "header", true, "The PHYSDU data header len.");
        options.addOption("F", "frequency", true, "The LED Tx clock rate.");
        options.addOption("rll", "rll-code", true, "The RLL Code (Manchester or 4B6B).");
        options.addOption("s", "save", false, "Save the result.");
        options.addOption("file", "file-path", true, "Path of the log file.");
        options.addOption("h", "help", false, "Show this help.");
        options.addOption("v", "verbose", true, "Verbbosity level (0=>No output, 5=>TRACE.");
    }
}
