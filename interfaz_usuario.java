package try1;
import java.io.IOException;
public class Try1{
 
 public static void main(String[] args) throws InterruptedException, IOException{
 lector b1= new lector();
 conexiont t = new conexiont();
 while(true){
 if(b1.leer()){
 Thread.sleep(20000);
 if(b1.leer()){
 while(true){
 t.conexiont(b1.getA(),b1.getB(),b1.getC(),b1.getD(),b1.getE());
 Thread.sleep(20000);
 if(!(b1.leer())){
 break;
 }
 }
 }
 }
 }
 }
}
package try1;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.net.*;
public class lector{
 protected int pixelp;
 protected int alto;
 protected int ancho;
 protected int a=0,b=0,c=0,d=0,e=1;
 protected conexiont con = new conexiont();
 public int getA(){
 
 return a;
 }
 public int getB(){
 
 return b;
 }
 public int getC(){
 
 return c;
 }
 public int getD(){
 
 return d;
 }
 public int getE(){
 
 return e;
 }
 public boolean leer() throws InterruptedException{
 
 
 try{
 System.out.println(e);
 InputStream input= new 
FileInputStream("C:\\Users\\a1810\\Pictures\\JPEG\\ir\\IR_"+a+b+c+d+e+".jpg");
 
 if(e==9){
 if(d==9){
 if(c==9){
 if(b==9){
 if(a==9){
 a=0;
 b=0;
 c=0;
 d=0;
 e=1;
 }
 a++;
 }
 b++;
 
 }
 c++;
 
 }
 d++;
 
 
 }else{e++;}
 
 ImageInputStream imageInput = ImageIO.createImageInputStream(input);
 BufferedImage imagenL = ImageIO.read(imageInput);
 alto=imagenL.getHeight();
 ancho=imagenL.getWidth();
 int mapaa[][][]= new int[alto][ancho][3];
 System.out.println("alto: "+alto+" ancho: "+ancho);
 
 for(int y=0;y<alto;y++){
 for(int x=0;x<ancho;x++){
 int srcPixel=imagenL.getRGB(x,y);
 Color c = new Color(srcPixel);
 int valR=c.getRed();
 int valG=c.getGreen();
 int valB=c.getBlue();
 
 mapaa[y][x][0]=valR;
 mapaa[y][x][1]=valG;
 mapaa[y][x][2]=valB;
 }
 }
 /*aqui finaliza el rgb*/
 
 pixelp=0;
 for(int y=0;y<alto;y++){
 for(int x=0;x<ancho;x++){
 if(mapaa[y][x][0]>230&&((mapaa[y][x][1]>mapaa[y][x][2])||((mapaa[y][x][2]-
mapaa[y][x][1])<9))){
 pixelp++; 
 }
 } 
 }
 System.out.println(pixelp);
 if(pixelp>=(alto*ancho*.05)){
 System.out.println("incendio");
 return true;
 
 }
 
 }catch(Exception ex){
 System.out.print(ex);
 
 }
 
 
 
 System.out.println("no incendio");
 return false;
 }
 
}
package try1;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.rmi.server.ExportException;
import javax.imageio.ImageIO;
class conexiont {
 
 public void conexiont(int a,int b,int c,int d,int e) throws IOException{
 int puerto=13000;
 try{
 System.out.println(e+" con");
 Socket socket1= new Socket("10.0.0.8",puerto);
 
 OutputStream outputStream = socket1.getOutputStream();
 
 
 BufferedImage image = ImageIO.read(new 
File("C:\\Users\\a1810\\Pictures\\JPEG\\ir\\IR_"+a+b+c+d+e+".jpg"));
 
 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
 ImageIO.write(image, "jpg", byteArrayOutputStream);
 byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
 outputStream.write(size);
 outputStream.write(byteArrayOutputStream.toByteArray());
 outputStream.flush();
 System.out.println("Flushed: " + System.currentTimeMillis());
 
 
 socket1.close();
 
 }catch(UnknownHostException e2){
 System.out.print(e2);
 
 }catch(ExportException e2){
 System.out.println(e2);
 puerto++;
 conexiont(a, b, c, d, e-1);
 }
 }
 
 
}
}
