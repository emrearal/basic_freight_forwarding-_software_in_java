package heanalikibin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Yukicindekikonteynerekleduzenlesil {
	static JDialog yavru;
	static JTextField sirajt,prefixjt,suffixjt,kapjt,kgjt;
	static JLabel siratxt,prefixtxt,suffixtxt,kaptxt,kgtxt;
	static JButton tamamdugme;
	@SuppressWarnings("rawtypes")
	static JComboBox kontlist;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void konteynerekleduzenlesil(String neislemi) {
		
		yavru=new JDialog(Yuk.frame,"Konteyner "+neislemi+" Ýþlemi",true);
		yavru.setBounds(250,250,445,150);
		yavru.setLayout(null);
		
		sirajt=new JTextField();
		sirajt.setEditable(false);
		sirajt.setBounds(20, 30, 30, 26);
		yavru.add(sirajt);
		
		prefixjt=new JTextField();
		prefixjt.setBounds(60, 30, 50, 26);
		yavru.add(prefixjt);
		prefixjt.addKeyListener(new java.awt.event.KeyAdapter() {    // prefix alanýna sadece int 4 harf girme izni
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            String a=prefixjt.getText().toUpperCase();
			            prefixjt.setText(a);
		    	   		int uzunluk = prefixjt.getText().length();
			            if (uzunluk>4) {
			            	prefixjt.setText("");
			            }
			        }
			    });
	
		suffixjt=new JTextField();
		suffixjt.setBounds(130, 30, 60, 26);
		yavru.add(suffixjt);
		suffixjt.addKeyListener(new java.awt.event.KeyAdapter() {    // SUFFÝX alanýna sadece int max999999 girme izni
		       public void keyReleased(java.awt.event.KeyEvent evt) {
			            try {
			   			
							int miktar = Integer.parseInt(suffixjt.getText());
							if (miktar>999999) {
								suffixjt.setText("");
							}
				            } catch (Exception e) {
			               suffixjt.setText("");
			            }
			        }
			    });
					
		kapjt=new JTextField();
		kapjt.setBounds(200, 30, 50, 26);
		yavru.add(kapjt);
		kapjt.addKeyListener(new java.awt.event.KeyAdapter() {    // kap alanýna sadece int max999999 girme izni
       public void keyReleased(java.awt.event.KeyEvent evt) {
	            try {
	   			
					int miktar = Integer.parseInt(kapjt.getText());
					if (miktar>999999) {
						kapjt.setText("");
					}
		            } catch (Exception e) {
	               kapjt.setText("");
	            }
	        }
	    });
			
		kgjt=new JTextField();
		kgjt.setBounds(270, 30, 50, 26);
		yavru.add(kgjt);
		kgjt.addKeyListener(new java.awt.event.KeyAdapter() {    // kg alanýna sadece int max99999 (5 basamak) girme izni

	        public void keyReleased(java.awt.event.KeyEvent evt) {
	            try {
	         	  
					int miktar = Integer.parseInt(kgjt.getText());
					if (miktar>99999) {
						kgjt.setText("");
					}
	             } catch (Exception e) {
	              kgjt.setText("");
	             }
	        }
	    });
		kontlist = new JComboBox(Anasinif.konteynertipleri);
		kontlist.setBounds(340, 30, 70, 26);
		kontlist.setSelectedIndex(0);
		yavru.add(kontlist);
	
		if (neislemi.equals("SÝLME") | neislemi.equals("DÜZENLEME")) {
		
			if (neislemi.equals("SÝLME")) {
				prefixjt.setEditable(false);
				suffixjt.setEditable(false);
				kapjt.setEditable(false);
				kgjt.setEditable(false);
				kontlist.setEnabled(false);				
			}
			
			int goster= Integer.parseInt(Yuk.secilenkonteyner)-1;
    		sirajt.setText(Yuk.tablodata[goster][0]);
    		prefixjt.setText(Yuk.tablodata[goster][1].substring(0, 4));
    		suffixjt.setText(Yuk.tablodata[goster][1].substring(4));
    		kapjt.setText(Yuk.tablodata[goster][2]);
    		kgjt.setText(Yuk.tablodata[goster][3]);
    		kontlist.setSelectedIndex(Integer.parseInt(Yuk.tablodata[goster][4]));
    		
  		}
		
		siratxt=new JLabel("#");
		siratxt.setBounds(25,10,26,26);
		yavru.add(siratxt);
		
		prefixtxt=new JLabel("Prefix");
		prefixtxt.setBounds(65,10,50,26);
		yavru.add(prefixtxt);
		
		suffixtxt=new JLabel("Suffix");
		suffixtxt.setBounds(135,10,50,26);
		yavru.add(suffixtxt);
		
		kaptxt=new JLabel("   Kap");
		kaptxt.setBounds(205,10,50,26);
		yavru.add(kaptxt);
		
		kgtxt=new JLabel("    KG");
		kgtxt.setBounds(275,10,50,26);
		yavru.add(kgtxt);
		
		tamamdugme=new JButton("Tamam");
		tamamdugme.setBounds(150,65,120,30);
		yavru.add(tamamdugme);
		tamamdugme.addActionListener(new ActionListener() {     
            public void actionPerformed(ActionEvent e) {
  
            	if (neislemi.equals("EKLEME")) {
            		kontrekleme();
            		return;
            	}
            	if (neislemi.equals("SÝLME")) {
            		kontrsilme();
            		return;
            	}
            	
            	if (neislemi.equals("DÜZENLEME")) {
            		
            		kontrduzenleme();
            		return;
            	}
            }
       });
		
		if (Yuk.parsiyel.isSelected()) {
			prefixjt.setEditable(false);
			suffixjt.setEditable(false);
			kontlist.setEnabled(false);
		}
	
		yavru.setVisible(true);
	}
      private static void kontrekleme() {
    	
    	if (prefixjt.getText().length()!=4 | suffixjt.getText().length()!=6 | 
    			kapjt.getText().length()<1 | kgjt.getText().length()<1 ) {
    		Bilgipenceresi.anons("Prefix 4 karakter, Suffix 6 hane, Kap ve Kg ise en az bir hane olmalýdýr");
    		return;
    	}
    	  
    	int siradakikont=Yuk.tablodata.length+1;
    	
    	if (siradakikont>99) { // sql satýr sýnýrý. bir yüke en fazla 99 konteyner kaydedilebilir
    		return;
    	}
    	
    	String[][] yenidizin=new String [siradakikont][5];
    	
    	System.arraycopy(Yuk.tablodata, 0, yenidizin, 0, siradakikont-1);
    	
    	String siradakikonttxt=String.valueOf(siradakikont);
    	if (siradakikont<10) {   // 2 basamaða tamamlama
    		siradakikonttxt="0"+siradakikonttxt;
    		sirajt.setText(siradakikonttxt);
    	}
    	
    	yenidizin[siradakikont-1][0]=siradakikonttxt;
    	yenidizin[siradakikont-1][1]=prefixjt.getText()+suffixjt.getText();
    	
    	String kap= kapjt.getText(); 
    	int z=6-kap.length();
        	
    	if (kap.length()<6) {  // 6 basamaða tamamlama
    		for (int i=0; i<z; i++) {
    			
    			kap=" "+kap;
    		}
    		kapjt.setText(kap);
    	}
    	yenidizin[siradakikont-1][2]=kapjt.getText();
    	
    	String kg= kgjt.getText();  // 5 basamaða tamamlama
    	int t=5-kg.length();
    	if (kg.length()<5) {
    		for (int p=0; p<t; p++) {
    			
    			kg=" "+kg;
    		}
    		kgjt.setText(kg);
    	}
    	
    	yenidizin[siradakikont-1][3]=kgjt.getText();
    	yenidizin[siradakikont-1][4]=String.valueOf(kontlist.getSelectedIndex());
    	Yuk.tablodata=new String [siradakikont][5];
    	System.arraycopy(yenidizin, 0, Yuk.tablodata, 0, siradakikont);
    	Yuk.Konteynerlistesitablosu.gostermeliktablodata=new String [siradakikont][5];
    	Yuk.Konteynerlistesitablosu.gostermeligidegistir() ;
    	yavru.dispose();
      }
      
      private static void kontrsilme() {
       int verisayisi=Yuk.tablodata.length;
       
       int c=0;
       String[][] yenidizin=new String [verisayisi-1][5];
    	  
    	  for(int i=0; i<verisayisi ;i++) {
    		  
    		  if(i!=Integer.parseInt(Yuk.secilenkonteyner)-1) {
    		  
    			  String siradakikonttxt=String.valueOf(i+1-c);
    		    	if ((i+1)<10) {   // 2 basamaða tamamlama
    		    		siradakikonttxt="0"+siradakikonttxt;
    		    		
    		    	}    			  
    			 yenidizin[i-c][0]=String.valueOf(siradakikonttxt);
    			 yenidizin[i-c][1]=Yuk.tablodata[i][1];
    			 yenidizin[i-c][2]=Yuk.tablodata[i][2];
    			 yenidizin[i-c][3]=Yuk.tablodata[i][3];
    			 yenidizin[i-c][4]=Yuk.tablodata[i][4];
    			
    	  } else {
    		  c=1;
    	  }
   		
		}
    	  Yuk.tablodata=new String [verisayisi-1][5];
    	  Yuk.Konteynerlistesitablosu.gostermeliktablodata=new String [verisayisi-1][5];
          System.arraycopy(yenidizin, 0,Yuk.tablodata , 0, verisayisi-1);  
        
          Yuk.Konteynerlistesitablosu.gostermeligidegistir() ;  	
          
    	  yavru.dispose();
}
      
      private static void kontrduzenleme() {
    	  
    	  if (prefixjt.getText().length()!=4 | suffixjt.getText().length()!=6 
    		 |	kapjt.getText().length()<1 | kgjt.getText().length()<1 ) {
    		  Bilgipenceresi.anons("Prefix 4 karakter, Suffix 6 hane, Kap ve Kg ise en az bir hane olmalýdýr");
      	return;
      	}
   
    	  int secim= Integer.parseInt(Yuk.secilenkonteyner)-1;
    	  
    	  Yuk.tablodata[secim][1]=prefixjt.getText()+suffixjt.getText();
    	  
    	  String kap= kapjt.getText(); 
      	int z=6-kap.length();
       	if (kap.length()<6) {  // 6 basamaða tamamlama
      		for (int i=0; i<z; i++) {
      			
      			kap=" "+kap;
      		}
      		kapjt.setText(kap);
       	}
    	  Yuk.tablodata[secim][2]=kapjt.getText();
    	  
    	  String kg= kgjt.getText();  // 5 basamaða tamamlama
    	  
    	int t=5-kg.length();
     	if (kg.length()<5) {
      		for (int p=0; p<t; p++) {
      			kg=" "+kg;
     		}
     		kgjt.setText(kg);
    	}
    	  Yuk.tablodata[secim][3]=kgjt.getText();
    	  Yuk.tablodata[secim][4]=String.valueOf(kontlist.getSelectedIndex());
    	  
    	  Yuk.Konteynerlistesitablosu.gostermeligidegistir() ; 
    	  
    	  yavru.dispose();
      }
     
}