package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXDatePicker;

public class Yuk implements ActionListener  {
	
	static JFrame frame;
	static JLabel yuknotxt,poznotxt,kompletxt,parsiyeltxt,mblnotxt,hblnotxt,musteritxt,ureticitxt,gondericitxt,alicitxt,notify1txt,notify2txt,
	              ydacentetxt,faturakesilenfirmatxt,turkcemaladitxt,yabancimaladitxt,teslimseklitxt,odemeseklitxt,odemekentitxt,
	              malbedelitxt,yuklemekentitxt,yuklemelimanitxt,aktarmalimanitxt,varislimanitxt,sonvariskentitxt,varisulkesitxt,
	              toplambrutkgtxt,toplamnetkgtxt,toplamhacimtxt,toplamkaptxt,cwtxt,istipitxt;
	
	static JTextField mblnojt,hblnojt,musterijt,ureticijt,gondericijt,alicijt,notify1jt,notify2jt,
    ydacentejt,faturakesilenfirmajt,turkcemaladijt,yabancimaladijt,teslimseklijt,odemeseklijt,odemekentijt,
    malbedelijt,yuklemekentijt,yuklemelimanijt,aktarmalimanijt,varislimanijt,sonvariskentijt,varisulkesijt,
    toplambrutkgjt,toplamnetkgjt,toplamhacimjt,toplamkapjt,cwjt,yuknojt,poznojt,
    mblnotarihjt;
		
	static JButton dugmekaydet,dugmeara,dugmesil,dugmeyeni,dugmekonteynerekle,dugmekonteynerduzenle,dugmekonteynersil,dugmehblbas;
	
	static JButton musteriSECdugme,ureticiSECdugme,gondericiSECdugme,aliciSECdugme,notify1SECdugme,notify2SECdugme,
    ydacenteSECdugme,faturakesilenfirmaSECdugme,yuklemekentiSECdugme,yuklemelimaniSECdugme,aktarmalimaniSECdugme,varislimaniSECdugme,sonvariskentiSECdugme,
    teslimsekliSECdugme,odemesekliSECdugme,odemekentiSECdugme;
	
	static int musterikodu ,ureticikodu ,alicikodu=0 ,notify1kodu  ,gondericikodu=0, notify2kodu  ,
	ydacentekodu  , faturakesilenfirmakodu ,teslimseklikodu ,odemeseklikodu ,
	odemekentikodu ,yuklemekentikodu , yuklemelimanikodu , aktarmalimanikodu ,
	varislimanikodu , sonvariskentikodu;
	
	static boolean yukbayrak=false;
	
	static String hangiyukugosterem="",neyapam="";
	
	static String duzenlenecekyuk[];
	
	static JTable konttablosu;
	static JRadioButton komple,parsiyel;
	static JPanel panelalanlar;
	static JScrollPane sp;
	static ButtonGroup grup;
	static JXDatePicker picker;
	static String[][] tablodata;
	static String secilenkonteyner="";
	static TableModel tableModel;
	static Container aticine;
	static DateFormat df = new SimpleDateFormat("yyyy.MM.dd"); 
	@SuppressWarnings("rawtypes")
	static JComboBox istipicb;
	
	private enum dugmecevabi {
		dugmekaydet,dugmeara,dugmesil,dugmeyeni,dugmekonteynerekle,dugmekonteynerduzenle,dugmekonteynersil,musteriSECdugme,dugmehblbas,
		ureticiSECdugme,gondericiSECdugme,aliciSECdugme,notify1SECdugme,notify2SECdugme,
	    ydacenteSECdugme,faturakesilenfirmaSECdugme,yuklemekentiSECdugme,yuklemelimaniSECdugme,aktarmalimaniSECdugme,varislimaniSECdugme,sonvariskentiSECdugme,
	    teslimsekliSECdugme,odemesekliSECdugme,odemekentiSECdugme;
		}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void yuk(String yuknum, String yapilacakislem) throws ParseException {
		
		yukbayrak=true;
		
		musterikodu=0; ureticikodu=0;alicikodu=0; notify1kodu=0;gondericikodu=0; notify2kodu=0;
		ydacentekodu=0; faturakesilenfirmakodu=0;teslimseklikodu=0;odemeseklikodu=0;
		odemekentikodu=0;yuklemekentikodu=0; yuklemelimanikodu=0; aktarmalimanikodu=0;
		varislimanikodu=0; sonvariskentikodu=0;
		 
		hangiyukugosterem=yuknum;
		neyapam=yapilacakislem;
		 
		 frame= new JFrame("Yük Penceresi");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setLocation(50,150);
		 frame.setSize(1250, 575);
		 
		 frame.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                yukbayrak=false;
	                e.getWindow().dispose();
	            }
	        });
	
		 panelalanlar=new JPanel();
		 panelalanlar.setLayout(null);
		 		 
		 dugmekonteynerekle=new JButton("Konteyner Ekle");
		 dugmekonteynerekle.setBounds(500, 450, 140, 20);
		 dugmekonteynerekle.addActionListener(new Yuk());
		 dugmekonteynerekle.setActionCommand(dugmecevabi.dugmekonteynerekle.name());
		 
		 dugmekonteynerduzenle=new JButton("Konteyner Düzenle");
		 dugmekonteynerduzenle.setBounds(662,450,140, 20);
		 dugmekonteynerduzenle.addActionListener(new Yuk());
		 dugmekonteynerduzenle.setActionCommand(dugmecevabi.dugmekonteynerduzenle.name());
		 
		 dugmekonteynersil=new JButton("Konteyner Sil");
		 dugmekonteynersil.setBounds(824,450,140, 20);
		 dugmekonteynersil.addActionListener(new Yuk());
		 dugmekonteynersil.setActionCommand(dugmecevabi.dugmekonteynersil.name());
		 
		 dugmekaydet=new JButton("Kaydet");
		 dugmekaydet.setBounds(120,10, 90, 20);
		 dugmekaydet.addActionListener(new Yuk());
		 dugmekaydet.setActionCommand(dugmecevabi.dugmekaydet.name());
		 panelalanlar.add(dugmekaydet);
		 
		 dugmeyeni=new JButton("Yeni");
		 dugmeyeni.setBounds(20, 10, 90, 20);
		 dugmeyeni.addActionListener(new Yuk());
		 dugmeyeni.setActionCommand(dugmecevabi.dugmeyeni.name());
		 panelalanlar.add(dugmeyeni);
		 
		 dugmesil=new JButton("Sil");
		 dugmesil.setBounds(220,10, 90, 20);
		 dugmesil.addActionListener(new Yuk());
		 dugmesil.setActionCommand(dugmecevabi.dugmesil.name());
		 panelalanlar.add(dugmesil);
		 
		 dugmehblbas=new JButton("HBL Bas");
		 dugmehblbas.setBounds(320,10,90, 20);
		 dugmehblbas.addActionListener(new Yuk());
		 dugmehblbas.setActionCommand(dugmecevabi.dugmehblbas.name());
		 panelalanlar.add(dugmehblbas);
		 
		 dugmeara=new JButton("Ara");
		 dugmeara.setBounds(320, 10, 90, 20);       
		// panelalanlar.add(dugmeara);   // Düðme þimdilik iptal
		 
		 panelalanlar.add(dugmekonteynerekle);
		 panelalanlar.add(dugmekonteynerduzenle);
		 panelalanlar.add(dugmekonteynersil);
		 
		 komple=new JRadioButton();
		 komple.setBounds(110,45,20,20);
		 komple.setSelected(true);
		 parsiyel= new JRadioButton();
		 parsiyel.setBounds(110,70,20,20);
		 grup=  new ButtonGroup();
		 grup.add(komple);
		 grup.add(parsiyel);
		 panelalanlar.add(komple);
		 panelalanlar.add(parsiyel);
		 
		 kompletxt = new JLabel("Komple:");
		 kompletxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 kompletxt.setBounds(5, 45, 100, 20);
		 panelalanlar.add(kompletxt);
		 
		 parsiyeltxt = new JLabel("Parsiyel:");
		 parsiyeltxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 parsiyeltxt.setBounds(5, 70, 100, 20);
		 panelalanlar.add(parsiyeltxt);
		 
		 yuknotxt = new JLabel("Yük No:");
		 yuknotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuknotxt.setBounds(100, 45, 100, 26);
		 yuknojt=new JTextField();
		 yuknojt.setBounds(205,45, 100, 26);
		 yuknojt.setEditable(false);
		 panelalanlar.add(yuknotxt);
	     panelalanlar.add(yuknojt);
	     yuknojt.setText(yuknum);  // eðer yeni yük ise "" gelecek deðil ise önceden kayýtlý yük
	    
	     poznotxt = new JLabel("Poz. No:");
		 poznotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 poznotxt.setBounds(100, 70, 100, 26);
		 poznojt=new JTextField();
		 poznojt.setBounds(205,70, 100, 26);
		 poznojt.setEditable(false);
		 panelalanlar.add(poznotxt);
	     panelalanlar.add(poznojt);
	     
	     istipitxt = new JLabel("Grup:");
		 istipitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 istipitxt.setBounds(300, 45, 100, 20);
		 panelalanlar.add(istipitxt);
	    	
		 istipicb = new JComboBox(Anasinif.istipi);
	     istipicb.setBounds(410,45, 100, 20);
	     istipicb.setSelectedIndex(0);
		 panelalanlar.add(istipicb);
		 
		 if(neyapam.equals("düzenle")) {
    		istipicb.setEnabled(false);
    	 } 
		 
		 mblnotxt = new JLabel("MBL No ve Tarihi:");
		 mblnotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 mblnotxt.setBounds(5, 100, 100, 26);
		 mblnojt=new JTextField();
		 mblnojt.setBounds(110,100, 230, 26);
		 mblnojt.setEditable(false);
		 mblnotarihjt =new JTextField();
		 mblnotarihjt.setBounds(350,100,110,26);
		 mblnotarihjt.setEditable(false);
		 panelalanlar.add(mblnotxt);
	     panelalanlar.add(mblnojt);
		 panelalanlar.add(mblnotarihjt);
		 
		 picker = new JXDatePicker();                        // HBL için Tarih Seçici
		 picker.setDate(Calendar.getInstance().getTime()); 	
		 picker.setFormats(new SimpleDateFormat("yyyy.MM.dd"));
		 picker.setVisible(true);
		 picker.setBounds(350,125,120,26);
		 picker.getEditor().setEditable(false);
		 panelalanlar.add(picker);

		 hblnotxt = new JLabel("HBL No ve Tarihi:");
		 hblnotxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 hblnotxt.setBounds(5, 125, 100, 26);
		 panelalanlar.add(hblnotxt);
		 
		 hblnojt=new JTextField();
		 hblnojt.setBounds(110,125, 230, 26);
		 hblnojt.setEditable(false);
		 panelalanlar.add(hblnojt);
		 
		 musteritxt = new JLabel("Müþteri Adý:");
		 musteritxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 musteritxt.setBounds(5, 150, 100, 26);
		 musterijt=new JTextField();
		 musterijt.setBounds(110,150, 350, 26);
		 musterijt.setEditable(false);
		 musteriSECdugme=new JButton("Seç");
		 musteriSECdugme.addActionListener(new Yuk());
		 musteriSECdugme.setActionCommand(dugmecevabi.musteriSECdugme.name());
		 musteriSECdugme.setBounds(470,150,75,26);
		 panelalanlar.add(musteritxt);
	     panelalanlar.add(musterijt);
	     panelalanlar.add(musteriSECdugme);
		 
		 ureticitxt = new JLabel("Üretici Adý:");
		 ureticitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ureticitxt.setBounds(5, 175, 100, 26);
		 ureticijt=new JTextField();
		 ureticijt.setEditable(false);
		 ureticijt.setBounds(110,175, 350, 26);
		 ureticiSECdugme =new JButton("Seç");
		 ureticiSECdugme.addActionListener(new Yuk());
		 ureticiSECdugme.setActionCommand(dugmecevabi.ureticiSECdugme.name());
		 ureticiSECdugme.setBounds(470,175,75,26);
		 panelalanlar.add(ureticitxt);
	     panelalanlar.add(ureticijt);
		 panelalanlar.add(ureticiSECdugme);
		 
		 gondericitxt = new JLabel("Gönderici Adý:");
		 gondericitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 gondericitxt.setBounds(5, 200, 100, 26);
		 gondericijt=new JTextField();
		 gondericijt.setEditable(false);
		 gondericijt.setBounds(110,200, 350, 26);
		 gondericiSECdugme =new JButton("Seç");
		 gondericiSECdugme.addActionListener(new Yuk());
		 gondericiSECdugme.setActionCommand(dugmecevabi.gondericiSECdugme.name());
		 gondericiSECdugme.setBounds(470,200,75,26);
		 panelalanlar.add(gondericitxt);
	     panelalanlar.add(gondericijt);
		 panelalanlar.add(gondericiSECdugme);
		 
		 alicitxt = new JLabel("Alýcý Adý:");
		 alicitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 alicitxt.setBounds(5, 225, 100, 26);
		 alicijt=new JTextField();
		 alicijt.setEditable(false);
		 alicijt.setBounds(110,225, 350, 26);
		 aliciSECdugme =new JButton("Seç");
		 aliciSECdugme.addActionListener(new Yuk());
		 aliciSECdugme.setActionCommand(dugmecevabi.aliciSECdugme.name());
		 aliciSECdugme.setBounds(470,225,75,26);
		 panelalanlar.add(alicitxt);
	     panelalanlar.add(alicijt);
		 panelalanlar.add(aliciSECdugme);
		
		 notify1txt = new JLabel("Notify1 Adý:");
		 notify1txt.setHorizontalAlignment(SwingConstants.RIGHT);
		 notify1txt.setBounds(5, 250, 100, 26);
		 notify1jt=new JTextField();
		 notify1jt.setEditable(false);
		 notify1jt.setBounds(110,250, 350, 26);
		 notify1SECdugme =new JButton("Seç");
		 notify1SECdugme.addActionListener(new Yuk());
		 notify1SECdugme.setActionCommand(dugmecevabi.notify1SECdugme.name());
		 notify1SECdugme.setBounds(470,250,75,26);
		 panelalanlar.add(notify1txt);
	     panelalanlar.add(notify1jt);
		 panelalanlar.add(notify1SECdugme);
		 
		 notify2txt = new JLabel("Notify2 Adý:");
		 notify2txt.setHorizontalAlignment(SwingConstants.RIGHT);
		 notify2txt.setBounds(5, 275, 100, 26);
		 notify2jt=new JTextField();
		 notify2jt.setEditable(false);
		 notify2jt.setBounds(110,275, 350, 26);
		 notify2SECdugme =new JButton("Seç");
		 notify2SECdugme.addActionListener(new Yuk());
		 notify2SECdugme.setActionCommand(dugmecevabi.notify2SECdugme.name());
		 notify2SECdugme.setBounds(470,275,75,26);
		 panelalanlar.add(notify2txt);
	     panelalanlar.add(notify2jt);
		 panelalanlar.add(notify2SECdugme);
		 
		 ydacentetxt = new JLabel("Yurt Dýþý Acente:");
		 ydacentetxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 ydacentetxt.setBounds(5, 300, 100, 26);
		 ydacentejt=new JTextField();
		 ydacentejt.setEditable(false);
		 ydacentejt.setBounds(110,300, 350, 26);
		 ydacenteSECdugme =new JButton("Seç");
		 ydacenteSECdugme.addActionListener(new Yuk());
		 ydacenteSECdugme.setActionCommand(dugmecevabi.ydacenteSECdugme.name());
		 ydacenteSECdugme.setBounds(470,300,75,26);
		 panelalanlar.add(ydacentetxt);
	     panelalanlar.add(ydacentejt);
		 panelalanlar.add(ydacenteSECdugme);
		 
		 faturakesilenfirmatxt = new JLabel("Fat.Kesilen Firma:");
		 faturakesilenfirmatxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 faturakesilenfirmatxt.setBounds(5, 325, 100, 26);
		 faturakesilenfirmajt=new JTextField();
		 faturakesilenfirmajt.setEditable(false);
		 faturakesilenfirmajt.setBounds(110,325, 350, 26);
		 faturakesilenfirmaSECdugme =new JButton("Seç");
		 faturakesilenfirmaSECdugme.addActionListener(new Yuk());
		 faturakesilenfirmaSECdugme.setActionCommand(dugmecevabi.faturakesilenfirmaSECdugme.name());
		 faturakesilenfirmaSECdugme.setBounds(470,325,75,26);
		 panelalanlar.add(faturakesilenfirmatxt);
	     panelalanlar.add(faturakesilenfirmajt);
		 panelalanlar.add(faturakesilenfirmaSECdugme);
		 
		 turkcemaladitxt = new JLabel("Türkçe Mal Adý:");
		 turkcemaladitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 turkcemaladitxt.setBounds(5, 350, 100, 26);
		 turkcemaladijt=new JTextField();
		 turkcemaladijt.setBounds(110,350, 350, 26);
		 panelalanlar.add(turkcemaladitxt);
	     panelalanlar.add(turkcemaladijt);
	     
	     yabancimaladitxt = new JLabel("Yabancý Mal Adý:");
		 yabancimaladitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yabancimaladitxt.setBounds(5, 375, 100, 26);
		 yabancimaladijt=new JTextField();
		 yabancimaladijt.setBounds(110,375, 350, 26);
		 panelalanlar.add(yabancimaladitxt);
	     panelalanlar.add(yabancimaladijt);
	     
	     teslimseklitxt = new JLabel("Teslim Þekli:");
		 teslimseklitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 teslimseklitxt.setBounds(5, 400, 100, 26);
		 teslimseklijt=new JTextField();
		 teslimseklijt.setEditable(false);
		 teslimseklijt.setBounds(110,400, 100, 26);
		 teslimsekliSECdugme=new JButton("Seç");
		 teslimsekliSECdugme.addActionListener(new Yuk());
		 teslimsekliSECdugme.setActionCommand(dugmecevabi.teslimsekliSECdugme.name());
		 teslimsekliSECdugme.setBounds(218,400,90,20);
		 panelalanlar.add(teslimseklitxt);
	     panelalanlar.add(teslimseklijt);
	     panelalanlar.add(teslimsekliSECdugme);
	     
	     odemeseklitxt = new JLabel("Ödeme Þekli:");
		 odemeseklitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 odemeseklitxt.setBounds(5, 425, 100, 26);
		 odemeseklijt=new JTextField();
		 odemeseklijt.setEditable(false);
		 odemeseklijt.setBounds(110,425, 100, 26);
		 odemesekliSECdugme=new JButton("Seç");
		 odemesekliSECdugme.addActionListener(new Yuk());
		 odemesekliSECdugme.setActionCommand(dugmecevabi.odemesekliSECdugme.name());
		 odemesekliSECdugme.setBounds(218,425,90,26);
		 panelalanlar.add(odemeseklitxt);
	     panelalanlar.add(odemeseklijt);
	     panelalanlar.add(odemesekliSECdugme);
	     
	     odemekentitxt = new JLabel("Ödeme Kenti:");
		 odemekentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 odemekentitxt.setBounds(5, 450, 100, 26);
		 odemekentijt=new JTextField();
		 odemekentijt.setEditable(false);
		 odemekentijt.setBounds(110,450, 100, 26);
		 odemekentiSECdugme=new JButton("Seç");
		 odemekentiSECdugme.addActionListener(new Yuk());
		 odemekentiSECdugme.setActionCommand(dugmecevabi.odemekentiSECdugme.name());
		 odemekentiSECdugme.setBounds(218,450,90,26);
		 panelalanlar.add(odemekentitxt);
	     panelalanlar.add(odemekentijt);
	     panelalanlar.add(odemekentiSECdugme);
	     
	     malbedelitxt = new JLabel("Mal Bedeli:");
		 malbedelitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 malbedelitxt.setBounds(5, 475, 100, 26);
		 malbedelijt=new JTextField();
		 malbedelijt.setBounds(110,475, 100, 26);
		 panelalanlar.add(malbedelitxt);
	     panelalanlar.add(malbedelijt);
	         
	     yuklemekentitxt = new JLabel("Yükleme Kenti:");
		 yuklemekentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemekentitxt.setBounds(575, 100, 100, 26);
		 yuklemekentijt=new JTextField();
		 yuklemekentijt.setEditable(false);
		 yuklemekentijt.setBounds(680,100, 200, 26);
		 yuklemekentiSECdugme =new JButton("Seç");
		 yuklemekentiSECdugme.addActionListener(new Yuk());
		 yuklemekentiSECdugme.setActionCommand(dugmecevabi.yuklemekentiSECdugme.name());
		 yuklemekentiSECdugme.setBounds(890,100,75,26);
		 panelalanlar.add(yuklemekentitxt);
	     panelalanlar.add(yuklemekentijt);
		 panelalanlar.add(yuklemekentiSECdugme);
		 
		 yuklemelimanitxt = new JLabel("Yükleme Limaný:");
		 yuklemelimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 yuklemelimanitxt.setBounds(575, 125, 100, 26);
		 yuklemelimanijt=new JTextField();
		 yuklemelimanijt.setEditable(false);
		 yuklemelimanijt.setBounds(680,125, 200, 26);
		 yuklemelimaniSECdugme =new JButton("Seç");
		 yuklemelimaniSECdugme.addActionListener(new Yuk());
		 yuklemelimaniSECdugme.setActionCommand(dugmecevabi.yuklemelimaniSECdugme.name());
		 yuklemelimaniSECdugme.setBounds(890,125,75,26);
		 panelalanlar.add(yuklemelimanitxt);
	     panelalanlar.add(yuklemelimanijt);
		 panelalanlar.add(yuklemelimaniSECdugme);
		 
		 aktarmalimanitxt = new JLabel("Aktarma Limaný:");
		 aktarmalimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 aktarmalimanitxt.setBounds(575, 150, 100, 26);
		 aktarmalimanijt=new JTextField();
		 aktarmalimanijt.setEditable(false);
		 aktarmalimanijt.setBounds(680,150, 200, 26);
		 aktarmalimaniSECdugme =new JButton("Seç");
		 aktarmalimaniSECdugme.addActionListener(new Yuk());
		 aktarmalimaniSECdugme.setActionCommand(dugmecevabi.aktarmalimaniSECdugme.name());
		 aktarmalimaniSECdugme.setBounds(890,150,75,26);
		 panelalanlar.add(aktarmalimanitxt);
	     panelalanlar.add(aktarmalimanijt);
		 panelalanlar.add(aktarmalimaniSECdugme);
		 
		 varislimanitxt = new JLabel("Varýþ Limaný:");
		 varislimanitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varislimanitxt.setBounds(575, 175, 100, 26);
		 varislimanijt=new JTextField();
		 varislimanijt.setEditable(false);
		 varislimanijt.setBounds(680,175, 200, 26);
		 varislimaniSECdugme =new JButton("Seç");
		 varislimaniSECdugme.addActionListener(new Yuk());
		 varislimaniSECdugme.setActionCommand(dugmecevabi.varislimaniSECdugme.name());
		 varislimaniSECdugme.setBounds(890,175,75,26);
		 panelalanlar.add(varislimanitxt);
	     panelalanlar.add(varislimanijt);
		 panelalanlar.add(varislimaniSECdugme);
		 
		 sonvariskentitxt = new JLabel("Son Varýþ Kenti:");
		 sonvariskentitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 sonvariskentitxt.setBounds(575, 200, 100, 26);
		 sonvariskentijt=new JTextField();
		 sonvariskentijt.setEditable(false);
		 sonvariskentijt.setBounds(680,200, 200, 26);
		 sonvariskentiSECdugme =new JButton("Seç");
		 sonvariskentiSECdugme.addActionListener(new Yuk());
		 sonvariskentiSECdugme.setActionCommand(dugmecevabi.sonvariskentiSECdugme.name());
		 sonvariskentiSECdugme.setBounds(890,200,75,26);
		 panelalanlar.add(sonvariskentitxt);
	     panelalanlar.add(sonvariskentijt);
		 panelalanlar.add(sonvariskentiSECdugme);
		 
		 varisulkesitxt = new JLabel("Son Varýþ Ülkesi:");
		 varisulkesitxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 varisulkesitxt.setBounds(575, 225, 100, 26);
		 varisulkesijt=new JTextField();
		 varisulkesijt.setBounds(680,225, 200, 26);
		 varisulkesijt.setEditable(false);
		 panelalanlar.add(varisulkesitxt);
	     panelalanlar.add(varisulkesijt);
		 
	     toplambrutkgtxt = new JLabel("Toplam Brut Kg:");
		 toplambrutkgtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplambrutkgtxt.setBounds(575, 275, 100, 26);
		 toplambrutkgjt=new JTextField();
		 toplambrutkgjt.setBounds(680,275, 100, 26);
		 toplambrutkgjt.setEditable(false);
		 panelalanlar.add(toplambrutkgtxt);
	     panelalanlar.add(toplambrutkgjt);
	     
	     toplamnetkgtxt = new JLabel("Toplam Net Kg:");
		 toplamnetkgtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamnetkgtxt.setBounds(575, 300, 100, 26);
		 toplamnetkgjt=new JTextField("0");
		 toplamnetkgjt.addKeyListener(new java.awt.event.KeyAdapter() {   
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            try {
			   			
							int miktar = Integer.parseInt(toplamnetkgjt.getText());
							if (miktar>99999999) {
								toplamnetkgjt.setText("0");
							}
				            } catch (Exception e) {
			               toplamnetkgjt.setText("0");
			            }
			        }
			    });
		 toplamnetkgjt.setBounds(680,300, 100, 26);
		 panelalanlar.add(toplamnetkgtxt);
	     panelalanlar.add(toplamnetkgjt);
		
	     toplamhacimtxt = new JLabel("Toplam Hacim:");
		 toplamhacimtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamhacimtxt.setBounds(575, 325, 100, 26);
		 toplamhacimjt=new JTextField("0");
		 toplamhacimjt.addKeyListener(new java.awt.event.KeyAdapter() { 
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            try {
			   			
							Double miktar = Double.parseDouble(toplamhacimjt.getText());
							cwjt.setText(String.valueOf(miktar*1000));
							if (miktar>9998) {
								toplamhacimjt.setText("0");
								cwjt.setText("");
							}
							
				            } catch (Exception e) {
			               toplamhacimjt.setText("0");
			               cwjt.setText("0");
			            }
			        }
			    });
		 toplamhacimjt.setBounds(680,325, 100, 26);
		 panelalanlar.add(toplamhacimtxt);
	     panelalanlar.add(toplamhacimjt);
	     
	     toplamkaptxt = new JLabel("Toplam Kap:");
		 toplamkaptxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 toplamkaptxt.setBounds(575, 350, 100, 26);
		 toplamkapjt=new JTextField();
		 toplamkapjt.setBounds(680,350, 100, 26);
		 toplamkapjt.setEditable(false);
		 panelalanlar.add(toplamkaptxt);
	     panelalanlar.add(toplamkapjt);
	     
	     cwtxt = new JLabel("C.W.:");
		 cwtxt.setHorizontalAlignment(SwingConstants.RIGHT);
		 cwtxt.setBounds(575, 375, 100, 26);
		 cwjt=new JTextField();
		 cwjt.setBounds(680,375, 100, 26);
		 cwjt.setEditable(false);
		 panelalanlar.add(cwtxt);
	     panelalanlar.add(cwjt);
	     tablodata = new String [0][0];
	    
	     if (yuknojt.getText().equals("")) {   // yeni yük ise varsayýlan rez konteynerini yükle
	    	 
	    	 tablodata = new String [1][5];
	    	 tablodata[0][0]="01";
	    	 tablodata[0][1]="RZVN000000";
	    	 tablodata[0][2]="     0";
	    	 tablodata[0][3]="    0";
	    	 tablodata[0][4]="0";
	    	 
		     }else {
	    	 yukuekranagetir();
		     }
	     
	     if (!yuknojt.getText().equals("") && komple.isSelected()) {  // komple olarak kaydedilmiþ yük parsiyele çevrilemez, tersi mümkün
	    	 parsiyel.setVisible(false);
	     }
	     
	     aticine = frame.getContentPane();
	     Konteynerlistesitablosu.konteynerlistesitablosu();
	     aticine.add(panelalanlar, BorderLayout.CENTER);
		 frame.setVisible(true);
		
	}   // denizyuk metodu sonu

	@Override
	public void actionPerformed(ActionEvent e) {
		     if (e.getActionCommand()==dugmecevabi.musteriSECdugme.name())	{
		    	 
		    	 if (musterikodu!=0) {
		    		 musterikodu=0;
		    		 musterijt.setText("");
		    		 return;
		    	 }
			
		    	 Sirketkartlari.sirketkartlari("musteri");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.ureticiSECdugme.name())	{
		    	 
		    	 if (ureticikodu!=0) {
		    		 ureticikodu=0;
		    		 ureticijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("uretici");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.gondericiSECdugme.name())	{
		    	 
		    	 if (gondericikodu!=0) {
		    		 gondericikodu=0;
		    		 gondericijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("gonderici");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.aliciSECdugme.name())	{
		    	 
		    	 if (alicikodu!=0) {
		    		 alicikodu=0;
		    		 alicijt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("alici");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.notify1SECdugme.name())	{
		    	 
		    	 if (notify1kodu!=0) {
		    		 notify1kodu=0;
		    		 notify1jt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("notify1");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.notify2SECdugme.name())	{
		    	 
		    	 if (notify2kodu!=0) {
		    		 notify2kodu=0;
		    		 notify2jt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("notify2");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.ydacenteSECdugme.name())	{
		    	 
		    	 if (ydacentekodu!=0) {
		    		 ydacentekodu=0;
		    		 ydacentejt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("ydacente");
		     	 
		     } 
		     
		     if (e.getActionCommand()==dugmecevabi.faturakesilenfirmaSECdugme.name())	{
		    	 
		    	 if (faturakesilenfirmakodu!=0) {
		    		 faturakesilenfirmakodu=0;
		    		 faturakesilenfirmajt.setText("");
		    		 return;
		    	 }
					
		    	 Sirketkartlari.sirketkartlari("faturakesilenfirma");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.teslimsekliSECdugme.name())	{
		    	 
		    	 if (teslimseklikodu!=0) {
		    		 teslimseklikodu=0;
		    		 teslimseklijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler(null, "T.Sekli");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.odemesekliSECdugme.name())	{
		    	 
		    	 if (odemeseklikodu!=0) {
		    		 odemeseklikodu=0;
		    		 odemeseklijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler(null, "Odeme");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.odemekentiSECdugme.name())	{
		    	 
		    	 if (odemekentikodu!=0) {
		    		 odemekentikodu=0;
		    		 odemekentijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("odeme", "Kent");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.yuklemekentiSECdugme.name())	{
		    	 
		    	 if (yuklemekentikodu!=0) {
		    		 yuklemekentikodu=0;
		    		 yuklemekentijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("yukleme", "Kent");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.sonvariskentiSECdugme.name())	{
		    	 
		    	 if (sonvariskentikodu!=0) {
		    		 sonvariskentikodu=0;
		    		 sonvariskentijt.setText("");
		    		 varisulkesijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("sonvaris", "Kent");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.yuklemelimaniSECdugme.name())	{
		    	 
		    	 if (yuklemelimanikodu!=0) {
		    		 yuklemelimanikodu=0;
		    		 yuklemelimanijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("yukleme", "Liman");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.aktarmalimaniSECdugme.name())	{
		    	 
		    	 if (aktarmalimanikodu!=0) {
		    		 aktarmalimanikodu=0;
		    		 aktarmalimanijt.setText("");
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("aktarma", "Liman");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.varislimaniSECdugme.name())	{
		    	 
		    	 if (varislimanikodu!=0) {
		    		 varislimanikodu=0;
		    		 varislimanijt.setText("");
		    		 		    		 
		    		 return;
		    	 }
					
		    	 Ontanimliveriler.ontanimliveriler("varis", "Liman");
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmekaydet.name())	{
		    	 
		    	 if(gondericikodu==0 | alicikodu==0 | musterikodu==0 ) {
		    		 Bilgipenceresi.anons("Müþteri ,Gönderici ve Alýcý Mutlaka Girilmelidir");
		    		 return;
		    	 }
		    	 
		    	 if(neyapam.equals("düzenle")) {
		    		 veriduzenle ();
		    		 return;
		    	 } else {
		    		 veriekle();
		    		 Denizyoluanaekran.secilenyuk="";
		    		 Denizyoluanaekran.yukokuma("yenile");
		    		 Denizyoluanaekran.Yuklistesitablosu.tableModel.fireTableDataChanged();
		    		 yukbayrak=false;
			    	 frame.dispose();
			    	 try {yuk(sonyuknumarasinedir(),"düzenle");	} catch (ParseException e1) {e1.printStackTrace();
					}
		    	 }
		    	
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmekonteynerekle.name())	{
		    	 
		    	 if (tablodata[0][1].equals("RZVN000000") | parsiyel.isSelected()) {
		    		 return;
		    	 }
		    	 
		    	 int toplamkg=0;int toplamkap=0;	
		    	
		    	 Yukicindekikonteynerekleduzenlesil.konteynerekleduzenlesil("EKLEME");
		    	 for (int i=0 ; i<tablodata.length ;i++ ) {
		    		  
		    		 toplamkap=toplamkap+Integer.parseInt(tablodata[i][2].trim()); toplamkapjt.setText(String.valueOf(toplamkap));
		    		 toplamkg=toplamkg+Integer.parseInt(tablodata[i][3].trim()); toplambrutkgjt.setText(String.valueOf(toplamkg));
		    		  
		    			  }
			    	 
		    	Konteynerlistesitablosu.tableModel.fireTableDataChanged();
		    	secilenkonteyner="";
		     	 
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmekonteynersil.name())	{
		    	 int toplamkg=0;int toplamkap=0;
		    	 
		  	    	if (secilenkonteyner=="" | tablodata.length==1)  {
			    		return;
			    	}
		  	    	Yukicindekikonteynerekleduzenlesil.konteynerekleduzenlesil("SÝLME");
			    	 for (int i=0 ; i<tablodata.length ;i++ ) {
			    		 
			    		  toplamkap=toplamkap+Integer.parseInt(tablodata[i][2].trim()); toplamkapjt.setText(String.valueOf(toplamkap));
			    		  toplamkg=toplamkg+Integer.parseInt(tablodata[i][3].trim()); toplambrutkgjt.setText(String.valueOf(toplamkg));
			    		  
			    			  }
			    	Konteynerlistesitablosu.tableModel.fireTableDataChanged();
			    	secilenkonteyner="";
			     	 
			     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmekonteynerduzenle.name())	{
		    	 int toplamkg=0;int toplamkap=0;
		    	 
		    	    if (hangiyukugosterem.equals("") | parsiyel.isSelected()) {
		    	    	secilenkonteyner="1";
		    	    }
		    	 
			    	if (secilenkonteyner=="" )  {
			    		return;
			    	}
			    	Yukicindekikonteynerekleduzenlesil.konteynerekleduzenlesil("DÜZENLEME");
			    	 for (int i=0 ; i<tablodata.length ;i++ ) {
			    		  toplamkap=toplamkap+Integer.parseInt(tablodata[i][2].trim()); toplamkapjt.setText(String.valueOf(toplamkap));
			    		  toplamkg=toplamkg+Integer.parseInt(tablodata[i][3].trim()); toplambrutkgjt.setText(String.valueOf(toplamkg));
			    		  
			    			  }
			    	Konteynerlistesitablosu.tableModel.fireTableDataChanged();
			    	secilenkonteyner="";
			     	 
			     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmesil.name())	{
		    	 
		    	 if(hangiyukugosterem.equals("")) {
		    		 return;		    		 
		    	 }
		    	 
		    	 Sileyimmi.sonkarar("yuk");
		    	 
		    	 if (Sileyimmi.cevap.equals("hayýr")) {
		    		 return;
		    	 }else {
		    		 verisil();
		    		 Denizyoluanaekran.yukokuma("yenile");
		    		 Denizyoluanaekran.Yuklistesitablosu.tableModel.fireTableDataChanged();
				     secilenkonteyner="";
				     Denizyoluanaekran.secilenyuk="";
				     yukbayrak=false;
				     frame.dispose();
		    	 }
		    	 
			     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmeyeni.name())	{
		    	 
		    	 if(hangiyukugosterem.equals("") ) {
		    		 return;		    		 
		    	 }
		    	 yukbayrak=false;
		    	 frame.dispose();
		    	 try {
						Yuk.yuk("","yeni");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
		     }
		     
		     if (e.getActionCommand()==dugmecevabi.dugmehblbas.name())	{
		    	 
		    	 if(poznojt.getText().equals("") | poznojt.getText().equals("0") ) {
		    		 Bilgipenceresi.anons("Pozisyona alýnmamýþ yükün HBL'i basýlamaz");
		    		 return;		    		 
		    	 }
		    	 
		    	 Blyap.blyap(poznojt.getText(), hangiyukugosterem);
		    	 
		     }
		} //dugme override sonu
	
	public static String konteynerokuma(int yukno) {
	
		String konteynerhamveri="";
		String malcinsi="";
		int konteynersayisi=0;
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
		"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
					
					Statement stmt=con.createStatement();  
			        ResultSet rs=stmt.executeQuery("SELECT * FROM yukler where yukno='"+yukno+"';"); 
			
			while(rs.next()) {
				
				konteynerhamveri=rs.getString(25);
				malcinsi=rs.getString(15);
						}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		 
		konteynersayisi=konteynerhamveri.length()/24;
		tablodata = new String [konteynersayisi][5];
		int toplamkg=0;int toplamkap=0;
		for (int i=1; i<konteynersayisi+1; i++) {
			tablodata[i-1][0]=konteynerhamveri.substring(((i*24)-24),((i*24)-22)); // kont sýra no
			tablodata[i-1][1]=konteynerhamveri.substring(((i*24)-22),((i*24))-12);   // Kont No
			tablodata[i-1][2]=konteynerhamveri.substring(((i*24)-12),((i*24))-6);  //Kont kap
			tablodata[i-1][3]=konteynerhamveri.substring(((i*24)-6),(i*24)-1); //kont kg
			tablodata[i-1][4]=konteynerhamveri.substring((i*24)-1,i*24); // kont tip
			toplamkap=toplamkap+Integer.parseInt(tablodata[i-1][2].trim());
			toplamkg=toplamkg+Integer.parseInt(tablodata[i-1][3].trim()); 
			
			try {
				toplambrutkgjt.setText(String.valueOf(toplamkg));
				toplamkapjt.setText(String.valueOf(toplamkap));
			} catch (Exception e) {	}
		}
		
		return malcinsi;
		
	}  // konteynerokuma metodu sonu
	
	private void verisil() {
		
		if (duzenlenecekyuk[1].equals("1") ) {
			Bilgipenceresi.anons("Pozisyona Eklenmiþ Yük Silinemez");
			return;
		}
		
		try {  
		     
			Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
	        PreparedStatement st = connection.prepareStatement("DELETE FROM yukler WHERE yukno ='" +hangiyukugosterem + "';");
	        st.executeUpdate(); 
	  
			} catch(Exception e) {
	        System.out.println(e);
			}
		Bilgipenceresi.anons("Yük Silindi");
	}
	
	private void veriekle() {
	 
		int kontsayisi=tablodata.length;
		String konteynerhamveri="";
				
		for(int i=0; i<kontsayisi;i++) {
			konteynerhamveri=konteynerhamveri+tablodata[i][0]+tablodata[i][1]
			+tablodata[i][2]+tablodata[i][3]+tablodata[i][4];
			}
				
		 String sqlkomut="INSERT INTO yukler "
		 		+ "(  pozaalindi ,"
		 		+ "   komple ,"
		 		+ "   hblno  ,"
		 		+ "   hbltarihi ,"
		 		+ "   musterikodu  ,"
		 		+ "   ureticikodu ,"
		 		+ "   gondericikodu,"
		 		+ "   alicikodu ,"
		 		+ "   notify1kodu  ,"
		 		+ "   notify2kodu  ,"
		 		+ "   ydacentekodu  ,"
		 		+ "   faturakesilenfirmakodu ,"
		 		+ "   turkcemaladi ,"
		 		+ "   yabancimaladi ,"
		 		+ "   teslimseklikodu ,"
		 		+ "   odemeseklikodu ,"
		 		+ "   odemekentikodu ,"
		 		+ "   malbedeli,"
		 		+ "   yuklemekentikodu ,"
		 		+ "   yuklemelimanikodu ,"
		 		+ "   aktarmalimanikodu ,"
		 		+ "   varislimanikodu ,"
		 		+ "   sonvariskentikodu ,"
		 		+ "   konteynerbilgileri ,"
		 		+ "   netkg              ,"
		 		+ "   hacim              ,"
		 		+ "   istipi              ) VALUES ( "
		 		
		 		+ "     false,"    // poza alindi
		 		+ komple.isSelected()+","  //komple mi 
		 		+ "'"+hblnojt.getText()+"',"  // hbl no
		 		+ "'"+df.format(picker.getDate())+"',"  // hbl tarihi
		 		+ "'"+musterikodu+"',"  // müþteri kodu
		 		+ "'"+ureticikodu+"',"     //uretici kodu
		 		+ "'"+gondericikodu+"',"   // gonderici kodu
		 		+ "'"+alicikodu+"',"        // alici kodu
		 		+ "'"+notify1kodu+"',"       //notify1 kodu
		 		+ "'"+notify2kodu+"',"   //nt2 kodu
		 		+ "'"+ydacentekodu+"',"  // yurt dýþý acente kodu
		 		+ "'"+faturakesilenfirmakodu+"',"  // fk firma kodu
		 		+ "'"+turkcemaladijt.getText()+"',"    //turkçe mal adý
		 		+ "'"+yabancimaladijt.getText()+"',"  // yabanci dil mal adi
		 		+ "'"+teslimseklikodu+"',"        // teslim sekli kodu
		 		+ "'"+odemeseklikodu+"',"      //  odeme sekli kodu
		 		+ "'"+odemekentikodu+"',"             // odeme kenti kodu
		 		+ "'"+malbedelijt.getText()+"',"     // mal bedeli 
		 		+ "'"+yuklemekentikodu+"',"    // yukleme kenti kodu
		 		+ "'"+yuklemelimanikodu+"',"   // yukleme limani kodu
		 		+ "'"+aktarmalimanikodu+"',"   // akr lim kod
		 		+ "'"+varislimanikodu+"',"   // var lim kod
		 		+ "'"+sonvariskentikodu+"',"    // son var kenti kodu
		 		+ "'"+konteynerhamveri+"',"	// konteyner bilgisi
		        + "'"+toplamnetkgjt.getText()+"' ,"  // net kg
		        + "'"+toplamhacimjt.getText()+"' ," 
		        + " "+istipicb.getSelectedIndex()+" "+");";                              
		
		try{                                             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
			Statement stmt=con.createStatement();  
	
			stmt.executeUpdate(sqlkomut);
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		Bilgipenceresi.anons("Yeni Yük Kaydedildi");
			 	 
	}  //metod sonu
	
	public static void veriduzenle ()  {
		
		int kontsayisi=tablodata.length;
		String konteynerhamveri="";
			
		for(int i=0; i<kontsayisi;i++) {
			konteynerhamveri=konteynerhamveri+tablodata[i][0]+tablodata[i][1]
			+tablodata[i][2]+tablodata[i][3]+tablodata[i][4];
			}
		
		String sqlkomut="Update yukler set "
			+ "   komple ="+komple.isSelected()+","
			+"   hblno  ='"+hblnojt.getText()+"',"
			+"   hbltarihi ='"+df.format(picker.getDate())+"',"
			+"   musterikodu ='"+musterikodu+"',"
			+"   ureticikodu ='"+ureticikodu+"',"
			+"   gondericikodu ='"+gondericikodu+"',"
			+"   alicikodu ='"+alicikodu+"',"
			+"   notify1kodu ='"+notify1kodu+"',"
			+"   notify2kodu ='"+notify2kodu+"',"
			+ "   ydacentekodu ='"+ydacentekodu+"',"
			+"   faturakesilenfirmakodu='"+faturakesilenfirmakodu+"',"
			+"   turkcemaladi='"+turkcemaladijt.getText()+"',"
			+"   yabancimaladi='"+yabancimaladijt.getText()+"',"
			+"   teslimseklikodu='"+teslimseklikodu+"',"
			+"   odemeseklikodu='"+odemeseklikodu+"',"
			+"   odemekentikodu='"+odemekentikodu+"',"
			+"   malbedeli='"+malbedelijt.getText()+"',"
			+"   yuklemekentikodu='"+yuklemekentikodu+"',"
			+"   yuklemelimanikodu='"+yuklemelimanikodu+"'," 
			+"   aktarmalimanikodu='"+aktarmalimanikodu+"'," 
			+"   varislimanikodu='"+varislimanikodu+"',"
			+"   sonvariskentikodu ='"+sonvariskentikodu+"',"
			+"   konteynerbilgileri='"+konteynerhamveri+"',"
			+"   netkg ='"+toplamnetkgjt.getText()+"' ,"
			+"   hacim ='"+toplamhacimjt.getText()+"' , "
			+"   istipi ="+istipicb.getSelectedIndex()
			+" where yukno='"+hangiyukugosterem+"'";
		
		try{                                             
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
	"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
			Statement stmt=con.createStatement();  
	
			stmt.executeUpdate(sqlkomut);
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}  
		
		secilenkonteyner="";
		Denizyoluanaekran.secilenyuk="";
	   
		Denizyoluanaekran.yukokuma("acilis");
		Denizyoluanaekran.Yuklistesitablosu.tableModel.fireTableDataChanged();
		Bilgipenceresi.anons("Deðiþiklikler Kaydedildi");
		
	}
	
	public static void yukuekranagetir() throws ParseException {
		
		duzenlenecekyuk= new String[30];
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
					Statement stmt=con.createStatement();  
			   		ResultSet rs=stmt.executeQuery("select * from yukler where yukno='"+hangiyukugosterem+"'"); 
			
			while(rs.next()) {
				for (int j=0; j<30; j++ ) {
					duzenlenecekyuk[j]=rs.getString(j+1);
					}
			}
			con.close();  
			
			}catch(Exception e){ System.out.println(e);}
	
		int i= Integer.parseInt(hangiyukugosterem)-1;               
	
	    if (duzenlenecekyuk[2].equals("1")) {
	    	komple.setSelected(true);
	    	parsiyel.setVisible(false);
	    	
	    } else {
	    	parsiyel.setSelected(true);
	    	komple.setVisible(false);
	    	
	    }
	    
	    hblnojt.setText(duzenlenecekyuk[3]);       // hbl no    4
 	    
	    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(duzenlenecekyuk[4]);   // hbl tarihi           5
 	    picker.setDate(date1);    
 	    
 	    musterikodu=Integer.parseInt(duzenlenecekyuk[5]);     // müþteri kodu    6
 	    musterijt.setText(Ontanimliveriler.sirketkoducoz(musterikodu)[1]);
 	    musterijt.setCaretPosition(0);
 	   
 	    ureticikodu=Integer.parseInt(duzenlenecekyuk[6]);      //uretici kodu  7
 	    ureticijt.setText(Ontanimliveriler.sirketkoducoz(ureticikodu)[1]); 
 	    ureticijt.setCaretPosition(0);
 	    
 	    gondericikodu=Integer.parseInt(duzenlenecekyuk[7]);      //gonderici kodu  8
 	    gondericijt.setText(Ontanimliveriler.sirketkoducoz(gondericikodu)[1]);  
 	    gondericijt.setCaretPosition(0);
 	    
       alicikodu=Integer.parseInt(duzenlenecekyuk[8]);      //alici kodu  9
	    alicijt.setText(Ontanimliveriler.sirketkoducoz(alicikodu)[1]);   
	    alicijt.setCaretPosition(0);
	    
 	   notify1kodu=Integer.parseInt(duzenlenecekyuk[9]);      //notify1 kodu  10
	    notify1jt.setText(Ontanimliveriler.sirketkoducoz(notify1kodu)[1]);  
	    notify1jt.setCaretPosition(0);
 	   
 	   notify2kodu=Integer.parseInt(duzenlenecekyuk[10]);      //notify1 kodu  11
	    notify2jt.setText(Ontanimliveriler.sirketkoducoz(notify2kodu)[1]); 
	    notify2jt.setCaretPosition(0);
 
 	   ydacentekodu=Integer.parseInt(duzenlenecekyuk[11]);      //ydacentekodu kodu  12
	    ydacentejt.setText(Ontanimliveriler.sirketkoducoz(ydacentekodu)[1]); 
	    ydacentejt.setCaretPosition(0);
 	   
 	   faturakesilenfirmakodu=Integer.parseInt(duzenlenecekyuk[12]);      //faturakesilenfirmakodu kodu  13
	    faturakesilenfirmajt.setText(Ontanimliveriler.sirketkoducoz(faturakesilenfirmakodu)[1]); 
	    faturakesilenfirmajt.setCaretPosition(0);
 	
 	   turkcemaladijt.setText(duzenlenecekyuk[13]);    //turkçe mal adý 14
 	  turkcemaladijt.setCaretPosition(0);
 	
 	   yabancimaladijt.setText(duzenlenecekyuk[14]);    //yabancimaladi adý 15
 	  yabancimaladijt.setCaretPosition(0);
 	
 	   teslimseklikodu=Integer.parseInt(duzenlenecekyuk[15]);       // teslim sekli kodu  16
	   teslimseklijt.setText(Ontanimliveriler.teslimseklikoducoz(teslimseklikodu)); 
	   teslimseklijt.setCaretPosition(0);
 	
 	   odemeseklikodu=Integer.parseInt(duzenlenecekyuk[16]);      //  odeme sekli kodu  17
	   odemeseklijt.setText(Ontanimliveriler.odemeseklikoducoz(odemeseklikodu)); 
	   odemeseklijt.setCaretPosition(0);
 	   
 	   odemekentikodu=Integer.parseInt(duzenlenecekyuk[17]);      //  odeme kenti kodu  18
	   odemekentijt.setText(Ontanimliveriler.kentkoducoz(odemekentikodu)[0]);
	   odemekentijt.setCaretPosition(0);
 	   
 	   malbedelijt.setText(duzenlenecekyuk[18]);      // mal bedeli 19
 	   malbedelijt.setCaretPosition(0);
 	
 	   yuklemekentikodu=Integer.parseInt(duzenlenecekyuk[19]);    // yukleme kenti kodu  20
	   yuklemekentijt.setText(Ontanimliveriler.kentkoducoz(yuklemekentikodu)[0]); 
	   yuklemekentijt.setCaretPosition(0);
 	
 	   yuklemelimanikodu=Integer.parseInt(duzenlenecekyuk[20]);  // yukleme limani kodu  21
	   yuklemelimanijt.setText(Ontanimliveriler.limankoducoz(yuklemelimanikodu)); 
	   yuklemelimanijt.setCaretPosition(0);
	
 	   aktarmalimanikodu=Integer.parseInt(duzenlenecekyuk[21]);  // akr lim kod        22
	   aktarmalimanijt.setText(Ontanimliveriler.limankoducoz(aktarmalimanikodu));
	   aktarmalimanijt.setCaretPosition(0);
 	   
 	   varislimanikodu=Integer.parseInt(duzenlenecekyuk[22]);   // var lim kod          23
	   varislimanijt.setText(Ontanimliveriler.limankoducoz(varislimanikodu)); 
	   varislimanijt.setCaretPosition(0);
	   
 	   sonvariskentikodu=Integer.parseInt(duzenlenecekyuk[23]);   // var lim kod  24
	   sonvariskentijt.setText(Ontanimliveriler.kentkoducoz(yuklemekentikodu)[0]);
	   sonvariskentijt.setCaretPosition(0);
	   varisulkesijt.setText(Ontanimliveriler.kentkoducoz(yuklemekentikodu)[1]);
	   varisulkesijt.setCaretPosition(0);
	  
 	   konteynerokuma(i+1);    // konteyner ham bilgisi  25.  metod vt den direk kendisi okuyor
 	   
 	   toplamnetkgjt.setText(duzenlenecekyuk[25]);  // 26 toplam net kg
 	  
 	   if(duzenlenecekyuk[26].equals("")) {
 		  duzenlenecekyuk[26]="0";
 	   }
 	   toplamhacimjt.setText(duzenlenecekyuk[26]);  // 27 toplam hacim
 	   
 	   if (duzenlenecekyuk[1].equals("1")) {       // mbl numarasý doðrudan yüklendiði poz'dan çekiliyor. 
 		 mblnojt.setText (mblnumaramibul (duzenlenecekyuk[29])[0]);
 		 mblnojt.setCaretPosition(0);
 		 mblnotarihjt.setText (mblnumaramibul (duzenlenecekyuk[29])[1]);
 		 mblnotarihjt.setCaretPosition(0);
 		 String hblno1= mblnumaramibul (duzenlenecekyuk[29])[1].substring(0,7)+".";
 		 String hblno2= Anasinif.istipi[Integer.parseInt(duzenlenecekyuk[28])]+".";
 		 String hblno3=duzenlenecekyuk[29]+"."+hangiyukugosterem;
 		 
 		 hblnojt.setText(hblno1+hblno2+hblno3);
 	     hblnojt.setCaretPosition(0);
 	   }
 	   
 	    // 28 mbl no
 	   
 	   istipicb.setSelectedIndex(Integer.parseInt(duzenlenecekyuk[28]));  //29 istipi
 	    
 	   poznojt.setText(duzenlenecekyuk[29]);//30 numara  eðer yüke alýndý ise poz numarasý  
 	  poznojt.setCaretPosition(0);
 	 	    
 	  Double miktar = Double.parseDouble(toplamhacimjt.getText());
		cwjt.setText(String.valueOf(miktar*1000));
 	   
 		
	} // yukuekrana getir metodu sonu
	
	private static String[]  mblnumaramibul (String amahangipoza) {
	
		String [] mblnumarasi={"",""};
		
			try{  
					Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection(
			"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
						
						Statement stmt=con.createStatement();  
				        ResultSet rs=stmt.executeQuery("SELECT * FROM pozisyonlar where pozno='"+amahangipoza+"';"); 
					while(rs.next()) {
						mblnumarasi[0]=rs.getString(6); // mbl numarasi
						mblnumarasi[1]=rs.getString(7); // mbl tarihi
							}
				con.close();  
				}catch(Exception e){ System.out.println(e);}  
			return mblnumarasi;
	}
	
	public static String sonyuknumarasinedir() {
		//
		String sonyuknumarasi="";
		
		
		try{                                              
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
			
					Statement stmt=con.createStatement();  
			   		ResultSet rs=stmt.executeQuery("select * from yukler "); 
			
			while(rs.next()) {
			sonyuknumarasi=rs.getString(1);
			}
			con.close();  
			}catch(Exception e){ System.out.println(e);}
		System.out.println(sonyuknumarasi);
		return sonyuknumarasi;
		}
	
	@SuppressWarnings("serial")
	public static class Konteynerlistesitablosu extends JPanel {
		static Tablomodeli tableModel;
		static JScrollPane scrollPane;
		static String gostermeliktablodata[][] ;

		public Konteynerlistesitablosu() {
	        initializePanel();
	    }

	    private void initializePanel() {
	       
	       tableModel = new Tablomodeli();

	        JTable table = new JTable(tableModel);
	        
	        table.setFillsViewportHeight(true);
	        table.setPreferredScrollableViewportSize(new Dimension(500,100));
			   
		    table.getColumnModel().getColumn(0).setPreferredWidth(35);
		    table.getColumnModel().getColumn(1).setPreferredWidth(155);
		    table.getColumnModel().getColumn(2).setPreferredWidth(89);
		    
		    table.setFocusable(false);
		      table.addMouseListener(new MouseAdapter() {   // mouse listener kýsmýsý
		         public void mouseClicked(MouseEvent me) {
		        	 int toplamkg=0;int toplamkap=0;
		            if (me.getClickCount() == 2) {     
		              //
		            	 if (Yuk.hangiyukugosterem.equals("") | Yuk.parsiyel.isSelected()) {
				    	    	Yuk.secilenkonteyner="1";
				    	    }
				    	 
					    	
		            	 Yukicindekikonteynerekleduzenlesil.konteynerekleduzenlesil("DÜZENLEME");
					    	 for (int i=0 ; i<Yuk.tablodata.length ;i++ ) {
					    		  toplamkap=toplamkap+Integer.parseInt(Yuk.tablodata[i][2].trim()); Yuk.toplamkapjt.setText(String.valueOf(toplamkap));
					    		  toplamkg=toplamkg+Integer.parseInt(Yuk.tablodata[i][3].trim()); Yuk.toplambrutkgjt.setText(String.valueOf(toplamkg));
					    		  
					    			  }
					    	Konteynerlistesitablosu.tableModel.fireTableDataChanged();
					    	Yuk.secilenkonteyner="";  
			    	 //	
		            }
		         }
		      });
		    
		    ListSelectionModel cellSelectionModel = table.getSelectionModel();               // listeden seçileni dinleme kýsmý
	        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	          public void valueChanged(ListSelectionEvent e) {
	           
	        	  if(!e.getValueIsAdjusting())  {    						// bu IF iki defa yazmamasý için konuldu. 
	      
	        		  int selectedRow     = table.getSelectedRow();
	          		Yuk.secilenkonteyner = (String) table.getValueAt(selectedRow,0 );
	          		          		
	        	  }
	          }
	     });
		      scrollPane = new JScrollPane(table);
	        scrollPane.setPreferredSize(new Dimension(265,400));
	        this.setLayout(new BorderLayout());
	        this.add(scrollPane, BorderLayout.CENTER);
	    }

	    public static void showFrame() {
	        JPanel panel = new Konteynerlistesitablosu();
	        panel.setOpaque(true);

	     Yuk.frame.add(scrollPane,BorderLayout.EAST);  
	      
	    }

	    class Tablomodeli extends AbstractTableModel {
	     
	        private  String[] columnNames = { "#", "KONTEYNER#","KAP","KG","TÝP" };

	        public int getRowCount() {
	           
	        	return gostermeliktablodata.length;
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
	        		rowIndex=gostermeliktablodata.length-1;
	        		        		
	        	}
	            return gostermeliktablodata[rowIndex][columnIndex];
	        }
	    }
	    
	    public static void gostermeligidegistir () {  // konteyner tablosunun kaynaðýný da güncelle 
	  	  for (int i=0; i<Yuk.tablodata.length ;i++) {
	    		
	    		Konteynerlistesitablosu.gostermeliktablodata[i][0]=Yuk.tablodata[i][0];
	    		Konteynerlistesitablosu.gostermeliktablodata[i][1]=Yuk.tablodata[i][1];
	    		Konteynerlistesitablosu.gostermeliktablodata[i][2]=Yuk.tablodata[i][2];
	    		Konteynerlistesitablosu.gostermeliktablodata[i][3]=Yuk.tablodata[i][3];
	    		String neymiskonteyner=Anasinif.konteynertipleri[Integer.parseInt(Yuk.tablodata[i][4])];
	    		Konteynerlistesitablosu.gostermeliktablodata[i][4]=neymiskonteyner;
	    		    		
	    	}
	    }

	    public static void konteynerlistesitablosu () {
	        	
	    	gostermeliktablodata= new String[Yuk.tablodata.length][5];
	    	
	    	gostermeligidegistir ();
	    	    	
	        SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	                showFrame();
	            }
	        });
	    }
	}
		
	} // sinif sonu ;