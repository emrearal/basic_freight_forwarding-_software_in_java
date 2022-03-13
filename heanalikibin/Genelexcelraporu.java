package heanalikibin;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;

public class Genelexcelraporu{
	 File fileToSave;
	 HSSFSheet sheet;
	 String tarih1,tarih2,numara1,numara2;
	 boolean pozmu,tarihlimi;
	 String pozbasliklari [] = {"Poz#","Parsiyel mi?","Ýþ tipi","MBL no","Mbl Tarihi","Hat adý","Yukleme Gemisi","Yukleme G. Seferi","Aktarma Gemisi",
			 "Aktarma G. Seferi","TC Gemi Acentesi","Aktarma Acentesi","Y.D.Gemi Acentesi","Karþý Pozisyon","Kalkýþ Tarihi","Varýþ Tarihi","Navlun Tutarý",
			 "Navlun Prepaid mi? ","Yi Masraf Prepaid mi ?","Yd Masraf Prepaid mi?","Yukleme Kenti","Yukleme Limaný","Aktarma Limaný","Varýþ Limaný","Son Varýþ Kenti",
			 "Son Varýþ Ülkesi","Hat Rez No","Y.D Acente", "MBL'deki Gönderici","MBL'deki Alýcý","MBL'deki Notify","Free-Time","Toplam Brüt Kg",
			 "Toplam Net Kg","Toplam Hacim","Toplam Kap","Toplam Yük Adedi","Toplam TEU","Konteyner Sayýsý","Kar TL","Kar USD"};  // 41 adet
	 
	 String yukbasliklari [] = {"Yük#","Poza Alýndý mý ?","Komple mi ? ","Ýþ tipi", "Yüklendiði Poz" ,"Hbl No:","Hbl Tarihi","Müþteri Adý","Üretici Adý",
			 "Gönderici Adý","Alýcý Adý","Notify 1","Notify 2"," Y.D. Acente Adý","Fatura Kesilen Firma Adý","Turkçe Mal Adý",
			 "Yabancý Dilde Mal Adý","Teslim Þekli","Ödeme Þekli","Ödeme Kenti","Mal Bedeli","Yükleme Kenti","Yükleme Limaný",
			 "Aktarma Limaný","Varýþ Limaný","Son Varýþ Kenti", "Son Varýþ Ülkesi","Net Kg","Brüt KG","Hacim","Ýlk Konteyner No "," " ," " ," " ," " ," " ," " ,
			 " "," "," " ," "}; // 41 adet
	 
	 String basliklar [] = new String [41];
	 String satirlar [][];
	
	public Genelexcelraporu (Boolean pozmu,Boolean tarihlimi,String tarih1,String tarih2,String numara1,String numara2 ){
		this.pozmu=pozmu;
		this.tarihlimi=tarihlimi;
		this.tarih1=tarih1;
		this.tarih2=tarih2;
		this.numara1=numara1;
		this.numara2=numara2;
	}
	
	void genelexcelraporu() {
		
		String veritabani="",alan1="",alan2="",raporbasligi="",raporbasligi2=""; 
		
		if (pozmu) {  // poz dokumu yapýyorsak
			 veritabani="pozisyonlar";
			 alan1="pozno";
			 alan2="mbltarih";
			 basliklar = pozbasliklari.clone();
			 raporbasligi="Pozisyon";
			 
		}  else {  // yuk dokumu yapýyorsak
			veritabani="yukler";
			alan1="yukno";
			alan2="hbltarihi";
			basliklar = yukbasliklari.clone();
			raporbasligi="Yük";
		}
		
		int say=0;
		String komutseti="";
		
		if (tarihlimi==false) {  // eðer numara aralýðýnda sýralayacaksak
			komutseti="SELECT * FROM "+veritabani+" where "+alan1+" between '"+numara1+"' and '"+numara2+"'";
			raporbasligi2=numara1+" ile "+numara2+" Numaralar Arasý "+raporbasligi+" Raporu";
		} else  {  // eðer tarih aralýðýnda sýralayacaksak
 			komutseti="SELECT * FROM "+veritabani+" where "+ alan2+" between '"+tarih1+"' and '"+tarih2+"'";
 			raporbasligi2=Ontanimliveriler.tarihiterscevir(tarih1)+" ile "+Ontanimliveriler.tarihiterscevir(tarih2)+" Tarihleri Arasý "+raporbasligi+" Raporu";
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
			
			}catch(Exception e){ System.out.println(e);}  
		
		satirlar = new String [say][41];  // sayýya göre dizini boyutlayalým
		
		try{               // tekrar ayný aramayý yapýyoruz. 
			
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti); 
			        
		say=0;
		
			while(rs.next()) {
		
						 if (pozmu) {  // poz ise poz tablodatasýný oluþtýr
					
							 satirlar[say][0]=rs.getString(1);// pozno
				    	   				    	   
				    	   satirlar[say][1]="Hayýr";
				    	   if (rs.getString(2).equals("1")) {
				    		   satirlar[say][1]="Evet"; //parsiyel mi ?   
				    	   }
				    	   
				    	   satirlar[say][2]=Anasinif.istipi[rs.getInt(5)];//istipi
				    	   satirlar[say][3]=rs.getString(6); // mbl no
				    	   satirlar[say][4]=Ontanimliveriler.tarihiterscevir(rs.getString(7)); // mbl tarih
				    	   satirlar[say][5]=Ontanimliveriler.hatkoducoz(rs.getInt(8)); // hat adý
				    	   satirlar[say][6]=Ontanimliveriler.gemikoducoz(rs.getInt(9)) ; // yukleme gemisi adý
				    	   satirlar[say][7]=rs.getString(10);  // yukleme gemisi sefer no
				    	   satirlar[say][8]=Ontanimliveriler.gemikoducoz(rs.getInt(11)) ;  // aktarma gemisi 
				    	   satirlar[say][9]=rs.getString(12); // aktarma gemisi seferi
				    	   satirlar[say][10]=Ontanimliveriler.sirketkoducoz(rs.getInt(13))[1]; // yi gemiacentesi
				    	   satirlar[say][11]=Ontanimliveriler.sirketkoducoz(rs.getInt(14))[1]; // aktarma gemi acentesi
				    	   satirlar[say][12]=Ontanimliveriler.sirketkoducoz(rs.getInt(15))[1]; // Yd gemi acentesi
				    	   satirlar[say][13]=rs.getString(16); // karþý pozisyon
				    	   satirlar[say][14]=Ontanimliveriler.tarihiterscevir(rs.getString(17)) ; // kalkýþ tarihi
				    	   satirlar[say][15]=Ontanimliveriler.tarihiterscevir(rs.getString(18)); //variþ tarihi
				    	   satirlar[say][16]=rs.getString(19); // navlun tutarý
				    	   
				    	   satirlar[say][17]="Hayýr" ;  // navlun prepaid
				    	   if (rs.getString(20).equals("1")) {
				    		   satirlar[say][17]="Evet"; }
				    	   
				    	   satirlar[say][18]="Hayýr" ;   // yimasraf prepaid
				    	   if (rs.getString(21).equals("1")) {
				    		   satirlar[say][18]="Evet"; }
				    	   
				    	   satirlar[say][19]= " Hayýr" ;  //  yd masraf prepaid ? 
				    	   if (rs.getString(22).equals("1")) {
				    		   satirlar[say][19]="Evet"; }
			 	    	   
				    	   satirlar[say][20]=Ontanimliveriler.kentkoducoz(rs.getInt(23))[0];  //yukleme kenti
				    	   satirlar[say][21]=Ontanimliveriler.limankoducoz(rs.getInt(24)) ; // yukleme limaný  
				    	   satirlar[say][22]=Ontanimliveriler.limankoducoz(rs.getInt(25)) ; // aktarma limaný
				    	   satirlar[say][23]=Ontanimliveriler.limankoducoz(rs.getInt(26)) ; // varýþ limaný
				    	   String [] kentulke =Ontanimliveriler.kentkoducoz(rs.getInt(27)).clone();
				    	   satirlar[say][24]=kentulke[0]; // son varýþ kenti
				    	   satirlar[say][25]=kentulke[1]; // son varýþ ülkesi 
				    	   satirlar[say][26]=rs.getString(29); // hat rez no
				    	   satirlar[say][27]=Ontanimliveriler.sirketkoducoz(rs.getInt(30))[1]  ; // yd acente 
				    	   satirlar[say][28]=Ontanimliveriler.sirketkoducoz(rs.getInt(31))[1]  ; //mbl de gönderici 
				    	   satirlar[say][29]=Ontanimliveriler.sirketkoducoz(rs.getInt(32))[1]  ; //mbl de alýcý 
				    	   satirlar[say][30]=Ontanimliveriler.sirketkoducoz(rs.getInt(33))[1]  ; //mbl de notify 
				    	   satirlar[say][31]=rs.getString(35); // free time 
				    	   int rakamsalveriler[]=Pozisyon.pozdakikonteynerleribul(rs.getString(1)).clone();
				    	   satirlar[say][32]=String.valueOf(rakamsalveriler[0]); // toplam brüt kg
				    	   satirlar[say][33]=String.valueOf(rakamsalveriler[1]); // toplam net kg
				    	   satirlar[say][34]=String.valueOf(rakamsalveriler[2]); // toplam hacim
				    	   satirlar[say][35]=String.valueOf(rakamsalveriler[3]); // toplam kap
				    	   satirlar[say][36]=String.valueOf(rakamsalveriler[4]); // toplam yük adedi
				    	   satirlar[say][37]=String.valueOf(rakamsalveriler[5]); // toplam teu
				    	   satirlar[say][38]=String.valueOf(rakamsalveriler[6]); // toplam konteyner adedi
				    	   String[][] karzarar = Karzararraporu.karzarardiziniolustur(rs.getString(1), "", "").clone();
				    	   satirlar[say][39]=karzarar[0][3];  // toplam TL kar
				    	   satirlar[say][40]=karzarar[0][4];  // toplam usd kar
				    	   
						 } else {  // yük ise yük tablodatasýný oluþtýr
							 
							 satirlar[say][0]=rs.getString(1);// yukno
							 
							 satirlar[say][1]="Hayýr" ;  // poza alindi mi ? 
							 if (rs.getString(2).equals("1")) {
					    		   satirlar[say][1]="Evet"; }
							 
							 satirlar[say][2]="Hayir";  // komple mi ?
							 if (rs.getString(3).equals("1")) {
					    		   satirlar[say][2]="Evet"; }
							 
							 satirlar[say][3]=Anasinif.istipi[rs.getInt(29)];  // iþ tipi
							 satirlar[say][4]=rs.getString(30); // yüklendiði poz
							 satirlar[say][5]=rs.getString(4);  // HBL no
							 satirlar[say][6]=Ontanimliveriler.tarihiterscevir(rs.getString(5)) ; // Hbl tarihi
							 satirlar[say][7]=Ontanimliveriler.sirketkoducoz(rs.getInt(6))[1]  ; //müsteri adý
							 satirlar[say][8]=Ontanimliveriler.sirketkoducoz(rs.getInt(7))[1]  ; //üretici adý
							 satirlar[say][9]=Ontanimliveriler.sirketkoducoz(rs.getInt(8))[1]  ; //gönderici adý
							 satirlar[say][10]=Ontanimliveriler.sirketkoducoz(rs.getInt(9))[1]  ; //alýcý adý
							 satirlar[say][11]=Ontanimliveriler.sirketkoducoz(rs.getInt(10))[1]  ; //notify1 adý
							 satirlar[say][12]=Ontanimliveriler.sirketkoducoz(rs.getInt(11))[1]  ; //notify2 adý
							 satirlar[say][13]=Ontanimliveriler.sirketkoducoz(rs.getInt(12))[1]  ; // yd acente adý
							 satirlar[say][14]=Ontanimliveriler.sirketkoducoz(rs.getInt(13))[1]  ; //fat kes firma adý
							 satirlar[say][15]=rs.getString(14);  // Türkçe mal adý 
							 satirlar[say][16]=rs.getString(15);  // yabancý mal adý 
							 satirlar[say][17]=Ontanimliveriler.teslimseklikoducoz(rs.getInt(16));  // teslim þekli
							 satirlar[say][18]=Ontanimliveriler.odemeseklikoducoz(rs.getInt(17));  // ödeme þekli
							 satirlar[say][19]=Ontanimliveriler.kentkoducoz(rs.getInt(18))[0]; // ödeme kenti 
							 satirlar[say][20]=rs.getString(19);  // mal bedeli
							 satirlar[say][21]=Ontanimliveriler.kentkoducoz(rs.getInt(20))[0]; // yükleme kenti
							 satirlar[say][22]=Ontanimliveriler.limankoducoz(rs.getInt(21)); // yükleme limaný
							 satirlar[say][23]=Ontanimliveriler.limankoducoz(rs.getInt(22)); // aktarma limaný
							 satirlar[say][24]=Ontanimliveriler.limankoducoz(rs.getInt(23)); // varýþ limaný
							 satirlar[say][25]=Ontanimliveriler.kentkoducoz(rs.getInt(24))[0]; // son varýþ kenti
							 satirlar[say][26]=Ontanimliveriler.kentkoducoz(rs.getInt(24))[1]; // son varýþ ülkesi
							 satirlar[say][27]=rs.getString(26);  // Net Kg
							 satirlar[say][28]=Ontanimliveriler.yukunbrutkilosunubul(rs.getString(1)); // Brüt Kg  metoda gönderilecek.
							 satirlar[say][29]=rs.getString(27); // Hacim
							 satirlar[say][30]=rs.getString(25).substring(2,12); // Ýlk konteyner no
							 satirlar[say][31]=satirlar[say][32]=satirlar[say][33]=satirlar[say][34]=satirlar[say][35]=
									           satirlar[say][36]=satirlar[say][37]=satirlar[say][38]=satirlar[say][39]=satirlar[say][40]="  ";  // BOÞ Sütunlar
							 
						 }
						 
				      say++;
					}
			
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		
		
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
            rowhead.createCell(0).setCellValue(raporbasligi2);
            
            rowhead = sheet.createRow(5);
            
            for (int i=0;i<41; i++) {  // Baþlýklarý atýyoruz 
            	rowhead.createCell(i).setCellValue(basliklar[i]);
            }
           
    	    for (int i=0 ; i<satirlar.length; i++ ) {
    	    	
    	       HSSFRow row = sheet.createRow(i+7);
    	        for (int j=0;j<41; j++) {
    	        	
    	        	if (!pozmu) {    //eðer yük ise sayýsal verileri rakam olarak excele dök
    	        		if (j==27 | j==28 |j==29 ) {
        	        		row.createCell(j).setCellValue(Double.parseDouble(satirlar[i][j]));
        	        	}else {
        	        		row.createCell(j).setCellValue(satirlar[i][j]);
        	        	}
    	        	} else {  //eðer poz ise sayýsal verileri rakam olarak excele dök
    	        		if (j==32 | j==33 |j==34 | j==35 | j==36 |j==37 |j==38|j==39|j==40) {
        	        		row.createCell(j).setCellValue(Double.parseDouble(satirlar[i][j]));
        	        	}else {
        	        		row.createCell(j).setCellValue(satirlar[i][j]);
    	        	   }
     	             }
    	          }  // j döngüsü sonu
    	    }  // i döngüsü sonu
    	
    	    for (int i=1 ; i<45; i++ ) {
            	sheet.autoSizeColumn(i);
            }
    	    
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
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
}