# CamComSim
CamComSim is a LED-to-camera communication simulator

## Requirements

* Java JRE 8 (run only) or Java JDK 8 (build and run)

## Build

```sh
chmoad a+x ./gradlew
./gradlew build
```

## Usage

### CLI Arguments

```
usage: LED2Cam Simulator [-d <arg>] [-dg <arg>] [-e <arg>] [-f <arg>] [-F
       <arg>] [-file <arg>] [-G <arg>] [-H <arg>] [-h] [-i <arg>] [-P
       <arg>] [-r <arg>] [-rll <arg>] [-S <arg>] [-s] [-sc <arg>] [-v
       <arg>] [-X <arg>]
 -d,--distance <arg>         The distance between Tx and Rx.
 -dg,--ifg-ration <arg>      The camera IFG ratio.
 -e,--perror <arg>           The decoder PER.
 -f,--frame-interval <arg>   The frame period in ms.
 -F,--frequency <arg>        The LED Tx clock rate.
 -file,--file-path <arg>     Path of the log file.
 -G,--generation <arg>       The generation size in bytes.
 -H,--header <arg>           The PHYSDU data header len.
 -h,--help                   Show this help.
 -i,--nb-iteration <arg>     The number of simulation run.
 -P,--payload <arg>          The PHYSDU data payload len.
 -r,--nb-repeat <arg>        The number of PHYSDU repetition.
 -rll,--rll-code <arg>       The RLL Code (Manchester).
 -S,--strategy <arg>         The broadcast strategy
 -s,--save                   Save the result.
 -sc,--scenario <arg>        The scenario implementation class name.
 -v,--verbose <arg>          Verbbosity level (0=>No output, 5=>TRACE.
 -X,--stop <arg>             The simulation stop condition.

```

### Exemple
```sh

java -jar camcomsim-cli*.jar -v1 -i25 -r3 -P19 -H5 -d0 -F8000 -G1000 -e0.00001 -X6000X -sc=TestScenario

```
