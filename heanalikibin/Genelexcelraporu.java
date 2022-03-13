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
	 String pozbasliklari [] = {"Poz#","Parsiyel mi?","�� tipi","MBL no","Mbl Tarihi","Hat ad�","Yukleme Gemisi","Yukleme G. Seferi","Aktarma Gemisi",
			 "Aktarma G. Seferi","TC Gemi Acentesi","Aktarma Acentesi","Y.D.Gemi Acentesi","Kar�� Pozisyon","Kalk�� Tarihi","Var�� Tarihi","Navlun Tutar�",
			 "Navlun Prepaid mi? ","Yi Masraf Prepaid mi ?","Yd Masraf Prepaid mi?","Yukleme Kenti","Yukleme Liman�","Aktarma Liman�","Var�� Liman�","Son Var�� Kenti",
			 "Son Var�� �lkesi","Hat Rez No","Y.D Acente", "MBL'deki G�nderici","MBL'deki Al�c�","MBL'deki Notify","Free-Time","Toplam Br�t Kg",
			 "Toplam Net Kg","Toplam Hacim","Toplam Kap","Toplam Y�k Adedi","Toplam TEU","Konteyner Say�s�","Kar TL","Kar USD"};  // 41 adet
	 
	 String yukbasliklari [] = {"Y�k#","Poza Al�nd� m� ?","Komple mi ? ","�� tipi", "Y�klendi�i Poz" ,"Hbl No:","Hbl Tarihi","M��teri Ad�","�retici Ad�",
			 "G�nderici Ad�","Al�c� Ad�","Notify 1","Notify 2"," Y.D. Acente Ad�","Fatura Kesilen Firma Ad�","Turk�e Mal Ad�",
			 "Yabanc� Dilde Mal Ad�","Teslim �ekli","�deme �ekli","�deme Kenti","Mal Bedeli","Y�kleme Kenti","Y�kleme Liman�",
			 "Aktarma Liman�","Var�� Liman�","Son Var�� Kenti", "Son Var�� �lkesi","Net Kg","Br�t KG","Hacim","�lk Konteyner No "," " ," " ," " ," " ," " ," " ,
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
		
		if (pozmu) {  // poz dokumu yap�yorsak
			 veritabani="pozisyonlar";
			 alan1="pozno";
			 alan2="mbltarih";
			 basliklar = pozbasliklari.clone();
			 raporbasligi="Pozisyon";
			 
		}  else {  // yuk dokumu yap�yorsak
			veritabani="yukler";
			alan1="yukno";
			alan2="hbltarihi";
			basliklar = yukbasliklari.clone();
			raporbasligi="Y�k";
		}
		
		int say=0;
		String komutseti="";
		
		if (tarihlimi==false) {  // e�er numara aral���nda s�ralayacaksak
			komutseti="SELECT * FROM "+veritabani+" where "+alan1+" between '"+numara1+"' and '"+numara2+"'";
			raporbasligi2=numara1+" ile "+numara2+" Numaralar Aras� "+raporbasligi+" Raporu";
		} else  {  // e�er tarih aral���nda s�ralayacaksak
 			komutseti="SELECT * FROM "+veritabani+" where "+ alan2+" between '"+tarih1+"' and '"+tarih2+"'";
 			raporbasligi2=Ontanimliveriler.tarihiterscevir(tarih1)+" ile "+Ontanimliveriler.tarihiterscevir(tarih2)+" Tarihleri Aras� "+raporbasligi+" Raporu";
		}
		
		try{               // ilgili araman�n verilerinin say�s�n� ��renelim             
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
		
		satirlar = new String [say][41];  // say�ya g�re dizini boyutlayal�m
		
		try{               // tekrar ayn� aramay� yap�yoruz. 
			
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti); 
			        
		say=0;
		
			while(rs.next()) {
		
						 if (pozmu) {  // poz ise poz tablodatas�n� olu�t�r
					
							 satirlar[say][0]=rs.getString(1);// pozno
				    	   				    	   
				    	   satirlar[say][1]="Hay�r";
				    	   if (rs.getString(2).equals("1")) {
				    		   satirlar[say][1]="Evet"; //parsiyel mi ?   
				    	   }
				    	   
				    	   satirlar[say][2]=Anasinif.istipi[rs.getInt(5)];//istipi
				    	   satirlar[say][3]=rs.getString(6); // mbl no
				    	   satirlar[say][4]=Ontanimliveriler.tarihiterscevir(rs.getString(7)); // mbl tarih
				    	   satirlar[say][5]=Ontanimliveriler.hatkoducoz(rs.getInt(8)); // hat ad�
				    	   satirlar[say][6]=Ontanimliveriler.gemikoducoz(rs.getInt(9)) ; // yukleme gemisi ad�
				    	   satirlar[say][7]=rs.getString(10);  // yukleme gemisi sefer no
				    	   satirlar[say][8]=Ontanimliveriler.gemikoducoz(rs.getInt(11)) ;  // aktarma gemisi 
				    	   satirlar[say][9]=rs.getString(12); // aktarma gemisi seferi
				    	   satirlar[say][10]=Ontanimliveriler.sirketkoducoz(rs.getInt(13))[1]; // yi gemiacentesi
				    	   satirlar[say][11]=Ontanimliveriler.sirketkoducoz(rs.getInt(14))[1]; // aktarma gemi acentesi
				    	   satirlar[say][12]=Ontanimliveriler.sirketkoducoz(rs.getInt(15))[1]; // Yd gemi acentesi
				    	   satirlar[say][13]=rs.getString(16); // kar�� pozisyon
				    	   satirlar[say][14]=Ontanimliveriler.tarihiterscevir(rs.getString(17)) ; // kalk�� tarihi
				    	   satirlar[say][15]=Ontanimliveriler.tarihiterscevir(rs.getString(18)); //vari� tarihi
				    	   satirlar[say][16]=rs.getString(19); // navlun tutar�
				    	   
				    	   satirlar[say][17]="Hay�r" ;  // navlun prepaid
				    	   if (rs.getString(20).equals("1")) {
				    		   satirlar[say][17]="Evet"; }
				    	   
				    	   satirlar[say][18]="Hay�r" ;   // yimasraf prepaid
				    	   if (rs.getString(21).equals("1")) {
				    		   satirlar[say][18]="Evet"; }
				    	   
				    	   satirlar[say][19]= " Hay�r" ;  //  yd masraf prepaid ? 
				    	   if (rs.getString(22).equals("1")) {
				    		   satirlar[say][19]="Evet"; }
			 	    	   
				    	   satirlar[say][20]=Ontanimliveriler.kentkoducoz(rs.getInt(23))[0];  //yukleme kenti
				    	   satirlar[say][21]=Ontanimliveriler.limankoducoz(rs.getInt(24)) ; // yukleme liman�  
				    	   satirlar[say][22]=Ontanimliveriler.limankoducoz(rs.getInt(25)) ; // aktarma liman�
				    	   satirlar[say][23]=Ontanimliveriler.limankoducoz(rs.getInt(26)) ; // var�� liman�
				    	   String [] kentulke =Ontanimliveriler.kentkoducoz(rs.getInt(27)).clone();
				    	   satirlar[say][24]=kentulke[0]; // son var�� kenti
				    	   satirlar[say][25]=kentulke[1]; // son var�� �lkesi 
				    	   satirlar[say][26]=rs.getString(29); // hat rez no
				    	   satirlar[say][27]=Ontanimliveriler.sirketkoducoz(rs.getInt(30))[1]  ; // yd acente 
				    	   satirlar[say][28]=Ontanimliveriler.sirketkoducoz(rs.getInt(31))[1]  ; //mbl de g�nderici 
				    	   satirlar[say][29]=Ontanimliveriler.sirketkoducoz(rs.getInt(32))[1]  ; //mbl de al�c� 
				    	   satirlar[say][30]=Ontanimliveriler.sirketkoducoz(rs.getInt(33))[1]  ; //mbl de notify 
				    	   satirlar[say][31]=rs.getString(35); // free time 
				    	   int rakamsalveriler[]=Pozisyon.pozdakikonteynerleribul(rs.getString(1)).clone();
				    	   satirlar[say][32]=String.valueOf(rakamsalveriler[0]); // toplam br�t kg
				    	   satirlar[say][33]=String.valueOf(rakamsalveriler[1]); // toplam net kg
				    	   satirlar[say][34]=String.valueOf(rakamsalveriler[2]); // toplam hacim
				    	   satirlar[say][35]=String.valueOf(rakamsalveriler[3]); // toplam kap
				    	   satirlar[say][36]=String.valueOf(rakamsalveriler[4]); // toplam y�k adedi
				    	   satirlar[say][37]=String.valueOf(rakamsalveriler[5]); // toplam teu
				    	   satirlar[say][38]=String.valueOf(rakamsalveriler[6]); // toplam konteyner adedi
				    	   String[][] karzarar = Karzararraporu.karzarardiziniolustur(rs.getString(1), "", "").clone();
				    	   satirlar[say][39]=karzarar[0][3];  // toplam TL kar
				    	   satirlar[say][40]=karzarar[0][4];  // toplam usd kar
				    	   
						 } else {  // y�k ise y�k tablodatas�n� olu�t�r
							 
							 satirlar[say][0]=rs.getString(1);// yukno
							 
							 satirlar[say][1]="Hay�r" ;  // poza alindi mi ? 
							 if (rs.getString(2).equals("1")) {
					    		   satirlar[say][1]="Evet"; }
							 
							 satirlar[say][2]="Hayir";  // komple mi ?
							 if (rs.getString(3).equals("1")) {
					    		   satirlar[say][2]="Evet"; }
							 
							 satirlar[say][3]=Anasinif.istipi[rs.getInt(29)];  // i� tipi
							 satirlar[say][4]=rs.getString(30); // y�klendi�i poz
							 satirlar[say][5]=rs.getString(4);  // HBL no
							 satirlar[say][6]=Ontanimliveriler.tarihiterscevir(rs.getString(5)) ; // Hbl tarihi
							 satirlar[say][7]=Ontanimliveriler.sirketkoducoz(rs.getInt(6))[1]  ; //m�steri ad�
							 satirlar[say][8]=Ontanimliveriler.sirketkoducoz(rs.getInt(7))[1]  ; //�retici ad�
							 satirlar[say][9]=Ontanimliveriler.sirketkoducoz(rs.getInt(8))[1]  ; //g�nderici ad�
							 satirlar[say][10]=Ontanimliveriler.sirketkoducoz(rs.getInt(9))[1]  ; //al�c� ad�
							 satirlar[say][11]=Ontanimliveriler.sirketkoducoz(rs.getInt(10))[1]  ; //notify1 ad�
							 satirlar[say][12]=Ontanimliveriler.sirketkoducoz(rs.getInt(11))[1]  ; //notify2 ad�
							 satirlar[say][13]=Ontanimliveriler.sirketkoducoz(rs.getInt(12))[1]  ; // yd acente ad�
							 satirlar[say][14]=Ontanimliveriler.sirketkoducoz(rs.getInt(13))[1]  ; //fat kes firma ad�
							 satirlar[say][15]=rs.getString(14);  // T�rk�e mal ad� 
							 satirlar[say][16]=rs.getString(15);  // yabanc� mal ad� 
							 satirlar[say][17]=Ontanimliveriler.teslimseklikoducoz(rs.getInt(16));  // teslim �ekli
							 satirlar[say][18]=Ontanimliveriler.odemeseklikoducoz(rs.getInt(17));  // �deme �ekli
							 satirlar[say][19]=Ontanimliveriler.kentkoducoz(rs.getInt(18))[0]; // �deme kenti 
							 satirlar[say][20]=rs.getString(19);  // mal bedeli
							 satirlar[say][21]=Ontanimliveriler.kentkoducoz(rs.getInt(20))[0]; // y�kleme kenti
							 satirlar[say][22]=Ontanimliveriler.limankoducoz(rs.getInt(21)); // y�kleme liman�
							 satirlar[say][23]=Ontanimliveriler.limankoducoz(rs.getInt(22)); // aktarma liman�
							 satirlar[say][24]=Ontanimliveriler.limankoducoz(rs.getInt(23)); // var�� liman�
							 satirlar[say][25]=Ontanimliveriler.kentkoducoz(rs.getInt(24))[0]; // son var�� kenti
							 satirlar[say][26]=Ontanimliveriler.kentkoducoz(rs.getInt(24))[1]; // son var�� �lkesi
							 satirlar[say][27]=rs.getString(26);  // Net Kg
							 satirlar[say][28]=Ontanimliveriler.yukunbrutkilosunubul(rs.getString(1)); // Br�t Kg  metoda g�nderilecek.
							 satirlar[say][29]=rs.getString(27); // Hacim
							 satirlar[say][30]=rs.getString(25).substring(2,12); // �lk konteyner no
							 satirlar[say][31]=satirlar[say][32]=satirlar[say][33]=satirlar[say][34]=satirlar[say][35]=
									           satirlar[say][36]=satirlar[say][37]=satirlar[say][38]=satirlar[say][39]=satirlar[say][40]="  ";  // BO� S�tunlar
							 
						 }
						 
				      say++;
					}
			
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		
		
		// Buradan sonra excel tablosu olu�turuluyor 
		JFrame parentFrame = new JFrame();
    	JFileChooser fileChooser = new JFileChooser();                    // kaydedilecek excel dosyas�n�n ismini se�me  fayl �uuz�r
    	fileChooser.setDialogTitle("Dosya Ad�n� Belirleyin");   
    	 
    	int userSelection = fileChooser.showSaveDialog(parentFrame);
    	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    fileToSave = fileChooser.getSelectedFile();
    	     }
    	  
    	try {                 //excel olu�tur
    		String kayit=fileToSave+".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            sheet = workbook.createSheet("Bir");
    	     
            HSSFRow rowhead = sheet.createRow(2);
            rowhead.createCell(0).setCellValue(raporbasligi2);
            
            rowhead = sheet.createRow(5);
            
            for (int i=0;i<41; i++) {  // Ba�l�klar� at�yoruz 
            	rowhead.createCell(i).setCellValue(basliklar[i]);
            }
           
    	    for (int i=0 ; i<satirlar.length; i++ ) {
    	    	
    	       HSSFRow row = sheet.createRow(i+7);
    	        for (int j=0;j<41; j++) {
    	        	
    	        	if (!pozmu) {    //e�er y�k ise say�sal verileri rakam olarak excele d�k
    	        		if (j==27 | j==28 |j==29 ) {
        	        		row.createCell(j).setCellValue(Double.parseDouble(satirlar[i][j]));
        	        	}else {
        	        		row.createCell(j).setCellValue(satirlar[i][j]);
        	        	}
    	        	} else {  //e�er poz ise say�sal verileri rakam olarak excele d�k
    	        		if (j==32 | j==33 |j==34 | j==35 | j==36 |j==37 |j==38|j==39|j==40) {
        	        		row.createCell(j).setCellValue(Double.parseDouble(satirlar[i][j]));
        	        	}else {
        	        		row.createCell(j).setCellValue(satirlar[i][j]);
    	        	   }
     	             }
    	          }  // j d�ng�s� sonu
    	    }  // i d�ng�s� sonu
    	
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
    	
    	try {  // olu�turulan excel dosyas�n� a��yoruz 
    		File a = new File(fileToSave+".xls");
			Desktop.getDesktop().open(a);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
}