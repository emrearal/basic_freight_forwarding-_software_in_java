package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.jdesktop.swingx.JXDatePicker;

public class Denizyoluanaekran implements ActionListener  {
	
	static JFrame frame,yavru;
	static JLabel yukLimantxt,bosLimantxt,pozisyontxt,yuktxt,raportarihtxt,pozaraligitxt,sadeceserbesttxt,
	pozetiketitxt,yuketiketitxt,istipitxt;	
	static JTextField pozaraligijt,pozaraligijt2,yukLimanjt,bosLimanjt,sirketadicey;
	
	static int yuklimkod=0,boslimkod=0,topfatsilsirketkodu=0;

	static JButton dugmeara,dugmegit,dugmeyeni,dugmeexcelrapor,dugmeLimsec,dugmevarLimsec,dugmeyenile;
	static JRadioButton pozisyon,yuk,tariharaligi,numaraaraligi;
	static JPanel panelalanlar,paneltablolar;
	static ButtonGroup grup,grup2;
	static JXDatePicker picker,picker2;
	static String[][] poztablodata,yuktablodata; 
	static String secilenpozisyon="",secilenyuk="";;
	static DateFormat df = new SimpleDateFormat("yyyy.MM.dd"); 
	static JCheckBox sadeceserbest;
	static JMenuBar menubar=new JMenuBar();
	static JMenu ontanimlilar,raporlar,fatura;
	static JMenuItem liman,teslimsekli,kent,odeme,gemi,sirketler,hatlar,faturakalemleri,karzararraporu,toplufaturasil,faturaarama;
	@SuppressWarnings("rawtypes")
	static JComboBox istipicb;
		
	private enum dugmecevabi  {
		menuhatlar,menusirketler,dugmeara,dugmegit,dugmeyeni,dugmeexcelrapor,dugmeLimsec,dugmevarLimsec,dugmeyenile,menuliman
		,menuteslimsekli,menukent,menuodeme,menugemi,menufaturakalemleri,menukarzararraporu,menutoplufaturasil,menufaturaarama	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void pozyukislemleri() {
	
		 frame= new JFrame("Denizyolu Ana Ekraný ");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setLocation(50,10);
		 frame.setSize(1250, 575);
	
		 panelalanlar=new JPanel();
		 panelalanlar.setLayout(null);
		 
		 istipitxt = new JLabel("Grup:");
		 istipitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 istipitxt.setBounds(150, 95, 100, 20);
		 panelalanlar.add(istipitxt);
	     	
		 istipicb = new JComboBox(Anasinif.istipi);
	     istipicb.setBounds(255,95, 100, 20);
	     istipicb.setSelectedIndex(0);
		 panelalanlar.add(istipicb);
		 
		 liman = new JMenuItem("Limanlar");
		 liman.addActionListener(new Denizyoluanaekran());
		 liman.setActionCommand(dugmecevabi.menuliman.name());
		 	 
		 teslimsekli = new JMenuItem("Teslim Þekli");
		 teslimsekli.addActionListener(new Denizyoluanaekran());
		 teslimsekli.setActionCommand(dugmecevabi.menuteslimsekli.name());
		 	 
		 kent = new JMenuItem("Kentler");
		 kent.addActionListener(new Denizyoluanaekran());
		 kent.setActionCommand(dugmecevabi.menukent.name());
		 
		 odeme = new JMenuItem("Ödeme Þekli");
		 odeme.addActionListener(new Denizyoluanaekran());
		 odeme.setActionCommand(dugmecevabi.menuodeme.name());
		 
		 gemi = new JMenuItem("Gemiler");
		 gemi.addActionListener(new Denizyoluanaekran());
		 gemi.setActionCommand(dugmecevabi.menugemi.name());
		 
		 sirketler = new JMenuItem("Þirketler");
		 sirketler.addActionListener(new Denizyoluanaekran());
		 sirketler.setActionCommand(dugmecevabi.menusirketler.name());
		 
		 hatlar = new JMenuItem("Hatlar");
		 hatlar.addActionListener(new Denizyoluanaekran());
		 hatlar.setActionCommand(dugmecevabi.menuhatlar.name());
		 
		 faturakalemleri = new JMenuItem("Fatura Kalemleri");
		 faturakalemleri.addActionListener(new Denizyoluanaekran());
		 faturakalemleri.setActionCommand(dugmecevabi.menufaturakalemleri.name());
		 
		 karzararraporu= new JMenuItem("Kar/Zarar,TEU ve Tonaj Raporu");
		 karzararraporu.addActionListener(new Denizyoluanaekran());
		 karzararraporu.setActionCommand(dugmecevabi.menukarzararraporu.name());
		 
		 toplufaturasil= new JMenuItem("Toplu Gider Faturasý Silme");
		 toplufaturasil.addActionListener(new Denizyoluanaekran());
		 toplufaturasil.setActionCommand(dugmecevabi.menutoplufaturasil.name());
		 
		 faturaarama= new JMenuItem("Fatura Arama");
		 faturaarama.addActionListener(new Denizyoluanaekran());
		 faturaarama.setActionCommand(dugmecevabi.menufaturaarama.name());
		 
		 ontanimlilar= new JMenu("Tanýmlý_Veriler");
		 ontanimlilar.add(liman);
		 ontanimlilar.add(teslimsekli);
		 ontanimlilar.add(kent);
		 ontanimlilar.add(odeme);
		 ontanimlilar.add(gemi);
		 ontanimlilar.add(sirketler);
		 ontanimlilar.add(hatlar);
		 ontanimlilar.add(faturakalemleri);
		 
		 raporlar= new JMenu ("Raporlar");
		 raporlar.add(karzararraporu);
		 
		 fatura= new JMenu ("Fatura");
		 fatura.add(toplufaturasil);
		 fatura.add(faturaarama);
		 
		 menubar.add(ontanimlilar);
		 menubar.add(raporlar);
		 menubar.add(fatura);
		 
		 
		 frame.setJMenuBar(menubar);
				 
		 dugmeLimsec=new JButton("Seç");
		 dugmeLimsec.setBounds(365, 45,60, 27);
		 dugmeLimsec.addActionListener(new Denizyoluanaekran());
		 dugmeLimsec.setActionCommand(dugmecevabi.dugmeLimsec.name());
		 panelalanlar.add(dugmeLimsec);
		 
		 dugmeyenile=new JButton("Yenile");
		 dugmeyenile.setBounds(850,45,70, 50);
		 dugmeyenile.addActionListener(new Denizyoluanaekran());
		 dugmeyenile.setActionCommand(dugmecevabi.dugmeyenile.name());
		 panelalanlar.add(dugmeyenile);
		 
		 dugmevarLimsec=new JButton("Seç");
		 dugmevarLimsec.setBounds(365,70,60, 27);
		 dugmevarLimsec.addActionListener(new Denizyoluanaekran());
		 dugmevarLimsec.setActionCommand(dugmecevabi.dugmevarLimsec.name());
		 panelalanlar.add(dugmevarLimsec);
		
		 dugmegit=new JButton("Git");
		 dugmegit.setBounds(220,10, 90, 20);
		 dugmegit.addActionListener(new Denizyoluanaekran());
		 dugmegit.setActionCommand(dugmecevabi.dugmegit.name());
		 panelalanlar.add(dugmegit);
		 
		 dugmeexcelrapor=new JButton("XLS Döküm");
		 dugmeexcelrapor.setBounds(320,10, 95, 20);
		 dugmeexcelrapor.addActionListener(new Denizyoluanaekran());
		 dugmeexcelrapor.setActionCommand(dugmecevabi.dugmeexcelrapor.name());
		 panelalanlar.add(dugmeexcelrapor);
		 
		 dugmeyeni=new JButton("Yeni");
		 dugmeyeni.setBounds(20, 10, 90, 20);
		 dugmeyeni.addActionListener(new Denizyoluanaekran());
		 dugmeyeni.setActionCommand(dugmecevabi.dugmeyeni.name());
		 panelalanlar.add(dugmeyeni);
			 
		 dugmeara=new JButton("Ara");
		 dugmeara.setBounds(120,10, 90, 20);
		 dugmeara.addActionListener(new Denizyoluanaekran());
		 dugmeara.setActionCommand(dugmecevabi.dugmeara.name());
		 panelalanlar.add(dugmeara);
		 
		 grup=  new ButtonGroup();
		 grup2=  new ButtonGroup();
		 
		 pozisyon=new JRadioButton();
		 pozisyon.setBounds(110,43,25,25);
		 pozisyon.setSelected(false);
		 grup.add(pozisyon);
		 panelalanlar.add(pozisyon);
		
		 yuk= new JRadioButton();
		 yuk.setBounds(110,68,25,25);
		 yuk.setSelected(true);
		 grup.add(yuk);
		 panelalanlar.add(yuk);
		 
		 tariharaligi=new JRadioButton();
		 tariharaligi.setBounds(500,35,20,20);
		 tariharaligi.setSelected(true);
		 panelalanlar.add( tariharaligi);
		 grup2.add(tariharaligi);
		 
		 numaraaraligi=new JRadioButton();
		 numaraaraligi.setBounds(500,83,20,20);
		 numaraaraligi.setSelected(true);
		 grup2.add(numaraaraligi);
		 panelalanlar.add(numaraaraligi);
		 
		 pozisyontxt = new JLabel("Pozisyon:");
		 pozisyontxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 pozisyontxt.setBounds(5, 45, 100, 20);
		 panelalanlar.add(pozisyontxt);
		 
		 yuktxt = new JLabel("Yük:");
		 yuktxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuktxt.setBounds(5, 70, 100, 20);
		 panelalanlar.add(yuktxt);
		 
		 yukLimantxt = new JLabel("Yükleme Limaný:");
		 yukLimantxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yukLimantxt.setBounds(150, 45, 100, 20);
		 yukLimanjt=new JTextField();
		 yukLimanjt.setBounds(255,45, 110, 27);
		 yukLimanjt.setEditable(false);
		 panelalanlar.add(yukLimantxt);
	     panelalanlar.add(yukLimanjt);
	    
	     bosLimantxt = new JLabel("Boþaltma Limaný:");
	     bosLimantxt.setHorizontalAlignment(SwingConstants.RIGHT);
	     bosLimantxt.setBounds(150, 70, 100, 20);
		 bosLimanjt=new JTextField();
		 bosLimanjt.setBounds(255,70, 110, 27);
		 bosLimanjt.setEditable(false);
		 panelalanlar.add(bosLimantxt);
	     panelalanlar.add(bosLimanjt);
		 
		 raportarihtxt = new JLabel("MBL veya HBL Tarihi Aralýðý:");
		 raportarihtxt.setBounds(525, 35, 250, 20);
		 panelalanlar.add(raportarihtxt);
		
		 picker = new JXDatePicker();                        // rapor için Tarih Seçici
		 picker.setDate(Calendar.getInstance().getTime()); 	
		 picker.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 picker.setVisible(true);
		 picker.setBounds(525,55,120,25);
		 picker.getEditor().setEditable(false);
		 panelalanlar.add(picker);
		 
		 picker2 = new JXDatePicker();                       
		 picker2.setDate(Calendar.getInstance().getTime()); 	
		 picker2.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 picker2.setVisible(true);
		 picker2.setBounds(645,55,120,25);
		 picker2.getEditor().setEditable(false);
		 panelalanlar.add(picker2);
		 
	     Date tarih2=picker2.getDate();    // ilk tarihi ikincisinin 30 gün öncesine ayarlama
	     Calendar cal = new GregorianCalendar();
	     cal.setTime(tarih2);
	     cal.add(Calendar.DAY_OF_MONTH, -30);
	     Date otuzgunoncesi = cal.getTime();
	     picker.setDate(otuzgunoncesi);
	
	     pozaraligitxt = new JLabel("Pozisyon veya Yük Numarasý Aralýðý:");
		 pozaraligitxt.setBounds(525, 83, 250, 20);
		 pozaraligijt=new JTextField();
		 pozaraligijt.setBounds(525,102,120,25);
		 pozaraligijt.setText("1");
		 panelalanlar.add(pozaraligitxt);
	     panelalanlar.add(pozaraligijt);
	     pozaraligijt2=new JTextField();
	     pozaraligijt2.setText("50");
		 pozaraligijt2.setBounds(645,102,120,25);
		 panelalanlar.add(pozaraligijt2);
		 
		 pozetiketitxt = new JLabel("***POZÝSYON LÝSTESÝ***");
		 pozetiketitxt.setBounds(245, 120, 250, 20);
		 panelalanlar.add(pozetiketitxt);
		 
		 yuketiketitxt = new JLabel("***YÜK LÝSTESÝ***");
		 yuketiketitxt.setBounds(865, 120, 250, 20);
		 panelalanlar.add(yuketiketitxt);
		 
		 sadeceserbest= new JCheckBox();
		 sadeceserbest.setBounds(1130,120,20,20);
		 sadeceserbest.setSelected(true);
		 panelalanlar.add(sadeceserbest);
		 sadeceserbesttxt = new JLabel("Sadece Serbest Yükler:");
		 sadeceserbesttxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sadeceserbesttxt.setBounds(930, 120, 200, 20);
		 panelalanlar.add(sadeceserbesttxt);
		 
		 paneltablolar=new JPanel();
		 
	     pozokuma("acilis"); 
		 yukokuma("acilis");
    
		 Pozlistesitablosu.tablemodeltablo();
	     Yuklistesitablosu.tablemodeltablo();
	     frame.add(panelalanlar, BorderLayout.CENTER);
	     frame.add(paneltablolar, BorderLayout.SOUTH);
		 frame.setVisible(true);
		
	}   //  metodu sonu

	@Override
	public void actionPerformed(ActionEvent e) {
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeLimsec.name())	{
		    	 if (yuklimkod!=0) {
		    		 yuklimkod=0;
		    		 yukLimanjt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("Pozyukleme", "Liman");
			     }
		   
		     if (e.getActionCommand()==dugmecevabi.dugmevarLimsec.name())	{
		    	 if (boslimkod!=0) {
		    		 boslimkod=0;
		    		 bosLimanjt.setText("");
		    		 return;
		    	 }
		    	 Ontanimliveriler.ontanimliveriler("Pozvaris", "Liman");
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeyenile.name())	{
		    	 
		    	 if (yuk.isSelected()) {
		    		 yukokuma("yenile");
		    		 Yuklistesitablosu.tableModel.fireTableDataChanged();
		    	 }else {
		    		 pozokuma("yenile");
		    		 Pozlistesitablosu.tableModel.fireTableDataChanged();
		    	 }
		    	  
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeara.name())	{
		    	 
		    	 String tarih1=df.format(picker.getDate());
			     String tarih2=df.format(picker2.getDate()); 
			     String aralik1=pozaraligijt.getText();
			     String aralik2=pozaraligijt2.getText();
		    	 
		    	 if (yuk.isSelected()) {
		    		 Yukara yukraporu = new Yukara(tariharaligi.isSelected(),tarih1,tarih2,aralik1,aralik2);
		    		 yukraporu.yukarama();
		    		 
		    	 } else {
		    		 //Pozarama.pozarama();
		    		 Pozara pozraporu= new Pozara (tariharaligi.isSelected(),tarih1,tarih2,aralik1,aralik2);
		    		 pozraporu.pozarama();
		    		 
		    	 }
		     }
		    
		     if (e.getActionCommand()==dugmecevabi.dugmegit.name())	{
		    	 
		    try {
				
		    	if (yuk.isSelected()) {
		    		if(secilenyuk.equals("") | Yuk.yukbayrak==true) {
			    		return;
			    	}
		    		Yuk.yuk(secilenyuk,"düzenle");
		    	} else {
		    		if(secilenpozisyon.equals("") | Pozisyon.pozbayrak==true) {
			    		return;
			    	}
		    		Pozisyon.pozisyon("düzenle");
		    	}
			} catch (ParseException e1) {e1.printStackTrace();
			}
		     }
		    
		     if (e.getActionCommand()==dugmecevabi.dugmeyeni.name())	{
		    	 if (yuk.isSelected() && Yuk.yukbayrak==false) {
		    		 try {
							Yuk.yuk("","yeni");
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
		    		 
		    	 } 

		    	 if (pozisyon.isSelected() && Pozisyon.pozbayrak==false) {
		    		 
		    		try {
						Pozisyon.pozisyon("yeni");
					} catch (ParseException e1) {
					
						e1.printStackTrace();
					}
		    	 }
		     }
		    
		     if (e.getActionCommand()==dugmecevabi.menusirketler.name())	{
		    	 
		    Sirketkartlari.sirketkartlari("anaekran"); 
		    	 
		     }  
		     
		     if (e.getActionCommand()==dugmecevabi.menuhatlar.name())	{
		    	 
		    	 Ontanimliveriler.ontanimliveriler("anaekran","Hat");
				    	 
				     }  
		     
		     if (e.getActionCommand()==dugmecevabi.menufaturakalemleri.name())	{
		    	 
		    	 Ontanimliveriler.ontanimliveriler("anaekran","Faturakalemleri");
				    	 
				     }  
		
		     if (e.getActionCommand()==dugmecevabi.menuliman.name())	{
		    	 
		    	 Ontanimliveriler.ontanimliveriler("anaekran", "Liman");
		    	 
		     } 
		     
		     if (e.getActionCommand()==dugmecevabi.menuteslimsekli.name())	{
		    	 
		    	 Ontanimliveriler.ontanimliveriler("anaekran", "T.Sekli");
		    	 
		     } 
		     
		     if (e.getActionCommand()==dugmecevabi.menukent.name())	{
		    	 
			     Ontanimliveriler.ontanimliveriler("anaekran", "Kent");
			    	 
			     } 
		     
		     if (e.getActionCommand()==dugmecevabi.menuodeme.name())	{
		    	 
			     Ontanimliveriler.ontanimliveriler("anaekran", "Odeme");
			    	 
			     } 
		 	
		     if (e.getActionCommand()==dugmecevabi.menugemi.name())	{
		    	 
			     Ontanimliveriler.ontanimliveriler("anaekran", "Gemi");
			    	 
			     } 
		     
		     if (e.getActionCommand()==dugmecevabi.menukarzararraporu.name())	{
		    	 
		    	 Karzararraporu kzrapor = new Karzararraporu();
		    	 kzrapor.karzararraporu();
			    	 
			     } 
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeexcelrapor.name())	{
		    	 
		    	String tarih1=df.format(picker.getDate());
		     	String tarih2=df.format(picker2.getDate()); 
		     	String aralik1=pozaraligijt.getText();
		     	String aralik2=pozaraligijt2.getText();
		     	
		    	Genelexcelraporu rapor = new Genelexcelraporu(pozisyon.isSelected(),tariharaligi.isSelected(),tarih1,tarih2,aralik1,aralik2);	
		    	rapor.genelexcelraporu();
			   
		    	}
			    	 
		     if (e.getActionCommand()==dugmecevabi.menutoplufaturasil.name())	{
		    
		    	 topfatsil();	    	 
		    	} 
		     
		     if (e.getActionCommand()==dugmecevabi.menufaturaarama.name())	{
				    
		    	 Faturaarama.faturaarama();	    	 
		    	} 
		  	
		} //dugme override sonu
	
	

	private void topfatsil() {  // toplu faturayý topluca silme metodu

	    topfatsilsirketkodu=0;
		
		yavru = new JFrame("Silinecek Toplu Fatura Bilgilerini Giriniz");
		yavru.setBounds(50, 50, 430, 250);
		yavru.setLayout(null);
		
		JLabel fatnotext = new JLabel ("Fatura no:");
		fatnotext.setBounds(30,30,75,30);
		
		JTextField fatnocey = new JTextField();
		fatnocey.setBounds(120, 30, 200, 30);
		
		JLabel sirketaditext = new JLabel ("Þirket Adý:");
		sirketaditext.setBounds(30,70,75,30);
		
		sirketadicey = new JTextField();
		sirketadicey.setBounds(120, 70, 200, 30);
		sirketadicey.setEditable(false);
		
		JLabel tarihtxt = new JLabel ("Fatura Tarihi:");
		tarihtxt.setBounds(30,110,75,30);
		
		JXDatePicker tarih = new JXDatePicker();                        // rapor için Tarih Seçici
		tarih.setDate(Calendar.getInstance().getTime()); 	
		tarih.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		tarih.setVisible(true);
		tarih.setBounds(120,110,120,25);
		tarih.getEditor().setEditable(false);
				
		JButton sirketadisec = new JButton("Seç");
		sirketadisec.setBounds(330, 70, 50, 30);
		sirketadisec.addActionListener(new ActionListener() {     
            public void actionPerformed(ActionEvent e) {
            	Sirketkartlari.sirketkartlari("toplufaturasilme");
            }
        });
		
		JButton onay = new JButton("Tamam");
		onay.setBounds(165, 150, 75, 30);
		onay.addActionListener(new ActionListener() {  // düðmeye basýnca olacaklar aþaðýda...
			
            public void actionPerformed(ActionEvent e) {
            	if (fatnocey.getText().length()<3 | topfatsilsirketkodu==0 ) {
            		Bilgipenceresi.anons("Fatura numarasý en az 3 karakter olmalý ve þirket seçimi yapýlmýþ olmalý");
            		return;
            	}
            	
            int c=0;	
           
            	try{     // veri arama                            
        			Class.forName("com.mysql.cj.jdbc.Driver");  
        			Connection con=DriverManager.getConnection(
        					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
        					
        					Statement stmt=con.createStatement();  
        			        ResultSet rs=stmt.executeQuery("Select * FROM fiskayitlari WHERE sirketkart_0kod ='" +topfatsilsirketkodu+
			        				"' and (tip ='fatalac"+fatnocey.getText()+"' OR tip='dekalac"+fatnocey.getText()+
			        				"') and aciklama='Toplu_Gider_Faturasi_Parcasi' and tarih='"+df.format(tarih.getDate())+"'"); 
        			        
        			while(rs.next()) {
        				 
        				c++;
        						}
        			con.close();  
        			
        			}catch(Exception e1){ System.out.println(e1);}
            	
            	if (c==0) {
            		Bilgipenceresi.anons("Kayýt Bulunamadý !");
            		return;
             	}
            	
            	Sileyimmi.sonkarar("toplufaturasilme"+c);
            	
            	if (!Sileyimmi.cevap.equals("evet")) {
            		return;
            	} 
            	
            	try {     // veri silme   
   			     
					Class.forName("com.mysql.cj.jdbc.Driver");
			        Connection connection = DriverManager.getConnection
			        		("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			        PreparedStatement st = connection.prepareStatement
			        		("DELETE FROM fiskayitlari WHERE sirketkart_0kod ='" +topfatsilsirketkodu+
			        				"' and (tip ='fatalac"+fatnocey.getText()+"' OR tip='dekalac"+fatnocey.getText()+
			        				"') and aciklama='Toplu_Gider_Faturasi_Parcasi' and tarih='"+df.format(tarih.getDate())+"'"); 
			        st.executeUpdate(); 
			        
			        Bilgipenceresi.anons(c+" adet kayýt silindi");
			  
				} catch(Exception z) {
			        System.out.println(z); }
	         }
        });
		
		yavru.add(onay);
		yavru.add(fatnocey);
		yavru.add(fatnotext);
		yavru.add(sirketadisec);
		yavru.add(sirketadicey);
		yavru.add(sirketaditext);
		yavru.add(tarih);
		yavru.add(tarihtxt);
		yavru.setVisible(true);
   }

	public static void yukokuma(String neyiokuyayim) {  
		
		yuk.setSelected(true);
		String komutseti="",komutsetiek="",kapsayisi="",kgmiktari="";
		int toplamkap=0,toplamkg=0;
		
    	String tarih1=df.format(picker.getDate());
    	String tarih2=df.format(picker2.getDate());
    	
    	if(sadeceserbest.isSelected()) {
    		komutseti="SELECT * FROM yukler where pozaalindi=0 and ";
    	}else {
    		komutseti="SELECT * FROM yukler where ";
    	}
    	
    	 	komutsetiek="istipi="+istipicb.getSelectedIndex()+" and hbltarihi between '"+tarih1+"' and '"+tarih2+"' ";
    	
		if (neyiokuyayim.equals("yenile")) {
			komutsetiek="";
		//
			 if (tariharaligi.isSelected()) {
			
				 komutsetiek="istipi="+istipicb.getSelectedIndex()+" and hbltarihi between '"+tarih1+"' and '"+tarih2+"' ";
			  
			  if (yuklimkod!=0) {
					
				  komutsetiek=komutsetiek+" and yuklemelimanikodu='"+yuklimkod+"' ";
			  
		       }
			  if (boslimkod!=0) {
					
				  komutsetiek=komutsetiek+" and varislimanikodu='"+boslimkod+"' ";
		      }  
	  }
	  
	  if (numaraaraligi.isSelected()) {
		 
			  komutsetiek="istipi="+istipicb.getSelectedIndex()+" and yukno between '"+pozaraligijt.getText()+"' and '"+pozaraligijt2.getText()+"' ";
			  if (yuklimkod!=0) {
					
				  komutsetiek=komutsetiek+" and yuklemelimanikodu='"+yuklimkod+"' ";
			  
		       }
			  if (boslimkod!=0) {
					
				  komutsetiek=komutsetiek+" and varislimanikodu='"+boslimkod+"' ";
		      }  
	  }
	 
	}
		int c=0;
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
			
			while(rs.next()) {
				 
				c++;
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		yuktablodata = new String [c][6];

	      int y=0;
			
			try{                                              
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
						Statement stmt=con.createStatement();  
				   		ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
				
				while(rs.next()) {
					
						yuktablodata[y][0]=rs.getString(1);
						yuktablodata[y][1]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(8)))[1];
						yuktablodata[y][2]=Ontanimliveriler.sirketkoducoz(Integer.parseInt(rs.getString(9)))[1];
						yuktablodata[y][3]=rs.getString(25);
						
						y++;	
					}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}

			for (int i=0 ;i<c; i++) {
			
			int konthamveriuzunlugu=yuktablodata[i][3].length();
			String kontsayisi=yuktablodata[i][3].substring(konthamveriuzunlugu-24, konthamveriuzunlugu-22);
				
			
			for (int g=1 ; g<Integer.parseInt(kontsayisi)+1; g++) {
				
				
				kapsayisi=yuktablodata[i][3].substring(((g*24)-12),((g*24))-6);  //Kont kap
				toplamkap=toplamkap+Integer.parseInt(kapsayisi.trim()); 
				kgmiktari=yuktablodata[i][3].substring(((g*24)-6),(g*24)-1); //kont kg
				toplamkg=toplamkg+Integer.parseInt(kgmiktari.trim()); 
				
			}
			yuktablodata[i][3]=String.valueOf(toplamkap); // kont sayisi
			yuktablodata[i][4]=String.valueOf(toplamkg); // kont sayisi
			yuktablodata[i][5]=kontsayisi; // kont sayisi
			
			toplamkg=0;toplamkap=0;
			
		}
			
		}  //  metod sonu
	
public static void pozokuma(String neyiokuyayim) {  
	
		pozisyon.setSelected(true);
		String komutseti="",komutsetiek="";
	 	String tarih1=df.format(picker.getDate());
    	String tarih2=df.format(picker2.getDate());
    	komutseti="SELECT * FROM pozisyonlar where ";
    	komutsetiek="istipi="+istipicb.getSelectedIndex()+" and mbltarih between '"+tarih1+"' and '"+tarih2+"' ";
    	
		if (neyiokuyayim.equals("yenile")) {
			komutsetiek="";
		 
		  if (tariharaligi.isSelected()) {
		
				  komutsetiek="istipi="+istipicb.getSelectedIndex()+" and mbltarih between '"+tarih1+"' and '"+tarih2+"' ";
				  
				  if (yuklimkod!=0) {
						
					  komutsetiek=komutsetiek+" and yuklemelimanikodu='"+yuklimkod+"' ";
				  
			       }
				  if (boslimkod!=0) {
						
					  komutsetiek=komutsetiek+" and varislimanikodu='"+boslimkod+"' ";
			      }  
		  }
		  
		  if (numaraaraligi.isSelected()) {
				  komutsetiek="istipi="+istipicb.getSelectedIndex()+" and pozno between '"+pozaraligijt.getText()+"' and '"+pozaraligijt2.getText()+"' ";
				  if (yuklimkod!=0) {
						
					  komutsetiek=komutsetiek+" and yuklemelimanikodu='"+yuklimkod+"' ";
				  
			       }
				  if (boslimkod!=0) {
						
					  komutsetiek=komutsetiek+" and varislimanikodu='"+boslimkod+"' ";
			      }  
		  }
			
		}
		
		int c=0;
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
			
			while(rs.next()) {
				 
				c++;
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
		
		poztablodata = new String [c][7];

	      int y=0;
			
			try{                                              
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
						"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
						Statement stmt=con.createStatement();  
				   		ResultSet rs=stmt.executeQuery(komutseti+komutsetiek); 
				
				while(rs.next()) {
					
						poztablodata[y][0]=rs.getString(1);
						poztablodata[y][1]=String.valueOf(pozdakackonteynervar(rs.getString(1)));
						poztablodata[y][2]=Ontanimliveriler.gemikoducoz(Integer.parseInt(rs.getString(9)));
						poztablodata[y][3]=rs.getString(10);
						poztablodata[y][4]=Ontanimliveriler.limankoducoz(Integer.parseInt(rs.getString(24)));
						poztablodata[y][5]=Ontanimliveriler.limankoducoz(Integer.parseInt(rs.getString(26)));
						poztablodata[y][6]=rs.getString(6);
						
						y++;	
				}
				con.close();  
				
				}catch(Exception e){ System.out.println(e);}
			
				}  //  metod sonu
public static int pozdakackonteynervar(String pozno) {

	String konteynerhamveri="";
	int pozdakikonteynersayisi=0;
	
	@SuppressWarnings("unused")
	int pozdakiyukadedi=0;
	
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yuklendigipoz='"+pozno+"';"); 
		
		while(rs.next()) {
			konteynerhamveri=rs.getString(25);
			String kontsayisi=konteynerhamveri.substring(konteynerhamveri.length()-24, konteynerhamveri.length()-22);
			pozdakikonteynersayisi=pozdakikonteynersayisi+Integer.parseInt(kontsayisi);
			pozdakiyukadedi++;
		}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}
	return pozdakikonteynersayisi;  
}

@SuppressWarnings("serial")
public static class Pozlistesitablosu extends JPanel {
	static Tablomodeli tableModel;
	static JScrollPane scrollPane;

	public Pozlistesitablosu() {
        initializePanel();
    }

    private void initializePanel() {
       
       tableModel = new Tablomodeli();

        JTable table = new JTable(tableModel);
        
        table.setFillsViewportHeight(true);
      	   
	    table.getColumnModel().getColumn(0).setPreferredWidth(50);
	    table.getColumnModel().getColumn(1).setPreferredWidth(50);
	    table.getColumnModel().getColumn(2).setPreferredWidth(120);
	    table.getColumnModel().getColumn(3).setPreferredWidth(90);
	    table.getColumnModel().getColumn(4).setPreferredWidth(110);
	    table.getColumnModel().getColumn(5).setPreferredWidth(110);
	    table.getColumnModel().getColumn(6).setPreferredWidth(150);
	    
	    table.setFocusable(false);
	      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2) {     
	             //  JTable target = (JTable)me.getSource(); Bunlara þimdilik gerek yok. sadece çift klik yeterli. 2 listener kullanýyoruz
	              // int row = target.getSelectedRow();      
	              // int column = target.getSelectedColumn(); 
	              //JOptionPane.showMessageDialog(null, table.getValueAt(row, 0));
	               if( Pozisyon.pozbayrak==true) {
			    		return;
			    	} else {
			    		try {
							Pozisyon.pozisyon("düzenle");
						} catch (ParseException e) {	e.printStackTrace();
						}
			    	}
		    		
	            }
	         }
	      });
	   
	    
	    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
           
        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
      
        		  int selectedRow     = table.getSelectedRow();
          		Denizyoluanaekran.secilenpozisyon = (String) table.getValueAt(selectedRow,0 );
          		          		
        	  }
          }
     });
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600,360));

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new Pozlistesitablosu();
        panel.setOpaque(true);

     
       Denizyoluanaekran.paneltablolar.add(scrollPane,BorderLayout.WEST);  
    }

    class Tablomodeli extends AbstractTableModel {
     
        private  String[] columnNames = { "POZ#","K.Adet","Gemi","Sefer","Yük.Lim.","Var.Lim.","MBL#" };

        public int getRowCount() {
           
        	return Denizyoluanaekran.poztablodata.length;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {// tabloda seçim yapýnca nedense hata veriyor. 
        	if (rowIndex==-1) {
        		rowIndex=Denizyoluanaekran.poztablodata.length-1;
        		if (rowIndex==-1) {
        			rowIndex=0;
        		}
        		        		
        	}
            return Denizyoluanaekran.poztablodata[rowIndex][columnIndex];
        }
    }
    
    public static void tablemodeltablo () {
    	    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showFrame();
            }
        });
    }
 
}
    
    @SuppressWarnings("serial")
    public static class Yuklistesitablosu extends JPanel {
    	static Tablomodeli tableModel;
    	static JScrollPane scrollPane;

    	public Yuklistesitablosu() {
            initializePanel();
        }

        private void initializePanel() {
           
           tableModel = new Tablomodeli();

            JTable table = new JTable(tableModel);
            
            table.setFillsViewportHeight(true);
      	   
    	    table.getColumnModel().getColumn(0).setPreferredWidth(50);
    	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
    	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
    	    table.getColumnModel().getColumn(3).setPreferredWidth(55);
    	    table.getColumnModel().getColumn(4).setPreferredWidth(50);
    	    table.getColumnModel().getColumn(5).setPreferredWidth(45);
    	    
    	    table.setFocusable(false);
    	      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
    	         public void mouseClicked(MouseEvent me) {
    	            if (me.getClickCount() == 2) {     
    	             //  JTable target = (JTable)me.getSource(); Bunlara þimdilik gerek yok. sadece çift klik yeterli. 2 listener kullanýyoruz
    	              // int row = target.getSelectedRow();      
    	              // int column = target.getSelectedColumn(); 
    	              //JOptionPane.showMessageDialog(null, table.getValueAt(row, 0));
    	               if( Yuk.yukbayrak==true) {
    			    		return;
    			    	} else {
    			    		try {
    			    			Yuk.yuk(Denizyoluanaekran.secilenyuk,"düzenle");
    						} catch (ParseException e) { e.printStackTrace();
    						}
    			    	}
    		    		
    	            }
    	         }
    	      });
    	   	    
    	    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
              public void valueChanged(ListSelectionEvent e) {
               
            	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
          
            		  int selectedRow     = table.getSelectedRow();
              		Denizyoluanaekran.secilenyuk = (String) table.getValueAt(selectedRow,0 );
              	
            	  }
              }
         });
    	    
    	    scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(600,360));

            this.setLayout(new BorderLayout());
            this.add(scrollPane, BorderLayout.CENTER);
        }

        public static void showFrame() {
            JPanel panel = new Yuklistesitablosu();
            panel.setOpaque(true);

            Denizyoluanaekran.paneltablolar.add(scrollPane,BorderLayout.EAST); 
        }

        class Tablomodeli extends AbstractTableModel {
         
            private  String[] columnNames = { "Yük #", "Gönderici","Alýcý","Kap","Kg","K.Adet" };

            public int getRowCount() {
               
            	return Denizyoluanaekran.yuktablodata.length;
            }

            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
            
                return getValueAt(0, columnIndex).getClass();
            }

            public Object getValueAt(int rowIndex, int columnIndex) {// tabloda seçim yapýnca nedense hata veriyor. 
            	if (rowIndex==-1) {
            		rowIndex=Denizyoluanaekran.yuktablodata.length-1;
            		if (rowIndex==-1) {
            			rowIndex=0;
            		}
            		        		
            	}
                return Denizyoluanaekran.yuktablodata[rowIndex][columnIndex];
            }
        }

        public static void tablemodeltablo () {
        	    	
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    showFrame();
                }
            });
        }
    }


	} // sinif sonu ;