package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdesktop.swingx.JXDatePicker;



import  org.apache.poi.hssf.usermodel.HSSFRow;

public class Karzararraporu{
	 
	 static File fileToSave;
	
	 void karzararraporu() {
		
		
		JDialog yavru = new JDialog(Denizyoluanaekran.frame," K/Z,TEU,Tonaj Raporu Tarih Aralýðý Seçimi",true); 
		yavru.setResizable(true);
		yavru.setBounds(550,250, 320,120);
		
		JPanel panel= new JPanel();
		JPanel panel2=new JPanel();
		
		JXDatePicker picker = new JXDatePicker();                        //Tarih Seçici 1
	    picker.setDate(Calendar.getInstance().getTime()); 	
	    picker.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
	    picker.getEditor().setEditable(false);
	    picker.setVisible(true);
		panel.add(picker);
		
		JXDatePicker picker2 = new JXDatePicker();                        //Tarih Seçici 2 
	    picker2.setDate(Calendar.getInstance().getTime()); 
		picker2.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		picker2.getEditor().setEditable(false);
	    picker2.setVisible(true);
		panel.add(picker2);

		JButton yavrudugme2 = new JButton("ONAY");
		yavrudugme2.addActionListener(new ActionListener(){  
			    public void actionPerformed(ActionEvent e){  
			    
			    	DateFormat df = new SimpleDateFormat("yyyy.MM.dd"); 
			  String tarih1=df.format(picker.getDate());
			  String tarih2=df.format(picker2.getDate());
			    	
			    	yavru.dispose();
			    	excelyap(tarih1,tarih2);
			    		    
			    	}  
					});  
		
		panel2.add(yavrudugme2);
		
		yavru.add(panel, BorderLayout.CENTER);
		yavru.add(panel2, BorderLayout.PAGE_END);
		
		yavru.setVisible(true);
		
	}
	
	 static  void excelyap(String t1, String t2) {
		
		 HSSFSheet sheet;
		 String basliklar [] = {"Tarih","Poz#","Ýþ-Tipi","TL Kar/Zarar ","USD Kar/Zarar  ","TEU" ,"Brüt Kg","Konteyner Sayýsý"}; 
		
		String satirlar [][]=karzarardiziniolustur("",t1,t2).clone();
		
		
		// Buradan sonra excel tablosu oluþturuluyor 
		
		JFrame parentFrame = new JFrame();
    	JFileChooser fileChooser = new JFileChooser();                    // kaydedilecek excel dosyasýnýn ismini seçme  fayl çuuzýr
    	fileChooser.setDialogTitle("Dosya Adýný Belirleyin");   
    	 
    	int userSelection = fileChooser.showSaveDialog(parentFrame);
    	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    fileToSave = fileChooser.getSelectedFile();
    	     }
    	  
    	try {                 //excel oluþtur
    		String kayit=fileToSave+".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            sheet = workbook.createSheet("Bir");
    	     
            HSSFRow rowhead = sheet.createRow(2);
            rowhead.createCell(0).setCellValue(Ontanimliveriler.tarihiterscevir(t1)+" ve "+Ontanimliveriler.tarihiterscevir(t2)
            +" TARÝHLERÝ ARASI KAR / ZARAR ,TEU ve TONAJ RAPORU");
            
            rowhead = sheet.createRow(5);
            
            for (int i=0;i<8; i++) {
            	rowhead.createCell(i).setCellValue(basliklar[i]);
            }
           
    	    for (int i=0 ; i<satirlar.length; i++ ) {
    	    	
    	       HSSFRow row = sheet.createRow(i+7);
    	        
    	        		row.createCell(0).setCellValue(satirlar[i][0]);
    	        		row.createCell(1).setCellValue(satirlar[i][1]);
    	        		row.createCell(2).setCellValue(satirlar[i][2]);
    	        		row.createCell(3).setCellValue(Integer.parseInt(satirlar[i][3]));
    	        		row.createCell(4).setCellValue(Integer.parseInt(satirlar[i][4]));
    	        		row.createCell(5).setCellValue(Integer.parseInt(satirlar[i][5]));
    	        		row.createCell(6).setCellValue(Integer.parseInt(satirlar[i][6]));
    	        		row.createCell(7).setCellValue(Integer.parseInt(satirlar[i][7]));
    	    }
    	
    	    for (int i=1 ; i<10; i++ ) {
            	sheet.autoSizeColumn(i);
            }
    	    sheet.setColumnWidth(0,2700);
    	   
    	    
    	    FileOutputStream fileOut = new FileOutputStream(kayit);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
           
        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    	
    	try {  // oluþturulan excel dosyasýný açýyoruz 
    		File a = new File(fileToSave+".xls");
			Desktop.getDesktop().open(a);
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
    }

	public static String [][] karzarardiziniolustur (String pozno,String t1, String t2) {
		
		int say=0;
		String komutseti="";
		if (!pozno.equals("")) {  // tek bir poz arýyorsak;
			komutseti="select mbltarih,pozno,istipi from pozisyonlar where pozno= '"+pozno+"'";
		} else { // tarih aralýðý arýyorsak
			komutseti="select mbltarih,pozno,istipi from pozisyonlar where mbltarih between '"+t1+"' and '"+t2+"'";
		}
		
		try{               // ilgili aramanýn verilerinin sayýsýný öðrenelim             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti); 
			
			while(rs.next()) {
				        say++;
					}
			con.close();  
			
			}catch(Exception e1){ System.out.println(e1);}  
		
		String [][] satirlar = new String [say][8];  // sayýya göre dizini boyutlayalým
		
		try{               // tekrar ayný aramayý yapýyoruz. 
			
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti); 
			        
		say=0;
		
			while(rs.next()) {
				
				int karzarar= (int) Pozgelirgiderbakiyeler.veritablosunuduzenle(rs.getString(2));
		        float kur = Ontanimliveriler.kurusoyle(1)[0];
		        int usdkarzarar=(int) (karzarar/kur);
		        int [] teubrutkont = Pozisyon.pozdakikonteynerleribul(rs.getString(2)).clone();  
		        
		        satirlar[say][0]=Ontanimliveriler.tarihiterscevir(rs.getString(1))	; // tarih
		        satirlar[say][1]=rs.getString(2);                                // pozno
				satirlar[say][2]=Anasinif.istipi[rs.getInt(3)];  // iþtipi
				satirlar[say][3]=String.valueOf(karzarar);// kar tl
				satirlar[say][4]=String.valueOf(usdkarzarar);// kar USD
				satirlar[say][5]=String.valueOf(teubrutkont[5]) ; //teu
				satirlar[say][6]=String.valueOf(teubrutkont[0]) ; // brüt kg
				satirlar[say][7]=String.valueOf(teubrutkont[6]) ; // konteyner sayýsý
				say++; 
					}
			
			con.close();  
			
			}catch(Exception e){ }  
		
		
		return satirlar;
		
	}
  }