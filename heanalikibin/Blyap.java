package heanalikibin;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.pdf.BaseFont;

public class Blyap {
	
	static PdfDocument hazirlanacakbl;
	static int toplamkg,toplamadet;
	
	public static void blyap(String blpozno, String blyukno) {
		
	
		String bosbldosyasiyoluveadi="C:\\Users\\emrea\\Desktop\\bl\\TyholtBlBos.pdf";
		String[] pozdatalari = new String [35];
		String[] yukdatalari = new String [30];
		
		int sayfasayisi=1,konteynersayisi=1;
		
		toplamkg=toplamadet=0;
		
		String gondericiadi="",aliciadi="",notifyadi="",aktarmagemiadiveseferi="",kalkislimani="",gemiadiveseferi="",
				aktarmalimani="",sonvariskentiveulkesi="",blno="",karsireferans="",freetime="",
				varisacentesi="",teslimsekli="",tanzimyerivetarihi=""
				,nooriginals="3(three)                                             UAB TYHOLT"
				,varislimani="",gondericiadresi="",gondericisehir="",gondericiulke,gondericivergidairesi=""
				,gondericivergino="",aliciadresi="",alicisehir="",aliciulke,alicivergidairesi=""
				,alicivergino="",notifyadresi="",notifysehir="",notifyulke,notifyvergidairesi=""
				,notifyvergino="",varisacentesiadresi="",varisacentesisehir="",varisacentesiulke="",varisacentesivergidairesi=""
				,varisacentesivergino="";
	
		if (!blyukno.equals("0")) {  // house basýlacak ise ilgili yükün tüm verilerini yukdatalarý dizinine çekelim. 
			
			Yuk.konteynerokuma(Integer.parseInt(blyukno));
			
		    konteynersayisi=Yuk.tablodata.length;
			
			try{                     
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
			"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
						
						Statement stmt=con.createStatement();  
				        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yukno='"+blyukno+"';"); 
				
				while(rs.next()) {
					         for (int i=0; i<30; i++) {
					        	 yukdatalari[i]=rs.getString(i+1);
					         }
						}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}  
			
		} else {
			Pozisyon.pozdakikonteynerleribul(blpozno);
			
		    konteynersayisi=Pozisyon.tablodata.length;
			
		}
		
		String[][] blsatirlari = new String [35+konteynersayisi][2];
		sayfasayisi= ((int) konteynersayisi/20)+1;
		
		try{               // ilgili pozun tüm verilerini pozdatalarý dizinine çekelim.                           
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("SELECT * FROM pozisyonlar where pozno='"+blpozno+"';"); 
			
			while(rs.next()) {
				         for (int i=0; i<35; i++) {
				        	 pozdatalari[i]=rs.getString(i+1);
				         }
					}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		if (blyukno.equals("0")) {// eðer master basýyorsak
			gondericiadi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[1];
			gondericiadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[3] ;
			
			gondericisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[4] ;
			gondericiulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[5] ;
			gondericivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[6] ;
			gondericivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[30]))[7] ;
			
			aliciadi= Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[1];
			aliciadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[3] ;
			
			alicisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[4] ;
			aliciulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[5] ;
			alicivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[6] ;
			alicivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[31]))[7] ;
			
			
			notifyadi= Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[1];
			notifyadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[3] ;
			
			notifysehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[4] ;
			notifyulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[5] ;
			notifyvergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[6] ;
			notifyvergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[32]))[7] ;
			
			blno="MBL NUMBER: "+pozdatalari[5];
			karsireferans="Pos. #:"+pozdatalari[0]+ "    Load # : 0 ";
			
			varisacentesi= Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[1]; 
			varisacentesiadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[3] ;
			try {
				if (varisacentesiadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					varisacentesiadresi=varisacentesiadresi.substring(0,60);
				}
				if (gondericiadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					gondericiadresi=gondericiadresi.substring(0,60);
				}
				if (notifyadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					notifyadresi=notifyadresi.substring(0,60);
				}
				if (aliciadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					aliciadresi=aliciadresi.substring(0,60);
				}
			} catch (Exception e) {	}
			
			varisacentesisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[4] ;
			varisacentesiulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[5] ;
			varisacentesivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[6] ;
			varisacentesivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(pozdatalari[29]))[7] ;
			
			if (pozdatalari[19].equals("1")) {	teslimsekli="Freight Prepaid"; 	}
			else { teslimsekli="Freight Collect";}
		   
			tanzimyerivetarihi= "Ýstanbul "+Ontanimliveriler.tarihiterscevir(pozdatalari[6]);
		} else {  // eðer house basýyorsak 
			gondericiadi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[1]; 
			gondericiadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[3] ;
			
			gondericisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[4] ;
			gondericiulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[5] ;
			gondericivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[6] ;
			gondericivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[7]))[7] ;
			
			aliciadi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[1]; 
			aliciadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[3] ;
			
			alicisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[4] ;
			aliciulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[5] ;
			alicivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[6] ;
			alicivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[8]))[7] ;
			
			notifyadi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[1]; 
			notifyadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[3] ;
			
			notifysehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[4] ;
			notifyulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[5] ;
			notifyvergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[6] ;
			notifyvergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[9]))[7] ;
			
			blno="HBL NUMBER: "+yukdatalari[3];
			
			karsireferans="Pos #: "+pozdatalari[0]+"     Load #:"+yukdatalari[0];
			
			varisacentesi= Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[1]; 
			varisacentesiadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[3] ;
			
			try {
				if (varisacentesiadresi.length()>60 ) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					varisacentesiadresi=varisacentesiadresi.substring(0,60);
				}
				if (aliciadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					aliciadresi=aliciadresi.substring(0,60);
				}
				if (notifyadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					notifyadresi=notifyadresi.substring(0,60);
				}
				if (gondericiadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
					gondericiadresi=gondericiadresi.substring(0,60);
				}
			} catch (Exception e) {}
			
			varisacentesisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[4] ;
			varisacentesiulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[5] ;
			varisacentesivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[6] ;
			varisacentesivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(yukdatalari[11]))[7] ;
			
			teslimsekli=Ontanimliveriler.teslimseklikoducoz(Integer.parseInt(yukdatalari[15]));
			tanzimyerivetarihi= "Ýstanbul "+Ontanimliveriler.tarihiterscevir(yukdatalari[4]);
			//konteynersayisi=yuktekikonteynersatiri.length;
		}
	    // Ortak olanlar
		aktarmagemiadiveseferi= Ontanimliveriler.gemikoducoz(Integer.parseInt(pozdatalari[10]))+" / "+pozdatalari[11];
		kalkislimani=Ontanimliveriler.limankoducoz(Integer.parseInt(pozdatalari[23]));
		gemiadiveseferi= Ontanimliveriler.gemikoducoz(Integer.parseInt(pozdatalari[8]))+" / "+pozdatalari[9];
		aktarmalimani= Ontanimliveriler.limankoducoz(Integer.parseInt(pozdatalari[24]));
		sonvariskentiveulkesi=Ontanimliveriler.kentkoducoz(Integer.parseInt(pozdatalari[26]))[0]
				+"/"+Ontanimliveriler.kentkoducoz(Integer.parseInt(pozdatalari[26]))[1];
		varislimani=Ontanimliveriler.limankoducoz(Integer.parseInt(pozdatalari[25]));
		freetime=pozdatalari[34] ; 
		
		int basilankontsayisi=0,hz=0;
		
		for (int bastigimizsayfa=1; bastigimizsayfa-1<sayfasayisi ; bastigimizsayfa++) {
	
		 // Satýrlar ve konumlarý
		blsatirlari[0][0] = "050,800"; blsatirlari[0][1] =gondericiadi;
		blsatirlari[17][0] = "050,790"; blsatirlari[17][1] =gondericiadresi;
		blsatirlari[18][0] = "050,780"; blsatirlari[18][1] =gondericisehir;
		blsatirlari[19][0] = "050,770"; blsatirlari[19][1] =gondericiulke;
		blsatirlari[20][0] = "050,760"; blsatirlari[20][1] ="Tax Office :"+gondericivergidairesi+" /  Tax Number:"+gondericivergino;
		
		blsatirlari[1][0] = "050,730"; blsatirlari[1][1] =aliciadi ;
		blsatirlari[21][0] = "050,720"; blsatirlari[21][1] =aliciadresi;
		blsatirlari[22][0] = "050,710"; blsatirlari[22][1] =alicisehir+"  "+aliciulke;
		blsatirlari[23][0] = "050,700"; blsatirlari[23][1] ="Tax Office :"+alicivergidairesi+" /  Tax Number:"+alicivergino;
				
		blsatirlari[2][0] = "050,661"; blsatirlari[2][1] =notifyadi ;
		blsatirlari[24][0] = "050,651"; blsatirlari[24][1] =notifyadresi;
		blsatirlari[25][0] = "050,641"; blsatirlari[25][1] =notifysehir+"  "+notifyulke;
		blsatirlari[26][0] = "050,631"; blsatirlari[26][1] ="Tax Office :"+notifyvergidairesi+"/  Tax Number:"+notifyvergino;
		
		blsatirlari[3][0] = "050,605"; blsatirlari[3][1] =aktarmagemiadiveseferi ;
		blsatirlari[4][0] = "200,605"; blsatirlari[4][1] =kalkislimani ;
		blsatirlari[5][0] = "050,575"; blsatirlari[5][1] =gemiadiveseferi ;
		blsatirlari[6][0] = "200,575"; blsatirlari[6][1] =aktarmalimani ;
		blsatirlari[27][0] = "330,575"; blsatirlari[27][1] =blno ;
		blsatirlari[28][0] = "330,590"; blsatirlari[28][1] ="REFERENCE NUMBER: "+karsireferans ;
		
		blsatirlari[7][0] = "050,551"; blsatirlari[7][1] =varislimani ;
		blsatirlari[8][0] = "400,551"; blsatirlari[8][1] =sonvariskentiveulkesi ;
	
		//  Konteyner satýrlarý baþý
		
		
		for (int i=0; i<20 ;i++) {
			
			if (konteynersayisi==basilankontsayisi) {
				break;
			}
		
		if (!blyukno.equals("0")) {  // house basýyorsak kont satýrlarý 
			
			String satirno=String.valueOf(520-(i*10));
			blsatirlari[33+i][0] = "050,"+satirno ; blsatirlari[33+i][1] =yukkonteynersatirlari(Integer.parseInt(blyukno))[basilankontsayisi];
			
	    	} else {  // master basýyorsak kont satýrlarý
	    		
				String satirno=String.valueOf(520-(i*10));
				blsatirlari[33+i][0] = "050,"+satirno ; blsatirlari[33+i][1] =pozkonteynersatirlari(blpozno)[basilankontsayisi];
				
		        }
		  basilankontsayisi++;
		}
		
		blsatirlari[9][0]="200,310";  // ortak
		 
		 if (!blyukno.equals("0")) {
			   blsatirlari[9][1]="Total : "+toplamadet/Yuk.tablodata.length+" Packs    "+
		       "                                                       Total : "+ toplamkg/Yuk.tablodata.length+ " KG";
		 } else {
			   blsatirlari[9][1]="Total : "+toplamadet/Pozisyon.tablodata.length+" Packs     "+
			  "                                                           Total : "+ toplamkg/Pozisyon.tablodata.length+ " KG";	 
		 }
	
		 //   Konteyner satýrlarý sonu
	 	
		blsatirlari[10][0] = "040,315"; blsatirlari[10][1] ="Total Number of Containers :"+konteynersayisi ;
		blsatirlari[11][0] = "040,305"; blsatirlari[11][1] ="Free Time at port of Destination :"+freetime;
		blsatirlari[12][0] = "089,286"; blsatirlari[12][1] ="of "+Ontanimliveriler.sayiyiyaziyacevir(freetime, "UK")+" days including weekends";
		
		blsatirlari[13][0] = "040,190"; blsatirlari[13][1] =varisacentesi;
		blsatirlari[29][0] = "040,180"; blsatirlari[29][1] =varisacentesiadresi;
		blsatirlari[30][0] = "040,170"; blsatirlari[30][1] =varisacentesisehir;
		blsatirlari[31][0] = "040,160"; blsatirlari[31][1] =varisacentesiulke;
		blsatirlari[32][0] = "040,150"; blsatirlari[32][1] ="Tax Office :"+varisacentesivergidairesi+" /   Tax Number:"+varisacentesivergino;
		
		blsatirlari[14][0] = "240,080"; blsatirlari[14][1] =teslimsekli;
		blsatirlari[15][0] = "380,080"; blsatirlari[15][1] =tanzimyerivetarihi;
		blsatirlari[16][0] = "240,052"; blsatirlari[16][1] =nooriginals;
	
		String hazirlanacakblyoluveadi="C:\\Users\\emrea\\Desktop\\"+"BillOfLading"+String.valueOf(bastigimizsayfa)+".pdf";  // kaydedilecek dosya adý 
		
	try {    // pdf edit iþlemleri
		hazirlanacakbl = new PdfDocument(new PdfReader(bosbldosyasiyoluveadi), 
				new PdfWriter(hazirlanacakblyoluveadi));
	} catch (FileNotFoundException e1) {
		Bilgipenceresi.anons("Dosya bulunamadý");
		e1.printStackTrace();
	} catch (IOException e1) {
		Bilgipenceresi.anons("Dosya Veri Hatasý !");
		e1.printStackTrace();
	}
		
	PdfCanvas canvas = new PdfCanvas(hazirlanacakbl.getFirstPage());
	
	if (20*bastigimizsayfa<konteynersayisi) {
		hz=20;
	} else {
		hz=(konteynersayisi%20);
	}
	
	for (int i=0; i<33+hz;i++) {  //  döngüye girerek her satýrý tek tek basýyoruz. 

		canvas.beginText();
		
		int x=Integer.parseInt(blsatirlari[i][0].substring(0,3));
		int y=Integer.parseInt(blsatirlari[i][0].substring(4));
		
		try {
			canvas.setFontAndSize(PdfFontFactory.createFont("C:\\Windows\\Fonts" + "\\Arial.TTF",  // türkçe karakter basmak için gerekli
	                BaseFont.IDENTITY_H, BaseFont.EMBEDDED),8);
		} catch (IOException e1) {
			Bilgipenceresi.anons("Dosya Veri Hatasý !");
			e1.printStackTrace();
		}

		canvas.moveText(x,y);    // yazma konumu
		try {
			canvas.showText(blsatirlari[i][1]);   // yazdýr
		} catch (Exception e) {}
		canvas.endText();  // kaydet 
		
	} // döngü sonu 
		
	hazirlanacakbl. close();	 // kapat
	
	try {  // belki ileride file çuzýr ile farklý yere kaydedip açma yapýlabilir . default masa üstü de iyi çözüm aslýnda
		File a = new File(hazirlanacakblyoluveadi);  // yazýlan pdf dosyasýný (faturayý)  aç
		Desktop.getDesktop().open(a);
	} catch (IOException e) {}
	
		}  // sayfa basma döngüsü sonu
	
} // Metod sonu 

	private static String [] yukkonteynersatirlari(int yukno) {
	
		String malcinsi=Yuk.konteynerokuma(yukno);
		
		try {
			if (malcinsi.length()>37) {
				malcinsi=malcinsi.substring(0,36);
			}
		} catch (Exception e) {}
	
		String yukkontsat [] = new String [Yuk.tablodata.length];
		
		for (int i=0; i<Yuk.tablodata.length;i++) {
			
			yukkontsat[i]="";
			yukkontsat[i]=yukkontsat[i]+Yuk.tablodata[i][0]+" - ";
			yukkontsat[i]=yukkontsat[i]+Anasinif.konteynertipleri[Integer.parseInt(Yuk.tablodata[i][4])]+"   ";
			yukkontsat[i]=yukkontsat[i]+Yuk.tablodata[i][1]+"                            ";
			yukkontsat[i]=yukkontsat[i]+Yuk.tablodata[i][2]+" packs of "+malcinsi;
			
			toplamkg=toplamkg+Integer.parseInt(Yuk.tablodata[i][3].trim());
			toplamadet=toplamadet+Integer.parseInt(Yuk.tablodata[i][2].trim());
			
			for (int j=0 ; j<210-(yukkontsat[i].length()) ; j++) {
				yukkontsat[i]=yukkontsat[i]+" ";
			}
			
			yukkontsat[i]=yukkontsat[i]+Yuk.tablodata[i][3]+" KG ";
		    
		}
		
		return yukkontsat;
	}

	private static String [] pozkonteynersatirlari(String pozno) {
		
		Pozisyon.pozdakikonteynerleribul(pozno);
		String Pozisyonkontsat [] = new String [Pozisyon.tablodata.length];
		
		for (int i=0; i<Pozisyon.tablodata.length;i++) {
			
			String malcinsi=Pozisyon.masterblicinkonteynermalcinsi[i];
			
			try {
				if (malcinsi.length()>37) {
					malcinsi=malcinsi.substring(0,36);
				}
			} catch (Exception e) {}
		
			
			Pozisyonkontsat[i]="";
			Pozisyonkontsat[i]=Pozisyonkontsat[i]+Pozisyon.tablodata[i][0]+" - ";
			Pozisyonkontsat[i]=Pozisyonkontsat[i]+Anasinif.konteynertipleri[Integer.parseInt(Pozisyon.tablodata[i][4])]+"   ";
			Pozisyonkontsat[i]=Pozisyonkontsat[i]+Pozisyon.tablodata[i][1]+"                            ";
			Pozisyonkontsat[i]=Pozisyonkontsat[i]+Pozisyon.tablodata[i][2]+" packs of "+malcinsi;
			
			toplamkg=toplamkg+Integer.parseInt(Pozisyon.tablodata[i][3].trim());
			toplamadet=toplamadet+Integer.parseInt(Pozisyon.tablodata[i][2].trim());
			
			for (int j=0 ; j<190-(Pozisyonkontsat[i].length()) ; j++) {
				Pozisyonkontsat[i]=Pozisyonkontsat[i]+" ";
			}
			
			Pozisyonkontsat[i]=Pozisyonkontsat[i]+Pozisyon.tablodata[i][3]+" KG ";
		    
		}
		
		return Pozisyonkontsat;
		}
	
}// sýnýf sonu
