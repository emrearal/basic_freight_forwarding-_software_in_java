package heanalikibin;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.pdf.BaseFont;

public class Fatyap {
	
	static PdfDocument hazirlanacakfatura;
	static String konteynerno,toplamagirlik,toplamadet;
	
	public static void fatyap(String fisno) {
		DecimalFormat f = new DecimalFormat("##.00");
		
		String bosfaturadosyasiyoluveadi="C:\\Users\\emrea\\Desktop\\bosearsiv.pdf";
		String[][] faturasatirlari = new String [50][2];
		String[] pozdatalari = new String [34];
		String musteriadi="",tarih="",faturano="",tutar="",aciklama="",kdvtutari="",kdvorani="",
		parabirimi="",pozno="",yukno="",kur="",faturakalemi="",kdvharictutar="",tltutar="",tlkdvtutari=""
		,tlkdvharictutar="",refno="",gonderici="",alici="",seferno="",gemiadi="",musteriadresi="",musteriulke=""
		,musterivergidairesi="",musterivergino="",musterisehir="",orjinaltutar="",orjinaltltutar="";
		
		konteynerno=toplamagirlik=toplamadet="";
		
		try{               // veritaban�na ba�lan�p ilgili fis kayd� okunuyor                                 
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("SELECT * FROM fiskayitlari where fisno='"+fisno+"';"); 
			
			while(rs.next()) {
				musteriadi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[1] ;
				tarih=Ontanimliveriler.tarihiterscevir(rs.getString(4));
				faturano=rs.getString(5).substring(7);
				orjinaltutar=tutar=rs.getString(6);
				if ( (tutar.substring(tutar.length()-2,tutar.length()).equals("00"))){
					tutar=tutar.substring(0,(tutar.length()-3));
				}
				aciklama=rs.getString(7);
				kdvtutari=rs.getString(8);
				kdvharictutar=String.valueOf(Double.parseDouble(tutar)-Double.parseDouble(kdvtutari));
				
				if ( (kdvharictutar.substring(kdvharictutar.length()-1,kdvharictutar.length()).equals("0"))){
					kdvharictutar=kdvharictutar.substring(0,(kdvharictutar.length()-2));
				}
				
				
			
	            int kdvoranint = (int) Math.round((Double.parseDouble(kdvtutari) /Double.parseDouble(kdvharictutar))*100);
				
				kdvorani="%"+kdvoranint;
				
				parabirimi=Ontanimliveriler.parabirimikoducoz(rs.getString(9));
				pozno=rs.getString(10);
				yukno=rs.getString(11);
				kur=rs.getString(12);
				faturakalemi=Ontanimliveriler.faturakalemikoducoz(Integer.parseInt(rs.getString(13))) ;
				orjinaltltutar=tltutar=f.format(Double.parseDouble(tutar)*Double.parseDouble(kur));
				
				tlkdvtutari=f.format(Double.parseDouble(kdvtutari)*Double.parseDouble(kur));
				tlkdvharictutar=f.format(Double.parseDouble(kdvharictutar)*Double.parseDouble(kur));
				musteriadresi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[3] ;
				
				musterisehir=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[4] ;
				musteriulke=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[5] ;
				musterivergidairesi=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[6] ;
				musterivergino=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(3)))[7] ;
				
				try {
					if (musteriadresi.length()>60) {  //adres 60 karaklterden uzunsa ilk 60 karakteri al
						musteriadresi=musteriadresi.substring(0,60);
					}
					if (musteriadi.length()>60) {  //�nvan 60 karaklterden uzunsa ilk 60 karakteri al
						musteriadi=musteriadi.substring(0,60);
					}
					if (aciklama.length()>60) {  //a��klama 60 karakterden uzunsa ilk 60 karakteri al
						aciklama=aciklama.substring(0,60);
					}
				} catch (Exception e) {}
				
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		
		
		try{        // pozdatalar�n� �ekelim                                       
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
					
			        ResultSet rs=stmt.executeQuery("SELECT * FROM pozisyonlar where pozno='"+pozno+"';"); 
			
			while(rs.next()) {
				
				for (int j=0; j<34; j++) {
					pozdatalari[j]=rs.getString(j+1);
				}
				
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		
		
		if (!yukno.equals("0")) {  // e�er fatura y�k alt�nda kesilmi�se
			
			try{                                           
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
			"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
						
						Statement stmt=con.createStatement();  
				        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yukno='"+yukno+"';"); 
				
				while(rs.next()) {
					
					refno="Pozno:"+pozno+"  Y�k no:"+yukno;
					gonderici=Ontanimliveriler.sirketkoducoz(Integer.parseInt( rs.getString(8)))[1];
					alici=Ontanimliveriler.sirketkoducoz(Integer.parseInt( rs.getString(9)))[1];	
					konteynerno=rs.getString(25).substring(2,12);
					
					int konteynersayisi=rs.getString(25).length()/24;
					String[][] tablodata = new String [konteynersayisi][5];
					
					int toplamkg=0;int toplamkap=0;
					for (int i=1; i<konteynersayisi+1; i++) {
						tablodata[i-1][0]=rs.getString(25).substring(((i*24)-24),((i*24)-22)); // kont s�ra no
						tablodata[i-1][1]=rs.getString(25).substring(((i*24)-22),((i*24))-12);   // Kont No
						tablodata[i-1][2]=rs.getString(25).substring(((i*24)-12),((i*24))-6);  //Kont kap
						toplamkap=toplamkap+Integer.parseInt(tablodata[i-1][2].trim()); 
						tablodata[i-1][3]=rs.getString(25).substring(((i*24)-6),(i*24)-1); //kont kg
						toplamkg=toplamkg+Integer.parseInt(tablodata[i-1][3].trim()); 
						tablodata[i-1][4]=rs.getString(25).substring((i*24)-1,i*24); 
					}
					toplamagirlik=String.valueOf(toplamkg);
					toplamadet=String.valueOf(toplamkap);
					
				}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}  
			
		} else {  // e�er poz alt�ndan kesilmi�se
			
			toplamagirlik=String.valueOf(Pozisyon.pozdakikonteynerleribul(pozno)[0]) ;
			toplamadet=String.valueOf(Pozisyon.pozdakikonteynerleribul(pozno)[3]) ;
			refno="Pozisyon No"+pozno;
			gonderici=Ontanimliveriler.sirketkoducoz(Integer.parseInt( pozdatalari[30]))[1];
			alici=Ontanimliveriler.sirketkoducoz(Integer.parseInt( pozdatalari[31]))[1];
			
		}
		
		seferno= pozdatalari[9];
		gemiadi=Ontanimliveriler.gemikoducoz(Integer.parseInt(pozdatalari[8]));
		
		faturasatirlari[0][0] = "018,725"; faturasatirlari[0][1] =musteriadi;  // M��teri ad�
		faturasatirlari[1][0] = "018,715"; faturasatirlari[1][1] =musteriadresi; // M��teri adres1
		faturasatirlari[2][0] = "018,705"; faturasatirlari[2][1] =musterisehir;   // M��teri �ehir
		faturasatirlari[3][0] = "018,695"; faturasatirlari[3][1] =musteriulke;    // M��teri �lke
		faturasatirlari[4][0] = "018,685"; faturasatirlari[4][1] ="Vergi Dairesi: "+musterivergidairesi; //M��teri vergi dairesi
		faturasatirlari[5][0] = "018,675"; faturasatirlari[5][1] ="Vergi No:" +musterivergino;       // M��teri vergi no
		
		faturasatirlari[6][0] = "453,730"; faturasatirlari[6][1] =tarih;   // fatura tarihi
		faturasatirlari[7][0] = "453,715"; faturasatirlari[7][1] =faturano;  // fatura no
		faturasatirlari[8][0] = "453,685"; faturasatirlari[8][1] ="E-AR��V FATURA";  // fatura senaryosu ( ear�iv temel, ticari)
		faturasatirlari[9][0] = "453,670"; faturasatirlari[9][1] ="�ST�SNA";  // fatura tipi ( istisna, normal, tevkifatl� vs) 
		
		faturasatirlari[10][0] = "045,598"; faturasatirlari[10][1] =faturakalemi;  // Fatura kalemi
		faturasatirlari[11][0] = "330,593"; faturasatirlari[11][1] ="1"; // miktar
		faturasatirlari[12][0] = "351,593"; faturasatirlari[12][1] =tutar; // birim fiyat
		faturasatirlari[13][0] = "355,583"; faturasatirlari[13][1] =parabirimi; // birim fiyat d�vizi
		faturasatirlari[14][0] = "395,593"; faturasatirlari[14][1] =tutar; // toplam tutar
		faturasatirlari[15][0] = "400,583"; faturasatirlari[15][1] =parabirimi; // toplam tutar d�vizi
		faturasatirlari[16][0] = "450,593"; faturasatirlari[16][1] =kdvorani; // kdv oran�
		faturasatirlari[17][0] = "470,593"; faturasatirlari[17][1] =kdvtutari; // kdv tutar�
		
		faturasatirlari[18][0] = "507,583"; faturasatirlari[18][1] =""; // kdv istisnas� maddesi	
		
		if (kdvtutari.equals("0.00")) {
			faturasatirlari[18][0] = "507,583"; faturasatirlari[18][1] ="Uluslararas� Ta��ma."; // kdv istisnas� maddesi	
		}
		
		faturasatirlari[19][0] = "345,561"; faturasatirlari[19][1] =kur; //d�viz kuru
		faturasatirlari[20][0] = "345,546"; faturasatirlari[20][1] =tltutar; //mal hizmet toplam� tl
		
		faturasatirlari[21][0] = "345,530"; faturasatirlari[21][1] =tlkdvharictutar; //kdv matrah�  tl
		if(kdvtutari.equals("0.00")) {
			faturasatirlari[21][0] = "345,530"; faturasatirlari[21][1] ="0";	
		}
		
		faturasatirlari[22][0] = "345,515"; faturasatirlari[22][1] =tlkdvharictutar; //vergi hari� tutar tl
		faturasatirlari[23][0] = "345,500"; faturasatirlari[23][1] =tlkdvtutari; //hesaplanan kdv tl
		faturasatirlari[24][0] = "345,484"; faturasatirlari[24][1] =tltutar; //vergiler dahil toplam tutar tl
		faturasatirlari[25][0] = "345,468"; faturasatirlari[25][1] =tltutar; //�denecek tutar tl
		
		faturasatirlari[26][0] = "527,561"; faturasatirlari[26][1] =tutar+" "+parabirimi; //mal hizmet toplam� YP
		
		faturasatirlari[27][0] = "527,546"; faturasatirlari[27][1] =kdvharictutar+" "+parabirimi; //kdv matrah�  YP
		if(kdvtutari.equals("0.00")) {
			faturasatirlari[27][0] = "527,546"; faturasatirlari[27][1] ="0 "+parabirimi; 
		}
		
		faturasatirlari[28][0] = "527,530"; faturasatirlari[28][1] =kdvharictutar+" "+parabirimi; //vergi hari� tutar YP
		faturasatirlari[29][0] = "527,515"; faturasatirlari[29][1] =kdvtutari+" "+parabirimi; //hesaplanan kdv YP
		faturasatirlari[30][0] = "527,500"; faturasatirlari[30][1] =tutar+" "+parabirimi; //vergiler dahil toplam tutar YP
		faturasatirlari[31][0] = "527,484"; faturasatirlari[31][1] =tutar+" "+parabirimi; //�denecek tutar YP
		
		faturasatirlari[32][0] = "045,445"; faturasatirlari[32][1] ="TC ALTERNAT�FBANK A.�. TGBATRISXXX  "; //banka hesapno 1
		faturasatirlari[33][0] = "045,435"; faturasatirlari[33][1] ="TRY IBAN : TR69 0012 4000 0004 0937 7000 01  / Alternatif Bank"; //banka hesapno 2
		faturasatirlari[34][0] = "045,425"; faturasatirlari[34][1] ="USD IBAN : TR69 0012 4000 0004 0937 7000 02  / Alternatif Bank"; //banka hesapno 3
		faturasatirlari[35][0] = "045,415"; faturasatirlari[35][1] ="EUR IBAN : TR69 0012 4000 0004 0937 7000 03  / Alternatif Bank"; //banka hesapno 4
		faturasatirlari[36][0] = "045,405"; faturasatirlari[36][1] ="GBP IBAN : TR69 0012 4000 0004 0937 7000 04  / Alternatif Bank"; //banka hesapno 5
		
		faturasatirlari[37][0] = "087,384"; faturasatirlari[37][1] ="Elektronik";   // g�nderim �ekli
		faturasatirlari[38][0] = "087,374"; faturasatirlari[38][1] =refno;  // ref no pozisyon
		faturasatirlari[39][0] = "087,363"; faturasatirlari[39][1] =seferno;   // sefer no 
		faturasatirlari[40][0] = "087,352"; faturasatirlari[40][1] =gonderici;   // g�nderici 
		faturasatirlari[41][0] = "087,341"; faturasatirlari[41][1] =alici;   // al�c�
		faturasatirlari[42][0] = "087,330"; faturasatirlari[42][1] =gemiadi;  // gemi ad� 
		faturasatirlari[43][0] = "087,320"; faturasatirlari[43][1] =konteynerno;  // konteyner num 
		faturasatirlari[44][0] = "087,309"; faturasatirlari[44][1] =toplamagirlik;  // toplam a��rl�k
		faturasatirlari[45][0] = "087,298"; faturasatirlari[45][1] =toplamadet;  // toplam adet
		
		faturasatirlari[46][0] = "087,288"; faturasatirlari[46][1] =Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltltutar)[0],"TR")+" T�rk Liras� " +
				Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltltutar)[1],"TR")+" Kuru� ";  // yaln�z tl yaz� ile
		
		faturasatirlari[47][0] = "087,277"; faturasatirlari[47][1] =Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltutar)[0],"TR")+" "+parabirimi +" "+
				Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltutar)[1],"TR")+" Cent "; // yaln�z d�viz yaz� ile
		if (parabirimi.equals("TL")) {
			faturasatirlari[47][0] = "087,277"; faturasatirlari[47][1]=Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltltutar)[0],"TR")+" T�rk Liras� " +
					Ontanimliveriler.sayiyiyaziyacevir(lirayikurusuayir(orjinaltltutar)[1],"TR")+" Kuru� ";  // yaln�z tl yaz� ile	
		}
		
		faturasatirlari[48][0] = "135,255"; faturasatirlari[48][1] ="";  // vergi muafiyet sebebi
		
		if (kdvtutari.equals("0.00")) {
			faturasatirlari[48][0] = "135,255"; faturasatirlari[48][1] ="311-235 Uluslararas� Ta��mac�l�k istisnas�";  // vergi muafiyet sebebi
		}
		
		faturasatirlari[49][0] = "045,588"; faturasatirlari[49][1] ="A��klama:("+aciklama+")";  // A��klama
	
		String hazirlanacakfaturayoluveadi="C:\\Users\\emrea\\Desktop\\"+faturano+".pdf";  // kaydedilecek dosya ad� = fatura no 
		
	try {    // pdf edit i�lemleri
		hazirlanacakfatura = new PdfDocument(new PdfReader(bosfaturadosyasiyoluveadi), 
				new PdfWriter(hazirlanacakfaturayoluveadi));
	} catch (FileNotFoundException e1) {
		Bilgipenceresi.anons("Dosya bulunamad�");
		e1.printStackTrace();
	} catch (IOException e1) {
		Bilgipenceresi.anons("Dosya Veri Hatas� !");
		e1.printStackTrace();
	}
	
	for (int i=0; i<faturasatirlari.length;i++) {  //  d�ng�ye girerek her sat�r� tek tek bas�yoruz. 
		
		PdfCanvas canvas = new PdfCanvas(hazirlanacakfatura.getFirstPage());
		canvas.beginText();
		
		int x=Integer.parseInt(faturasatirlari[i][0].substring(0,3));
		int y=Integer.parseInt(faturasatirlari[i][0].substring(4));
		
		try {
			canvas.setFontAndSize(PdfFontFactory.createFont("C:\\Windows\\Fonts" + "\\Arial.TTF",  // t�rk�e karakter basmak i�in gerekli
	                BaseFont.IDENTITY_H, BaseFont.EMBEDDED),8);
		} catch (IOException e1) {
			Bilgipenceresi.anons("Dosya Veri Hatas� !");
			e1.printStackTrace();
		}

		canvas.moveText(x,y);    // yazma konumu
		try {
			canvas.showText(faturasatirlari[i][1]);   // yazd�r
		} catch (Exception e) {}
		canvas.endText();  // kaydet 
		
	} // d�ng� sonu 
		
	hazirlanacakfatura. close();	 // kapat
	
	try {  // belki ileride file �uz�r ile farkl� yere kaydedip a�ma yap�labilir . default masa �st� de iyi ��z�m asl�nda
		File a = new File(hazirlanacakfaturayoluveadi);  // yaz�lan pdf dosyas�n� (faturay�)  a�
		Desktop.getDesktop().open(a);
	} catch (IOException e) {}
	
} // Metod sonu 
	
	
	
	private static String[] lirayikurusuayir (String neyiayirayim) {
		
		String[] ayrilmishali= new String [2];
		ayrilmishali[0]= neyiayirayim.substring(0,neyiayirayim.length()-3);
		ayrilmishali[1]= neyiayirayim.substring(neyiayirayim.length()-2,neyiayirayim.length());
		
		return ayrilmishali;
	} // metod sonu
	
} // S�n�f sonu 