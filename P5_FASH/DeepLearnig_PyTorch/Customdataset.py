from torch.utils.data import Dataset
import os
import numpy as np
import glob

filepath = '/Users/jooyoungson/Datasets/c_dataset_v2.1_col3'

class ImgDataSet(Dataset):
    def __init__(self, src=filepath, transforms = None):
        self.src = src
        self.transform = transforms
    def __len__(self):
        pass
    def __getitem__(self,idx):
        pass

