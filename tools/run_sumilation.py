from subprocess import *


def jarWrapper(*args):
    process = Popen(['java', '-jar']+list(args), stdout=PIPE, stderr=PIPE)
    ret = []
    while process.poll() is None:
        line = process.stdout.readline()
        line = line.decode("utf-8")
        if line != '' and line.endswith('\n'):
            ret.append(line[:-1])
    stdout, stderr = process.communicate()
    ret += stdout.decode('utf-8').split('\n')
    return ret


NB_ITERATION = 30;
RP = [1, 2, 3, 4, 5, 7, 10]
D = [0, 5, 10, 15, 20, 25, 30, 35]
P = [8, 12, 16, 20, 24, 32, 64]
H = [0, 4, 8]
F = [6000, 8000, 10000]
G = [50, 100, 200, 1000]
IFG = 0.1
OUTPUT = './simu_result/'

#Run the simulation 270817
for g in G:
    print('processing G={}'.format(g))
    for f in F:
        print('  processing F={}'.format(f))
        for h in H:
            print('    processing H={}'.format(h))
            for p in P:
                print('      processing P={}'.format(p))
                for rp in RP:
                    for d in D:
                        args = ['simulator-1.0-SNAPSHOT.jar', '-v0', '-s', '-file={}'.format(OUTPUT),
                                '-df={}'.format(IFG), '-i{}'.format(NB_ITERATION), '-r{}'.format(rp), '-P{}'.format(p),
                                '-H{}'.format(h), '-d{}'.format(d), '-F{}'.format(f), '-G{}'.format(g),
                                '-e0.0001']
                        result = jarWrapper(*args)
