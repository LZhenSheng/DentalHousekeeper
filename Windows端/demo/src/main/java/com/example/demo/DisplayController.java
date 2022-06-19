package com.example.demo;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.example.demo.domain.Constant;
import com.example.demo.domain.DICOMImage;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.json.JSONArray;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import java.io.*;
import java.util.*;
import java.util.List;
import javafx.scene.paint.Color;

import static com.example.demo.util.ToastUtil.toast;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.*;

public class DisplayController extends Control {

    Stack<String> back;
    Stack<String> forword;
    Stack<Object> bback;
    Stack<Object> fforword;
    double alpha = 1;
    double contrast = 1;
    String id = null;
    String endpoint = "endpoint";
    String accessKeyId = "accessKeyId";
    String accessKeySecret = "accessKeySecret";
    String bucketName = "bucketName";
    String path = "";
    String jpg = "";
    String jpgg="";
    @FXML
    ImageView imageView;
    @FXML
    TableView<KV> tableView;

    @FXML
    Group root1;
    @FXML
    Group root;

    public void resume(ActionEvent actionEvent) {
        root1.getChildren().clear();
        back=new Stack<>();
        forword=new Stack<>();
        bback=new Stack<>();
        fforword=new Stack<>();
        jpgg=jpg;
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void larger(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat srcImage = imread(jpgg);
        Mat dstImage = srcImage.clone();
        alpha=alpha+0.1;
        dstImage.convertTo(dstImage, dstImage.type(), alpha, 1);
        jpgg=getPath();
        imwrite(jpgg, dstImage);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void smaller(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat srcImage = imread(jpgg);
        Mat dstImage = srcImage.clone();
        alpha=alpha-0.1;
        dstImage.convertTo(dstImage, dstImage.type(), alpha, 1);
        jpgg=getPath();
        imwrite(jpgg, dstImage);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void up(ActionEvent actionEvent){
        back.push(jpgg);
        Mat srcImage = imread(jpgg);
        Mat dstImage = srcImage.clone();
        contrast=contrast+0.1;
        dstImage.convertTo(dstImage, dstImage.type(), 1, contrast);
        jpgg=getPath();
        imwrite(jpgg, dstImage);
        imageView.setImage(new Image("file:"+jpgg));
    }
    public void down(ActionEvent actionEvent){
        back.push(jpgg);
        Mat srcImage = imread(jpgg);
        Mat dstImage = srcImage.clone();
        contrast=contrast-0.1;
        dstImage.convertTo(dstImage, dstImage.type(), 1, contrast);
        jpgg=getPath();
        imwrite(jpgg, dstImage);
        imageView.setImage(new Image("file:"+jpgg));
    }
    @FXML
    private void initialize() throws IOException, UnirestException {
        imageView.setPreserveRatio(true);
        back=new Stack<>();
        forword=new Stack<>();
        bback=new Stack<>();
        fforword=new Stack<>();
        Properties prop = new Properties();
        try {
            if (new File("users.properties").exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream("users.properties"));
                prop.load(in);
                id = prop.getProperty("dicom_image");
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("id===" + id);
        HttpResponse<JsonNode> response = Unirest.post(Constant.uri + "findDicomByDicomId")
                .field("id", id)
                .asJson();
        Gson gson = new Gson();
        JSONArray result = response.getBody().getArray();
        DICOMImage dicomImage = gson.fromJson(result.get(0).toString(), DICOMImage.class);
//        System.out.println(dicomImage.toString());
        path = path + dicomImage.getUri().split("/")[dicomImage.getUri().split("/").length - 1];
        jpgg=jpgg+dicomImage.getJpg().split("/")[dicomImage.getJpg().split("/").length-1];
//        System.out.println((dicomImage.getJpg()));
        imageView.setImage(new Image(dicomImage.getJpg()));
        centerImage();
        down();
    }

    public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;
            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();
            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }

    private void down() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 创建OSSClient实例。
                File file = new File("E:\\Program Files\\DICOM");
                if (!file.exists()) {
                    file.mkdir();
                }
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

                ossClient.getObject(new GetObjectRequest(bucketName, path), new File("E:\\Program Files\\DICOM\\" + path));
                ossClient.getObject(new GetObjectRequest(bucketName, jpgg), new File("E:\\Program Files\\DICOM\\" + jpgg));
                jpg="E:\\Program Files\\DICOM\\"+jpgg;
                jpgg="E:\\Program Files\\DICOM\\"+jpgg;
                ossClient.shutdown();
                try {
                    init("E:\\Program Files\\DICOM\\" + path);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Double pixelSpacing;
    static {
        System.load("C:\\Users\\李振生\\Desktop\\demo\\src\\main\\resources\\lib\\opencv_java455.dll");
    }
    double x,y;
    double lastx=0,lasty=0;
    public void init(String filePath) throws Exception {
        root.addEventFilter(MouseDragEvent.MOUSE_DRAGGED,event->{
            if(flag==6){
                back.push("1");
                if(lastx==0&&lasty==0){
                    Line line = new Line();
                    line.setStroke(Color.RED);
                    line.setStartX(x);
                    line.setStartY(y);
                    line.setEndX(event.getX());
                    line.setEndY(event.getY());
                    root1.getChildren().add(line);
                    bback.push(line);
                }else{
                    Line line = new Line();
                    line.setStroke(Color.RED);
                    line.setStartX(lastx);
                    line.setStartY(lasty);
                    line.setEndX(event.getX());
                    line.setEndY(event.getY());
                    root1.getChildren().add(line);
                    bback.push(line);
                }
                lastx=event.getX();
                lasty=event.getY();
            }
        });
        root.addEventFilter(MouseDragEvent.MOUSE_PRESSED, event->{
            x=event.getX();
            y=event.getY();
        });
        root.addEventFilter(MouseDragEvent.MOUSE_RELEASED,event->{
            if(flag==1){
                back.push("1");
                Line line = new Line();
                line.setStroke(Color.RED);
                line.setStartX(x);
                line.setStartY(y);
                line.setEndX(event.getX());
                line.setEndY(event.getY());
                root1.getChildren().add(line);
                bback.push(line);
            }else if(flag==2){
                back.push("1");
                Text text = new Text(event.getX(),event.getY(),"dsljkf");
                root1.getChildren().add(text);
                bback.push(text);
            }else if(flag==3){
                back.push("1");
                Circle circle = new Circle();
                circle.setCenterX(x);
                circle.setCenterY(y);
                circle.setRadius(Math.sqrt((event.getX()-x)*(event.getX()-x)+(event.getY()-y)*(event.getY()-y)));
                circle.setStroke(Color.RED);
                circle.setFill(null);
                root1.getChildren().add(circle);
                bback.push(circle);
            }else if(flag==4){
                back.push("1");
                Ellipse elipse = new Ellipse();
                elipse.setCenterX(x);
                elipse.setCenterY(y);
                elipse.setRadiusX(event.getX()-x);
                elipse.setRadiusY(event.getY()-y);
                elipse.setStroke(Color.RED);
                elipse.setFill(null);
                root1.getChildren().add(elipse);
                bback.push(elipse);
            }else if(flag==5){
                back.push("1");
                javafx.scene.shape.Rectangle rect=new Rectangle();
                rect.setX(x);
                rect.setY(y);
                rect.setWidth(event.getX()-x);
                rect.setHeight(event.getY()-y);
                rect.setStroke(Color.RED);
                rect.setFill(null);
                root1.getChildren().add(rect);
                bback.push(rect);
            }else if(flag==6){
                lastx=0;
                lasty=0;
            }else if(flag==7){
                back.push("2");
                Line line = new Line();
                line.setStroke(Color.RED);
                line.setStartX(x);
                line.setStartY(y);
                line.setEndX(event.getX());
                line.setEndY(event.getY());
                root1.getChildren().add(line);
                bback.push(line);
                double height=imageView.getImage().getHeight();
                double _x=(event.getX()-x)*height/imageView.getFitHeight();
                double _y=(event.getY()-y)*height/imageView.getFitHeight();
                double distances=Math.sqrt(_x*_x+_y*_y)*pixelSpacing;
                String result=String.format("%.2f",distances);
                Text text = new Text(event.getX()+5,event.getY()+5,result+"mm");
//                System.out.println(imageView.getImage().getWidth()+"--"+imageView.getImage().getHeight());
                text.setStroke(Color.RED);
                text.setLineSpacing(2);
                root1.getChildren().add(text);
                bback.push(text);
            }
        });

        imageView.setImage(new Image("file:"+jpgg));
        DicomInputStream dis = new DicomInputStream(new File(filePath));
        Attributes d = dis.readDataset();
        pixelSpacing=Double.valueOf(d.getString(Tag.PixelSpacing));
//        System.out.println(pixelSpacing+"-------------------");
        ObservableList<KV> data = FXCollections.observableArrayList(
                new KV("PatientID", d.getString(Tag.PatientID)),
                new KV("PatientName", d.getString(Tag.PatientName)),
                new KV("PatientSex", d.getString(Tag.PatientSex)),
                new KV("PatientBirthDate", d.getString(Tag.PatientBirthDate)),
                new KV("PatientAge", String.valueOf(d.getInt(Tag.PatientAge, 0))),
                new KV("PatientWeight", String.valueOf(d.getInt(Tag.PatientName, 0))),
                new KV("PatientAddress", d.getString(Tag.PatientAddress)),
                new KV("StudyID", d.getString(Tag.StudyID)),
                new KV("StudyTime", d.getString(Tag.StudyTime)),
                new KV("StudyDate", d.getString(Tag.StudyDate)),
                new KV("ModalitiesInStudy", d.getString(Tag.ModalitiesInStudy)),
                new KV("StudyDescription", d.getString(Tag.StudyDescription)),
                new KV("SeriesDate", d.getString(Tag.SeriesDate)),
                new KV("SeriesTime", d.getString(Tag.SeriesTime)),
                new KV("SeriesDescription", d.getString(Tag.SeriesDescription)));
        tableView.setEditable(true);

        // 每个Table的列
        TableColumn firstNameCol = new TableColumn("key");
        firstNameCol.setPrefWidth(150);
        firstNameCol.setStyle( "-fx-alignment: CENTER;");
        // 设置分箱的类下面的属性名
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("k"));
        TableColumn lastNameCol = new TableColumn("value");
        lastNameCol.setStyle( "-fx-alignment: CENTER;");
        lastNameCol.setPrefWidth(173);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("v"));
        tableView.setItems(data);
        tableView.getColumns().addAll(firstNameCol, lastNameCol);
    }

    public void back(ActionEvent actionEvent) {
        if(!back.empty()){
            String temp=back.peek();
            forword.push(back.peek());
            back.pop();
            if(temp.equals("1")){
                fforword.push(bback.peek());
                root1.getChildren().remove(bback.peek());
                bback.pop();
            }else if(temp.equals("2")){
                fforword.push(bback.peek());
                root1.getChildren().remove(bback.peek());
                bback.pop();
                fforword.push(bback.peek());
                root1.getChildren().remove(bback.peek());
                bback.pop();
            }else{
                jpgg=temp;
                imageView.setImage(new Image("file:"+jpgg));
            }
        }else{
            toast("回退栈为空");
        }
    }

    public void forward(ActionEvent actionEvent) {
        if(!forword.empty()){
            String temp=forword.peek();
            back.push(forword.peek());
            forword.pop();
            if(temp.equals("1")){
                bback.push(fforword.peek());
                root1.getChildren().add((Node) fforword.peek());
                fforword.pop();
            }else if(temp.equals("2")){
                bback.push(fforword.peek());
                root1.getChildren().add((Node) fforword.peek());
                fforword.pop();
                bback.push(fforword.peek());
                root1.getChildren().add((Node) fforword.peek());
                fforword.pop();
            }else{
                jpgg=temp;
                imageView.setImage(new Image("file:"+jpgg));
            }
        }else{
            toast("前进栈为空");
        }
    }

    public void gaussianFiltering(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat mat = Imgcodecs.imread(jpgg);
        Mat m = new Mat(mat.size(), mat.type());
        Size s = new Size(11, 11);
        int a = 0, b = 0;
        jpgg=getPath();
        Imgproc.GaussianBlur(mat, m, s, a, b);
        Imgcodecs.imwrite(jpgg, m);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void meanFiltering(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat mat = Imgcodecs.imread(jpgg);
        Mat m = new Mat(mat.size(), mat.type());
        Size s = new Size(5, 5);
        Imgproc.blur(mat, m, s);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, m);
    }

    public void medianFiltering(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat mat = Imgcodecs.imread(jpgg);
        Mat m = new Mat(mat.size(), mat.type());
        int i = 3;
        jpgg=getPath();
        Imgproc.medianBlur(mat, m, i);
        Imgcodecs.imwrite(jpgg, m);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void bilateralFiltering(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat mat = Imgcodecs.imread(jpgg);
        Mat m = new Mat(mat.size(), mat.type());
        int i1 = 10;
        int i2 = 200;
        int i3 = 10;
        jpgg=getPath();
        Imgproc.bilateralFilter(mat, m, i1, i2, i3);
        Imgcodecs.imwrite(jpgg, m);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void histogramEqualization(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src=imread(jpgg);
        Mat dst = src.clone();
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2YCrCb);
        List<Mat> list1 = new ArrayList<>();
        Core.split(dst, list1);
        Imgproc.equalizeHist(list1.get(0), list1.get(0));
        Core.normalize(list1.get(0), list1.get(0), 0, 255, Core.NORM_MINMAX);
        Core.merge(list1, dst);
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2BGR);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void restrictedHistogramEqualization(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src=imread(jpgg);
        Mat dst = src.clone();
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2YCrCb);
        List<Mat> list1 = new ArrayList<>();
        Core.split(dst, list1);
        CLAHE clahe = Imgproc.createCLAHE();
        clahe.setClipLimit(4);
        clahe.apply(list1.get(0), list1.get(0));
        Core.merge(list1, dst);
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2BGR);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void laplacianEnhancement(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src=imread(jpgg);
        Mat srcClone = src.clone();
        float[] kernel = {0, 0, 0, -1, 5f, -1, 0, 0, 0};
        Mat kernelMat = new Mat(3, 3, CvType.CV_32FC1);
        kernelMat.put(0, 0, kernel);
        Imgproc.filter2D(srcClone, srcClone, CV_8UC3, kernelMat);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, srcClone);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void logarithmicTransformation(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src=imread(jpgg);
        Mat srcClone = src.clone();
        Mat imageResult = new Mat(srcClone.size(), CvType.CV_32FC3);
        Core.add(srcClone, new Scalar(5, 5, 5), srcClone);
        srcClone.convertTo(srcClone, CvType.CV_32F);
        Core.log(srcClone, imageResult);
        Core.normalize(imageResult, imageResult, 0, 255, Core.NORM_MINMAX);
        Core.convertScaleAbs(imageResult, imageResult);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, imageResult);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void GammaTransform(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src=imread(jpgg);
        Mat srcClone = src.clone();
        srcClone.convertTo(srcClone, CvType.CV_32F);
        Core.pow(srcClone, 4, srcClone);
        Core.normalize(srcClone, srcClone, 0, 255, Core.NORM_MINMAX);
        Core.convertScaleAbs(srcClone, srcClone);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, srcClone);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void prewittSharpening(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src = Imgcodecs.imread(jpgg);
        Mat dst = new Mat();
        Mat kernel = new Mat(3,3, CvType.CV_32FC1);
        float[] data = new float[]{
                -1,-1,-1,
                0,0,0,
                1,1,1
        };
        kernel.put(0,0,data);
        Imgproc.filter2D(src, dst, -1, kernel);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void sobelSharpening(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src = Imgcodecs.imread(jpgg);
        Mat dst = new Mat();
        Mat kernel = new Mat(3,3, CvType.CV_32FC1);
        float[] data = new float[]{
                -1,-2,-1,
                0,0,0,
                1,2,1
        };
        kernel.put(0,0,data);
        Imgproc.filter2D(src, dst, -1, kernel);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void laplacianSharpening(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src = Imgcodecs.imread(jpgg);
        Mat dst = new Mat();
        Mat kernel = new Mat(3,3, CvType.CV_32FC1);
        float[] data = new float[]{
                0,-1,0,
                -1,4,-1,
                0,-1,0
        };
        kernel.put(0,0,data);
        Imgproc.filter2D(src, dst, -1, kernel);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void kirschSharpening(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat src = Imgcodecs.imread(jpgg);
        Mat dst = new Mat();
        Mat kernel = new Mat(3,3, CvType.CV_32FC1);
        float[] data = new float[]{
                5,5,5,
                -3,0,-3,
                -3,-3,-3
        };
        kernel.put(0,0,data);
        Imgproc.filter2D(src, dst, -1, kernel);
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, dst);
        imageView.setImage(new Image("file:"+jpgg));
    }

    String newPath;
    int index=0;
    public void pseudoColor(ActionEvent actionEvent) {
        back.push(newPath);
        Mat im = imread(jpgg, IMREAD_GRAYSCALE);
        Mat im_out = new Mat();
        applyColorMap(im, im_out, colormap_name(index++));
        if(index>12){
            index=1;
        }
        newPath=getPath();
        Imgcodecs.imwrite(newPath, im_out);
        imageView.setImage(new Image("file:"+newPath));
    }

    public int colormap_name(int id)
    {
        switch(id){
            case 1 :
                return COLORMAP_AUTUMN;
            case 2 :
                return COLORMAP_BONE;
            case 3 :
                return COLORMAP_JET;
            case 4 :
                return COLORMAP_WINTER;
            case 5 :
                return COLORMAP_RAINBOW;
            case 6 :
                return COLORMAP_OCEAN;
            case 7:
                return COLORMAP_SUMMER;
            case 8 :
                return COLORMAP_SPRING;
            case 9 :
                return COLORMAP_COOL;
            case 10 :
                return COLORMAP_HSV;
            case 11 :
                return COLORMAP_PINK;
            case 12 :
                return COLORMAP_HOT;
        }
        return COLORMAP_HOT;
    }

    public void taggingLine(ActionEvent actionEvent) {
        flag=1;
    }

    int flag=0;
    public void click(ActionEvent actionEvent) {
        flag=0;
    }

    public void taggingText(ActionEvent actionEvent) {
        flag=2;
    }

    public void taggingCircle(ActionEvent actionEvent) {
        flag=3;
    }

    public void taggingArea(ActionEvent actionEvent) {
        flag=4;
    }

    public void taggingRect(ActionEvent actionEvent) {
        flag=5;
    }

    public void COLORMAP_AUTUMN(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat im = imread(jpgg, IMREAD_GRAYSCALE);
        Mat im_out = new Mat();
        applyColorMap(im, im_out, colormap_name(0));
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, im_out);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void COLORMAP_JET(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat im = imread(jpgg, IMREAD_GRAYSCALE);
        Mat im_out = new Mat();
        applyColorMap(im, im_out, colormap_name(2));
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, im_out);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void COLORMAP_SUMMER(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat im = imread(jpgg, IMREAD_GRAYSCALE);
        Mat im_out = new Mat();
        applyColorMap(im, im_out, colormap_name(6));
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, im_out);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void COLORMAP_HOT(ActionEvent actionEvent) {
        back.push(jpgg);
        Mat im = imread(jpgg, IMREAD_GRAYSCALE);
        Mat im_out = new Mat();
        applyColorMap(im, im_out, colormap_name(11));
        jpgg=getPath();
        Imgcodecs.imwrite(jpgg, im_out);
        imageView.setImage(new Image("file:"+jpgg));
    }

    public void drawPoint(ActionEvent actionEvent) {
        flag=6;
    }

    public void distance(ActionEvent actionEvent) {
        flag=7;
    }

    public class KV {

        private SimpleStringProperty k;
        private SimpleStringProperty v;

        public KV(String k, String v) {
            this.k = new SimpleStringProperty(k);
            this.v = new SimpleStringProperty(v);
        }


        public String getK() {
            return k.get();
        }

        public void setK(String k) {
            this.k.set(k);
        }

        public String getV() {
            return v.get();
        }

        public void setV(String v) {
            this.v.set(v);
        }
    }

    public String getPath(){
        return "E:\\Program Files\\DICOM\\"+UUID.randomUUID()+".jpg";
    }
}