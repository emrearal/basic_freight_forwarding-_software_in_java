package heanalikibin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
	
public class Sileyimmi  {
	
	static JButton yavrudugme ;
	static JButton yavrudugme2 ;
	static JDialog yavru ;
	static JLabel baslik ;
	static String cevap="";
		
			public static void sonkarar(String nereden) {
				
				cevap="";
				
				yavrudugme= new JButton("Evet");
				yavrudugme2 = new JButton("�ptal");
				
				if (nereden.equals("sirketkart")) {
				yavru = new JDialog(Sirketkartlaridegirtireklesil.yavru,"EM�N M�S�N�Z ???",true); 
				}
				
				if (nereden.equals("firmakart")) {
					yavru = new JDialog(Sirketkartlaridegirtireklesil.yavru,"EM�N M�S�N�Z ???",true); 
					}
				
				if (nereden.equals("carihareket")) {
					yavru = new JDialog(Sirketkartlaridegirtireklesil.yavru,"EM�N M�S�N�Z ???",true); 
					}
				
				if (nereden.equals("ontanimli")) {
					yavru = new JDialog(Ontanimliveridegirtireklesil.yavru,"EM�N M�S�N�Z ???",true); 
					}
				
				if (nereden.equals("yuk")) {
					yavru = new JDialog(Yuk.frame,"EM�N M�S�N�Z ???",true); 
					}
				
				if (nereden.equals("pozgelirgider")) {
					yavru = new JDialog(Pozgelirgiderbakiyeler.frame,"EM�N M�S�N�Z ???",true); 
					}
				
				
				if (nereden.length()>16) {
			
				if (nereden.substring(0,16).equals("toplufaturasilme")) {
					yavru = new JDialog(Denizyoluanaekran.yavru,nereden.substring(16)+"adet kay�t silinecek EM�N M�S�N�Z ???",true); 
					}
				
				}
				
				yavru.setResizable(false);
					
				baslik = new JLabel("         Varsa �li�kili T�m Kay�tlar da Silinecektir" );
				baslik.setBounds(15,5,270,20);
				
				yavru.setSize(310,100); 
				yavru.setLayout(null);
				yavru.setLocation(800,50);
				yavru.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				yavru.add(yavrudugme);
				yavru.add(yavrudugme2);
				yavru.add(baslik);
				
				yavrudugme.setBounds(40,30,80,20);
				yavrudugme.setVisible(true);
				yavrudugme.addActionListener(new ActionListener() {     
		            public void actionPerformed(ActionEvent e) {
		            	cevap="evet";
					    yavru.dispose();
		            }
		        });
				
				yavrudugme2.setBounds(180,30,80,20);
				yavrudugme2.setVisible(true);
				yavrudugme2.addActionListener(new ActionListener() {     
		            public void actionPerformed(ActionEvent f) {
		            	cevap="hay�r";
					    yavru.dispose();
					    
		            }
		        });
				yavru.setVisible(true);
				
				
			}
}