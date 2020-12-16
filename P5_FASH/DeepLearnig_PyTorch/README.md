# Multi Labeling Classication 

## Requirements
- PyTorch


## Model
- Basic CNN
- MobileNetV2 + Transferlearning


##  
```
class CNN(nn.Module):
    def __init__(self):
        super(CNN, self).__init__()        
        #Layer1
        # CONV
            # input_channel_개수: 3
            # output_channel_개수(필터의 개수): 64
            # kernel_size(필터의 사이즈): 3 X 3
            # padding = 1
        # ReLU
        # MaxPool
            # kernel_size = 2
            # stride = 2
        #    Input           -> (1, 3, 224, 224)
        #    layer1.Conv     -> (1, 64, 224, 224)
        #    layer1.Pool     -> (1, 64, 112, 112)
        self.layer1 = nn.Sequential(
                        nn.Conv2d(3, 64, 3, padding=1),
                        nn.ReLU(),
                        nn.MaxPool2d(kernel_size=2, stride=2))
        #Layer2
        # CONV
            # input_channel_개수: 64
            # output_channel_개수(필터의 개수): 32
            # kernel_size(필터의 사이즈): 3 X 3
            # padding = 1
        # ReLU
        # MaxPool
            # kernel_size = 2
            # stride = 2
        #    Input           -> (2, 64, 112, 112)
        #    layer2.Conv     -> (2, 32, 112, 112)
        #    layer2.Pool     -> (2, 32, 56, 56)
        self.layer2 = nn.Sequential(
                        nn.Conv2d(64, 32,  3, padding=1),
                        nn.ReLU(),
                        nn.MaxPool2d(kernel_size=2, stride=2))
        
        #Layer3
        # CONV
            # input_channel_개수: 32
            # output_channel_개수(필터의 개수): 10
            # kernel_size(필터의 사이즈): 4 X 4
            # padding = 1
        # ReLU
        # MaxPool
            # kernel_size = 2
            # stride = 2
        #    Input           -> (2, 32, 56, 56)
        #    layer2.Conv     -> (2, 10, 55, 55)
        #    layer2.Pool     -> (2, 10, 27, 27)
        self.layer3 = nn.Sequential(
                        nn.Conv2d(32, 10, 4, padding=1),
                        nn.ReLU(),
                        nn.MaxPool2d(kernel_size=2, stride=2, padding=1))
        
        self.layer4 = nn.Sequential(
                        nn.Conv2d(10, 5, 2),
                        nn.Conv2d(5, 1, 2),
                        nn.MaxPool2d(kernel_size=2, padding=1))
        
        # L4 FC 14 * 14 inputs -> 196 outputs
        self.fc1 = torch.nn.Linear(196, 39, bias=True)

    def forward(self, x):
        out = self.layer1(x)
        out = self.layer2(out)
        out = self.layer3(out)
        out = self.layer4(out)
        out = out.view(-1, 196)   # Flatten them for FC
        out = self.fc1(out)
        
        return out

```
