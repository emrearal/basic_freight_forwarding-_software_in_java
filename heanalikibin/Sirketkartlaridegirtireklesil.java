package heanalikibin;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Sirketkartlaridegirtireklesil implements ActionListener {
	
	static JLabel baslik,lb1,lb2,lb3,lb4,lb5,lb6,lb7,lb8,lb9,lb10,lb11;
	static JButton yavrudugme ,yavrudugme2 ;
	static JDialog yavru ;
	static JTextField jt1,jt2,jt3,jt4,jt5,jt6,jt7,jt8,jt9,jt10;
	static String ekranj1,ekranj2,ekranj3,ekranj4,ekranj5,ekranj6,ekranj7,ekranj8,ekranj9,ekranj10;
		
	private enum evethayir {
		basvazgec,baskaydet;
	}
	
public static void ekle() {
	
	yavrudugme= new JButton("VAZGEÇ");
	yavrudugme2 = new JButton("ONAY");
	yavru = new JDialog(Sirketkartlari.frame,"Firma Cari Kartý Ýþlemleri",true); 
	yavru.setResizable(false);
		
	baslik= new JLabel("");
	baslik.setBounds(200,10,250,20);
	
	if (Sirketkartlari.eklebasildi==true) {
		baslik.setText("FÝRMA CARÝ KARTI EKLE");
	}
	
	if (Sirketkartlari.duzenlebasildi==true) {
		baslik.setText("FÝRMA CARÝ KARTI DÜZENLE");
	}

	if (Sirketkartlari.silebasildi==true) {
		baslik.setText("FÝRMA CARÝ KARTI SÝL");
	}
	
	yavru.setSize(600,450); 
	yavru.setLocation(450,150);
	yavru.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	yavru.add(yavrudugme);
	yavru.add(yavrudugme2);
	yavru.add(baslik);
	
	yavrudugme.setBounds(70,330,200,50);
	yavrudugme.setVisible(true);
	yavrudugme.addActionListener(new Sirketkartlaridegirtireklesil());
	yavrudugme.setActionCommand(evethayir.basvazgec.name());
	
	yavrudugme2.setBounds(340,330,200,50);
	yavrudugme2.setVisible(true);
	yavrudugme2.addActionListener(new Sirketkartlaridegirtireklesil());
	yavrudugme2.setActionCommand(evethayir.baskaydet.name());
		
	jt1= new JTextField();
	jt1.setBounds(130,50,420,26);
	jt1.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt1);
		
	jt2= new JTextField();
	jt2.setBounds(130,90,170,26);
	jt2.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt2);
	
	jt3= new JTextField();
	jt3.setBounds(130,130,170,26);
	jt3.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt3);
	
	jt10= new JTextField();
	jt10.setBounds(380,130,170,26);
	jt10.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt10);
	
	jt4= new JTextField();
	jt4.setBounds(130,170,170,26);
	jt4.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt4);
	
	jt5= new JTextField();
	jt5.setBounds(380,170,170,26);
	jt5.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt5);
	
	jt6= new JTextField();
	jt6.setBounds(130,210,170,26);
	jt6.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt6);
	
	jt7= new JTextField();
	jt7.setBounds(380,210,170,26);
	jt7.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt7);
	
	jt8= new JTextField();
	jt8.setBounds(380,90,170,26);
	jt8.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt8);
	
	jt9= new JTextField();
	jt9.setBounds(130,280,170,26);
	jt9.setHorizontalAlignment(SwingConstants.RIGHT);
	yavru.add(jt9);
	jt9.addKeyListener(new java.awt.event.KeyAdapter() {    // limit alanýna sadece double girme izni

        public void keyReleased(java.awt.event.KeyEvent evt) {
            try {
                @SuppressWarnings("unused")
				double number = Double.parseDouble(jt9.getText());
                
            } catch (Exception e) {
              jt9.setText("");
            }
        }
    });
	
		if (Sirketkartlari.duzenlebasildi==true | Sirketkartlari.silebasildi==true | Sirketkartlari.gosterbasildi==true ) {
	     	veriarama(Sirketkartlari.selectedData); 
		
	}
	
	lb1= new JLabel("Firma Ünvaný:");
	lb1.setHorizontalAlignment(SwingConstants.RIGHT);
	lb1.setBounds(20,50,100,20);
	yavru.add(lb1);
	
	lb2= new JLabel("Telefon:");
	lb2.setHorizontalAlignment(SwingConstants.RIGHT);
	lb2.setBounds(20,90,100,20);
	yavru.add(lb2);
	
	
	lb3= new JLabel("Adres:");
	lb3.setHorizontalAlignment(SwingConstants.RIGHT);
	lb3.setBounds(20,130,100,26);
	yavru.add(lb3);
	
	lb10= new JLabel("Kiþi          :");
	lb10.setHorizontalAlignment(SwingConstants.RIGHT);
	lb10.setBounds(270,130,100,26);
	yavru.add(lb10);
	
	lb4= new JLabel("Þehir:");
	lb4.setHorizontalAlignment(SwingConstants.RIGHT);
	lb4.setBounds(20,170,100,26);
	yavru.add(lb4);
	
	lb5= new JLabel("Ülke          :");
	lb5.setHorizontalAlignment(SwingConstants.RIGHT);
	lb5.setBounds(270,170,100,26);
	yavru.add(lb5);
		
	lb6= new JLabel("Vergi Dairesi:");
	lb6.setHorizontalAlignment(SwingConstants.RIGHT);
	lb6.setBounds(20,210,100,26);
	yavru.add(lb6);
	
	lb7= new JLabel("Vergi No :");
	lb7.setHorizontalAlignment(SwingConstants.RIGHT);
	lb7.setBounds(270,210,100,26);
	yavru.add(lb7);
	
	lb8= new JLabel("E-mail     :");
	lb8.setHorizontalAlignment(SwingConstants.RIGHT);
	lb8.setBounds(270,90,100,26);
	yavru.add(lb8);
		
	lb9= new JLabel("Borç Limiti:");
	lb9.setHorizontalAlignment(SwingConstants.RIGHT);
	lb9.setBounds(20,280,100,26);
	yavru.add(lb9);
	
	lb11= new JLabel("");                                // Bunu koymayýnca tablo kayýyor. Neden bilmiyorum. 
	lb11.setHorizontalAlignment(SwingConstants.RIGHT);
	lb11.setBounds(20,310,100,26);
	yavru.add(lb11);

	if (Sirketkartlari.gosterbasildi==true | Sirketkartlari.silebasildi==true) {
		baslik.setText(Sirketkartlari.secilifirmaadi.toUpperCase());
		
		if (Sirketkartlari.gosterbasildi==true) {
		yavrudugme2.setVisible(false);
		yavrudugme.setText("KAPAT");
		}
		
		jt1.setEditable(false);
		jt2.setEditable(false);
		jt3.setEditable(false);
		jt4.setEditable(false);
		jt5.setEditable(false);
		jt6.setEditable(false);
		jt7.setEditable(false);
		jt8.setEditable(false);
		jt9.setEditable(false);
	   jt10.setEditable(false);
		}
	
	    lb9.setVisible(false);jt9.setVisible(false);  // Bu alan firma seçiminde gerekli deðil
		yavru.setVisible(true); 
	}
	
@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand()==evethayir.basvazgec.name()) {
			yavru.dispose();
			
		}
		
		if (e.getActionCommand()==evethayir.baskaydet.name()) {
			
			if (Sirketkartlari.eklebasildi==true) {
				firmaveriekle();
			    }
			
			if (Sirketkartlari.duzenlebasildi==true) {
				firmaveridegistir(Sirketkartlari.selectedData);
			    }
			
			if (Sirketkartlari.silebasildi==true) {
				
				firmaverisil(Sirketkartlari.selectedData);
			    }
			return;
		}
	}

private void firmaverisil(String ekrankod) {
	Sileyimmi.sonkarar("sirketkart");
	if (Sileyimmi.cevap.equals("evet")) {
	
		try {     // cari kart silme   
     
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
        PreparedStatement st = connection.prepareStatement("DELETE FROM sirketkartlari WHERE sirketkart_0kod ='" +ekrankod + "';");
        st.executeUpdate(); 
  
	} catch(Exception e) {
        System.out.println(e);
    }
		Sirketkartlari.frame.dispose();
		Sirketkartlari.sirketkartlari(Sirketkartlari.kimsoruyor);
	    yavru.dispose();
	}
	yavru.dispose();
}

private void firmaveridegistir(String ekrankod) {
	
	ekranioku();
	
	if ((ekranj1.length()<5)) {
		baslik.setText("ÜNVAN EN AZ 5 KARAKTER OLMALIDIR");
		return;
	  }
	 Connection con = null;
     PreparedStatement ps = null;
     try {
        con = DriverManager.getConnection("jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass); 
        
        String query = "update sirketkartlari set sirketkart_1unvan=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj1);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
       
        query = "update sirketkartlari set sirketkart_2tel=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj2);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
                  
        query = "update sirketkartlari set sirketkart_3adres=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj3);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_4il=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj4);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_5ulke=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj5);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_6vd=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj6);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_7vn=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj7);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_8email=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj8);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_9limit=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj9);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        query = "update sirketkartlari set sirketkart_10kisi=? where sirketkart_0kod=? ";
        ps = con.prepareStatement(query);
        ps.setString(1, ekranj10);
        ps.setString(2, ekrankod);
        ps.executeUpdate();
        
        } catch (Exception e) {
           e.printStackTrace();
     }	
     
     Sirketkartlari.frame.dispose();
 	Sirketkartlari.sirketkartlari(Sirketkartlari.kimsoruyor);
     yavru.dispose();
}

private void firmaveriekle() {
	
	ekranioku();
	
	if ((ekranj1.length()<5)) {
		baslik.setText("ÜNVAN EN AZ 5 KARAKTER OLMALIDIR");
		return;
				
	}
	
	try{                                             
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);	
		Statement stmt=con.createStatement();  
		String emir1 = "INSERT INTO sirketkartlari";
		String emir2 ="(sirketkart_1unvan,sirketkart_2tel,sirketkart_3adres,sirketkart_4il,sirketkart_5ulke,sirketkart_6vd,sirketkart_7vn,sirketkart_8email,sirketkart_9limit,sirketkart_10kisi)";
		String emir3 =	"VALUES ('"+ekranj1+"','"+ekranj2+"','"+ekranj3+"','"+ekranj4+"','"+ekranj5+"','"+ekranj6+"','"+ekranj7+"','"+ekranj8+"','"+ekranj9+"','"+ekranj10+"')";
		stmt.executeUpdate(emir1+emir2+emir3);
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
	Sirketkartlari.frame.dispose();
	Sirketkartlari.sirketkartlari(Sirketkartlari.kimsoruyor);
    yavru.dispose();
 	 
}
private void ekranioku() {
	
	ekranj1=jt1.getText().trim();
	ekranj2=jt2.getText().trim();
	ekranj3=jt3.getText().trim();
	ekranj4=jt4.getText().trim();
	ekranj5=jt5.getText().trim();
	ekranj6=jt6.getText().trim();
	ekranj7=jt7.getText().trim();
	ekranj8=jt8.getText().trim();
	ekranj9=jt9.getText().trim();
	if (ekranj9.equals("")) {
		ekranj9="0";
	}
	ekranj10=jt10.getText().trim();
}

private static void veriarama(String ekrankod) {
	
	try{                                              
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection(
				"jdbc:mysql://"+Anasinif.sqlip+"/nal2000?useUnicode=true&characterEncoding=UTF-8",Anasinif.sqluser,Anasinif.sqlpass);
				
				Statement stmt=con.createStatement();  
		        ResultSet rs=stmt.executeQuery("SELECT * FROM sirketkartlari WHERE sirketkart_0kod =" +ekrankod + ";"); 
		
		while(rs.next()) {
			
			 jt1.setText( rs.getString(2));
			 jt2.setText( rs.getString(3));
			 jt3.setText( rs.getString(4));
			 jt4.setText( rs.getString(5));
			 jt5.setText( rs.getString(6));
			 jt6.setText( rs.getString(7));
			 jt7.setText( rs.getString(8));
			 jt8.setText( rs.getString(9));
			 jt9.setText( rs.getString(10));
			 jt10.setText(rs.getString(11));
	          
		}
		con.close();  
		
		}catch(Exception e){ System.out.println(e);}  
	
}
}