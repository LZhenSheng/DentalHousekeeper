# 牙医管家客户端的设计与开发
## 1.主要工作
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/图片1.png"/>
</div>

## 2.意义
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/图片2.png"/>
</div>

## 3.系统层次结构图
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/图片3.png"/>
</div>

## 4.开发环境
### 4.1开发软件
|  类型   | 软件  |
|  ----  | ----  |
| Android端  | Android Studio、玲珑模拟器 |
| Windows端  | Idea、JavaFX Scene Builder |
| 服务端  | Idea、Sqlyog、Postman |
### 4.2处理框架
|  类型   | 软件  |
|  ----  | ----  |
| Windows端界面框架  | JavaFX |
| Dicom处理框架  | DCM4CHE |
| 数字图像化处理-Windows  | OpenCV |
| 数字图像化处理-Android  | GpuImage |
| 标注-Windows  | JavaFX |
| 标注-Android  | Canvas |
| 服务端框架  | SpringBoot+Mybatis |
| 支付  | 支付宝支付 |
| 文件存储  | 阿里云OSS |
| 短信服务  | 阿里云短信服务 |
| 服务器  | 腾讯云 |
| 数据库  | Mysql |
| 图片处理框架-Android  | Glide |
| 视频会议  | 腾讯云实时音视频服务 |

## 5.界面
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/25.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/26.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/1.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/2.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/3.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/4.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/5.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/6.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/7.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/8.png"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/9.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/10.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/11.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/12.jpg"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/13.jpg"/>
</div>

<div  align=center>
<img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/14.jpg" width="400"/><img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/15.jpg" width="400"/>
</div>

<div  align=center>
<img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/16.jpg" width="400"/><img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/17.jpg" width="400"/>
</div>
<div  align=center>
<img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/18.jpg" width="400"/><img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/19.jpg" width="400"/>
</div>
<div  align=center>
<img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/20.jpg" width="400"/><img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/21.png" width="400"/>
</div>
<div  align=center>
<img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/22.png" width="400"/><img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/23.jpg" width="400"/>
</div>
<div  align=center>
 <img src="https://github.com/lijianxing66628/DentalHousekeeper/blob/main/images/24.jpg" width="400"/>
</div>

## 6.尚未实现功能
（1）图像融合

（2）图像分割：数据集比较少，而图像的特征又特别明显，导致使用公开数据集训练的模型在预测时分割效果特别不好
## 7.不足之处
（1）Android和Windows端使用的都是原生UI，界面一般--------Android可以使用XUI和QUMI，Windows端可以使用fx-falsework（发现了问题，但已经是毕设后期，重新改UI不太现实）

（2）DICOM标注结果没有分享功能--------Android可以使用MobSDK分享、保存本地，Windows端可以保存到本地（发现了问题，不过是在答辩结束后）
## 8.缺点
（1）没有看完相关论文---------（知网相关论文很多，没有及时的参考）

[我的博客](https://blog.csdn.net/weixin_44575660?type=blog)
