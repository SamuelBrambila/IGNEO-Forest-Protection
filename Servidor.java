package servidorm;
import java.sql.SQLException;
public class Servidor {
escucha es1[] = new escucha[10];
int hilos=0;
 
 public static void main(String[] args) throws ClassNotFoundException, SQLException {
 escucha es1= new escucha();
 serverf interfz= new serverf();
 interfz.setVisible(true);
 
 es1.start();
 
 
 }
 public void hilo(){
 while(true){
 if(es1[hilos].occ){
 hilos++;
 encender();
 }else if(es1[hilos].occ&&hilos!=0){
 apagar();
 }
 }
 }
 
 public void encender(){
 es1[hilos].start();
 }
 public void apagar(){
 es1[hilos].destroy();
 
 }
}
Conexión a la base de datos
package servidorm;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class conexiondb {
 
 public Connection cnx=null;
 
 public Connection conectarDB()
 throws ClassNotFoundException,
 SQLException{
 if(cnx==null){
 Class.forName("com.mysql.jdbc.Driver");
 cnx=(Connection)
 DriverManager.getConnection("jdbc:mysql://localhost:3306/ex1","root","");
 
 }
 //System.err.println("conexion exitosa");
 return cnx;
 
}
 
 public void cerrarDB(){
 
 if(cnx!=null){
 try {
 cnx.close();
 } catch (SQLException ex) {
 Logger.getLogger(conexiondb.class.getName()).log(Level.SEVERE, null, ex);
 }
 
 }
 
 
 } 
 
}
package servidorm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class datos {
 
 public void setestadoip(String gg, String ip){
 
 
 conexiondb cone = new conexiondb();
 try {
 Connection con = cone.conectarDB();
 String query = "UPDATE datos SET estado='"+gg+"' WHERE ip = '"+ip+"'";
 PreparedStatement ps = con.prepareStatement(query);
 //ps.setString(1,"vig");
 ps.execute();
 System.out.println("estado cambio");
 } catch (SQLException | ClassNotFoundException ex) {
 System.out.print(ex);
 }finally{
 cone.cerrarDB();
 }
 }
 public void setestadoid(String gg, String id){
 
 
 conexiondb cone = new conexiondb();
 try {
 Connection con = cone.conectarDB();
 String query = "UPDATE datos SET estado='"+gg+"' WHERE id = '"+id+"'";
 PreparedStatement ps = con.prepareStatement(query);
 //ps.setString(1,"vig");
 ps.execute();
 } catch (SQLException | ClassNotFoundException ex) {
 System.out.print(ex);
 }finally{
 cone.cerrarDB();
 }
 }
 
 public void sethilo(int hilo){
 
 conexiondb cone = new conexiondb();
 try {
 Connection con = cone.conectarDB();
 String query = "UPDATE datos SET hilos='"+hilo+"' WHERE ip = '/10.0.0.2'";
 PreparedStatement ps = con.prepareStatement(query);
 ps.execute();
 } catch (SQLException | ClassNotFoundException ex) {
 System.out.print(ex);
 }finally{
 cone.cerrarDB();
 }
 }
 
 public String getestado(String id){
 String gg1="";
 conexiondb cone = new conexiondb();
 
 try{
 Connection con = cone.conectarDB();
 String query = "SELECT estado FROM datos WHERE id='"+id+"'";
 PreparedStatement ps = con.prepareStatement(query);
 ResultSet rs = ps.executeQuery();
 while(rs.next()){
 gg1=rs.getString("estado");
 }
 }catch(SQLException | ClassNotFoundException ex){
 
 System.out.print(ex);
 }finally{
 cone.cerrarDB();
 }
 return gg1;
 }
 
 public String getid(String ip){
 String gg2="";
 conexiondb cone = new conexiondb();
 
 try{
 Connection con = cone.conectarDB();
 String query = "SELECT id FROM datos WHERE ip='"+ip+"'";
 PreparedStatement ps = con.prepareStatement(query);
 ResultSet rs = ps.executeQuery();
 while(rs.next()){
 gg2=rs.getString("id");
 }
 }catch(SQLException | ClassNotFoundException ex){
 
 System.out.print(ex);
 }finally{
 cone.cerrarDB();
 }
 return gg2;
 }
}
package servidorm;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
public class escucha extends Thread {
int puerto=13000;
boolean occ; 
datos d1 = new datos();
serverf sf=new serverf();
@Override
public void run(){
 
 
 int enumerador = 1;
 
 
 try{
 
 ServerSocket serverSocket2 = new ServerSocket(puerto);
 
 while(true){
 
 Socket socket2 = serverSocket2.accept();
 if(socket2.isConnected()){
 this.occ=true;
 }else{this.occ=false;}
 //System.out.println(socket2.getLocalAddress().toString());
 
 String dre=socket2.getLocalAddress().toString();
 d1.setestadoip("monitoreo",dre);
 System.out.print(dre);
 
 
 InputStream inputStream = socket2.getInputStream();
 System.out.println("Reading: " + System.currentTimeMillis());
 byte[] sizeAr = new byte[4];
 inputStream.read(sizeAr);
 int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
 byte[] imageAr = new byte[size];
 inputStream.read(imageAr);
 BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
 System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + 
System.currentTimeMillis());
 ImageIO.write(image, "jpg", new File( "src/paquetexd/fotos/"+d1.getid(dre)+"/"+ enumerador 
+".jpg"));
 enumerador++;
 
 
 }
 } catch (IOException ex) {
 Logger.getLogger(escucha.class.getName()).log(Level.SEVERE, null, ex);
 }finally{
 
 
 }
} 
}
Interfaz grafica
package servidorm;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class serverf extends javax.swing.JFrame{
 datos dat = new datos();
 public serverf() {
 
 initComponents();
 ImageIcon imgIcon1=new ImageIcon(getClass().getResource("/paquetexd/camara2.png"));
 Icon icoimage= new 
ImageIcon(imgIcon1.getImage().getScaledInstance(80,40,Image.SCALE_DEFAULT));
 c4.setIcon(icoimage);
 c5.setIcon(icoimage);
 c6.setIcon(icoimage);
 c7.setIcon(icoimage);
 c8.setIcon(icoimage);
 c9.setIcon(icoimage);
 c10.setIcon(icoimage);
 c11.setIcon(icoimage);
 c12.setIcon(icoimage);
 
 
 
 }
 public void cmp(){
 for(int i=4;i<=12;i++){
 if("monitoreo".equals(dat.getestado(("c"+i)))){
 
 changeborder(i);
 }
 }
 }
 public void changeborder(int i){
 switch(i){
 case 4:
 c4.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 5:
 c5.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 6:
 c6.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 7:
 c7.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 8:
 c8.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 9:
 c9.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 10:
 c10.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 11:
 c11.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 case 12:
 c12.setBorder(BorderFactory.createLineBorder(Color.red));
 break;
 
 }
 }
 
 public void opt(int nc){
 String id[]={"c4","c5","c6","c7","c8","c9","c10","c11","c12"};
 int op=JOptionPane.showConfirmDialog(null,"¿decea refresar a funcioamiento normal?",("camara 
"+nc+1),JOptionPane.YES_NO_OPTION,1,setPicture(id[nc]));
 switch(op){
 case JOptionPane.YES_OPTION:
 System.out.println("yes yes");
 dat.setestadoid("vigilancia",id[nc]);
 dat.sethilo(0);
 
 switch(nc+4){
 case 4:
 c4.setBorder(null);
 break;
 case 5:
 c5.setBorder(null);
 break;
 case 6:
 c6.setBorder(null);
 break;
 case 7:
 c7.setBorder(null);
 break;
 case 8:
 c8.setBorder(null);
 break;
 case 9:
 c9.setBorder(null);
 break;
 case 10:
 c10.setBorder(null);
 break;
 case 11:
 c11.setBorder(null);
 break;
 case 12:
 c12.setBorder(null);
 break;
 
 }
 
 break;
 case JOptionPane.NO_OPTION:
 System.out.println("no no");
 break;
 }
 
 }
 public Icon setPicture(String t){
 int contador=0;
 try{
 while(true){
 contador++;
 ImageIcon imgIcon1=new 
ImageIcon(getClass().getResource("/paquetexd/fotos/"+t+"/"+contador+".jpg"));
 
 }
 }catch(Exception ex){
 contador--; 
 }finally{
 ImageIcon imgIcon1=new 
ImageIcon(getClass().getResource("/paquetexd/fotos/"+t+"/"+contador+".jpg"));
 Icon icoimage= new 
ImageIcon(imgIcon1.getImage().getScaledInstance(640,480,Image.SCALE_DEFAULT));
 return icoimage; 
 }
 
 
 }
 /**
 * This method is called from within the constructor to initialize the form.
 * WARNING: Do NOT modify this code. The content of this method is always
 * regenerated by the Form Editor.
 */
 @SuppressWarnings("unchecked")
 // <editor-fold defaultstate="collapsed" desc="Generated Code"> 
 private void initComponents() {
 jPanel1 = new javax.swing.JPanel();
 c13 = new javax.swing.JLabel();
 c12 = new javax.swing.JLabel();
 c11 = new javax.swing.JLabel();
 c10 = new javax.swing.JLabel();
 c9 = new javax.swing.JLabel();
 c8 = new javax.swing.JLabel();
 c7 = new javax.swing.JLabel();
 c6 = new javax.swing.JLabel();
 c5 = new javax.swing.JLabel();
 c4 = new javax.swing.JLabel();
 fondo = new javax.swing.JLabel();
 setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
 setTitle("servidor");
 setIconImages(null);
 setName("frm1"); // NOI18N
 setUndecorated(true);
 setResizable(false);
 addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
 public void mouseMoved(java.awt.event.MouseEvent evt) {
 formMouseMoved(evt);
 }
 });
 addPropertyChangeListener(new java.beans.PropertyChangeListener() {
 public void propertyChange(java.beans.PropertyChangeEvent evt) {
 formPropertyChange(evt);
 }
 });
 jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
 c13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
 jPanel1.add(c13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, -1, -1));
 c12.setText("9");
 c12.setPreferredSize(new java.awt.Dimension(80, 40));
 c12.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c12MouseEntered(evt);
 }
 });
 jPanel1.add(c12, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 170, -1, -1));
 c11.setText("8");
 c11.setPreferredSize(new java.awt.Dimension(80, 40));
 c11.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c11MouseEntered(evt);
 }
 });
 jPanel1.add(c11, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 390, -1, -1));
 c10.setText("7");
 c10.setPreferredSize(new java.awt.Dimension(80, 40));
 c10.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c10MouseEntered(evt);
 }
 });
 jPanel1.add(c10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, -1, -1));
 c9.setText("6");
 c9.setPreferredSize(new java.awt.Dimension(80, 40));
 c9.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c9MouseEntered(evt);
 }
 });
 jPanel1.add(c9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, -1, -1));
 c8.setText("5");
 c8.setPreferredSize(new java.awt.Dimension(80, 40));
 c8.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c8MouseEntered(evt);
 }
 });
 jPanel1.add(c8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));
 c7.setText("4");
 c7.setPreferredSize(new java.awt.Dimension(80, 40));
 c7.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c7MouseEntered(evt);
 }
 });
 jPanel1.add(c7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, -1, -1));
 c6.setText("3");
 c6.setPreferredSize(new java.awt.Dimension(80, 40));
 c6.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c6MouseEntered(evt);
 }
 });
 jPanel1.add(c6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));
 c5.setText("2");
 c5.setPreferredSize(new java.awt.Dimension(80, 40));
 c5.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c5MouseEntered(evt);
 }
 });
 jPanel1.add(c5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, -1, -1));
 c4.setText("1");
 c4.setToolTipText("");
 c4.setPreferredSize(new java.awt.Dimension(80, 40));
 c4.setVerifyInputWhenFocusTarget(false);
 c4.addMouseListener(new java.awt.event.MouseAdapter() {
 public void mouseEntered(java.awt.event.MouseEvent evt) {
 c4MouseEntered(evt);
 }
 });
 jPanel1.add(c4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));
 fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetexd/mapa.png"))); // 
NOI18N
 jPanel1.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));
 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
 getContentPane().setLayout(layout);
 layout.setHorizontalGroup(
 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
 .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 
javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
 );
 layout.setVerticalGroup(
 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
 .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 
javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
 );
 pack();
 setLocationRelativeTo(null);
 }// </editor-fold> 
 private void c4MouseEntered(java.awt.event.MouseEvent evt) { 
 if("monitoreo".equals(dat.getestado(("c4")))){
 opt(0);
 }
 
 } 
 private void c11MouseEntered(java.awt.event.MouseEvent evt) { 
 if("monitoreo".equals(dat.getestado(("c11")))){
 opt(7);
 }
 } 
 private void formPropertyChange(java.beans.PropertyChangeEvent evt) { 
 // TODO add your handling code here:
 
 } 
 private void formMouseMoved(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 cmp();
 } 
 private void c5MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c5")))){
 opt(1);
 }
 } 
 private void c6MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c6")))){
 opt(2);
 }
 } 
 private void c7MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c7")))){
 opt(3);
 }
 } 
 private void c8MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c8")))){
 opt(4);
 }
 } 
 private void c9MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c9")))){
 opt(5);
 }
 } 
 private void c10MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c10")))){
 opt(6);
 }
 } 
 private void c12MouseEntered(java.awt.event.MouseEvent evt) { 
 // TODO add your handling code here:
 if("monitoreo".equals(dat.getestado(("c12")))){
 opt(8);
 }
 } 
 
 /**
 * @param args the command line arguments
 */
 
 public static void main(String args[]) {
 
 /* Set the Nimbus look and feel */
 //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
 /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
 */
 try {
 for (javax.swing.UIManager.LookAndFeelInfo info : 
javax.swing.UIManager.getInstalledLookAndFeels()) {
 if ("Nimbus".equals(info.getName())) {
 javax.swing.UIManager.setLookAndFeel(info.getClassName());
 break;
 }
 }
 } catch (ClassNotFoundException ex) {
 java.util.logging.Logger.getLogger(serverf.class.getName()).log(java.util.logging.Level.SEVERE, null, 
ex);
 } catch (InstantiationException ex) {
 java.util.logging.Logger.getLogger(serverf.class.getName()).log(java.util.logging.Level.SEVERE, null, 
ex);
 } catch (IllegalAccessException ex) {
 java.util.logging.Logger.getLogger(serverf.class.getName()).log(java.util.logging.Level.SEVERE, null, 
ex);
 } catch (javax.swing.UnsupportedLookAndFeelException ex) {
 java.util.logging.Logger.getLogger(serverf.class.getName()).log(java.util.logging.Level.SEVERE, null, 
ex);
 }
 //</editor-fold>
 
 /* Create and display the form */
 java.awt.EventQueue.invokeLater(new Runnable() {
 @Override
 public void run() {
 
 new serverf().setVisible(true);
 
 }
 });
 }
 // Variables declaration - do not modify 
 private javax.swing.JLabel c10;
 private javax.swing.JLabel c11;
 private javax.swing.JLabel c12;
 private javax.swing.JLabel c13;
 private javax.swing.JLabel c4;
 private javax.swing.JLabel c5;
 private javax.swing.JLabel c6;
 private javax.swing.JLabel c7;
 private javax.swing.JLabel c8;
 private javax.swing.JLabel c9;
 private javax.swing.JLabel fondo;
 private javax.swing.JPanel jPanel1;
 // End of variables declaration 
}
