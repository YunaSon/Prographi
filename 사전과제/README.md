# prography_5th_dl

tensorflow의 머신러닝 모듈 라이브러리인 Tensorflow Hub를 사용하였고, Image Module은 SSD기반의 MobileNetV2를 사용해서 구현 하였습니다. 

## 공식 링크
- MachineLearningModuleLibrary : Tensorflow Hub (https://www.tensorflow.org/hub)
- Image Module: (SSD 기반의) MobileNetV2 (https://tfhub.dev/google/openimages_v4/ssd/mobilenet_v2/1)

# Environment
- Python 3.x
- tensorflow 1.14.0
- tensorflow_hub 0.6.0

## CNN Model(Image Module)
- MobileNetV2

## Object Detection
- SSD(Single Shot Detector)

## 결과이미지 
<img width="631" alt="result_img" src="https://user-images.githubusercontent.com/39859458/64210426-2b897100-cede-11e9-9eb5-3f261cb600f6.png">


## 결과 설명 및 한계.
Tensorflow Hub는 텐서플로우의 머신러닝 라이브러리중 하나로 pre-train된 ML모듈입니다. 즉, 보내주신 데이터로 학습을 하는 것이 아니라 모듈에 내장된(pre-train된) 것으로 물체를 인식 하는 것이 가능합니다. 물론, 필요에 따라 Convolution Layer를 추가하는 등의 방법으로 신경망 모델을 최적화 해야 합니다. 저 역시 Numpy를 이용해 이미지를 reshaping하고 벡터화(on-hot등의 단순화)시키려고 data 전처리 작업을 계속 시도 하였으나, 구현에는 실패 하였습니다. 
그래서 위의 결과 이미지와 같이 Tensorflow Hub를 만을 이용하여 Object Detection을 구현 하였고 그 결과, 소주를 Bottle이나 Drink로 감지하였습니다. 

## 아쉬운 점 
제가 추가하려던 방식은 two-stage detector 방식인 Faster R-CNN을 사용하여 이미지를 training시키고 test와 validation을 하고 싶었습니다만, 데이터처리와 Layer에 계속 실패하여 너무 아쉬었습니다. 


## 결과 Source code
https://github.com/YunaSon/TIL/blob/master/Archive_2019/prography_5th_dl/prography_deep.ipynb
