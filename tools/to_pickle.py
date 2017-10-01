import json
import pandas as pd
from subprocess import *


# Load json simulation result
def loadSimuResults(files, concat=False):
    FILENAME = 'merged.json'
    simu = pd.DataFrame()
    for file_ in files:
        if file_ != FILENAME:
            with open(file_) as data_file:
                simu_result = json.load(data_file)
                simu_result = pd.io.json.json_normalize(simu_result)
                simu = pd.concat([simu_result, simu.iloc[:, 1:]], axis=0)
    return simu


files = glob.glob('simu_result/*.json')
simu_result = loadSimuResults(files)
simu_result.rename(columns=lambda x: x.replace('.', ''), inplace=True)
simu_result.to_pickle('simu_result/simu.pkl.gz')
