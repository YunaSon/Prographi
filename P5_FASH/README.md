# FASH_Prographi5

## FASH 딥러닝 기반 옷장 정리, 코디 추천 앱
<img src="https://github.com/YunaSon/FASH_Prographi5/blob/master/fash_logo.png" width = 20%></img>


- 기술 스택: Pytorch, Django, ios 

## app dowonload link
https://apps.apple.com/kr/app/fash/id1492692916


## 제품 포스터
http://prography.org/product


## 딥러닝 기술 설명 
### Multi Labeling Classication 

### Requirements

- Python
- PyTorch


### Model
- Basic CNN
- MobileNetV2 + Transferlearning


### Problem
|파일이름|color|style|part|season|category
|------|---|---|---|---|---|
|sleeveless_0039|black|casual|top|summer|sleeveless|
|sweater_0022|navy|modern|top|summer|sleeveless|


**즉, Multi Labeling 이면서 다중 분류 문제"**

- category = ['t_shirt_long','t_shirt_short','dress_shirt_long','dress_shirt_short',
 'blouse_long','blouse_short','others_shirt','mtm','sweater','hoody','vest','sleeveless','hightop',
 'jean','slacks','jogger','leggings','skirt',
 'long_padding','short_padding','coat','trench_coat','blazer','fleece_jacket','coach_jacket',
 'leather_jacket','hoody_jacket','cardigan','jumper','dress',
 'sneakers','running_shoes','sandal','loafer','walker','short_boots','long_boots','hill','flat_shoes'] #39

- style = ['casual','dandy','formal/office','lovely','luxury','modern','purity','sexy','sporty','street','vintage']
#11

- season = ['All','spring/fall','summer','winter'] #4


- color = ['beige','black','blue','brown','dark_beige','gray','green','khaki','navy','orange','pink',
'purple','red','sky','white','yellow','yellow_green'] #17

- part = ['bottom','onepiece','outer','shoes','top'] #5


### Input data

    └── Dataset
        └── fashion
            └── train
                └── blazer
                    └── xxx.jpg 
                └── blouse_long    
                    └── xxx.jpg 
            └── val
                └── blazer
                    └── xxx.jpg 
                └── blouse_long    
                    └── xxx.jpg 
            └── test
                └── blazer
                    └── xxx.jpg 
                └── blouse_long    
                    └── xxx.jpg
