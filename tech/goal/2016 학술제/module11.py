

## success!

import os
import numpy as np
import cv2
from matplotlib import pyplot as plt
import sys

cvcam = cv2.VideoCapture(0)
## camera 켜기
o = os.getcwd() + "/test_module/"
## 현재 디렉토리를 작업디렉토리로서~ 외부 파일 가져오기

point = 1
## point 정수형 선언

fist_detect=False
winNm = "jj_project_v.01"
endApp = False

fist_cascade = cv2.CascadeClassifier(o+"data/xml/hand.xml")
face_cascade = cv2.CascadeClassifier(o+"data/xml/haarcascade_frontalface_alt.xml")

info = ''
img2 = cv2.imread(o+"data/img/JJlogo.jpg")
img3 = cv2.imread(o+"data/img/aritem/cat/jj0001.jpg")

termin = (cv2.TERM_CRITERIA_EPS | cv2.TERM_CRITERIA_COUNT, 10, 0.03)
featureP = dict( maxCorners=500, qualityLevel=0.01, minDistance=7, blockSize=5)
lk_param = dict( winSize=(15,15), maxLevel=2, criteria=termin)
font = cv2.FONT_HERSHEY_SIMPLEX


def stage_main(cvcam):
    global point
    point = 0
    ## main

    while(True):

        img = cv2.imread(o+"data/img/stage/main_stage.jpg")
        cv2.imshow(winNm, img)

        k = cv2.waitKey(30)
        if k == ord('e'):
            stage_optical(cvcam)
            ##결과창
        if k == ord('p'):
            stage_picture(cvcam)
        if k == ord('i'):
            stage_intro()
        if k == ord('j'):
            stage_made()
        if k == ord('q'):
            endApp = True
            break;


def stage_made():
    ## 만든이 스테이지

    while(True):
        k = cv2.waitKey(30)
        if k == ord('b'):
            break;

        img = cv2.imread(o+"data/img/stage/made_stage.jpg")
        cv2.imshow(winNm, img)


def stage_intro():

    ##인트로 스테이지

    while(True):
        k = cv2.waitKey(30)
        if k == ord('i'):
            break;
        if k == ord('q'):
            endApp = True
            break;

        img = cv2.imread(o+"data/img/stage/intro_stage.jpg")
        cv2.imshow(winNm, img)



def stage_picture(cvcam):
    
    ## 스노우 같은 얼굴인식 이미지 덮어 씌우기 - 캡쳐는 아직 미구현
    while (True):
        ret, frame = cvcam.read()

        if not frame is None:
            adPicture(10, 10, frame)

        k = cv2.waitKey(30)
        if k == ord('j'):
            ## 표준값으로 되돌리기
            a=0;
        if k == ord('a'):
            img3 = cv2.imread(o+"data/img/aritem/cat/jj0001.jpg")
        if k == ord('s'):
            img3 = cv2.imread(o+"data/sona.jpg")
        if k == ord('w'):
            img3 = cv2.imread(o+"data/witch.jpg")
        if k == ord('q'):
            break;



def stage_optical(cvcam):

    ## 광학 흐름(영상학) + haar(Knn머신러닝) 인식을 통한 미니게임

    while (cvcam.isOpened()):
        ret, frame = cvcam.read()

        if not frame is None:
            drawItem(10, 10, frame)

        k = cv2.waitKey(30)
        if k == ord('j'):
            a=0
            ## 이 점수로 종료! 부분.
        if k == ord('q'):
            break;

##collision = detectCollision(x, y , width , height , x2, y2 , width2 , height2)

def addPoint():
    global point 
    
    point = point + 1


def detectCollision(x1,y1,w1,h1,x2,y2,w2,h2):

	if (x2+w2>=x1>x2 and y2+h2>=y1>=y2):
		return True
	elif (x2+w2>=x1+w1>x2 and y2+h2>=y1>=y2) :
		return True
	elif (x2+w2>=x1>=x2 and y2+h2>=y1+h1>=y2) :
		return True
	elif (x2+w2>=x1+w1>=x2 and y2+h2>=y1+h1>=y2) :
		return True
	else:
		return False
		

def adPicture(vpos, hpos, img1):

    ## 스노우 같은 증강현실 이미지 - CyberLink 웹캠으로 실행시 문제 없음.

    rows, cols, channels = img2.shape
    roi = img1[vpos:rows+vpos, hpos:cols+hpos]

    img2gray = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
    ret, mask = cv2.threshold(img2gray, 10, 255, cv2.THRESH_BINARY)
    mask_inv = cv2.bitwise_not(mask)
    img1_bg = cv2.bitwise_and(roi, roi, mask = mask_inv)
    img2_bg = cv2.bitwise_and(img2, img2, mask = mask)
    dst = cv2.add(img1_bg, img2_bg)
    img1[vpos:rows+vpos, hpos:cols+hpos] = dst

    faces = face_cascade.detectMultiScale(img1, 1.3, 5)

    for(x,y,w,h) in faces:

        rows, cols, channels = img3.shape
        cv2.rectangle(img1, (x,y), (x+w, y+h), (255,0,0),2)
        
        a = float(w)/float(rows)
        b = float(h)/float(cols)

        print "a = %f, b = %f, w = %d, h = %d, rows=%d, cols=%d" %(x,y,w,h,rows,cols)

        if (x+rows)>500 or (y+cols)>500:
            break;

        img4 = cv2.resize(img3, None, fx=a,fy=b, interpolation = cv2.INTER_AREA)
        rows, cols, channels = img4.shape
        print "a = %f, b = %f, w = %d, h = %d, rows=%d, cols=%d" %(x,y,w,h,rows,cols)
        roi = img1[x:x+rows, y:y+cols]
        img3gray = cv2.cvtColor(img4, cv2.COLOR_BGR2GRAY)
        ret, mask = cv2.threshold(img3gray, 10, 255, cv2.THRESH_BINARY)
        
        try:
            mask_inv = cv2.bitwise_not(mask)
            img1_bg = cv2.bitwise_and(roi, roi, mask = mask_inv)
            img3_bg = cv2.bitwise_and(img4, img4, mask = mask)
            dst = cv2.add(img1_bg, img3_bg)
        
            img1[x:x+rows, y:y+cols] = dst
        except:
            break;

    cv2.imshow(winNm, img1)


def denseOptFlow(frame, px,py,pw,ph):

    ## 광학 흐름을 이용한 랜덤 출력

    global point

    color = (0, 255, 0)

    olditem = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    itemP1 = cv2.goodFeaturesToTrack(olditem, mask=None, **featureP)

    fitem = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    itemP2, st, err = cv2.calcOpticalFlowPyrLK(olditem, fitem, itemP1, None, **lk_param)
    
    if itemP1 is not None and itemP2 is not None:
        goodNew = itemP2[st == 1]
        goodOld = itemP1[st == 1]


    for i, (new, old) in enumerate(zip(goodNew, goodOld)):
        
        ftext = ''
        a, b = new.ravel()
        c, d = new.ravel()

        if(detectCollision(px,py,pw,ph,a,b,1,1)):
            addPoint()
        else:
            ftext = "p-(%d, %d)" %(a,b)
            cv2.putText(frame, ftext, (a, b), font, 0.5, (255,255,0), 2)
            cv2.circle(frame, (a,b), 3, color, -1)
            

        ftext = "Point : %d " %(point)
        cv2.putText(frame, ftext, (330, 120), font, 0.5, (128,128,0), 2)
        
    olditem = fitem.copy()
    itemP1 = goodNew.reshape(-1, 1, 2)

    cv2.imshow(winNm, frame)


def drawItem(vpos, hpos, img1):

    ## 인식한 아이템(여기서는 손) 인식하고 출력

    rows, cols, channels = img2.shape
    roi = img1[vpos:rows+vpos, hpos:cols+hpos]

    img2gray = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
    ret, mask = cv2.threshold(img2gray, 10, 255, cv2.THRESH_BINARY)
    mask_inv = cv2.bitwise_not(mask)
    img1_bg = cv2.bitwise_and(roi, roi, mask = mask_inv)
    img2_bg = cv2.bitwise_and(img2, img2, mask = mask)
    dst = cv2.add(img1_bg, img2_bg)
    img1[vpos:rows+vpos, hpos:cols+hpos] = dst

    if fist_detect:
        info = "Found fist! You can broken brick!"
    else:
        info = "Disapear fist! Your fist use this game!"

    fists = fist_cascade.detectMultiScale(img1, 1.3, 5)
    cv2.putText(img1, info, (5,15), font, 0.5, (255,0,255), 1)

    px = py = 0
    pw = ph = 0

    for(x,y,w,h) in fists:
        cv2.rectangle(img1, (x,y), (x+w, y+h), (255,0,0),2)
        cv2.putText(img1, "Go!", (x-5, y-5), font, 0.5, (255,255,0), 2)
        px = x
        py = y
        pw = w
        ph = h
    
    denseOptFlow(img1, px,py,pw,ph)



if __name__ == '__main__':

    ## 여기가 시작점.

    print o
    
    while (True):
        if (cvcam.isOpened()):
            break;
        

    stage_intro()
    stage_main(cvcam)
    
    cvcam.release()
    cv2.destroyAllWindows()
    pass

